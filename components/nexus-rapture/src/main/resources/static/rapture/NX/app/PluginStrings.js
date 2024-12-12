/*global Ext, NX*/

/**
 * Application strings
 *
 * @since 3.0
 */
Ext.define(
  "NX.app.PluginStrings",
  {
    "@aggregate_priority": 90,

    singleton: true,

    requires: ["NX.I18n"],

    //
    // Note: Symbols follow the following naming convention:
    // <Class>_<Name>_<Component or Attribute>
    //

    keys: {
      // Buttons
      Button_Back: "返回",
      Button_Cancel: "取消",
      Button_Close: "关闭",
      Button_Create: "创建",
      Button_Discard: "丢弃",
      Button_Next: "下一步",
      Button_Save: "保存",

      Column_No_Data: "无数据",

      // Header
      Header_Panel_Logo_Text: "可信软件仓库",
      Header_BrowseMode_Title: "浏览",
      Header_BrowseMode_Tooltip: "浏览服务器内容",
      Header_AdminMode_Title: "管理",
      Header_AdminMode_Tooltip: "服务器管理和配置",
      Header_Health_Tooltip: "系统状态",
      Header_QuickSearch_Empty: "搜索组件",
      Header_QuickSearch_Tooltip: "快速组件关键词搜索",
      Header_Refresh_Tooltip: "刷新当前视图和数据",
      Refresh_Message: "已刷新",
      Header_UserMode_Title: "用户",
      User_Tooltip: "你好, {0}. 管理你的用户账户。",
      Header_SignIn_Text: "登录",
      Header_SignIn_Tooltip: "登录",
      Header_SignOut_Text: "登出",
      Header_SignOut_Tooltip: "登出",
      Header_Help_Tooltip: "帮助",
      Help_Feature_Text: "帮助：",
      Header_Help_Feature_Tooltip: "当前选中功能的帮助和文档",
      Header_Help_About_Text: "关于",
      Header_Help_About_Tooltip: "关于可信软件仓库",
      Header_Help_Documentation_Text: "文档",
      Header_Help_Documentation_Tooltip: "产品文档",
      Header_Help_KB_Text: "知识库",
      Header_Help_KB_Tooltip: "知识库",
      Header_Help_Guides_Text: "TianheCloud指南",
      Header_Help_Guides_Tooltip: "TianheCloud指南",
      Header_Help_Community_Text: "社区",
      Header_Help_Community_Tooltip: "社区信息",
      Header_Help_Issues_Text: "问题追踪",
      Header_Help_Issues_Tooltip: "问题和缺陷追踪",
      Header_Help_Support_Text: "支持",
      Header_Help_Support_Tooltip: "产品支持",

      // Footer
      Footer_Panel_HTML:
        "版权所有 &copy; 2008-至今, 天河国云 Inc. 保留所有权利。",

      // Sign in
      SignIn_Title: "登录",
      User_SignIn_Mask: "正在登录&hellip;",
      SignIn_Username_Empty: "用户名",
      SignIn_Password_Empty: "密码",
      SignIn_Submit_Button: "登录",
      SignIn_Cancel_Button: "@Button_Cancel",

      // Filter box
      Grid_Plugin_FilterBox_Empty: "筛选",

      // Dialogs
      Dialogs_Info_Title: "信息",
      Dialogs_Error_Title: "错误",
      Dialogs_Error_Message: "操作失败",
      Add_Submit_Button: "@Button_Create",
      Add_Cancel_Button: "@Button_Cancel",
      ChangeOrderWindow_Submit_Button: "@Button_Save",
      ChangeOrderWindow_Cancel_Button: "@Button_Cancel",

      // Server
      User_ConnectFailure_Message: "操作失败，无法连接到服务器",
      State_Reconnected_Message: "服务器已重新连接",
      State_Disconnected_Message: "服务器已断开",
      UiSessionTimeout_Expire_Message: "会话即将过期",
      UiSessionTimeout_Expired_Message: "会话在 {0} 分钟不活动后已过期",
      User_SignedIn_Message: "用户已登录：{0}",
      User_SignedOut_Message: "用户已登出",
      User_Credentials_Message: "用户名或密码错误，或没有权限使用该应用。",
      Util_DownloadHelper_Download_Message: "下载已启动",
      Windows_Popup_Message: "窗口弹出被阻止！",

      // License
      State_Installed_Message: "许可证已安装",
      State_Uninstalled_Message: "许可证已卸载",
      State_License_Expiry:
        '您的许可证将在 {0} 天后过期。<a href="http://links.sonatype.com/products/nexus/pro/store">联系我们续订。</a>',
      State_License_Expired:
        '您的许可证已过期。<a href="http://links.sonatype.com/products/nexus/pro/store">联系我们续订。</a>',
      State_License_Invalid_Message:
        "您的许可证已被检测为丢失或无效。请上传有效的许可证以继续操作。",

      // About modal
      AboutWindow_Title: "关于可信软件仓库",
      AboutWindow_About_Title: "版权",
      AboutWindow_License_Tab: "许可证",

      // Authentication modal
      Authenticate_Title: "身份验证",
      Authenticate_Help_Text: "您请求的操作需要验证您的凭据。",
      User_Controller_Authenticate_Mask: "正在验证&hellip;",
      User_View_Authenticate_Submit_Button: "验证",
      User_Retrieving_Mask: "正在获取验证令牌&hellip;",
      Authenticate_Cancel_Button: "@Button_Cancel",

      // Expiry modal
      ExpireSession_Title: "会话",
      ExpireSession_Help_Text: "会话即将过期",
      UiSessionTimeout_Expire_Text: "会话将在 {0} 秒后过期",
      SignedOut_Text: "您的会话已过期。请重新登录。",
      ExpireSession_Cancel_Button: "@Button_Cancel",
      ExpireSession_SignIn_Button: "登录",

      // Unsaved changes modal
      UnsavedChanges_Title: "未保存的更改",
      UnsavedChanges_Help_HTML: "<p>您确定要丢弃更改吗？</p>",
      UnsavedChanges_Discard_Button: "丢弃更改",
      UnsavedChanges_Back_Button: "返回",
      Menu_Browser_Title: "您将失去未保存的更改",

      // Unsupported browser
      UnsupportedBrowser_Title: "您使用的浏览器不受支持",
      UnsupportedBrowser_Alternatives_Text: "以下是本应用支持的浏览器列表",
      UnsupportedBrowser_Continue_Button: "忽略并继续",

      // 404
      Feature_NotFoundPath_Text: '路径 "{0}" 未找到',
      Feature_NotFound_Text: "路径未找到",

      // Buttons
      SettingsForm_Save_Button: "@Button_Save",
      SettingsForm_Discard_Button: "@Button_Discard",
      Ldap_LdapServerConnectionAdd_Text: "@Button_Next",

      // Item selector
      Form_Field_ItemSelector_Empty: "筛选",

      // Settings form
      SettingsForm_Load_Message: "加载中",
      SettingsForm_Submit_Message: "保存中",

      // Browse -> Welcome
      Dashboard_Title: "欢迎",
      Dashboard_Description: "了解可信软件仓库",

      // Field validation messages
      Util_Validator_Text:
        "仅允许字母、数字、下划线(_) 、连字符(-)和点(.)，且不能以下划线或点开头。",
      Util_Validator_Hostname: "主机名必须有效",
      Util_Validator_Trim: "角色 ID 不能以空格开头或结尾。",
      Util_Validator_Url: '该字段应为 URL，格式为 "http://www.example.com"',

      // Wizard
      Wizard_Next: "@Button_Next",
      Wizard_Back: "@Button_Back",
      Wizard_Cancel: "@Button_Cancel",
      Wizard_Screen_Progress: "第{0}步，总共{1}步",

      // SearchBoxTip
      SearchBoxTip_ExactMatch: '使用 <b>""</b> 进行精确匹配 - "example"',
      SearchBoxTip_Wildcard:
        "使用 <b>*</b> 或 <b>?</b> 进行通配符匹配 - ex?mpl*",
      SearchBoxTip_LearnMore: "了解更多...",

      // DependencySnippet Panel
      DependencySnippetPanel_Title: "用法",
      DependencySnippetPanel_Copy_Button_Tooltip: "复制代码片段到剪贴板",
    },
  },
  function (obj) {
    NX.I18n.register(obj);
  }
);
