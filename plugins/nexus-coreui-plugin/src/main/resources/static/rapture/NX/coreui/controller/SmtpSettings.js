/*global Ext, NX*/

/**
 * SMTP System Settings controller.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.controller.SmtpSettings', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.Messages',
    'NX.Permissions',
    'NX.I18n'
  ],

  views: [
    'system.SmtpSettings',
    'system.VerifySMTPConnection'
  ],

  refs: [
    {
      ref: 'panel',
      selector: 'nx-coreui-system-smtp-settings'
    },
    {
      ref: 'content',
      selector: 'nx-feature-content'
    }
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.getApplication().getIconController().addIcons({
      'verifysmtpconnection': {
        file: 'emails.png',
        variants: ['x16', 'x32']
      }
    });

    me.getApplication().getFeaturesController().registerFeature({
      mode: 'admin',
      path: '/System/Email Server',
      text: NX.I18n.get('SmtpSettings_Text'),
      description: NX.I18n.get('SmtpSettings_Description'),
      view: { xtype: 'nx-coreui-system-smtp-settings' },
      iconConfig: {
        file: 'email.png',
        variants: ['x16', 'x32']
      },
      visible: function () {
        return NX.Permissions.check('nexus:settings:read');
      }
    }, me);

    me.listen({
      component: {
        'nx-coreui-system-smtp-settings button[action=verify]': {
          click: me.showVerifyConnectionWindow
        },
        'nx-coreui-system-verifysmtpconnection button[action=verify]': {
          click: me.verifyConnection
        }
      }
    });
  },

  /**
   * @private
   */
  showVerifyConnectionWindow: function (button) {
    var form = button.up('form'),
        values = form.getForm().getFieldValues();

    Ext.widget('nx-coreui-system-verifysmtpconnection', { smtpSettings: values });
  },

  /**
   * @private
   * Verifies SMTP connection.
   */
  verifyConnection: function (button) {
    var me = this,
        win = button.up('window'),
        form = button.up('form'),
        email = form.getForm().getFieldValues().email,
        panel = me.getPanel(),
        smtpSettings = panel.down('form').getForm().getFieldValues();

    win.close();
    me.getContent().mask(NX.I18n.format('SmtpSettings_Verify_Mask', smtpSettings.host));

    NX.direct.coreui_Email.sendVerification(smtpSettings, email, function (response) {
      me.getContent().unmask();
      if (Ext.isObject(response) && response.success) {
        NX.Messages.success(NX.I18n.get('SmtpSettings_Verify_Success'));
      }
    });
  }

});
