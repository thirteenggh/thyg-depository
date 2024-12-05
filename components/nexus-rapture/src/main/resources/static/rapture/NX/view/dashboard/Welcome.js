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
 * Welcome dashboard.
 *
 * @since 3.0
 */
Ext.define("NX.view.dashboard.Welcome", {
  extend: "Ext.container.Container",
  alias: "widget.nx-dashboard-welcome",
  requires: ["NX.Icons"],

  cls: "nx-iframe-full",
  width: "100%",
  layout: "fit",
  padding: "24px",
  items: [
    {
      xtype: "container", // top-container
      layout: {
        type: "hbox", // 水平布局
        align: "stretch",
        pack: "space-between", // 使卡片间有间隔
      },
      items: [
        // 系统健康卡片
        {
          xtype: "container",
          cls: "top-card",
          flex: 1,
          layout: "hbox", // 水平布局
          pack: "space-between",
          items: [
            {
              xtype: "container",
              cls: "top-left",
              layout: "hbox", // 水平布局
              flex: 1,
              items: [
                {
                  xtype: "image",
                  cls: "top-icon",
                  src: NX.Icons.url("health", "x80"),
                  alt: "系统健康",
                },
                {
                  xtype: "container",
                  cls: "top-title",
                  html: "<h2>系统健康</h2><label>查看系统状态</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "top-right",
              width: "24px",
              items: [
                {
                  xtype: "image",
                  cls: "top-icon",
                  src: NX.Icons.url("arrow", "x24"),
                },
              ],
            },
          ],
        },
        // 清理策略卡片
        {
          xtype: "container",
          cls: "top-card",
          flex: 1,
          layout: "hbox", // 水平布局
          margin: "0 32px",
          items: [
            {
              xtype: "container",
              cls: "top-left",
              layout: "hbox", // 水平布局
              items: [
                {
                  xtype: "image",
                  cls: "top-icon",
                  src: NX.Icons.url("clear", "x80"),
                  alt: "清理策略",
                },
                {
                  xtype: "container",
                  cls: "top-title",
                  html: "<h2>清理策略</h2><label>管理清理策略</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "top-right",
              items: [
                {
                  xtype: "image",
                  cls: "top-icon",
                  src: NX.Icons.url("arrow", "x24"),
                },
              ],
            },
          ],
        },
        // 浏览存储库卡片
        {
          xtype: "container",
          cls: "top-card",
          flex: 1,
          layout: "hbox", // 水平布局
          items: [
            {
              xtype: "container",
              cls: "top-left",
              layout: "hbox", // 水平布局
              items: [
                {
                  xtype: "image",
                  cls: "top-icon",
                  src: NX.Icons.url("browse", "x80"),
                  alt: "浏览",
                },
                {
                  xtype: "container",
                  cls: "top-title",
                  html: "<h2>浏览</h2><label>浏览存储库</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "top-right",
              items: [
                {
                  xtype: "image",
                  cls: "top-icon",
                  src: NX.Icons.url("arrow", "x24"),
                },
              ],
            },
          ],
        },
      ],
    },
    // 主流技术栈软件仓库
    {
      xtype: "container",
      cls: "repository-container",
      margin: "24px 0",
      items: [
        {
          xtype: "container",
          cls: "repository-title",
          html: "<h2>主流技术栈软件仓库</h2>",
        },
        {
          xtype: "container",
          cls: "repository-cards",
          layout: {
            type: "hbox", // 水平布局
            align: "stretch",
            pack: "space-between",
          },
          items: [
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              items: [
                {
                  xtype: "image",
                  cls: "repository-icon",
                  src: NX.Icons.url("Maven", "x80"),
                  alt: "Maven",
                },
                {
                  xtype: "container",
                  cls: "repository-title",
                  margin: "12px 0 0 32px",
                  html: "<h3>Maven库(Java)</h3><label>流行的项目管理和构建工具，专为Java应用程序设计</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              margin: "0 32px",
              items: [
                {
                  xtype: "image",
                  cls: "repository-icon",
                  src: NX.Icons.url("Pypi", "x80"),
                  alt: "Pypi",
                },
                {
                  xtype: "container",
                  cls: "repository-title",
                  margin: "12px 0 0 32px",
                  html: "<h3>Pypi库 (Python)</h3><label>官方第三方软件包仓库，使得Python开发更加便捷和高效</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              items: [
                {
                  xtype: "image",
                  cls: "repository-icon",
                  src: NX.Icons.url("NPM", "x80"),
                  alt: "NPM",
                },
                {
                  xtype: "container",
                  cls: "repository-title",
                  margin: "12px 0 0 32px",
                  html: "<h3>NPM库 (JavaScript)</h3><label>JS中最大的软件包管理器和默认包仓库，极大地促进了JavaScript生态系统的发展和代码重用</label>",
                },
              ],
            },
          ],
        },
      ],
    },
    // 私有Docker镜像库
    {
      xtype: "container",
      cls: "repository-container",
      items: [
        {
          xtype: "container",
          cls: "repository-title",
          html: "<h2>私有Docker镜像库</h2>",
        },
        {
          xtype: "container",
          cls: "repository-cards",
          layout: {
            type: "hbox", // 水平布局
            align: "stretch",
            pack: "space-between",
          },
          items: [
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              items: [
                {
                  xtype: "image",
                  cls: "repository-icon",
                  src: NX.Icons.url("Nginx", "x80"),
                  alt: "Nginx",
                },
                {
                  xtype: "container",
                  cls: "repository-title",
                  margin: "12px 0 0 32px",
                  html: "<h3>Nginx</h3><label>安全托管Nginx镜像，优化Web服务部署</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              margin: "0 32px",
              items: [
                {
                  xtype: "image",
                  cls: "repository-icon",
                  src: NX.Icons.url("Mysql", "x80"),
                  alt: "Mysql",
                },
                {
                  xtype: "container",
                  cls: "repository-title",
                  margin: "12px 0 0 32px",
                  html: "<h3>Mysql</h3><label>安全存储Mysql镜像，便捷数据库部署与管理</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              items: [
                {
                  xtype: "image",
                  cls: "repository-icon",
                  src: NX.Icons.url("Redis", "x80"),
                  alt: "Redis",
                },
                {
                  xtype: "container",
                  cls: "repository-title",
                  margin: "12px 0 0 32px",
                  html: "<h3>Redis</h3><label>高效管理Redis数据缓存服务</label>",
                },
              ],
            },
          ],
        },
      ],
    },
  ],
});
