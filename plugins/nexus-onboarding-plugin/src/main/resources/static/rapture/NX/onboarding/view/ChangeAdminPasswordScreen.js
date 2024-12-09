/*global Ext, NX*/

/**
 * @since 3.17
 */
Ext.define('NX.onboarding.view.ChangeAdminPasswordScreen', {
  extend: 'NX.onboarding.view.OnboardingScreen',
  alias: 'widget.nx-onboarding-change-admin-password-screen',
  requires: [
    'NX.Conditions',
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    Ext.apply(me, {
      title: NX.I18n.render(me, 'Title'),

      buttons: ['back', '->', 'next'],

      fields: [{
        xtype: 'form',
        defaults: {
          anchor: '100%'
        },
        items: [
          {
            xtype: 'nx-password',
            name: 'password',
            itemId: 'password',
            fieldLabel: NX.I18n.get('User_UserChangePassword_Password_FieldLabel'),
            allowBlank: false,
            listeners: {
              change: function(){
                var me = this;
                me.up('form').down('#verifyPassword').validate();
              }
            }
          },
          {
            xtype: 'nx-password',
            itemId: 'verifyPassword',
            fieldLabel: NX.I18n.get('User_UserChangePassword_PasswordConfirm_FieldLabel'),
            allowBlank: false,
            submitValue: false,
            validator: function () {
              var me = this;
              return (me.up('form').down('#password').getValue() === me.getValue()) ? true : NX.I18n.get('User_UserChangePassword_NoMatch_Error');
            }
          }
        ]
      }]
    });

    me.callParent();
  }

});
