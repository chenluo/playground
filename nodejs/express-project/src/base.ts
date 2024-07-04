import fs from "node:fs"
import readline from "node:readline"
import events from "node:events"
export interface IndexBase {
    buildIndex(): Promise<void>
    search(keyword:string):Array<{}>
}

export async function readData(): Promise<Array<string>> {
    const result:string[] = []

    const stream = fs.createReadStream('./enwiki-20240701-abstract1.xml')
    const rl = readline.createInterface(stream)
    let entry = ''
    rl.on('line', (data) => {
        if (data == '<doc>') {
            entry+=data
            return
        }
        if (data == '</doc>') {
            result.push(entry)
            entry=''
            return
        }
        entry+=data
    })
    rl.on('close', () => {console.log('read lines end')})

    
    await events.once(rl, 'close')

    return result
}

export default IndexBase
