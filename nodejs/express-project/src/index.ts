import FlexSearchIndex from "./index-flexsearch";

const flexsearchIndex = new FlexSearchIndex
flexsearchIndex.buildIndex().then((v) => {
    const result = flexsearchIndex.search('a')
    console.log(result)
})