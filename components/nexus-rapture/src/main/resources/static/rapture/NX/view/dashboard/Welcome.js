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

  cls: "nx-dashboard-welcome",
  width: "100%",
  height: 1080,
  layout: {
    type: "vbox",
    align: "stretch",
  },
  padding: 24,
  items: [
    {
      xtype: "container",
      layout: {
        type: "hbox",
        align: "stretch",
      },
      height: 160,
      // flex: 1,
      items: [
        // 系统健康卡片
        {
          xtype: "container",
          cls: "top-card",
          flex: 1,
          layout: "hbox",
          height: 160,
          items: [
            {
              xtype: "container",
              cls: "top-left",
              layout: "hbox", // 水平布局
              flex: 1,
              height: 80,
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
                  height: 80,
                  html: "<h2>系统健康</h2><label>查看系统状态</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "top-right",
              width: 24,
              height: 80,
              layout: {
                type: 'vbox',
                align: 'middle'
              },
              items: [
                {
                  xtype: "image",
                  cls: "top-arrow",
                  src: NX.Icons.url("arrow", "x24"),
                },
              ],
            },
          ],
          listeners: {
            render: function (container) {
              container.getEl().on("click", function () {
                NX.Bookmarks.navigateTo(
                  NX.Bookmarks.fromToken("admin/support/status")
                );
              });
            },
          },
        },
        // 清理策略卡片
        {
          xtype: "container",
          cls: "top-card",
          flex: 1,
          layout: "hbox", // 水平布局
          height: 160,
          margin: "0 32",
          items: [
            {
              xtype: "container",
              cls: "top-left",
              layout: "hbox", // 水平布局
              flex: 1,
              height: 80,
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
                  height: 80,
                  html: "<h2>清理策略</h2><label>管理清理策略</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "top-right",
              width: 24,
              height: 80,
              layout: {
                type: 'vbox',
                align: 'middle'
              },
              items: [
                {
                  xtype: "image",
                  cls: "top-arrow",
                  src: NX.Icons.url("arrow", "x24"),
                },
              ],
            },
          ],
          listeners: {
            render: function (container) {
              container.getEl().on("click", function () {
                NX.Bookmarks.navigateTo(
                  NX.Bookmarks.fromToken("admin/repository/cleanuppolicies")
                );
              });
            },
          },
        },
        // 浏览存储库卡片
        {
          xtype: "container",
          cls: "top-card",
          flex: 1,
          layout: "hbox", // 水平布局
          height: 160,
          items: [
            {
              xtype: "container",
              cls: "top-left",
              layout: "hbox", // 水平布局
              flex: 1,
              height: 80,
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
                  height: 80,
                  html: "<h2>浏览</h2><label>浏览存储库</label>",
                },
              ],
            },
            {
              xtype: "container",
              cls: "top-right",
              width: 24,
              height: 80,
              layout: {
                type: 'vbox',
                align: 'middle'
              },
              items: [
                {
                  xtype: "image",
                  cls: "top-arrow",
                  src: NX.Icons.url("arrow", "x24"),
                },
              ],
            },
          ],
          listeners: {
            render: function (container) {
              container.getEl().on("click", function () {
                NX.Bookmarks.navigateTo(
                  NX.Bookmarks.fromToken("browse/browse")
                );
              });
            },
          },
        },
      ],
    },
    // 主流技术栈软件仓库
    {
      xtype: "container",
      cls: "repository-container",
      margin: "24 0 24 0",
      // height: 340,
      flex: 1,
      layout: {
        type: "hbox",
        align: "stretch",
      },
      items: [
        {
          xtype: "container",
          cls: "repository-title",
          height: 28,
          html: "<h2>主流技术栈软件仓库</h2>",
          margin: "0 0 32 0",
        },
        {
          xtype: "container",
          cls: "repository-cards",
          layout: {
            type: "hbox", // 水平布局
            align: "stretch",
            pack: "space-between",
          },
          // height: 200,
          flex:1,
          items: [
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              // height: 200,
              padding: 32,
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
                  margin: "12 0 0 32",
                  height: 124,
                  html: "<h3>Maven库(Java)</h3><label>流行的项目管理和构建工具，专为Java应用程序设计</label>",
                },
              ],
              listeners: {
                render: function (container) {
                  container.getEl().on("click", function () {
                    NX.Bookmarks.navigateTo(
                      NX.Bookmarks.fromToken("browse/browse:maven-central")
                    );
                  });
                },
              },
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              margin: "0 32",
              // height: 200,
              padding: 32,
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
                  margin: "12 0 0 32",
                  height: 124,
                  html: "<h3>Pypi库 (Python)</h3><label>官方第三方软件包仓库，使得Python开发更加便捷和高效</label>",
                },
              ],
              listeners: {
                render: function (container) {
                  container.getEl().on("click", function () {
                    NX.Bookmarks.navigateTo(
                      NX.Bookmarks.fromToken("browse/browse")
                    );
                  });
                },
              },
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              // height: 200,
              padding: 32,
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
                  margin: "12 0 0 32",
                  height: 124,
                  html: "<h3>NPM库 (JavaScript)</h3><label>JS中最大的软件包管理器和默认包仓库，极大地促进了JavaScript生态系统的发展和代码重用</label>",
                },
              ],
              listeners: {
                render: function (container) {
                  container.getEl().on("click", function () {
                    console.log("browse/browse");
                    NX.Bookmarks.navigateTo(
                      NX.Bookmarks.fromToken("browse/browse")
                    );
                  });
                },
              },
            },
          ],
        },
      ],
    },
    // 私有Docker镜像库
    {
      xtype: "container",
      cls: "repository-container",
      // height: 340,
      flex: 1,
      items: [
        {
          xtype: "container",
          cls: "repository-title",
          height: 28,
          html: "<h2>私有Docker镜像库</h2>",
          margin: "0 0 32 0",
        },
        {
          xtype: "container",
          cls: "repository-cards",
          layout: {
            type: "hbox", // 水平布局
            align: "stretch",
            pack: "space-between",
          },
          // height: 200,
          flex:1,
          items: [
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              // height: 200,
              padding: 32,
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
                  margin: "12 0 0 32",
                  height: 124,
                  html: "<h3>Nginx</h3><label>安全托管Nginx镜像，优化Web服务部署</label>",
                },
              ],
              listeners: {
                render: function (container) {
                  container.getEl().on("click", function () {
                    NX.Bookmarks.navigateTo(
                      NX.Bookmarks.fromToken(
                        "browse/search/docker=format%3Ddocker%20AND%20attributes.docker.imageName%3D*nginx"
                      )
                    );
                  });
                },
              },
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              margin: "0 32",
              // height: 200,
              padding: 32,
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
                  margin: "12 0 0 32",
                  height: 124,
                  html: "<h3>Mysql</h3><label>安全存储Mysql镜像，便捷数据库部署与管理</label>",
                },
              ],
              listeners: {
                render: function (container) {
                  container.getEl().on("click", function () {
                    NX.Bookmarks.navigateTo(
                      NX.Bookmarks.fromToken(
                        "browse/search/docker=format%3Ddocker%20AND%20attributes.docker.imageName%3D*mysql"
                      )
                    );
                  });
                },
              },
            },
            {
              xtype: "container",
              cls: "repository",
              flex: 1,
              layout: "hbox",
              // height: 200,
              padding: 32,
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
                  margin: "12 0 0 32",
                  height: 124,
                  html: "<h3>Redis</h3><label>高效管理Redis数据缓存服务</label>",
                },
              ],
              listeners: {
                render: function (container) {
                  container.getEl().on("click", function () {
                    NX.Bookmarks.navigateTo(
                      NX.Bookmarks.fromToken(
                        "browse/search/docker=format%3Ddocker%20AND%20attributes.docker.imageName%3D*redis"
                      )
                    );
                  });
                },
              },
            },
          ],
        },
      ],
    },
  ],
});
