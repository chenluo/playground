import { Document, Index, Worker } from "flexsearch"
import IndexBase, { readData, readLines, writeData } from "./base"

class FlexSearchIndexByDocument implements IndexBase {
    private doc;
    constructor() {
        this.doc = new Document({ document: { id: 'id', index: ['content'] } });
    }
    close(): void {
    }
    async importIndex(): Promise<void> {
        let length
        await readLines("./flexsearch-doc.dat").then(async (lines) => {
            length = lines.length
            for (let line of lines) {
                const [k, v] = line.split('#', 2)
                await this.doc.import(k, JSON.parse(v))
            }
            console.log('imported' + length + ' docs')
        })
            .catch(async (err) => {
                console.error(err)
                // fallback to read original data file
                let docs = await readData()
                const reg = /<[^>]*>/g
                docs.forEach((v, i) => {
                    this.doc.add({ id: i.toString(), content: v.replace(reg, '') })
                })
                console.log('indexed ' + docs.length + ' docs')
                await this.exportIndex()
            })
    }
    async exportIndex(): Promise<void> {
        await this.doc.export((k, v) => {
            writeData("./flexsearch-doc.dat", k.toString(), JSON.stringify(v))
        }).then(() => {
            console.log('export finished')
        })
    }
    search(keyword: string): Array<{}> {
        const hits = this.doc.search(keyword)
        return hits
    }

}
export default FlexSearchIndexByDocument
