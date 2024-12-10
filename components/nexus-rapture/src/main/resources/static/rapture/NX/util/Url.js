/*global Ext, NX*/

/**
 * URL related utils.
 *
 * @since 3.0
 */
Ext.define('NX.util.Url', {
  singleton: true,
  requires: [
    'Ext.String'
  ],

  baseUrl: NX.app.baseUrl,

  urlSuffix: NX.app.urlSuffix,

  /**
   * @public
   */
  urlOf: function (path) {
    var baseUrl = this.baseUrl;

    if (!Ext.isEmpty(path)) {
      if (Ext.String.endsWith(baseUrl, '/')) {
        baseUrl = baseUrl.substring(0, baseUrl.length - 1);
      }
      if (!Ext.String.startsWith(path, '/')) {
        path = '/' + path;
      }
      return baseUrl + path;
    }
    return this.baseUrl;
  },

  licenseUrl: function () {
    var edition = NX.State.getEdition();
    if ('EVAL' === edition || 'OSS' === edition) {
      return NX.util.Url.urlOf('/OSS-LICENSE.html')
    } else {
      return NX.util.Url.urlOf('/PRO-LICENSE.html')
    }
  },

  /**
   * Creates a link.
   *
   * @public
   * @param {String} url to link to
   * @param {String} [text] link text. If omitted, defaults to url value.
   * @param {String} [target] link target. If omitted, defaults to '_blank'
   * @param {String} [id] link id
   */
  asLink: function (url, text, target, id) {
    target = target || '_blank';
    if (Ext.isEmpty(text)) {
      text = url;
    }
    if (id) {
      id = ' id="' + id + '"';
    } else {
      id = '';
    }
    return '<a href="' + url + '" target="' + target + '"' + id + ' rel="noopener">' + Ext.htmlEncode(text) + '</a>';
  },

  /**
   * Allows text to be easily copy/pasted.
   *
   * @public
   * @param {String} value to copy
   */
  asCopyWidget: function (value) {
    return '<button onclick="Ext.widget(\'nx-copywindow\', { copyText: \'' + value + '\' });" title="' + value + '"><i class="fa fa-clipboard"></i> 复制</button>';
  },

  /**
   * Helper to append cache busting suffix to given url.
   *
   * @param {string} url
   * @returns {string}
   */
  cacheBustingUrl: function(url) {
    return url + '?' + this.urlSuffix;
  }
});
