/*global Ext, NX*/

/**
 * Configuration for Repository Groups.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.facet.GroupFacet', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.nx-coreui-repository-group-facet',
  requires: [
    'NX.I18n',
    'NX.coreui.store.RepositoryReference'
  ],

  /**
   * @cfg String
   * Set the format to narrow the format of groups available to choose from.
   */
  format: undefined,

  /**
   * @cfg {boolean}
   * Setting to control if the format supports group writes
   */
  supportsGroupWrite: false,

  /**
   * @cfg String
   * Set the help hint for insert in Group Deployment section.
   */
  helpHint: undefined,

  /**
   * @override
   */
  initComponent: function() {
    var me = this;
    var writableEnabled = NX.State.getValue('groupWritableEnabled') && me.supportsGroupWrite;

    me.selectableFilter = function(item) {
      return me.up('form').down('#groupMemberNames').getValue().includes(item.data.name);
    }

    me.availableHostedRepositories = Ext.create('NX.coreui.store.RepositoryReference', {
      remote: true,
      sorters: undefined,
      remoteFilter: true,
      autoLoad: true,
      filters: [
        {property: 'format', value: me.format},
        {property: 'type', value: 'hosted'}
      ]
    });

    me.selectableHostedRepos = Ext.create('Ext.data.ChainedStore', {
      source: me.availableHostedRepositories,
      remoteFilter: false,
    });

    me.repositoryStore = Ext.create('NX.coreui.store.RepositoryReference', {remote: true, sorters: undefined});
    me.repositoryStore.filter([
      {property: 'format', value: me.format}
    ]);
    me.repositoryStore.load(function() {
      //TODO - KR hackity hack, but it appears that the store loading somehow unsets values?
      var form = me.up('form');
      if (form) {
        var record = form.getRecord();
        if (record) {
          me.repositoryStore.filter([
            {property: 'format', value: me.format},
            {
              filterFn: function(item) {
                return item.get('name') !== record.get('name');
              }
            }
          ]);
          var memberNames = record.get('attributes').group.memberNames;
          form.down('#groupMemberNames').setValue(memberNames);
          // clears isDirty state after setting the value
          form.down('#groupMemberNames').resetOriginalValue();

          if (writableEnabled) {
            var groupWriteMember = record.get('attributes').group.groupWriteMember;
            form.down('#groupWriteMember').setValue(groupWriteMember);
            form.down('#groupWriteMember').resetOriginalValue();
          }
        }
        me.selectableHostedRepos.filterBy(me.selectableFilter);
      }
    });

    var writableComboConfig =
        {
          xtype: 'combo',
          name: 'attributes.group.groupWriteMember',
          itemId: 'groupWriteMember',
          fieldLabel: NX.I18n.get('Repository_Facet_GroupWriteFacet_Writable_Repository_FieldLabel'),
          helpText: NX.I18n.get('Repository_Facet_GroupWriteFacet_Writable_Repository_HelpText'),
          forceSelection: true,
          editable: true,
          allowBlank: true,
          store: me.selectableHostedRepos,
          displayField: 'name',
          emptyText: 'Select...',
          queryMode: 'local',
          valueField: 'name',
          triggers: {
            clear: {
              cls: 'x-form-clear-trigger', weight: -1, handler: Ext.form.field.ComboBox.prototype.clearValue,
            }
          }
        };

    var groupMembersConfig = {
      xtype: 'nx-itemselector',
      name: 'attributes.group.memberNames',
      itemId: 'groupMemberNames',
      fieldLabel: NX.I18n.get('Repository_Facet_GroupFacet_Members_FieldLabel'),
      helpText: NX.I18n.get('Repository_Facet_GroupFacet_Members_HelpText'),
      buttons: ['up', 'add', 'remove', 'down'],
      fromTitle: NX.I18n.get('Repository_Facet_GroupFacet_Members_FromTitle'),
      toTitle: NX.I18n.get('Repository_Facet_GroupFacet_Members_ToTitle'),
      store: me.repositoryStore,
      valueField: 'id',
      displayField: 'name',
      allowBlank: false,
      delimiter: null,
      forceSelection: true,
      queryMode: 'local',
      triggerAction: 'all',
      selectOnFocus: false,
      itemCls: 'required-field'

    };

    const helpLabelElement =
        {
          xtype: 'nx-responsive-panel',
          layout: {
            type: 'hbox',
            align: 'center',
            pack: 'center'
          },
          items: [
            {
              xtype: 'panel',
              bodypadding: '10px',
              width: '85%',
              html: me.helpHint
            }
          ]
        };

    var fieldSetItems = [];
    if (writableEnabled) {
      groupMembersConfig.listeners = {
        change: function(selector, newValue) {
          var form = selector.up('form');
          var currentRepository = form.down('#groupWriteMember').getValue();
          if (newValue.indexOf(currentRepository) === -1) {
            form.down('#groupWriteMember').clearValue();
          }
          me.selectableHostedRepos.filterBy(me.selectableFilter);
        }
      };
      fieldSetItems.push(writableComboConfig);
      if (me.helpHint) {
        fieldSetItems.push(helpLabelElement);
      }
    }
    fieldSetItems.push(groupMembersConfig);

    me.items = [
      {
        xtype: 'fieldset',
        cls: 'nx-form-section',
        title: NX.I18n.get('Repository_Facet_GroupFacet_Title'),
        items: fieldSetItems
      }
    ];
    me.callParent();
  }

});
