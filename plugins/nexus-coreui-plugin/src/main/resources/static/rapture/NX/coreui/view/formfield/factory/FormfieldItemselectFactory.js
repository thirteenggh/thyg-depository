/*global Ext, NX*/

/**
 * 'itemselect' factory.
 *
 * @since 3.1
 */
Ext.define('NX.coreui.view.formfield.factory.FormfieldItemselectFactory', {
  singleton: true,
  alias: [
    'nx.formfield.factory.itemselect'
  ],
  requires: [
    'Ext.data.Store',
    'Ext.data.SortTypes',
    'NX.ext.form.field.ItemSelector'
  ],
  mixins: {
    logAware: 'NX.LogAware'
  },

  /**
   * Create control.
   */
  create: function (formField, disableSort) {
    var filters,
        attributes = formField['attributes'] || {},
        idMapping = formField['idMapping'] || 'id',
        nameMapping = formField['nameMapping'] || 'name',
        itemConfig = {
          xtype: 'nx-itemselector',
          fieldLabel: formField.label,
          helpText: formField.helpText,
          name: formField.id,
          valueField: idMapping,
          displayField: nameMapping,
          width:600,

          itemCls: formField.required ? 'required-field' : '',
          allowBlank: !formField.required,
          delimiter: ',',

          // initialValue is null, but expected to be undefined for not set
          value: formField.initialValue ? formField.initialValue : undefined,

          listeners: {
            afterrender: function() {
              var value = this.getValue();
              if (formField.required && value === undefined || (Ext.isArray(value) && value.length === 0)) {
                // HACK: default blank behavior is not properly rendering required field error, forcing error to display
                this.markInvalid(this.blankText);
              }
            }
          }
        };

    if (attributes['buttons']) {
      itemConfig.buttons = attributes['buttons'];
    }
    if (attributes['fromTitle']) {
      itemConfig.fromTitle = attributes['fromTitle'];
    }
    if (attributes['toTitle']) {
      itemConfig.toTitle = attributes['toTitle'];
    }
    if (attributes['valueAsString']) {
      itemConfig.valueAsString = attributes['valueAsString'];
    }

    if (formField['storeApi']) {
      if (formField['storeFilters']) {
        filters = [];
        Ext.Object.each(formField['storeFilters'], function (key, value) {
          filters.push({ property: key, value: value });
        });
      }

      var args = {
        proxy: {
          type: 'direct',
          api: {
            read: 'NX.direct.' + formField['storeApi']
          },
          reader: {
            type: 'json',
            rootProperty: 'data',
            idProperty: idMapping,
            successProperty: 'success'
          }
        },

        fields: [
          { name: 'id', mapping: idMapping },
          { name: 'name', mapping: nameMapping, sortType: Ext.data.SortTypes.asUCString }
        ],

        filters: filters,
        sortOnLoad: true,
        sorters: { property: nameMapping, direction: 'ASC' },
        remoteFilter: true,
        autoLoad: true
      };

      if (disableSort) {
        delete args.sortOnLoad;
        delete args.sorters;
      }
      itemConfig.store = Ext.create('Ext.data.Store', args);
    }

    return Ext.create('NX.ext.form.field.ItemSelector', itemConfig);
  }

});
