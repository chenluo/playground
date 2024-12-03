const fs = require('fs');

// const jsdomPatch = {
//   name: 'jsdom-patch',
//   setup(build) {
//     build.onLoad({ filter: /XMLHttpRequest-impl\.js$/ }, async (args) => {
//       let contents = await fs.promises.readFile(args.path, 'utf8');
//       contents = contents.replace(
//         'const syncWorkerFile = require.resolve ? require.resolve("./xhr-sync-worker.js") : null;',
//         `const syncWorkerFile = "${require.resolve(
//           'jsdom/lib/jsdom/living/xhr/xhr-sync-worker.js',
//         )}";`.replaceAll('\\', process.platform === 'win32' ? '\\\\' : '\\'),
//       );
//       return { contents, loader: 'js' };
//     });
//   },
// };

// from: https://github.com/evanw/esbuild/issues/1685#issuecomment-944928069
const plugin = {
  name: 'excludeVendorFromSourceMap',
  setup(build) {
    build.onLoad({ filter: /node_modules/ }, (args) => {
      if (args.path.endsWith('.js')) {
        return {
          contents:
            fs.readFileSync(args.path, 'utf8') +
            '\n//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIiJdLCJtYXBwaW5ncyI6IkEifQ==',
          loader: 'default',
        };
      }
    });
  },
};

module.exports = [plugin];
