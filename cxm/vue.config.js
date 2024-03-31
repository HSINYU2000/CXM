const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // devServer: {
  //   // host: 'localhost',
  //   // port: 8999,
  //   proxy: {
  //     '/api': {
  //       target: "http://localhost:8777",
  //       chargeOrigin: true,
  //       securs: "fasle",
  //       pathRewrite: {
  //         '^/api': ''
  //       }
  //     }
  //   }
  // },

})
