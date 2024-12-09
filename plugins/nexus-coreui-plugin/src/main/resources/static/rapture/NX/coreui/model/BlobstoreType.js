/*global Ext, NX*/

/**
 * Blobstore type model.
 *
 * @since 3.6
 */
Ext.define('NX.coreui.model.BlobstoreType', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'string', sortType: 'asUCText'},
    {name: 'name', type: 'string', sortType: 'asUCText'},
    {name: 'formFields', type: 'auto' /*object*/},
    {name: 'customFormName', type: 'string', sortType: 'asUCText'},
    {name: 'isModifiable', type: 'boolean'},
    {name: 'isEnabled', type: 'boolean'}
  ]
});
