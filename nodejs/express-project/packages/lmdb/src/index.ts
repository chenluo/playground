import { EventEmitter } from "events";
import LmdbIndex from "./search/index-lmdb";
import cluster from "cluster"
import { exit } from "process";

if (cluster.isPrimary) {
    console.log(`primary thread enter`)
    for (let i = 0; i < 6; i++) {
        cluster.fork()
    }
    cluster.on(`exit`, (worker, code, signal) => {
        console.log(`worker ${worker.process.pid} exited`)
    })
} else {
    const workerStart = new Date
    let run = 0;
    const pms = []
    while (run < 10) {
        pms.push(runSearch());
        run++;
    }
    console.log(`[${process.pid}] run ${run} times`)
    Promise.all(pms).then(() => exit())
}

async function runSearch(): Promise<void> {
    const idx = new LmdbIndex;
    const emitter = new EventEmitter();
    let start: Date;
    let end: Date;
    emitter.on('SearchStart', () => {
        console.log('search started');
        start = new Date;
    });
    emitter.on('SearchComplete', () => {
        console.log('search complete');
        end = new Date;
        console.log(`[${process.pid}] start at: ${start.toISOString()}`);
        console.log(`[${process.pid}] end at: ${end.toISOString()}`);
        console.log(`[${process.pid}] duration: ${end.getTime() - start.getTime()} ms`);
    });
    const pms = idx.importIndex().then((v) => {
        emitter.emit('SearchStart');
        const result = idx.search('keyboard');
        emitter.emit('SearchComplete');
        console.log('hit ' + result.length + ' docs');
    })
        // .then(() => {
        //     emitter.emit('SearchStart')
        //     const result = idx.search('keyboard', 1, 1000)
        //     emitter.emit('SearchComplete')
        //     console.log('hit ' + result.length + ' docs')
        // }).then(() => {
        //     emitter.emit('SearchStart')
        //     const result = idx.search('keyboard', 1, 10000)
        //     emitter.emit('SearchComplete')
        //     console.log('hit ' + result.length + ' docs')
        // }).then(() => {
        //     emitter.emit('SearchStart')
        //     const result = idx.search('keyboard', 1, 100000)
        //     emitter.emit('SearchComplete')
        //     console.log('hit ' + result.length + ' docs')
        // }).then(() => {
        //     emitter.emit('SearchStart')
        //     const result = idx.search('keyboard', 1, 1000000)
        //     emitter.emit('SearchComplete')
        //     console.log('hit ' + result.length + ' docs')
        // })
        .then(() => idx.close())
    return pms;
}
