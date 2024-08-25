import { useState } from "react"


function MyComponent() {
    const [state, setState] = useState<string>("")
    setTimeout( () => {
        setState("fetched")
    }, 1000)

    return (
        state === "" ? <div>waiting response</div>:<div>got it: {state}</div>
    )
}


async function fetchData(): Promise<string> {
    return "got"
}

export default MyComponent