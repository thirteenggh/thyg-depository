/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
/*global Ext, NX*/

/**
 * @since 3.17
 */
Ext.define(
  "NX.onboarding.app.PluginStrings",
  {
    "@aggregate_priority": 90,

    singleton: true,
    requires: ["NX.I18n"],

    keys: {
      Onboarding_Text: "入门",
      Onboarding_Description: "需要关注的配置更改",
      Onboarding_Authenticate:
        "您的 <b>管理员</b> 用户密码位于 <br><b>{0}</b> 服务器上。",
      Onboarding_LoadStepsError: "无法从服务器检索设置步骤",
    },

    bundles: {
      "NX.onboarding.view.OnboardingStartScreen": {
        Title: "设置",
        Description: "<p>本向导将帮助您完成所需的设置任务。</p>",
      },
      "NX.onboarding.view.OnboardingCompleteScreen": {
        Title: "完成",
        Description:
          "<p>设置任务已完成，祝您愉快地使用 私有弹性云计算软件平台！</p>",
        Finish_Button: "完成",
      },
      "NX.onboarding.view.ChangeAdminPasswordScreen": {
        Title: "请选择管理员用户的密码",
      },
      "NX.onboarding.view.ConfigureAnonymousAccessScreen": {
        Title: "配置匿名访问",
        Description:
          "<p><b>启用匿名访问</b> 意味着默认情况下，用户可以无需凭据搜索、浏览和下载仓库中的组件。请 <b>考虑贵组织的安全隐患。</b>" +
          "<br>" +
          "<p><b>禁用匿名访问</b> 需要谨慎选择，因为这 <b>将要求所有用户和/或构建工具提供凭据。</b>" +
          "<br><br>" +
          '<a href="https://links.sonatype.com/products/nexus/anonymous-access/docs" target="_blank" rel="noopener">更多信息 <span class="x-fa fa-external-link"></span></a></p>',
        Enable_Label: "启用匿名访问",
        Disable_Label: "禁用匿名访问",
      },
    },
  },
  function (obj) {
    NX.I18n.register(obj);
  }
);
