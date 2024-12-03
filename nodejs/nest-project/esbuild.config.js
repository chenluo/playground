const fs = require('fs');
try {
  fs.rmSync('.esbuild', { recursive: true, force: true });
} catch (e) {
  // remove build directory before build.
}
let isDevelop = process.env.IS_OFFLINE === 'true';
console.log(`isDevelop stage: ${isDevelop}`);
module.exports = (serverless) => ({
  external: ["class-transformer", "class-validator", "@nestjs/websockets", "@nestjs/microservices"],
  // plugins: 'esbuild-plugin.js',
  // exclude: ['@aws-sdk/*'],
  watch: {
    pattern: ['src/**/*.ts'],
    ignore: ['.serverless/**/*', '.build', '.esbuild', 'node_modules', 'dist'],
  },
  sourcemap: isDevelop,
});