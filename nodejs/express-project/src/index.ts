import { EventEmitter } from "events";
import FlexSearchIndex from "./index-flexsearch";
import FlexSearchIndexByDocument from "./index-flexsearch-document";
import LmdbIndex from "./index-lmdb";
import { time } from "console";

const idx = new LmdbIndex
const emitter = new EventEmitter()
let start: Date
let end: Date
emitter.on('SearchStart', () => {
    console.log('search started')
    start = new Date
})
emitter.on('SearchComplete', () => {
    console.log('search complete')
    end = new Date
    console.log(`start at: ${start.toISOString()}`)
    console.log(`end at: ${end.toISOString()}`)
    console.log(`duration: ${end.getTime() - start.getTime()} ms`)
})
idx.importIndex().then((v) => {
    emitter.emit('SearchStart')
    const result = idx.search('keyboard')
    emitter.emit('SearchComplete')
    console.log('hit ' + result.length + ' docs')
}).then(() => {
    emitter.emit('SearchStart')
    const result = idx.search('keyboard', 1, 1000)
    emitter.emit('SearchComplete')
    console.log('hit ' + result.length + ' docs')
}).then(() => {
    emitter.emit('SearchStart')
    const result = idx.search('keyboard', 1, 10000)
    emitter.emit('SearchComplete')
    console.log('hit ' + result.length + ' docs')
}).then(() => {
    emitter.emit('SearchStart')
    const result = idx.search('keyboard', 1, 100000)
    emitter.emit('SearchComplete')
    console.log('hit ' + result.length + ' docs')
}).then(() => {
    emitter.emit('SearchStart')
    const result = idx.search('keyboard', 1, 1000000)
    emitter.emit('SearchComplete')
    console.log('hit ' + result.length + ' docs')
})
    .then(() => idx.close())