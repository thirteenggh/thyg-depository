/*global Ext, NX*/

/**
 * An URL **{@link Ext.form.field.Text}**.
 *
 * @since 3.0
 */
Ext.define('NX.ext.form.field.Url', {
  extend: 'Ext.form.field.Text',
  alias: 'widget.nx-url',
  requires: [
      'NX.util.Validator'
  ],
  vtype: 'nx-url',

  useTrustStore: function (field) {
    if (Ext.String.startsWith(field.getValue(), 'https://')) {
      return {
        name: 'useTrustStoreFor' + Ext.String.capitalize(field.getName()),
        url: field
      };
    }
    return undefined;
  }

});
