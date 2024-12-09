/*global Ext*/

Ext.define('NX.model.dev.Condition', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'id', type: 'string' },
    { name: 'condition', defaultValue: undefined },
    { name: 'satisfied', type: 'boolean' }
  ]
});
