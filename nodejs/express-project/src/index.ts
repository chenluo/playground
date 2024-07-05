import FlexSearchIndex from "./index-flexsearch";
import FlexSearchIndexByDocument from "./index-flexsearch-document";
import LmdbIndex from "./index-lmdb";

const idx = new LmdbIndex 
idx.importIndex().then((v) => {
    const result = idx.search('keyboard')
    console.log('hit ' + result.length + ' docs')
}).then(() => idx.close())