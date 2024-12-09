
module.exports = {
  presets: [
    '@babel/preset-react',
    [
      '@babel/preset-env',
      {
        // see https://help.sonatype.com/repomanager3/system-requirements#SystemRequirements-WebBrowser
        targets: 'last 1 Chrome version, last 1 Firefox version, Firefox ESR, last 1 Safari version, ie >= 11, last 1 Edge version'
      }
    ]
  ],
  plugins: [
    '@babel/plugin-proposal-optional-chaining',
    '@babel/plugin-proposal-export-default-from'
  ]
};
