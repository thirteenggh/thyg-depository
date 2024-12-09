/*global Ext, NX*/

/**
 * URL related utils.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.util.RepositoryUrls', {
  singleton: true,
  requires: [
    'NX.util.Url',
    'NX.Assert'
  ],

  mixins: {
    logAware: 'NX.LogAware'
  },

  /**
   * Strategies for building urls to download assets.
   *
   * @private
   */
  repositoryUrlStrategies: {
    maven2: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    apt: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    cocoapods: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    conan: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    conda: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    npm: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    nuget: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    r: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    raw: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    rubygems: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    docker: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    bower: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    pypi: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    yum: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    gitlfs: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    go: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    helm: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    },
    p2: function (me, assetModel) {
      var repositoryName = assetModel.get('repositoryName'),
          assetName = me.getAssetName(assetModel);
      return NX.util.Url.asLink(NX.util.Url.baseUrl + '/repository/' + encodeURIComponent(repositoryName) + '/' + encodeURI(assetName), assetName);
    }
  },

  /**
   * Get the asset name without beginning '/'.
   *
   * @private
   * @param {Object} assetModel the asset to fetch its name.
   * @return the asset name.
   */
  getAssetName: function(assetModel) {
    var assetName = assetModel.get('name');
    return assetName.startsWith('/') ? assetName.substring(1) : assetName;
  },

  /**
   * Add a strategy to build repository download links for a particular strategy.
   *
   * @public
   */
  addRepositoryUrlStrategy: function (format, strategy) {
    this.repositoryUrlStrategies[format] = strategy;
  },

  /**
   * Creates a link to an asset in a repository.
   *
   * @public
   * @param {Object} assetModel the asset to create a link for
   * @param {String} format the format of the repository storing this asset
   */
  asRepositoryLink: function (assetModel, format) {
    //<if assert>
    NX.Assert.assert(assetModel, 'Expected an assetModel with format: ' + format);
    //</if>
    //<if debug>
    this.logTrace('Creating link for format and asset:', format, assetModel.get('name'));
    //</if>

    var linkStrategy = this.repositoryUrlStrategies[format];
    return linkStrategy(this, assetModel);
  }

});
