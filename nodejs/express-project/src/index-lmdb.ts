import { open } from "lmdb"
import IndexBase, { readData, readLines, writeData } from "./base"

class LmdbIndex implements IndexBase {
    private idx;
    constructor() {
        this.idx = open({ path: './lmdb-data' })
    }
    async importIndex(): Promise<void> {
        let length
        if (this.idx.getKeysCount() == 0) {
            let docs = await readData()
            const reg = /<[^>]*>/g
            const pms = new Array<Promise<boolean>>

            let i = 0;
            const chunkSize = 100
            for (let i = 0; i < docs.length; i += chunkSize) {
                const slice = docs.slice(i, Math.min(docs.length, i+chunkSize))

                this.idx.transactionSync(() => {
                    let j = 0;
                    for (let v of slice) {
                        this.idx.putSync(i + j, v)
                        j++
                    }
                })
                if (i % 1_000 == 0) {
                    console.log('progress: ' + i + '/' + docs.length)
                }
            }
            console.log('indexed ' + docs.length + ' docs')
        }
        console.log('loaded ' + this.idx.getKeysCount() + ' lmdb entries')
    }
    async exportIndex(): Promise<void> {

    }
    search(keyword: string, start?:number, end?:number): Array<{}> {
        const hits: any[] = []
        for (let { key, value } of this.idx.getRange({start, end})) {
            if (isMatch(value, keyword)) {
                hits.push(value)
            }
        }
        return hits
    }
    close() {
        this.idx.close()
    }
}
function isMatch(value: string, keyword: string): boolean {
    if (value.toLocaleLowerCase().indexOf(keyword.toLocaleLowerCase()) != -1) {
        return true;
    }
    return false;
}

export default LmdbIndex


