const merge = require('webpack-merge');
const path = require('path');

const common = require('./webpack.common');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'source-map',

  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, 'target', 'classes', 'static')
  }
});
