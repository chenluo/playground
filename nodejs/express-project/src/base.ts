import fs, { PathLike } from "node:fs"
import readline from "node:readline"
import events from "node:events"
export interface IndexBase {
    importIndex(): Promise<void>
    exportIndex(): Promise<void>
    search(keyword: string): Array<{}>
    close(): void
}

export async function readData(): Promise<Array<string>> {
    const result: string[] = []

    const stream = fs.createReadStream('./enwiki-20240701-abstract1.xml')
    const rl = readline.createInterface(stream)
    let entry = ''
    rl.on('line', (data) => {
        if (data == '<doc>') {
            entry += data
            return
        }
        if (data == '</doc>') {
            result.push(entry)
            entry = ''
            return
        }
        entry += data
    })
    rl.on('close', () => { console.log('read lines end') })
    await events.once(rl, 'close')
    return result
}

export async function readLines(fn: PathLike): Promise<Array<string>> {
    const stream = fs.createReadStream(fn)
    const rl = readline.createInterface(stream)
    let result: string[] = []
    rl.on('line', (data) => {
        result.push(data)
    })
    rl.on('close', () => { console.log('read lines end') })

    await events.once(rl, 'close')

    return result
}

export async function writeData(fn: PathLike, k: string, v: string) {
    if (!fs.existsSync(fn)) {
        fs.writeFileSync(fn, '', { flag: 'wx' })
    }
    fs.appendFileSync(fn, k + '#' + v + '\n')
}

export default IndexBase
