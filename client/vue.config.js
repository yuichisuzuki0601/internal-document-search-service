module.exports = {
  configureWebpack: {
    devtool: 'source-map'
  },
  transpileDependencies: [
    'vuetify'
  ],
  outputDir: '../src/main/resources/static',
  pages: {
    index: {
      entry: 'src/main.js',
      title: '社内文書検索サービス',
    }
  }
}
