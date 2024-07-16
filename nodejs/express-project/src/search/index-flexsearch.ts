import { randomUUID } from "crypto"
import { Document, Index, Worker } from "flexsearch"
import IndexBase, { readData, readLines, writeData } from "./base"

class FlexSearchIndex implements IndexBase {
    private idx;
    constructor() {
        this.idx = new Index({ tokenize: 'full' });
    }
    close(): void {
    }
    async importIndex(): Promise<void> {
        let length
        await readLines("./flexsearch.dat").then((lines) => {
            length = lines.length
            lines.forEach(async (line) => {
                const [k, v] = line.split('#', 2)
                await this.idx.import(k, v)
            })
            console.log('imported' + length + ' docs')
        })
            .catch(async (err) => {
                console.error(err)
                // fallback to read original data file
                let docs = await readData()
                const reg = /<[^>]*>/g
                let promises: Array<Promise<Index>> = new Array()
                docs.forEach((v, i) => {
                    promises.push(this.idx.addAsync(i, v.replace(reg, '')))
                })
                await Promise.all(promises).then((v) => {
                    console.log("all finished")
                })
                console.log('indexed ' + docs.length + ' docs')
                await this.exportIndex()
            })
    }
    async exportIndex(): Promise<void> {
        await this.idx.export((k, v) => {
            writeData("./flexsearch.dat", k.toString(), v)
        })
    }
    search(keyword: string): Array<{}> {
        const hits = this.idx.search(keyword)
        return hits
    }

}
export default FlexSearchIndex
