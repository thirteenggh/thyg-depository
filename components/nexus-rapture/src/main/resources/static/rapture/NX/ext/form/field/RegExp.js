/*global Ext*/

/**
 * An regular expression **{@link Ext.form.field.Text}**.
 *
 * @since 3.0
 */
Ext.define('NX.ext.form.field.RegExp', {
  extend: 'Ext.form.field.Text',
  alias: 'widget.nx-regexp',

  validator: function (value) {
    try {
      new RegExp(value);
    }
    catch (err) {
      return err.message;
    }
    return true;
  }

});
