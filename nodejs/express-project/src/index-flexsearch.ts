import { randomUUID } from "crypto"
import { Document, Index, Worker } from "flexsearch"
import IndexBase, { readData } from "./base"

class FlexSearchIndex implements IndexBase {
    private idx;
    private docs:Array<string> = new Array<string>();
    constructor() {
        this.idx = new Index({ tokenize: 'full'});

    }
    async buildIndex(): Promise<void> {
        this.docs = await readData()
        const reg = /<[^>]*>/g
        let promises:Array<Promise<Index>> = new Array()
        this.docs.slice(0, 10).forEach((v, i) => {
            promises.push(this.idx.addAsync(i, v.replace(reg, '')))
        })
        await Promise.all(promises).then((v) => {
            console.log("all finished")
        })
        console.log('indexed ' + this.docs.length + ' docs')
    }
    search(keyword:string): Array<{}> {
        const hits = this.idx.search(keyword)
        console.log(hits.toString())
        return new Array<{}>()
    }

}
export default FlexSearchIndex
