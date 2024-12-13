/*global Ext, NX*/

/**
 * CoreUi plugin strings.
 *
 * @since 3.0
 */
Ext.define(
  "NX.coreui.app.PluginStrings",
  {
    "@aggregate_priority": 90,

    singleton: true,
    requires: ["NX.I18n"],

    /**
     * String keys.
     *
     * Keys follow the following naming convention:
     *
     * Class_Name>_[Component_or_Attribute]: string
     *
     * @type {Object}
     */
    keys: {
      // Browse -> Browse
      Assets_Info_Repository: "存储库",
      Assets_Info_Format: "格式",
      Assets_Info_Group: "组件组",
      Assets_Info_Name: "组件名称",
      Assets_Info_Version: "组件版本",
      Assets_Info_Path: "路径",
      Assets_Info_ContentType: "内容类型",
      Assets_Info_FileSize: "文件大小",
      Assets_Info_Last_Downloaded: "最后下载时间",
      Assets_Info_No_Downloads: "尚未下载",
      Assets_Info_Locally_Cached: "本地缓存",
      Assets_Info_BlobRef: "Blob引用",
      Assets_Info_Blob_Created: "Blob创建时间",
      Assets_Info_Blob_Updated: "Blob更新时间",
      Assets_Info_ContainingRepositoryName: "包含存储库名称",
      Assets_Info_Downloaded_Count: "最近30天下载次数",
      Assets_Info_Downloaded_Unit: "次下载",
      Assets_Info_UploadedBy: "上传者",
      Assets_Info_UploadedIp: "上传者IP地址",
      AssetInfo_Delete_Button: "删除资产",
      AssetInfo_Delete_Title: "确认删除？",
      AssetInfo_Delete_Success: "资产已删除：{0}",
      FolderInfo_Delete_Button: "删除文件夹",
      FolderInfo_Delete_Title: "删除整个文件夹？",
      FolderInfo_Delete_Text:
        "您有权限删除的所有资产将在文件夹 '{0}' 下被移除。视图不会自动刷新以显示进度。此操作无法撤销。",
      FolderInfo_Delete_Success: "文件夹正在后台删除",
      Component_Asset_Tree_Title_Feature: "树形视图",
      Component_Asset_Tree_Description_Feature: "查看组件和资产的树形布局",
      Component_Asset_Tree_EmptyText_View: "在存储库中未找到组件/资产",
      Component_Asset_Tree_Expand_Failure: "无法显示请求的树形条目",
      Component_Asset_Tree_Filtered_EmptyText_View:
        '所有组件都已被过滤掉，尝试使用<a href="#browse/search">搜索</a>代替？',
      Component_Asset_Tree_Results_Warning:
        "可能还有更多结果，如果找不到您要找的内容，请尝试过滤结果或进行搜索。",
      Component_Asset_Tree_Html_View: "HTML视图",
      Component_Asset_Tree_Upload_Component: "上传组件",

      ComponentDetails_Delete_Button: "删除组件",
      ComponentDetails_Analyze_Button: "分析应用",
      ComponentDetails_View_Vulnerabilities_Button: "查看漏洞",
      ComponentDetails_View_Vulnerabilities_Count_Button: "查看 {0} 个漏洞",
      ComponentDetails_Browse_Snapshots_Button: "浏览快照(SNAPSHOT)",
      ComponentDetails_Delete_Body: "这将删除与组件 {0} 相关的所有资产",
      ComponentDetails_Delete_Title: "确认删除？",
      ComponentDetails_Delete_Success: "组件已删除：{0}",
      ComponentDetails_Analyze_Success:
        "分析正在进行。报告准备好后将发送电子邮件。",
      ComponentDetails_Loading_Mask: "正在加载...",
      ComponentDetails_Rebuild_Warning:
        "浏览树正在重建；直到重建完成，结果可能不完整。",

      ComponentUtils_Delete_Button_Unauthenticated: "请先登录",
      ComponentUtils_Delete_Asset_No_Permissions: "您没有权限删除此资产",
      ComponentUtils_Delete_Component_No_Permissions: "您没有权限删除此组件",

      AnalyzeApplication_Button_Unauthenticated: "请先登录",

      AnalyzeApplicationWindow_Title: "分析应用",
      AnalyzeApplicationWindow_Form_Asset_FieldLabel: "应用资产",
      AnalyzeApplicationWindow_Form_Asset_HelpText: "选择包含应用的资产",
      AnalyzeApplicationWindow_Form_Asset_EmptyText: "选择一个资产",
      AnalyzeApplicationWindow_Form_Email_FieldLabel: "电子邮件地址",
      AnalyzeApplicationWindow_Form_Email_HelpText: "摘要报告将发送到此地址",
      AnalyzeApplicationWindow_Form_Password_FieldLabel: "报告密码",
      AnalyzeApplicationWindow_Form_Password_HelpText: "访问详细报告的密码",
      AnalyzeApplicationWindow_Form_ProprietaryPackages_FieldLabel: "专有包",
      AnalyzeApplicationWindow_Form_ProprietaryPackages_HelpText:
        "逗号分隔的专有包列表",
      AnalyzeApplicationWindow_Form_Label_FieldLabel: "报告标签",
      AnalyzeApplicationWindow_Form_Label_HelpText: "报告的名称",
      AnalyzeApplicationWindow_Analyze_Button: "分析",
      AnalyzeApplicationWindow_Cancel_Button: "取消",
      AnalyzeApplicationWindow_Form_Html:
        '<p>应用分析对应用进行深度检查，识别潜在风险。</p>',
      AnalyzeApplicationWindow_Loading_Mask: "正在加载",
      AnalyzeApplicationWindow_No_Assets_Error_Title: "组件无应用资产",
      AnalyzeApplicationWindow_No_Assets_Error_Message:
        "此组件没有应用资产，或您没有权限阅读其任何应用资产",

      HealthCheckInfo_Most_Popular_Version_Label: "最受欢迎的版本",
      HealthCheckInfo_Age_Label: "年龄",
      HealthCheckInfo_Popularity_Label: "流行度",
      HealthCheckInfo_Loading_Text: "正在加载...",
      HealthCheckInfo_Disabled_Tooltip:
        "启用存储库健康检查（RHC）后，年龄和流行度数据才可用。",
      HealthCheckInfo_Error_Tooltip: "检索组件数据出错",
      HealthCheckInfo_Quota_Tooltip:
        "年龄和流行度数据的查询限制已达到。请联系Sonatype支持扩展当前配额限制。",
      HealthCheckInfo_Unavailable_Tooltip: "此组件无数据",

      // Vulnerability
      Vulnerability_Information: "信息",
      Vulnerability_NotScanned: "漏洞信息不可用",
      Vulnerability_Count: "漏洞数量",
      Vulnerability_Ref: "漏洞详情",

      // Browse -> Search
      Search_Text: "搜索",
      Search_Description: "按属性搜索组件",
      Search_SaveSearchFilter_Title: "保存搜索过滤器",
      Search_SaveSearchFilter_Name_FieldLabel: "过滤器名称",
      Search_SaveSearchFilter_Description_FieldLabel: "过滤器描述",
      Search_Results_Limit_Message: "仅显示前 {0} 条结果，共 {1} 条",
      Search_Results_TimedOut_Message:
        "搜索超时，超出 {0} 秒，请细化您的搜索条件。{1}",
      Search_Results_TimedOut_LearnMore: "了解更多",
      SearchCriteria_Keyword_FieldLabel: "关键词",
      SearchCriteria_RepositoryName_FieldLabel: "存储库名称",
      SearchCriteria_Name_FieldLabel: "名称",
      SearchCriteria_Tag_FieldLabel: "标签",
      SearchCriteria_Format_FieldLabel: "格式",
      SearchCriteria_Group_FieldLabel: "组",
      SearchCriteria_Checksum_Group: "校验和",
      SearchDocker_Group: "Docker 存储库",
      SearchMaven_Group: "Maven 存储库",
      SearchNpm_Group: "npm 存储库",
      SearchNuget_Group: "NuGet 存储库",
      SearchPyPi_Group: "PyPI 存储库",
      SearchRubygems_Group: "RubyGems 存储库",
      SearchGitLfs_Group: "Git LFS 存储库",
      SearchYum_Group: "Yum 存储库",
      SearchCriteria_MD5_FieldLabel: "MD5",
      SearchCriteria_SHA1_FieldLabel: "SHA-1",
      SearchCriteria_SHA256_FieldLabel: "SHA-256",
      SearchCriteria_SHA2_FieldLabel: "SHA-512",
      SearchCriteria_Version_FieldLabel: "版本",
      Search_TextSearchCriteria_Filter_EmptyText: "任意",
      SearchDocker_Image_Name_FieldLabel: "镜像名称",
      SearchDocker_Image_Tag_FieldLabel: "镜像标签",
      SearchDocker_LayerId_FieldLabel: "层ID",
      SearchDocker_ContentDigest_FieldLabel: "内容摘要",
      SearchMaven_ArtifactID_FieldLabel: "构件ID",
      SearchMaven_BaseVersion_FieldLabel: "基础版本",
      SearchMaven_Extension_FieldLabel: "扩展名",
      SearchMaven_GroupID_FieldLabel: "组ID",
      SearchMaven_Classifier_FieldLabel: "分类器",
      SearchMaven_Version_FieldLabel: "版本",
      SearchNpm_Scope_FieldLabel: "范围",
      SearchNpm_Name_FieldLabel: "名称",
      SearchNpm_Version_FieldLabel: "版本",
      SearchNpm_Author_FieldLabel: "作者",
      SearchNpm_Description_FieldLabel: "描述",
      SearchNpm_Keywords_FieldLabel: "关键词",
      SearchNpm_License_FieldLabel: "许可证",
      SearchNuget_ID_FieldLabel: "ID",
      SearchNuget_Tags_FieldLabel: "标签",
      SearchPyPi_Classifiers_FieldLabel: "分类器",
      SearchPyPi_Description_FieldLabel: "描述",
      SearchPyPi_Keywords_FieldLabel: "PyPI关键词",
      SearchPyPi_Summary_FieldLabel: "摘要",
      SearchRubygems_Name_FieldLabel: "名称",
      SearchRubygems_Version_FieldLabel: "版本",
      SearchRubygems_Platform_FieldLabel: "平台",
      SearchRubygems_Summary_FieldLabel: "摘要",
      SearchRubygems_Description_FieldLabel: "描述",
      SearchRubygems_Licenses_FieldLabel: "许可证",
      SearchRubygems_Homepage_FieldLabel: "首页",
      SearchYum_Architecture_FieldLabel: "架构",
      SearchYum_Name_FieldLabel: "包名称",
      SearchGolang_Group: "Go 存储库",
      SearchGolang_License_FieldLabel: "许可证",
      Search_More_Text: "更多条件",
      Search_SearchResultList_Format_Header: "格式",
      Search_SearchResultList_Group_Header: "组",
      Search_SearchResultList_Name_Header: "名称",
      Search_SearchResultList_Repository_Header: "存储库",
      Search_SearchResultList_Version_Header: "版本",
      Search_SearchResultList_EmptyText: "没有组件匹配过滤器条件",
      Search_Assets_Group: "组",
      Search_Assets_Name: "名称",
      Search_Assets_Format: "格式",
      Search_Assets_Repository: "存储库",
      Search_Assets_Version: "版本",
      SearchResultAssetList_Name_Header: "名称",
      Component_AssetInfo_Info_Title: "摘要",
      Component_Vulnerability_Info_Title: "OSS Index漏洞",
      Component_AssetInfo_Attributes_Title: "属性",
      Component_AssetInfo_HealthCheck_Title: "组件IQ",

      // Browse -> Search -> Bower
      SearchBower_Text: "Bower",
      SearchBower_Description: "在Bower存储库中搜索组件",

      // Browse -> Search -> Docker
      SearchDocker_Text: "Docker",
      SearchDocker_Description: "在Docker存储库中搜索组件",

      // Browse -> Search -> R
      SearchR_Text: "R",
      SearchR_Description: "在R存储库中搜索组件",

      // Browse -> Search -> Raw
      SearchRaw_Text: "Raw",
      SearchRaw_Description: "在Raw存储库中搜索组件",

      // Browse -> Search -> Git LFS
      SearchGitLfs_Text: "Git LFS",
      SearchGitLfs_Description: "在Git LFS存储库中搜索组件",

      // Browse -> Search -> Go
      SearchGolang_Text: "Go",
      SearchGolang_Description: "在Go存储库中搜索组件",

      // Browse -> Search -> Helm
      SearchHelm_Text: "Helm",
      SearchHelm_Description: "在Helm存储库中搜索组件",
      SearchHelm_Group: "Helm存储库",
      Repository_Facet_HelmFacet_Title: "Helm设置",

      // Browse -> Search -> npm
      SearchNpm_Text: "npm",
      SearchNpm_Description: "在npm存储库中搜索组件",

      // Browse -> Search -> Nuget
      SearchNuget_Text: "NuGet",
      SearchNuget_Description: "在NuGet存储库中搜索组件",

      // Browse -> Search -> PyPI
      SearchPyPi_Text: "PyPI",
      SearchPyPi_Description: "在PyPI存储库中搜索组件",

      // Browse -> Search -> Rubygems
      SearchRubygems_Text: "RubyGems",
      SearchRubygems_Description: "在RubyGems存储库中搜索组件",

      // Browse -> Search -> Custom
      Search_Custom_Text: "自定义",
      Search_Custom_Description: "按自定义条件搜索组件",

      // Browse -> Search -> Maven
      SearchMaven_Text: "Maven",
      SearchMaven_Description: "按Maven坐标搜索组件",

      // Browse -> Search -> Yum
      SearchYum_Text: "Yum",
      SearchYum_Description: "在Yum存储库中搜索组件",

      // Browse -> Search -> Apt
      SearchApt_Text: "Apt",
      SearchApt_Description: "在Apt存储库中搜索组件",

      // Browse -> Search -> Cocoapods
      SearchCocoapods_Text: "Cocoapods",
      SearchCocoapods_Description: "在Cocoapods存储库中搜索组件",

      // Browse -> Search -> p2
      SearchP2_Text: "P2",
      SearchP2_Description: "在P2存储库中搜索组件",
      SearchP2_Group: "P2存储库",
      SearchP2_PluginName_FieldLabel: "插件名称",

      // Browse -> Search -> Conan
      SearchConan_Group: "Conan存储库",
      SearchConan_Text: "Conan",
      SearchConan_Description: "在Conan存储库中搜索组件",
      SearchConan_BaseVersion_FieldLabel: "基础版本",
      SearchConan_Channel_FieldLabel: "频道",

      // Browse -> Search -> Conda
      SearchConda_Text: "Conda",
      SearchConda_Description: "在Conda存储库中搜索组件",
      SearchConda_Group: "Conda存储库",
      SearchConda_License_FieldLabel: "许可证",

      // Browse -> Browse
      FeatureGroups_Browse_Text: "浏览",
      FeatureGroups_Browse_Description: "浏览资产和组件",

      // Browse -> Upload
      FeatureGroups_Upload_Text: "上传",
      FeatureGroups_Upload_Description: "上传内容到存储库",
      FeatureGroups_Upload_Wait_Message: "正在上传您的组件...",
      FeatureGroups_Upload_Successful: "组件上传成功",
      FeatureGroups_Upload_Successful_Link_Text: "立即查看。",
      FeatureGroups_Upload_Successful_Text: "组件已上传到 {0} 存储库",
      FeatureGroups_Upload_Asset_Form_Title: "为这个组件选择资产",
      FeatureGroups_Upload_Asset_Form_File_Label: "文件",
      FeatureGroups_Upload_Asset_Form_Remove_Button: "移除",
      FeatureGroups_Upload_Asset_Form_Add_Asset_Button: "添加另一个资产",
      FeatureGroups_Upload_Asset_Form_Not_Unique_Error_Message: "资产不唯一",
      FeatureGroups_Upload_Form_Upload_Button: "上传",
      FeatureGroups_Upload_Form_Discard_Button: "取消",
      FeatureGroups_Upload_Form_Browse_Button: "浏览",
      FeatureGroups_Upload_Form_DetailsFromPom_Mask:
        "组件详情将从提供的POM文件中提取。",

      // Admin -> Repository
      FeatureGroups_Repository_Text: "存储库",
      FeatureGroups_Repository_Description: "存储库管理",

      // Admin -> Repository -> Repositories
      Repositories_Text: "存储库",
      Repositories_Description: "管理存储库",
      Repositories_Delete_Mask: "正在删除存储库",
      Repositories_Create_Title: "创建存储库：{0}",
      Repositories_SelectRecipe_Title: "选择工具",
      Repository_RepositoryAdd_Create_Success: "已创建存储库：",
      Repository_RepositoryAdd_Create_Error: "您没有权限创建存储库",
      Repository_RepositorySettingsForm_Update_Success: "已更新存储库：",
      Repository_RepositorySettingsForm_Update_Error: "您没有权限更新存储库",
      Repository_RepositoryList_New_Button: "创建存储库",
      Repository_RepositoryList_Name_Header: "名称",
      Repository_RepositoryList_Type_Header: "类型",
      Repository_RepositoryList_Format_Header: "格式",
      Repository_RepositoryList_Status_Header: "状态",
      Repository_RepositoryList_URL_Header: "URL",
      Repository_RepositoryList_Filter_EmptyText: "没有存储库匹配“{$filter}”",
      Repository_RepositoryList_EmptyText:
        '<div class="summary">还没有创建存储库<br><span style="font-weight: lighter; font-size: small;">或者您没有权限浏览它们</span></div><div class="panel nx-subsection"><h3 class="title"><span class="icon"></span>什么是存储库？</h3><p>存储库是存储位置，例如包、库、二进制文件和容器，它们被检索以便安装或使用。创建和管理存储库是可信软件仓库配置的重要组成部分，因为它允许您向最终用户展示内容，同时也为他们提供了存储更多内容的位置。</p></div>',
      Repository_RepositoryFeature_Delete_Button: "删除存储库",
      Repository_RepositoryFeature_RebuildIndex_Button: "重建索引",
      Repository_RepositoryFeature_HealthCheckDisable_Button: "禁用健康检查",
      Repository_RepositoryFeature_HealthCheckEnable_Button: "启用健康检查",
      Repository_RepositoryFeature_InvalidateCache_Button: "使缓存失效",
      Repository_RepositorySettings_Title: "设置",
      Repository_Facet_BowerProxyFacet_Title: "Bower",
      Repository_Facet_BowerProxyFacet_RewritePackageUrls_FieldLabel:
        "启用包URL重写",
      Repository_Facet_BowerProxyFacet_RewritePackageUrls_HelpText:
        "强制Bower通过代理存储库检索包",
      Repository_Facet_DockerHostedFacet_V1_Title: "Docker Registry API支持",
      Repository_Facet_DockerHostedFacet_V1_Enabled: "启用Docker V1 API",
      Repository_Facet_DockerHostedFacet_V1_Enabled_Help:
        "允许客户端使用V1 API与此存储库交互",
      Repository_Facet_DockerConnectorFacet_Title: "存储库连接器",
      Repository_Facet_DockerConnectorFacet_Help:
        '<em>连接器允许Docker客户端直接连接到托管注册表，但并不总是必需的。</em>',
      Repository_Facet_DockerConnectorFacet_HttpPort_FieldLabel: "HTTP",
      Repository_Facet_DockerConnectorFacet_HttpPort_HelpText:
        "在指定端口创建HTTP连接器。通常，如果服务器位于安全代理后面，则使用此设置。",
      Repository_Facet_DockerConnectorFacet_HttpsPort_FieldLabel: "HTTPS",
      Repository_Facet_DockerConnectorFacet_HttpsPort_HelpText:
        "在指定端口创建HTTPS连接器。通常，如果服务器配置为https，则使用此设置。",
      Repository_Facet_DockerProxyFacet_IndexType_FieldLabel: "Docker索引",
      Repository_Facet_DockerProxyFacet_IndexTypeRegistry_BoxLabel:
        "使用代理注册表（如上所述）",
      Repository_Facet_DockerProxyFacet_IndexTypeHub_BoxLabel: "使用Docker Hub",
      Repository_Facet_DockerProxyFacet_IndexTypeCustom_BoxLabel: "自定义索引",
      Repository_Facet_DockerProxyFacet_IndexUrl_HelpText: "Docker索引的位置",
      Repository_Facet_DockerProxyFacet_ForeignLayers_FieldLabel: "外部层缓存",
      Repository_Facet_DockerProxyFacet_ForeignLayers_HelpText:
        "允许可信软件仓库下载和缓存外部层",
      Repository_Facet_DockerProxyFacet_ForeignLayersWhitelist_FieldLabel:
        "外部层允许的URL",
      Repository_Facet_DockerProxyFacet_ForeignLayersWhitelist_HelpText:
        "用于识别允许的外部层请求URL的正则表达式",
      Repository_Facet_DockerProxyFacet_ForeignLayersWhitelist_AddButton:
        "添加URL模式",
      Repository_Facet_DockerProxyFacet_BasicAuth_FieldLabel:
        "允许匿名docker拉取",
      Repository_Facet_DockerProxyFacet_BasicAuth_BoxLabel:
        "允许匿名docker拉取（需要Docker Bearer Token Realm）",
      Repository_Facet_YumHostedFacet_Title: "Yum",
      Repository_Facet_YumHostedFacet_RepodataDepth_FieldLabel: "Repodata深度",
      Repository_Facet_YumHostedFacet_RepodataDepth_HelpText:
        "指定创建repodata文件夹的存储库深度",
      Repository_Facet_YumHostedFacet_DeployPolicy_FieldLabel: "布局策略",
      Repository_Facet_YumHostedFacet_DeployPolicy_HelpText:
        "验证所有路径是否为RPM或yum元数据",
      Repository_Facet_YumHostedFacet_DeployPolicy_EmptyText: "选择一个策略",
      Repository_Facet_YumHostedFacet_DeployPolicy_StrictItem: "严格",
      Repository_Facet_YumHostedFacet_DeployPolicy_PermissiveItem: "宽容",
      Repository_Facet_AptFacet_Title: "APT设置",
      Repository_Facet_AptFacet_Distribution_FieldLabel: "发行版",
      Repository_Facet_AptFacet_Distribution_HelpText:
        "要获取的发行版，例如bionic",
      Repository_Facet_AptFacet_Flat_FieldLabel: "Flat",
      Repository_Facet_AptFacet_Flat_HelpText: "此存储库是否为平面存储库？",
      Repository_Facet_AptSigningFacet_Keypair_FieldLabel: "签名密钥",
      Repository_Facet_AptSigningFacet_Keypair_HelpText:
        "PGP签名密钥对（例如gpg --export-secret-key --armor <Name or ID>）",
      Repository_Facet_AptSigningFacet_Passphrase_FieldLabel: "密码短语",
      Repository_Facet_CondaFacet_Title: "Conda设置",
      Repository_Facet_GroupFacet_Title: "组",
      Repository_Facet_NugetGroupFacet_NugetGroupValidationLabel:
        '<span style="color: red; ">组存储库不能包含NuGet v2和v3成员的混合。您不能添加<b>{0}</b>（{1}），因为组中包含了<b>{2}</b>（{3}）。</span>',
      Repository_Facet_HttpClientFacet_Title: "HTTP",
      Repository_Facet_Maven2Facet_Title: "Maven 2",
      Repository_Facet_NegativeCacheFacet_Title: "负缓存",
      Repository_Facet_NugetProxyFacet_Title: "NuGet",
      Repository_Facet_ProxyFacet_Title: "代理",
      Repository_Facet_Raw_Title: "原始",
      Repository_Facet_Raw_ContentDisposition_FieldLabel: "内容处置",
      Repository_Facet_Raw_ContentDisposition_HelpText:
        "添加内容处置头部作为‘附件’以禁用某些内容在浏览器中的内联显示。",
      Repository_Facet_Raw_ContentDisposition_Inline: "内联",
      Repository_Facet_Raw_ContentDisposition_Attachment: "附件",
      Repository_Facet_StorageFacet_Title: "存储",
      Repository_Facet_StorageFacetHosted_Title: "托管",
      Repository_Facet_RoutingRuleFacet_Title: "路由规则",
      Repository_Facet_RoutingRuleFacet_HelpText:
        "选择一个规则以限制某些请求不被此存储库服务",
      Repository_Facet_ProxyFacet_Autoblock_FieldLabel: "自动阻止启用",
      Repository_Facet_ProxyFacet_Autoblock_HelpText:
        "如果检测到远程对等节点不可达/无响应，则自动阻止出站连接",
      Repository_Facet_ProxyFacet_Blocked_FieldLabel: "已阻止",
      Repository_Facet_ProxyFacet_Blocked_HelpText: "阻止对此存储库的出站连接",
      Repository_RepositorySettingsForm_Name_FieldLabel: "名称",
      Repository_RepositorySettingsForm_Name_HelpText: "此存储库的唯一标识符",
      Repository_RepositorySettingsForm_URL_FieldLabel: "URL",
      Repository_RepositorySettingsForm_URL_HelpText: "访问此存储库的URL",
      Repository_Facet_GroupFacet_Members_FieldLabel: "成员存储库",
      Repository_Facet_GroupFacet_Members_HelpText: "选择并排序组成此组的存储库",
      Repository_Facet_GroupFacet_Members_FromTitle: "可用",
      Repository_Facet_GroupFacet_Members_ToTitle: "成员",
      Repository_Facet_GroupWriteFacet_Writable_Repository_FieldLabel:
        "可写存储库",
      Repository_Facet_GroupWriteFacet_Writable_Repository_HelpText:
        "POST和PUT请求将被路由到的成员存储库",
      Repository_Facet_StorageFacetHosted_Deployment_FieldLabel: "部署策略",
      Repository_Facet_StorageFacetHosted_Deployment_HelpText:
        "控制是否允许部署和更新构件",
      Repository_Facet_StorageFacetHosted_Deployment_EmptyText: "选择一个策略",
      Repository_Facet_StorageFacetHosted_Deployment_AllowItem: "允许重新部署",
      Repository_Facet_StorageFacetHosted_Deployment_DisableItem:
        "禁用重新部署",
      Repository_Facet_StorageFacetHosted_Deployment_DisableLatestItem:
        "仅允许对‘最新’标签重新部署",
      Repository_Facet_StorageFacetHosted_Deployment_DisableLatestItemHelpText:
        "允许对‘最新’标签进行重新部署，但对于所有其他标签则遵循部署策略",
      Repository_Facet_StorageFacetHosted_Deployment_ReadOnlyItem: "只读",
      Repository_Facet_ProxyFacet_Remote_FieldLabel: "远程存储",
      Repository_Facet_ProxyFacet_Remote_HelpText: "被代理的远程存储库的位置",
      Repository_Facet_ProxyFacet_Remote_EmptyText: "输入URL",
      Repository_Facet_ProxyFacet_Bower_Remote_HelpText:
        "被代理的远程存储库的位置，例如 https://registry.bower.io",
      Repository_Facet_ProxyFacet_Docker_Remote_HelpText:
        "被代理的远程存储库的位置，例如 https://registry-1.docker.io",
      Repository_Facet_ProxyFacet_Maven_Remote_HelpText:
        "被代理的远程存储库的位置，例如 https://repo1.maven.org/maven2/",
      Repository_Facet_ProxyFacet_Npm_Remote_HelpText:
        "被代理的远程存储库的位置，例如 https://registry.npmjs.org",
      Repository_Facet_ProxyFacet_Nuget_Remote_HelpText:
        "被代理的远程存储库的位置，例如 https://api.nuget.org/v3/index.json",
      Repository_Facet_ProxyFacet_Pypi_Remote_HelpText:
        "被代理的远程存储库的位置，例如 https://pypi.org",
      Repository_Facet_ProxyFacet_Rubygems_Remote_HelpText:
        "被代理的远程存储库的位置，例如 https://rubygems.org",
      Repository_Facet_ProxyFacet_Yum_Remote_HelpText:
        "被代理的远程存储库的位置，例如 http://mirror.centos.org/centos/",
      Ssl_SslUseTrustStore_BoxLabel: "使用信任库",
      Ssl_SslUseTrustStore_Certificate_Button: "查看证书",
      Ssl_SslUseTrustStore_Certificate_HelpText:
        "使用存储在信任库中的证书连接到外部系统",
      Maven2Facet_VersionPolicy_FieldLabel: "版本策略",
      Maven2Facet_VersionPolicy_HelpText: "此存储库存储哪种类型的构件？",
      Maven2Facet_VersionPolicy_EmptyText: "选择一个策略",
      Maven2Facet_VersionPolicy_MixedItem: "混合",
      Maven2Facet_VersionPolicy_ReleaseItem: "发布",
      Maven2Facet_VersionPolicy_SnapshotItem: "快照",
      Repository_Facet_Maven2Facet_LayoutPolicy_FieldLabel: "布局策略",
      Repository_Facet_Maven2Facet_LayoutPolicy_HelpText:
        "验证所有路径是否为maven构件或元数据路径",
      Repository_Facet_Maven2Facet_LayoutPolicy_EmptyText: "选择一个策略",
      Repository_Facet_Maven2Facet_LayoutPolicy_StrictItem: "严格",
      Repository_Facet_Maven2Facet_LayoutPolicy_PermissiveItem: "宽容",
      Repository_RepositorySettingsForm_Format_FieldLabel: "格式",
      Repository_RepositorySettingsForm_Format_HelpText:
        "存储库的格式（例如maven2、docker、raw、nuget...）",
      Repository_RepositorySettingsForm_Type_FieldLabel: "类型",
      Repository_RepositorySettingsForm_Type_HelpText:
        "存储库的类型（例如组、托管或代理）",
      Repository_RepositorySettingsForm_Online_FieldLabel: "在线",
      Repository_RepositorySettingsForm_Online_HelpText:
        "如果选中，存储库接受传入请求",
      Repository_Facet_ProxyFacet_ArtifactAge_FieldLabel: "最大组件年龄",
      Repository_Facet_ProxyFacet_MetadataAge_FieldLabel: "最大元数据年龄",
      Repository_Facet_ProxyFacet_ArtifactAge_HelpText:
        "在重新检查远程存储库之前缓存构件的时间（分钟）。发布存储库应使用-1。",
      Repository_Facet_ProxyFacet_MetadataAge_HelpText:
        "在重新检查远程存储库之前缓存元数据的时间（分钟）。",
      Repository_Facet_HttpClientFacet_ConnectionRetries_FieldLabel: "连接重试",
      Repository_Facet_HttpClientFacet_ConnectionRetries_HelpText:
        "如果初始连接尝试超时，总共重试次数",
      Repository_Facet_HttpClientFacet_ConnectionTimeout_FieldLabel: "连接超时",
      Repository_Facet_HttpClientFacet_ConnectionTimeout_HelpText:
        "等待活动前的秒数，然后停止并重试连接。留空以使用全局定义的HTTP超时设置。",
      Repository_Facet_HttpClientFacet_EnableCircularRedirects_FieldLabel:
        "启用循环重定向",
      Repository_Facet_HttpClientFacet_EnableCircularRedirects_HelpText:
        "启用重定向到同一位置（某些服务器可能需要）",
      Repository_Facet_HttpClientFacet_EnableCookies_FieldLabel: "启用Cookie",
      Repository_Facet_HttpClientFacet_EnableCookies_HelpText:
        "允许存储和使用Cookie",
      Repository_Facet_StorageFacet_BlobStore_FieldLabel: "Blob存储",
      Repository_Facet_StorageFacet_BlobStore_HelpText:
        "用于存储存储库内容的Blob存储",
      Repository_Facet_StorageFacet_BlobStore_EmptyText: "选择一个Blob存储",
      Repository_Facet_StorageFacet_ContentTypeValidation_FieldLabel:
        "严格内容类型验证",
      Repository_Facet_StorageFacet_ContentTypeValidation_HelpText:
        "验证上传到此存储库的所有内容是否为适当的MIME类型，适用于存储库格式",
      Repository_Facet_StorageFacet_DataStore_FieldLabel: "数据存储",
      Repository_Facet_StorageFacet_DataStore_HelpText:
        "用于存储内容元数据的数据存储",
      Repository_Facet_StorageFacet_DataStore_EmptyText: "选择一个数据存储",
      Repository_Facet_NegativeCacheFacet_Enabled_FieldLabel: "未找到缓存启用",
      Repository_Facet_NegativeCacheFacet_Enabled_HelpText:
        "缓存不在代理存储库中存在的内容的响应",
      Repository_Facet_NegativeCacheFacet_TTL_FieldLabel: "未找到缓存TTL",
      Repository_Facet_NegativeCacheFacet_TTL_HelpText:
        "缓存文件未在存储库中找到的事实的时间（分钟）",
      Repository_Facet_NugetProxyFacet_ProtocolVersion: "协议版本",
      Repository_Facet_NugetProxyFacet_V2: "NuGet V2",
      Repository_Facet_NugetProxyFacet_V3: "NuGet V3",
      Repository_Facet_NugetProxyFacet_ItemMaxAge_FieldLabel:
        "元数据查询缓存年龄",
      Repository_Facet_NugetProxyFacet_ItemMaxAge_HelpText:
        "从代理存储库缓存查询结果的时间（秒）",
      Repository_Facet_Npm_Title: "npm",
      Repository_Facet_Npm_RemoveNonCataloged_Label: "移除未编目版本",
      Repository_Facet_Npm_RemoveNonCataloged_HelpText:
        '从npm包元数据中移除未编目版本。<span style="font-weight: bold">IQ: 审计和隔离</span>功能必须启用，此功能才能生效。',
      Repository_Facet_HttpClientFacet_AuthenticationType_FieldLabel:
        "认证类型",
      Repository_Facet_HttpClientFacet_AuthenticationType_Username: "用户名",
      Repository_Facet_HttpClientFacet_AuthenticationType_NTLM: "Windows NTLM",
      Repository_Facet_HttpClientFacet_AuthenticationType_Bearer_Token:
        "预认证Bearer令牌",
      Repository_Facet_HttpClientFacet_Authentication_Title: "认证",
      Repository_Facet_HttpClientFacet_HTTP_Title: "HTTP请求设置",
      Repository_Facet_CleanupPolicyFacet_Title: "清理",
      Repository_Facet_CleanupPolicyFacet_Policy_FieldLabel: "清理策略",
      Repository_Facet_CleanupPolicyFacet_Policy_HelpText:
        "匹配任何已应用策略的组件将被删除",
      Repository_Facet_CleanupPolicyFacet_Policy_FromTitle: "可用",
      Repository_Facet_CleanupPolicyFacet_Policy_ToTitle: "已应用",
      Repository_Facet_CleanupPolicyFacet_Policy_EmptyText: "无",
      Repository_Formats_All: "（所有格式）",
      Repository_Facet_GolangFacet_Title: "Go设置",

      HealthCheckRepositoryColumn_Header: "健康检查",
      HealthCheckRepositoryColumn_Analyzing: "正在分析...",
      HealthCheckRepositoryColumn_Analyzing_Tooltip:
        "<span><h2>分析进行中</h2>" +
        "您的存储库内容正在被分析。这个过程应该只需要几分钟。<br><br>" +
        "当分析完成并且此页面刷新后，我们将向您展示存储库中前5个最脆弱的组件，过去一个月的下载次数，以及年比概览。</span>",
      HealthCheckRepositoryColumn_View_Permission_Error:
        "<span><h2>没有权限查看摘要报告</h2>" +
        "要查看存储库健康检查摘要报告，您的用户账户必须具有必要的权限。</span>",
      HealthCheckRepositoryColumn_Analyze: "分析",
      HealthCheckRepositoryColumn_Analyze_Tooltip:
        "<span><h2>存储库健康检查分析</h2>点击此按钮请求IQ服务器进行存储库健康检查（RHC）" +
        "该过程是非侵入性和非破坏性的。IQ服务器将返回有关存储库中开源组件的可操作质量和安全信息。",
      HealthCheckRepositoryColumn_Analyze_Dialog_Title: "分析存储库",
      HealthCheckRepositoryColumn_Analyze_Dialog_Msg:
        "您是否要分析存储库 {0} 和其他存储库的安全隐患和许可问题？",
      HealthCheckRepositoryColumn_Analyze_Dialog_Ok_Text: "是的，所有存储库",
      HealthCheckRepositoryColumn_Analyze_Dialog_Yes_Text: "是的，仅此存储库",
      HealthCheckRepositoryColumn_Analyze_Permission_Error:
        "<span><h2>没有权限分析存储库</h2>" +
        "要分析存储库，您的用户账户必须有权限启动分析。</span>",
      HealthCheckRepositoryColumn_Loading: "正在加载...",
      HealthCheckRepositoryColumn_Unavailable_Tooltip:
        "<span><h2>存储库健康检查不可用</h2>无法在此存储库上执行存储库健康检查（RHC），" +
        "因为它是不支持的类型或服务中断。<br><br>",

      HealthCheckSummary_Help:
        '',

      // Admin -> Repository -> Blob Stores
      Blobstores_Text: "Blob存储",
      Blobstores_Description: "管理Blob存储",
      Blobstores_Delete_Mask: "正在删除Blob存储",
      Blobstores_Update_Mask: "正在更新Blob存储",
      Blobstores_Create_Title: "创建Blob存储",
      Blobstores_Update_Success: "Blob存储已更新：{0}",
      Blobstore_BlobstoreAdd_Create_Success: "Blob存储已创建：",
      Blobstore_BlobstoreAdd_Create_Error: "您没有权限创建Blob存储",
      Blobstore_BlobstoreSettingsForm_Update_Success: "Blob存储已更新：",
      Blobstore_BlobstoreSettingsForm_Update_Error: "不支持Blob存储的更新",
      Blobstore_BlobstoreList_New_Button: "创建Blob存储",
      Blobstore_BlobstoreList_Name_Header: "名称",
      Blobstore_BlobstoreList_Type_Header: "类型",
      Blobstore_BlobstoreList_State_Header: "状态",
      Blobstore_BlobstoreList_BlobCount_Header: "Blob计数",
      Blobstore_BlobstoreList_TotalSize_Header: "总大小",
      Blobstore_BlobstoreList_AvailableSpace_Header: "可用空间",
      Blobstore_BlobstoreList_Filter_EmptyText: "没有Blob存储匹配“{$filter}”",
      Blobstore_BlobstoreList_EmptyText:
        '<div class="summary">还没有创建Blob存储<br>' +
        '<span style="font-weight: lighter; font-size: small;">或者您没有权限浏览它们</span></div>' +
        '<div class="panel nx-subsection"><h3 class="title"><span class="icon"></span>什么是Blob存储？</h3>' +
        '<p>您通过代理存储库下载的二进制资产，或发布到托管存储库的资产，都存储在与这些存储库关联的Blob存储中。在传统的单节点NXRM部署中，Blob存储通常与本地文件系统目录相关联，通常在sonatype-work目录内。</p></div>',
      Blobstore_BlobstoreList_Failed: "失败",
      Blobstore_BlobstoreList_Started: "已启动",
      Blobstore_BlobstoreList_Unlimited: "无限制",
      Blobstore_BlobstoreList_Unavailable: "不可用",
      Blobstore_BlobstoreFeature_Delete_Button: "删除Blob存储",
      Blobstore_BlobstoreFeature_Delete_Disabled_Message:
        "此Blob存储正在被以下使用：{0}, {1}, {2}，无法删除",
      Blobstore_BlobstoreFeature_Editing_Enabled_Message:
        "更新Blob存储配置将导致其暂时不可用。编辑配置可能会使Blob存储处于非功能状态。更改值时请小心。",
      Blobstore_BlobstoreFeature_Promote_Button: "提升为组",
      Blobstore_BlobstoreFeature_Confirm_Title: "创建Blob存储组？",
      Blobstore_BlobstoreFeature_Confirm_Warning: "警告：此操作无法撤销",
      Blobstore_BlobstoreFeature_Promote_Success:
        "Blob存储：{0}已提升为Blob存储组",
      Blobstore_BlobstoreFeature_Update_Title: "更新Blob存储？",
      Blobstore_BlobstoreFeature_Update_Warning:
        "警告：Blob存储将暂时不可用。此功能不迁移数据到新位置。",
      Blobstore_BlobstoreSettings_Title: "设置",
      Blobstore_BlobstoreAdd_Type_FieldLabel: "类型",
      Blobstore_BlobstoreAdd_Type_EmptyText: "选择一个类型",
      Blobstore_BlobstoreSettingsForm_Name_FieldLabel: "名称",
      Blobstore_BlobstoreSettingsForm_State_FieldLabel: "状态:",
      Blobstore_BlobstoreSettingsForm_Path_FieldLabel: "路径",
      Blobstore_BlobstoreSettingsForm_EnableSoftQuota_FieldLabel: "启用软配额",
      Blobstore_BlobstoreSettingsForm_SoftQuota_HelpText:
        "软配额在违反限制时提供警告。它从不会导致操作被拒绝",
      Blobstore_BlobstoreSettingsForm_QuotaType_FieldLabel: "配额类型",
      Blobstore_BlobstoreSettingsForm_QuotaLimit_FieldLabel: "配额限制（MB）",

      // Admin -> Repository -> Selectors
      Selectors_Text: "内容选择器",
      Selectors_Description: "管理内容选择器",
      Selectors_Create_Title: "创建选择器",
      Selector_SelectorAdd_Create_Error: "您没有权限创建选择器",
      Selector_SelectorAdd_Create_Success: "选择器已创建：{0}",
      Selector_SelectorSettingsForm_Update_Error: "您没有权限更新选择器",
      Selector_SelectorSettingsForm_Update_Success: "选择器已更新：{0}",
      Selector_SelectorList_New_Button: "创建选择器",
      Selector_SelectorList_Name_Header: "名称",
      Selector_SelectorList_Type_Header: "类型",
      Selector_SelectorList_Description_Header: "描述",
      Selector_SelectorList_EmptyText:
        '<div class="summary">还没有创建内容选择器<br>' +
        '<span style="font-weight: lighter; font-size: small;">或者您没有权限浏览它们</span></div>' +
        '<div class="panel nx-subsection"><h3 class="title"><span class="icon"></span>什么是内容选择器？</h3>' +
        '<p>内容选择器为您提供了从所有内容中选择特定内容的手段。您选择的内容将根据CSEL（内容选择器表达语言）编写的表达式进行评估。</p></div>',
      Selector_SelectorList_Filter_EmptyText: "没有选择器匹配“{$filter}”",
      Selector_SelectorFeature_Delete_Button: "删除选择器",
      Selector_SelectorFeature_Delete_Disabled_Message:
        "此选择器不能被删除，因为它正在被{0}使用",
      Selectors_Delete_Message: "选择器已删除：{0}",
      Selector_SelectorFeature_Settings_Title: "设置",
      Selector_SelectorSettingsForm_Name_FieldLabel: "名称",
      Selector_SelectorSettingsForm_Type_FieldLabel: "类型",
      Selector_SelectorSettingsForm_Type_Jexl: "JEXL",
      Selector_SelectorSettingsForm_Type_Sonatype: "CSEL",
      Selector_SelectorSettingsForm_Description_FieldLabel: "描述",
      Selector_SelectorSettingsForm_Expression_FieldLabel: "搜索表达式",
      Selector_SelectorSettingsForm_Expression_HelpText:
        "使用查询来识别存储库、组件或资产",
      Selector_SelectorSettingsForm_Expression_Examples:
        '<div style="font-size: 11px"><br/>' +
        "<h4>内容选择器表达式示例：</h4>" +
        '<p>选择所有“raw”格式内容<br/><i>format == "raw"</i></p>' +
        '<p>选择所有路径以“/org/sonatype/nexus”开头的“maven2”内容<br/></p>' +
        "<br/>" +
        "</div>",
      Selector_SelectorSettingsForm_Expression_Examples_jexl:
        '<div style="font-size: 11px"><br/>' +
        "<h4>JEXL查询示例：</h4>" +
        '<p>选择所有“raw”格式内容<br/><i>format == "raw"</i></p>' +
        '<p>选择所有路径以“/org/sonatype/nexus”开头的“maven2”内容<br/></p>' +
        "<br/>" +
        "</div>",
      Selector_SelectorSettingsForm_SelectorID_Title: "选择器ID",
      Selector_SelectorSettingsForm_Specification_Title: "规范",
      Selector_SelectorSettingsForm_Preview_Button: "预览结果",

      // Admin -> Repository -> Selectors -> Preview Window
      SelectorPreviewWindow_Title: "预览结果",
      SelectorPreviewWindow_expression_FieldLabel: "表达式",
      SelectorPreviewWindow_expression_jexl: "JEXL",
      SelectorPreviewWindow_expression_csel: "CSEL",
      SelectorPreviewWindow_type_FieldLabel: "类型",
      SelectorPreviewWindow_repository_FieldLabel: "预览存储库",
      SelectorPreviewWindow_repository_HelpText:
        "选择一个存储库来评估内容选择器并查看可用的内容。",
      SelectorPreviewWindow_repository_EmptyText: "选择一个存储库...",
      SelectorPreviewWindow_EmptyText_View: "存储库中没有资产匹配表达式",
      SelectorPreviewWindow_EmptyText_Filter: "没有资产匹配“{$filter}”",
      SelectorPreviewWindow_Name_Column: "名称",
      SelectorPreviewWindow_Preview_Button: "预览",

      // Admin -> Security
      FeatureGroups_Security_Title: "安全",
      FeatureGroups_Security_Description: "安全管理",

      // Admin -> Security -> Privileges
      Privileges_Text: "权限",
      Privileges_Description: "管理权限",
      Privileges_Update_Mask: "正在更新权限",
      Privileges_Update_Success: "权限已更新：{0}",
      Privileges_Create_Success: "权限已创建：{0}",
      Privileges_Delete_Success: "权限已删除：{0}",
      Privileges_Select_Title: "选择权限类型",
      Privilege_PrivilegeList_New_Button: "创建权限",
      Privilege_PrivilegeList_Name_Header: "名称",
      Privilege_PrivilegeList_Description_Header: "描述",
      Privilege_PrivilegeList_Type_Header: "类型",
      Privilege_PrivilegeList_Permission_Header: "权限",
      Privilege_PrivilegeList_EmptyText: "没有定义权限",
      Privilege_PrivilegeList_Filter_EmptyText: "没有权限匹配“{$filter}”",
      Privilege_PrivilegeFeature_Details_Tab: "摘要",
      Privilege_PrivilegeFeature_Delete_Button: "删除权限",
      Privilege_PrivilegeFeature_Settings_Title: "设置",
      Privilege_PrivilegeSelectType_Type_Header: "类型",
      Privilege_PrivilegeAdd_Create_Error: "您没有权限创建权限",
      Privilege_PrivilegeSettingsForm_Update_Success: "权限已更新：{0}",
      Privilege_PrivilegeSettingsForm_Update_Error:
        "您没有权限更新权限或权限为只读",
      Privilege_PrivilegeSettingsForm_Description_FieldLabel: "描述",
      Privilege_PrivilegeSettingsForm_Name_FieldLabel: "名称",
      Privileges_Summary_ID: "ID",
      Privileges_Summary_Type: "类型",
      Privileges_Summary_Name: "名称",
      Privileges_Summary_Description: "描述",
      Privileges_Summary_Permission: "权限",
      Privileges_Summary_Property: "属性-{0}",
      Privileges_Create_Title: "创建{0}权限",

      // Admin -> Security -> Roles
      Roles_Text: "角色",
      Roles_Description: "管理角色",
      Roles_Create_Title: "创建角色",
      Role_RoleAdd_Create_Error: "您没有权限创建角色",
      Role_RoleAdd_Create_Success: "角色已创建：",
      Role_RoleSettingsForm_Update_Error: "您没有权限更新角色或角色为只读",
      Role_RoleSettingsForm_Update_Success: "角色已更新：",
      Role_RoleList_New_Button: "创建角色",
      Role_RoleList_New_NexusRoleItem: "角色",
      Roles_New_ExternalRoleItem: "外部角色映射",
      Role_RoleList_Name_Header: "名称",
      Role_RoleList_Source_Header: "来源",
      Role_RoleList_Description_Header: "描述",
      Role_RoleList_EmptyText: "没有定义角色",
      Role_RoleList_Filter_EmptyText: "没有角色匹配“{$filter}”",
      Role_RoleFeature_Delete_Button: "删除角色",
      Roles_Delete_Message: "角色已删除：{0}",
      Role_RoleFeature_Settings_Title: "设置",
      Role_RoleSettingsForm_RoleID_FieldLabel: "角色ID",
      Role_RoleSettingsForm_MappedRole_FieldLabel: "映射角色",
      Role_RoleSettingsForm_MappedRole_EmptyText: "选择一个角色",
      Role_RoleSettingsForm_Name_FieldLabel: "角色名称",
      Role_RoleSettingsForm_Description_FieldLabel: "角色描述",
      Role_RoleSettingsForm_Privileges_FieldLabel: "权限",
      Role_RoleSettingsForm_Privileges_FromTitle: "可用",
      Role_RoleSettingsForm_Privileges_ToTitle: "已授权",
      Role_RoleSettingsForm_Roles_FieldLabel: "角色",
      Role_RoleSettingsForm_Roles_FromTitle: "可用",
      Role_RoleSettingsForm_Roles_ToTitle: "包含",

      // Admin -> Security -> Users
      User_Text: "用户",
      User_Description: "管理用户",
      User_UserSettingsForm_Update_Error: "您没有权限更新用户或用户为外部用户",
      User_UserSettingsForm_Update_Success: "用户已更新：",
      User_UserSettingsForm_UpdateRoles_Success: "用户角色映射已更新：{0}",
      User_UserSettingsExternalForm_Remove_Error: "无法移除角色",
      Users_Create_Title: "创建用户",
      User_UserAdd_Password_FieldLabel: "密码",
      User_UserAdd_PasswordConfirm_FieldLabel: "确认密码",
      User_UserChangePassword_NoMatch_Error: "密码不匹配",
      User_UserAdd_Create_Error: "您没有权限创建用户",
      User_UserAdd_Create_Success: "用户已创建：",
      User_UserChangePassword_Title: "更改密码",
      User_UserChangePassword_Password_FieldLabel: "新密码",
      User_UserChangePassword_PasswordConfirm_FieldLabel: "确认密码",
      User_UserChangePassword_Submit_Button: "更改密码",
      User_UserChangePassword_Cancel_Button: "@Button_Cancel",
      User_UserChangePassword_NoPermission_Error: "您没有权限更改密码",
      User_UserList_New_Button: "创建本地用户",
      User_UserList_Source_Label: "来源：",
      User_UserList_Default_Button: "默认",
      User_UserList_Filter_EmptyText: "通过用户ID过滤",
      User_UserList_ID_Header: "用户ID",
      User_UserList_Realm_Header: "域",
      User_UserList_FirstName_Header: "名字",
      User_UserList_LastName_Header: "姓氏",
      User_UserList_Email_Header: "电子邮件",
      User_UserList_Status_Header: "状态",
      User_UserList_EmptyText: "没有定义用户",
      User_UserFeature_Delete_Button: "删除用户",
      Users_Delete_Success: "用户已删除：{0}",
      User_UserFeature_More_Button: "更多",
      User_UserFeature_ChangePasswordItem: "更改密码",
      Users_Change_Success: "密码已更改",
      User_UserFeature_Settings_Title: "设置",
      User_UserSettingsForm_ID_FieldLabel: "ID",
      User_UserSettingsForm_ID_HelpText: "这将用作用户名",
      User_UserSettingsForm_FirstName_FieldLabel: "名字",
      User_UserSettingsForm_LastName_FieldLabel: "姓氏",
      User_UserSettingsForm_Email_FieldLabel: "电子邮件",
      User_UserSettingsForm_Email_HelpText: "用于通知",
      User_UserSettingsForm_Status_FieldLabel: "状态",
      User_UserSettingsForm_Status_EmptyText: "选择状态",
      User_UserSettingsForm_Status_ActiveItem: "激活",
      User_UserSettingsForm_Status_DisabledItem: "禁用",
      User_UserSettingsExternalForm_Roles_FieldLabel: "角色",
      User_UserSettingsExternalForm_Roles_FromTitle: "可用",
      User_UserSettingsExternalForm_Roles_ToTitle: "已授权",
      User_UserSettingsExternalForm_ExternalRoles_FieldLabel: "外部角色",
      User_UserSettingsExternalForm_ExternalRoles_HelpText:
        "外部角色应在其源处管理，此处无法管理",

      // Admin -> Security -> Anonymous
      AnonymousSettings_Text: "匿名",
      AnonymousSettings_Description: "无需认证即可浏览服务器内容",
      SamlConfiguration_Text: "SAML配置",
      SamlConfiguration_Description: "SAML身份提供者配置",
      Security_AnonymousSettings_Update_Error: "您没有权限配置匿名用户",
      Security_AnonymousSettings_Update_Success: "匿名安全设置已$action",
      Security_AnonymousSettings_Allow_BoxLabel: "允许匿名用户访问服务器",
      Security_AnonymousSettings_Username_FieldLabel: "用户名",
      Security_AnonymousSettings_Realm_FieldLabel: "域",

      // Admin -> Security -> LDAP
      LdapServers_Text: "LDAP",
      LdapServers_Description: "管理LDAP服务器配置",
      LdapServers_Update_Mask: "正在更新LDAP连接",
      LdapServers_Update_Success: "LDAP服务器已更新：{0}",
      Ldap_LdapServerConnectionForm_Update_Error: "您没有权限更新LDAP服务器",
      LdapServers_Create_Mask: "正在创建LDAP连接",
      LdapServers_CreateConnection_Title: "创建LDAP连接",
      LdapServers_CreateUsersAndGroups_Title: "选择用户和组",
      LdapServers_Create_Success: "LDAP服务器已创建：{0}",
      Ldap_LdapServerConnectionAdd_Create_Error: "您没有权限创建LDAP服务器",
      LdapServers_Delete_Success: "LDAP服务器已删除：{0}",
      Ldap_LdapServerChangeOrder_Title: "更改LDAP服务器排序",
      LdapServers_ChangeOrder_Success: "LDAP服务器排序已更改",
      Ldap_LdapServerUserAndGroupLoginCredentials_Title: "登录凭据",
      Ldap_LdapServerUserAndGroupLoginCredentials_Text:
        "您请求的操作需要验证您的凭据。",
      Ldap_LdapServerUserAndGroupLoginCredentials_Input_Text:
        "<div>输入您的LDAP服务器凭据</div>",
      Ldap_LdapServerUserAndGroupLoginCredentials_Username_FieldLabel:
        "LDAP服务器用户名",
      Ldap_LdapServerUserAndGroupLoginCredentials_Password_FieldLabel:
        "LDAP服务器密码",
      Ldap_LdapServerUserAndGroupLoginCredentials_Submit_Button: "测试连接",
      Ldap_LdapServerUserAndGroupLoginCredentials_Cancel_Button:
        "@Button_Cancel",
      Ldap_LdapServerUserAndGroupMappingTestResults_Title: "用户映射测试结果",
      Ldap_LdapServerUserAndGroupMappingTestResults_ID_Header: "用户ID",
      Ldap_LdapServerUserAndGroupMappingTestResults_Name_Header: "名称",
      Ldap_LdapServerUserAndGroupMappingTestResults_Email_Header: "电子邮件",
      Ldap_LdapServerUserAndGroupMappingTestResults_Roles_Header: "角色",
      Ldap_LdapServerList_New_Button: "创建连接",
      Ldap_LdapServerList_ChangeOrder_Button: "更改顺序",
      Ldap_LdapServerList_ClearCache_Button: "清除缓存",
      Ldap_LdapServerList_Order_Header: "顺序",
      Ldap_LdapServerList_Name_Header: "名称",
      Ldap_LdapServerList_URL_Header: "URL",
      Ldap_LdapServerList_Filter_EmptyText: "没有LDAP服务器匹配“{$filter}”",
      Ldap_LdapServerList_EmptyText:
        '<div class="summary">还没有定义LDAP服务器<br>' +
        '<span style="font-weight: lighter; font-size: small;">或者您没有权限浏览它们</span></div>' +
        '<div class="panel nx-subsection"><h3 class="title"><span class="icon"></span>什么是LDAP？</h3>' +
        '<p>您可以配置NXRM实例使用LDAP进行认证和用户角色映射。存储库管理器可以缓存认证信息，并支持多个LDAP服务器和用户/组映射，以利用组织中所有存储库管理器的中央认证设置。</p></div>',
      Ldap_LdapServerFeature_Delete_Button: "删除连接",
      Ldap_LdapServerFeature_Connection_Title: "连接",
      Ldap_LdapServerFeature_UserAndGroup_Title: "用户和组",
      LdapServers_ClearCache_Success: "LDAP缓存已清除",
      LdapServers_VerifyConnection_Mask: "正在检查与{0}的连接",
      LdapServers_VerifyConnection_Success: "与LDAP服务器的连接已验证：{0}",
      LdapServers_VerifyMapping_Mask: "正在检查{0}上的用户映射",
      LdapServers_VerifyMapping_Success: "LDAP服务器用户映射已验证：{0}",
      LdapServers_VerifyLogin_Mask: "正在检查{0}上的登录",
      LdapServers_VerifyLogin_Success: "LDAP登录已成功完成：{0}",
      LdapServersConnectionFieldSet_Address_Text: "LDAP服务器地址:",
      LdapServersConnectionFieldSet_Address_HelpText:
        "LDAP服务器通常监听端口389（ldap://）或端口636（ldaps://）",
      LdapServersConnectionFieldSet_Name_FieldLabel: "名称",
      LdapServersConnectionFieldSet_Protocol_EmptyText: "协议",
      LdapServersConnectionFieldSet_Protocol_PlainItem: "ldap",
      LdapServersConnectionFieldSet_Protocol_SecureItem: "ldaps",
      LdapServersConnectionFieldSet_Host_EmptyText: "主机名",
      LdapServersConnectionFieldSet_Port_EmptyText: "端口",
      LdapServersConnectionFieldSet_Base_FieldLabel: "搜索基础DN",
      LdapServersConnectionFieldSet_Base_HelpText:
        'LDAP连接URL中要添加的位置（例如 "dc=example,dc=com"）',
      LdapServersConnectionFieldSet_AuthMethod_FieldLabel: "认证方法",
      LdapServersConnectionFieldSet_AuthMethod_EmptyText: "选择认证方法",
      LdapServersConnectionFieldSet_AuthMethod_SimpleItem: "简单认证",
      LdapServersConnectionFieldSet_AuthMethod_AnonymousItem: "匿名认证",
      LdapServersConnectionFieldSet_AuthMethod_DigestItem: "DIGEST-MD5",
      LdapServersConnectionFieldSet_AuthMethod_CramItem: "CRAM-MD5",
      LdapServersConnectionFieldSet_SaslRealm_FieldLabel: "SASL域",
      LdapServersConnectionFieldSet_SaslRealm_HelpText:
        "要绑定的SASL域（例如 mydomain.com）",
      LdapServersConnectionFieldSet_Username_FieldLabel: "用户名或DN",
      LdapServersConnectionFieldSet_Username_HelpText:
        "如果使用简单认证，则必须是完全合格的用户名",
      LdapServersConnectionFieldSet_ChangePasswordItem: "更改密码",
      LdapServersConnectionFieldSet_Password_FieldLabel: "密码",
      LdapServersConnectionFieldSet_Password_HelpText: "绑定使用的密码",
      LdapServersConnectionFieldSet_Rules_Text: "连接规则",
      LdapServersConnectionFieldSet_Rules_HelpText:
        "设置超时参数和最大连接尝试次数，以避免被黑名单",
      LdapServersConnectionFieldSet_Rules_Text1: "等待 ",
      LdapServersConnectionFieldSet_Rules_Text2: " 秒后超时。在 ",
      LdapServersConnectionFieldSet_Rules_Text3: " 秒后重试，最多 ",
      LdapServersConnectionFieldSet_Rules_Text4: " 次失败尝试。",
      Ldap_LdapServerConnectionForm_VerifyConnection_Button: "验证连接",
      Ldap_LdapServerUserAndGroupFieldSet_Template_FieldLabel: "配置模板",
      Ldap_LdapServerUserAndGroupFieldSet_Template_EmptyText: "选择模板",
      Ldap_LdapServerUserAndGroupFieldSet_BaseDN_FieldLabel: "用户相对DN",
      Ldap_LdapServerUserAndGroupFieldSet_BaseDN_HelpText:
        "用户对象所在的位置（例如 ou=people）。这个值将被追加到搜索基础DN以形成完整的用户搜索基础DN",
      Ldap_LdapServerUserAndGroupFieldSet_UserSubtree_FieldLabel: "用户子树",
      Ldap_LdapServerUserAndGroupFieldSet_UserSubtree_HelpText:
        "用户是否位于用户基础DN以下的结构中？",
      Ldap_LdapServerUserAndGroupFieldSet_ObjectClass_FieldLabel: "对象类",
      Ldap_LdapServerUserAndGroupFieldSet_ObjectClass_HelpText:
        "用户对象的LDAP类（例如 inetOrgPerson）",
      Ldap_LdapServerUserAndGroupFieldSet_UserFilter_FieldLabel: "用户过滤器",
      Ldap_LdapServerUserAndGroupFieldSet_UserFilter_HelpText:
        '限制用户搜索的LDAP搜索过滤器（例如 "attribute=foo" 或 "(|(mail=*@example.com)(uid=dom*))"）',
      Ldap_LdapServerUserAndGroupFieldSet_UserID_FieldLabel: "用户ID属性",
      Ldap_LdapServerUserAndGroupFieldSet_RealName_FieldLabel: "真实姓名属性",
      Ldap_LdapServerUserAndGroupFieldSet_Email_FieldLabel: "电子邮件属性",
      Ldap_LdapServerUserAndGroupFieldSet_Password_FieldLabel: "密码属性",
      Ldap_LdapServerUserAndGroupFieldSet_Password_HelpText:
        "如果此字段为空，则用户将被认证对LDAP服务器的绑定。",
      Ldap_LdapServerUserAndGroupFieldSet_GroupMap_FieldLabel:
        "将LDAP组映射为角色",
      Ldap_LdapServerUserAndGroupFieldSet_GroupType_FieldLabel: "组类型",
      Ldap_LdapServerUserAndGroupFieldSet_GroupType_EmptyText: "选择组类型",
      Ldap_LdapServerUserAndGroupFieldSet_GroupType_DynamicItem: "动态组",
      Ldap_LdapServerUserAndGroupFieldSet_GroupType_StaticItem: "静态组",
      Ldap_LdapServerUserAndGroupFieldSet_GroupBaseDN_FieldLabel: "组相对DN",
      Ldap_LdapServerUserAndGroupFieldSet_GroupBaseDN_HelpText:
        "组对象所在的位置（例如 ou=Group）。这个值将被追加到搜索基础DN以形成完整的组搜索基础DN",
      Ldap_LdapServerUserAndGroupFieldSet_GroupSubtree_FieldLabel: "组子树",
      Ldap_LdapServerUserAndGroupFieldSet_GroupSubtree_HelpText:
        "组是否位于组基础DN以下的结构中。",
      Ldap_LdapServerUserAndGroupFieldSet_GroupObject_FieldLabel: "组对象类",
      Ldap_LdapServerUserAndGroupFieldSet_GroupObject_HelpText:
        "组对象的LDAP类（例如 posixGroup）",
      Ldap_LdapServerUserAndGroupFieldSet_GroupID_FieldLabel: "组ID属性",
      Ldap_LdapServerUserAndGroupFieldSet_GroupMember_FieldLabel: "组成员属性",
      Ldap_LdapServerUserAndGroupFieldSet_GroupMember_HelpText:
        "包含组中用户名的LDAP属性。",
      Ldap_LdapServerUserAndGroupFieldSet_GroupMemberFormat_FieldLabel:
        "组成员格式",
      Ldap_LdapServerUserAndGroupFieldSet_GroupMemberFormat_HelpText:
        '存储在组成员属性中的用户ID格式（例如 "uid=${username},ou=people,dc=example,dc=com"）',
      Ldap_LdapServerUserAndGroupFieldSet_GroupMemberOf_FieldLabel:
        "组成员资格属性",
      Ldap_LdapServerUserAndGroupFieldSet_GroupMemberOf_HelpText:
        "设置此属性以存储用户对象中包含组DN的属性",
      Ldap_LdapServerUserAndGroupForm_VerifyGroupMapping_Button: "验证用户映射",
      Ldap_LdapServerUserAndGroupForm_VerifyLogin_Button: "验证登录",

      // Admin -> Security -> Realms
      RealmSettings_Text: "域设置",
      RealmSettings_Description: "管理活动的安全域及其顺序",
      Security_RealmSettings_Update_Error: "您没有权限配置域",
      Security_RealmSettings_Update_Success: "安全域设置已$action",
      Security_RealmSettings_Available_FromTitle: "可用",
      Security_RealmSettings_Available_ToTitle: "活动",

      // Admin -> Security -> SSL Certificates
      SslCertificates_Text: "SSL证书",
      SslCertificates_Description: "管理信任库中用于SSL的信任证书",
      SslCertificates_Paste_Title: "粘贴PEM格式证书",
      Ssl_SslCertificateAddFromPem_Cancel_Button: "@Button_Cancel",
      SslCertificates_Load_Title: "从服务器加载证书",
      Ssl_SslCertificateAddFromServer_Load_FieldLabel:
        "请输入一个主机名、主机名:端口或URL以从服务器获取SSL证书",
      SslTrustStore_Load_Mask: "正在加载证书...",
      Ssl_SslCertificateAddFromServer_Cancel_Button: "@Button_Cancel",
      SslCertificates_Load_Success: "SSL证书已创建：{0}",
      Ssl_SslCertificateList_New_Button: "加载证书",
      Ssl_SslCertificateList_Load_Button: "从服务器加载",
      Ssl_SslCertificateList_Paste_Button: "粘贴PEM",
      Ssl_SslCertificateList_Name_Header: "名称",
      Ssl_SslCertificateList_IssuedTo_Header: "发行给",
      Ssl_SslCertificateList_IssuedBy_Header: "发行者",
      Ssl_SslCertificateList_Fingerprint_Header: "指纹",
      Ssl_SslCertificateList_EmptyText:
        '<div class="summary">还没有定义SSL证书<br>' +
        '<span style="font-weight: lighter; font-size: small;">或者您没有权限浏览它们</span></div>' +
        '<div class="panel nx-subsection"><h3 class="title"><span class="icon"></span>什么是SSL？</h3>' +
        '<p>使用安全套接层（SSL）通信与存储库管理器是重要的安全特性和推荐的最佳实践。安全通信可以是入站或出站的。出站客户端通信可能包括：与代理存储库、电子邮件服务器、LDAPS服务器的集成。入站客户端通信包括：Web浏览器HTTPS访问、工具访问存储库内容、使用REST API。</p></div>',
      Ssl_SslCertificateList_Filter_EmptyText: "没有SSL证书匹配“{$filter}”",
      Ssl_SslCertificateDetailsWindow_Title: "证书详情",
      SslCertificates_Remove_Button: "从信任库中移除证书",
      SslCertificates_Add_Button: "添加证书到信任库",
      Ssl_SslCertificateFeature_Delete_Button: "删除证书",
      SslCertificates_Delete_Success: "SSL证书已删除：{0}",
      Ssl_SslCertificateDetailsWindow_Cancel_Button: "@Button_Cancel",
      Ssl_SslCertificateDetailsForm_Subject_Title: "主题",
      Ssl_SslCertificateDetailsForm_SubjectCommonName_FieldLabel: "通用名称",
      Ssl_SslCertificateDetailsForm_SubjectOrganization_FieldLabel: "组织",
      Ssl_SslCertificateDetailsForm_SubjectUnit_FieldLabel: "单位",
      Ssl_SslCertificateDetailsForm_Issuer_Title: "发行者",
      Ssl_SslCertificateDetailsForm_IssuerName_FieldLabel: "通用名称",
      Ssl_SslCertificateDetailsForm_IssuerOrganization_FieldLabel: "组织",
      Ssl_SslCertificateDetailsForm_IssuerUnit_FieldLabel: "单位",
      Ssl_SslCertificateDetailsForm_Certificate_Title: "证书",
      Ssl_SslCertificateDetailsForm_CertificateIssuedOn_FieldLabel: "发行日期",
      Ssl_SslCertificateDetailsForm_CertificateValidUntil_FieldLabel:
        "有效期至",
      Ssl_SslCertificateDetailsForm_CertificateFingerprint_FieldLabel: "指纹",
      Ssl_SslCertificateDetailsForm_RetrievedUntrustedConnection_Html:
        "<b>此证书是通过不受信任的连接检索的。在添加之前，请始终验证详细信息。</b>",

      // Admin -> Support
      FeatureGroups_Support_Text: "支持",
      FeatureGroups_Support_Description: "支持工具",

      // Admin -> Support -> Metrics
      Metrics_Text: "指标",
      Metrics_Description: "提供服务器指标",
      Metrics_Load_Mask: "正在加载...",
      Metrics_Refresh_Warning: "无法刷新指标数据",
      Support_Metrics_Download_Button: "下载",
      Metrics_Download_Tooltip: "下载指标数据",
      Support_Metrics_Dump_Button: "线程转储",
      Support_Metrics_Dump_Tooltip: "下载线程转储",
      Support_Metrics_MemoryUsage_Title: "内存使用情况",
      Support_Metrics_Heap_Title: "堆",
      Metrics_Heap_NonHeapItem: "非堆",
      Metrics_Heap_Available: "可用",
      Support_Metrics_ThreadStates_Title: "线程状态",
      Metrics_ThreadStates_New: "新建",
      Metrics_ThreadStates_Terminated: "已终止",
      Metrics_ThreadStates_Blocked: "阻塞",
      Metrics_ThreadStates_Runnable: "可运行",
      Metrics_ThreadStates_TimedWaiting: "等待超时",
      Metrics_ThreadStates_Waiting: "等待",
      Support_Metrics_Dispatches_Title: "活动Web请求",
      Support_Metrics_ResponseCode_Title: "Web响应代码",
      Support_Metrics_Requests_Title: "Web请求",

      // Admin -> Support -> System Information
      SysInfo_Title: "系统信息",
      SysInfo_Description: "显示系统信息",
      SysInfo_Load_Mask: "正在加载...",
      Support_SysInfo_Download_Button: "下载",

      // Admin -> System
      FeatureGroups_System_Text: "系统",
      FeatureGroups_System_Description: "系统管理",

      // Admin -> System -> API
      Api_Text: "API",
      Api_Description: "了解如何以编程方式与可信软件仓库交互",

      // Admin -> System -> Capabilities
      Capabilities_Text: "功能",
      Capabilities_Description: "管理功能",
      Capabilities_Update_Mask: "正在更新功能",
      Capabilities_Enable_Mask: "正在启用功能",
      Capabilities_Disable_Mask: "正在禁用功能",
      Capabilities_Update_Error: "您没有权限更新功能",
      Capability_CapabilityAdd_Create_Error: "您没有权限更新功能",
      Capabilities_Update_Success: "功能已更新：{0}",
      Capability_CapabilitySettingsForm_Update_Error: "您没有权限创建功能",
      Capabilities_Create_Title: "创建{0}功能",
      Capabilities_Create_Success: "功能已创建：{0}",
      Capabilities_Delete_Success: "功能已删除：{0}",
      Capability_CapabilityList_New_Button: "创建功能",
      Capability_CapabilityList_Type_Header: "类型",
      Capability_CapabilityList_Description_Header: "描述",
      Capability_CapabilityList_Notes_Header: "备注",
      Capability_CapabilityList_EmptyText: "没有定义功能",
      Capability_CapabilityList_Filter_EmptyText: "没有功能匹配条件“{$filter}”",
      Capability_CapabilityFeature_Delete_Button: "删除",
      Capability_CapabilityFeature_Enable_Button: "启用",
      Capability_CapabilityFeature_Disable_Button: "禁用",
      Capability_CapabilitySummary_Title: "摘要",
      Capability_CapabilitySettings_Title: "设置",
      Capability_CapabilitySettingsForm_Enabled_FieldLabel: "启用此功能",
      Capability_CapabilitySummary_Status_Title: "状态",
      Capability_CapabilitySummary_About_Title: "关于",
      Capability_CapabilitySummary_Notes_Title: "备注",
      Capabilities_Enable_Text: "功能已启用：{0}",
      Capabilities_Disable_Text: "功能已禁用：{0}",
      Capabilities_Select_Title: "选择功能类型",
      Capability_CapabilitySelectType_Description_Header: "描述",
      Capability_CapabilitySelectType_Type_Header: "类型",
      Capabilities_TypeName_Text: "类型",
      Capabilities_Description_Text: "描述",
      Capabilities_State_Text: "状态",
      Capability_CapabilitySummary_Notes_HelpText: "关于配置功能可选的备注",
      Capability_CapabilityStatus_EmptyText: "此功能不提供任何状态信息",

      // Admin -> System -> Cleanup Policies
      CleanupPolicies_Text: "清理策略",
      CleanupPolicies_Description: "管理组件移除配置",
      CleanupPolicies_Create_Title: "创建清理策略",
      CleanupPolicies_Delete_Title: "确认删除？",
      CleanupPolicies_Delete_Description: "此清理策略没有被任何存储库使用",
      CleanupPolicies_Delete_Description_Multiple: "此清理策略被{0}存储库使用",
      CleanupPolicies_Delete_Success: "清理策略已删除：{0}",
      CleanupPolicy_CleanupPolicyList_Preview_Button: "预览结果",
      CleanupPolicy_CleanupPolicyList_New_Button: "创建清理策略",
      CleanupPolicy_CleanupPolicyList_Filter_EmptyState:
        "没有清理策略匹配“{$filter}”",
      CleanupPolicy_CleanupPolicyList_EmptyState:
        '<div class="summary">还没有创建清理策略<br>' +
        '<span style="font-weight: lighter; font-size: small;">或者您没有权限浏览它们</span></div>' +
        '<div class="panel nx-subsection"><h3 class="title"><span class="icon"></span>什么是清理策略？</h3>' +
        '<p>清理策略可用于从您的存储库中移除内容。这些策略将在配置的频率下执行。创建后，必须从<a href="#admin/repository/repositories">存储库配置屏幕</a>分配清理策略给存储库。</p></div>',
      CleanupPolicy_CleanupPolicyList_Name_Header: "名称",
      CleanupPolicy_CleanupPolicyList_Format_Header: "格式",
      CleanupPolicy_CleanupPolicyList_Notes_Header: "备注",
      CleanupPolicy_CleanupPolicyFeature_Settings_Title: "设置",
      CleanupPolicy_CleanupPolicyFeature_Delete_Button: "删除",
      CleanupPolicy_CleanupPolicySettingsForm_Update_Success:
        "清理策略已更新：",
      CleanupPolicy_CleanupPolicySettingsForm_Update_Error:
        "您没有权限更新清理策略",
      CleanupPolicy_CleanupPolicySettingsForm_CleanupPolicy_Title: "清理策略",
      CleanupPolicy_CleanupPolicySettingsForm_Name_FieldLabel: "名称",
      CleanupPolicy_CleanupPolicySettingsForm_Name_HelpText:
        "清理策略的唯一名称",
      CleanupPolicy_CleanupPolicySettingsForm_Format_FieldLabel: "格式",
      CleanupPolicy_CleanupPolicySettingsForm_Format_HelpText:
        "此清理策略可以应用的格式",
      CleanupPolicy_CleanupPolicySettingsForm_Notes_FieldLabel: "备注",
      CleanupPolicy_CleanupPolicySettingsForm_Criteria_Title: "标准",
      CleanupPolicy_CleanupPolicySettingsForm_AddCriteria_Text: "添加标准",
      CleanupPolicy_CleanupPolicySettingsForm_LastBlobUpdated_FieldLabel:
        "发布前",
      CleanupPolicy_CleanupPolicySettingsForm_LastBlobUpdated_HelpText:
        "限制清理到NXRM发布超过给定天数的组件。（Blob更新日期）",
      CleanupPolicy_CleanupPolicySettingsForm_LastDownloaded_FieldLabel:
        "最后下载前",
      CleanupPolicy_CleanupPolicySettingsForm_LastDownloaded_HelpText:
        "限制清理到最后下载超过给定天数的组件（最后下载日期）或者从未下载且上传日期超过给定天数的组件",
      CleanupPolicy_CleanupPolicySettingsForm_IsPrerelease_FieldLabel:
        "发布类型",
      CleanupPolicy_CleanupPolicySettingsForm_IsPrerelease_HelpText:
        "限制清理到此发布类型的组件",
      CleanupPolicy_CleanupPolicySettingsForm_IsPrerelease_Prereleases_Item:
        "预发布/快照版本",
      CleanupPolicy_CleanupPolicySettingsForm_IsPrerelease_Releases_Item:
        "发布版本",
      CleanupPolicy_CleanupPolicySettingsForm_Regex_FieldLabel:
        "资产名称匹配器",
      CleanupPolicy_CleanupPolicySettingsForm_Regex_HelpText:
        '限制清理到至少有一个资产名称匹配指定的正则表达式模式的组件。' ,
      CleanupPolicy_CleanupPolicyAdd_Create_Error: "您没有权限创建清理策略",
      CleanupPolicy_CleanupPolicyAdd_Create_Success: "清理策略已创建：",
      CleanupPolicy_CleanupPolicyPreviewWindow_Title: "清理策略预览",
      CleanupPolicy_CleanupPolicyPreviewWindow_repository_FieldLabel:
        "预览存储库",
      CleanupPolicy_CleanupPolicyPreviewWindow_repository_HelpText:
        "选择一个存储库来预览如果应用此策略可能会被清理的内容",
      CleanupPolicy_CleanupPolicyPreviewWindow_repository_EmptyText:
        "选择一个存储库",
      CleanupPolicy_CleanupPolicyPreviewWindow_Preview_Button: "预览",
      CleanupPolicy_CleanupPolicyPreviewWindow_EmptyText_View:
        "存储库中没有资产匹配标准",
      CleanupPolicy_CleanupPolicyPreviewWindow_EmptyText_Filter:
        "没有资产匹配“{$filter}”",
      CleanupPolicy_CleanupPolicyPreviewWindow_Group_Column: "组",
      CleanupPolicy_CleanupPolicyPreviewWindow_Name_Column: "名称",
      CleanupPolicy_CleanupPolicyPreviewWindow_Version_Column: "版本",
      CleanupPolicy_CleanupPolicyPreviewWindow_Total_Component_Count:
        "匹配标准的组件计数查看",
      CleanupPolicy_CleanupPolicyPreviewWindow_Total_Component_Count_Out_Of:
        " 共 ",
      CleanupPolicy_CleanupPolicyPreviewWindow_Warning:
        "结果可能只是使用当前标准将被删除的内容的样本",

      // Admin -> System -> Email Server
      SmtpSettings_Text: "电子邮件服务器",
      SmtpSettings_Description: "管理电子邮件服务器配置",
      System_SmtpSettings_Update_Error: "您没有权限配置电子邮件服务器",
      System_SmtpSettings_Update_Success: "电子邮件服务器配置已$action",
      System_SmtpSettings_Enabled_FieldLabel: "启用",
      System_SmtpSettings_Host_FieldLabel: "主机",
      System_SmtpSettings_Port_FieldLabel: "端口",
      System_SmtpSettings_Username_FieldLabel: "用户名",
      System_SmtpSettings_Password_FieldLabel: "密码",
      System_SmtpSettings_FromAddress_FieldLabel: "发件人地址",
      System_SmtpSettings_SubjectPrefix_FieldLabel: "主题前缀",
      System_SmtpSettings_SslTlsSection_FieldLabel: "SSL/TLS选项",
      System_SmtpSettings_StartTlsEnabled_FieldLabel:
        "启用不安全连接的STARTTLS支持",
      System_SmtpSettings_StartTlsRequired_FieldLabel: "要求STARTTLS支持",
      System_SmtpSettings_SslOnConnectEnabled_FieldLabel:
        "连接时启用SSL/TLS加密",
      System_SmtpSettings_SslCheckServerIdentityEnabled_FieldLabel:
        "启用服务器身份验证",

      System_SmtpSettings_VerifyServer_Button: "验证电子邮件服务器",
      System_VerifySmtpConnection_VerifyServer_Title: "验证电子邮件服务器",
      System_VerifySmtpConnection_HelpText: "您想将测试电子邮件发送到哪里？",
      SmtpSettings_Verify_Mask: "正在检查电子邮件服务器 {0}",
      SmtpSettings_Verify_Success: "电子邮件服务器验证邮件已成功发送",

      // Admin -> System -> HTTP
      HttpSettings_Text: "HTTP",
      HttpSettings_Description: "管理出站HTTP/HTTPS配置",
      System_HttpSettings_Update_Error: "您没有权限配置HTTP",
      System_HttpSettings_Update_Success: "HTTP系统设置已$action",
      System_HttpSettings_Proxy_Title: "HTTP代理",
      System_HttpSettings_ProxyHost_FieldLabel: "HTTP代理主机",
      System_HttpSettings_ProxyHost_HelpText:
        '不需要http://（例如 "proxy-host" 或 "192.168.1.101"）',
      System_HttpSettings_ProxyPort_FieldLabel: "HTTP代理端口",
      System_HttpSettings_Authentication_Title: "认证",
      System_HttpSettings_ExcludeHosts_FieldLabel:
        "从HTTP/HTTPS代理中排除的主机",
      System_HttpSettings_ExcludeHosts_HelpText:
        '接受Java "http.nonProxyHosts" 通配符模式（每行一个，没有 ' |
        " 主机分隔符）",
      System_HttpSettings_HttpsProxy_Title: "HTTPS代理",
      System_HttpSettings_HttpsProxyHost_FieldLabel: "HTTPS代理主机",
      System_HttpSettings_HttpsProxyHost_HelpText:
        '不需要https://（例如 "proxy-host" 或 "192.168.1.101"）',
      System_HttpSettings_HttpsProxyPort_FieldLabel: "HTTPS代理端口",
      System_HttpSettings_HttpsProxyAuthentication_Title: "认证",

      // Admin -> System -> Bundles
      Bundles_Text: "捆绑包",
      Bundles_Description: "查看OSGI捆绑包",
      System_BundleList_Filter_EmptyText: "没有捆绑包匹配“{$filter}”",
      System_BundleList_ID_Header: "ID",
      System_BundleList_Name_Header: "名称",
      System_BundleList_SymbolicName_Header: "符号名称",
      System_BundleList_Version_Header: "版本",
      System_BundleList_State_Header: "状态",
      System_BundleList_Location_Header: "位置",
      System_BundleList_Level_Header: "级别",
      System_BundleList_Fragment_Header: "片段",
      System_BundleList_EmptyText: "没有捆绑包匹配“{$filter}”",
      System_Bundles_Details_Tab: "摘要",
      Bundles_ID_Info: "ID",
      Bundles_Name_Info: "名称",
      Bundles_SymbolicName_Info: "符号名称",
      Bundles_Version_Info: "版本",
      Bundles_State_Info: "状态",
      Bundles_Location_Info: "位置",
      Bundles_StartLevel_Info: "启动级别",
      Bundles_Fragment_Info: "片段",
      Bundles_Fragments_Info: "片段",
      Bundles_FragmentHosts_Info: "片段主机",
      Bundles_LastModified_Info: "最后修改",
      Bundles_Summary_Info: "{0}",

      // Admin -> System -> Nodes
      Nodes_Toggling_read_only_mode: "切换只读模式",
      Nodes_Disable_read_only_mode: "禁用只读模式",
      Nodes_Disable_read_only_mode_dialog: "禁用只读模式？",
      Nodes_Enable_read_only_mode: "启用只读模式",
      Nodes_Enable_read_only_mode_dialog: "启用只读模式？",
      Nodes_Read_only_mode_warning: "Trust Repository处于只读模式",
      Nodes_force_release_dialog: "强制禁用只读模式？",
      Nodes_force_release: "强制禁用只读模式",
      Nodes_Quorum_lost_warning:
        '无法达到足够的可信软件仓库节点以实现法定人数；数据库处于只读模式。<a href="#admin/system/nodes/clusterreset">故障排除</a>',
      Nodes_OSS_Message: "您正在运行可信软件仓库的单节点实例。",
      Nodes_enable_read_only_mode_dialog_description:
        "您确定要拒绝添加新的组件和更改配置吗？",
      Nodes_disable_read_only_mode_dialog_description:
        "您确定要停止拒绝添加新的组件和更改配置吗？",
      Nodes_force_release_warning:
        "警告：只读模式已被系统任务启用。在这些任务完成之前释放只读模式可能会导致它们失败和/或造成数据丢失。",
      Nodes_force_release_confirmation: "您确定要强制释放只读模式吗？",
      Nodes_NodeSettings_Title: "编辑节点",
      Nodes_NodeSettingsForm_Update_Error: "您没有权限更新节点",
      Nodes_NodeSettingsForm_Update_Success: "节点已更新，节点名称现在是：",
      Nodes_NodeSettingsForm_ID_FieldLabel: "节点ID",
      Nodes_NodeSettingsForm_ID_HelpText: "系统生成的节点身份",
      Nodes_NodeSettingsForm_Local_FieldLabel: "本地",
      Nodes_NodeSettingsForm_Local_HelpText: "当前UI会话是否连接到列出的节点",
      Nodes_NodeSettingsForm_SocketAddress_FieldLabel: "套接字地址",
      Nodes_NodeSettingsForm_SocketAddress_HelpText:
        "列出的节点用于与集群通信的IP地址和端口号",
      Nodes_NodeSettingsForm_FriendlyName_FieldLabel: "节点名称",
      Nodes_NodeSettingsForm_FriendlyName_HelpText: "此节点的自定义别名",

      // Admin -> System -> Tasks
      Tasks_Text: "任务",
      Tasks_Description: "管理计划任务",
      Tasks_Select_Title: "选择类型",
      Task_TaskSelectType_Filter_EmptyText: "没有类型匹配“{$filter}”",
      Task_TaskSelectType_Name_Header: "类型",
      Tasks_Update_Mask: "正在更新任务",
      Tasks_Run_Mask: "正在运行任务",
      Tasks_Stop_Mask: "正在停止任务",
      Task_TaskAdd_Create_Error: "您没有权限创建任务",
      Tasks_Create_Title: "创建{0}任务",
      Tasks_Create_Success: "任务已创建：{0}",
      Task_TaskList_New_Button: "创建任务",
      Task_TaskList_Name_Header: "名称",
      Task_TaskList_Type_Header: "类型",
      Task_TaskList_Status_Header: "状态",
      Task_TaskList_Schedule_Header: "计划",
      Task_TaskList_NextRun_Header: "下次运行",
      Task_TaskList_LastRun_Header: "上次运行",
      Task_TaskList_LastResult_Header: "上次结果",
      Task_TaskList_EmptyState:
        '<div class="summary">还没有定义计划任务<br>' +
        '<span style="font-weight: lighter; font-size: small;">或者您没有权限浏览它们</span></div>' +
        '<div class="panel nx-subsection"><h3 class="title"><span class="icon"></span>什么是计划任务？</h3>' +
        '<p>存储库管理器允许您安排维护任务的执行。这些任务可以对所有存储库或特定存储库执行定期维护步骤，配置在可配置的时间表上，或者简单地执行其他系统维护。</p></div>',
      Task_TaskList_Filter_EmptyState: "没有计划任务匹配“{$filter}”",
      Task_TaskFeature_Delete_Button: "删除任务",
      Tasks_Delete_Success: "任务已删除：{0}",
      Task_TaskFeature_Run_Button: "运行",
      Tasks_RunConfirm_Title: "确认？",
      Tasks_RunConfirm_HelpText: "运行{0}任务？",
      Tasks_Run_Success: "任务已开始：{0}",
      Tasks_Run_Disabled: "任务被禁用",
      Task_TaskFeature_Stop_Button: "停止",
      Tasks_StopConfirm_Title: "确认？",
      Tasks_StopConfirm_HelpText: "停止{0}任务？",
      Tasks_Stop_Success: "任务已停止：{0}",
      TaskFeature_Summary_Title: "摘要",
      Tasks_Settings_Title: "设置",
      Tasks_ID_Info: "ID",
      Tasks_Name_Info: "名称",
      Tasks_Type_Info: "类型",
      Tasks_Status_Info: "状态",
      Tasks_NextRun_Info: "下次运行",
      Tasks_LastRun_Info: "上次运行",
      Tasks_LastResult_Info: "上次结果",
      Task_TaskSettingsForm_Update_Error: "您没有权限更新任务或任务为只读",
      Tasks_Update_Success: "任务已更新：{0}",
      Task_TaskSettingsForm_Enabled_FieldLabel: "任务启用",
      Task_TaskSettingsForm_Enabled_HelpText:
        "此标志确定任务当前是否激活。要暂时禁用此任务，请取消选中此复选框。",
      Task_TaskSettingsForm_Name_FieldLabel: "任务名称",
      Task_TaskSettingsForm_Name_HelpText: "计划任务的名称",
      Task_TaskSettingsForm_Email_FieldLabel: "通知电子邮件",
      Task_TaskSettingsForm_Email_HelpText:
        "如果满足以下条件，将发送电子邮件到此地址",
      Task_TaskSettingsForm_NotificationCondition_FieldLabel: "发送通知",
      Task_TaskSettingsForm_NotificationCondition_HelpText:
        "触发通知电子邮件的条件",
      Task_TaskSettingsForm_NotificationCondition_FailureItem: "失败",
      Task_TaskSettingsForm_NotificationCondition_SuccessFailureItem:
        "成功或失败",
      Task_TaskScheduleFieldSet_Recurrence_FieldLabel: "任务频率",
      Task_TaskScheduleFieldSet_Recurrence_HelpText:
        "此任务将运行的频率。手动 - 此任务只能手动运行。一次 - 在指定的日期/时间运行任务一次。每小时 - 每小时运行任务一次。每天 - 每天在指定时间运行任务。每周 - 每周在指定日子和时间运行任务。每月 - 每月在指定的日子和时间运行任务。高级 - 使用提供的cron字符串运行任务。",
      Task_TaskScheduleFieldSet_Recurrence_EmptyText: "选择频率",
      Task_TaskScheduleFieldSet_Recurrence_ManualItem: "手动",
      Task_TaskScheduleFieldSet_Recurrence_OnceItem: "一次",
      Task_TaskScheduleFieldSet_Recurrence_HourlyItem: "每小时",
      Task_TaskScheduleFieldSet_Recurrence_DailyItem: "每天",
      Task_TaskScheduleFieldSet_Recurrence_WeeklyItem: "每周",
      Task_TaskScheduleFieldSet_Recurrence_MonthlyItem: "每月",
      Task_TaskScheduleFieldSet_Recurrence_AdvancedItem:
        "高级（提供CRON表达式）",
      Task_TaskScheduleDaily_StartDate_FieldLabel: "开始日期",
      Task_TaskScheduleHourly_EndDate_FieldLabel: "开始时间",
      Task_TaskScheduleDaily_Recurring_FieldLabel: "运行此任务的时间",
      Task_TaskScheduleMonthly_Days_FieldLabel: "运行此任务的日子",
      Task_TaskScheduleMonthly_Days_BlankText: "至少应选择一天",
      Task_TaskScheduleAdvanced_Cron_FieldLabel: "CRON表达式",
      Task_TaskScheduleAdvanced_Cron_EmptyText: "* * * * * * ",
      Task_TaskScheduleAdvanced_Cron_HelpText: "控制任务运行的cron表达式。",
      Task_TaskScheduleAdvanced_Cron_AfterBodyEl:
        '<div style="font-size: 11px"><p>从左到右字段和接受的值是：</p>' +
        "<table>" +
        "<thead><tr><th>字段名称</th><th>允许的值</th></tr></thead>" +
        "<tbody>" +
        "<tr><td>秒</td><td>0-59</td></tr>" +
        "<tr><td>分钟</td><td>0-59</td></tr>" +
        "<tr><td>小时</td><td>0-23</td></tr>" +
        "<tr><td>月份中的日期</td><td>1-31</td></tr>" +
        "<tr><td>月份</td><td>1-12或JAN-DEC</td></tr>" +
        "<tr><td>星期中的日期</td><td>1-7或SUN-SAT</td></tr>" +
        "<tr><td>年份（可选）</td><td>空，1970-2099</td></tr>" +
        "</tbody>" +
        "</table>" +
        "<br/>" +
        "<p>特殊标记包括：（所有可接受的值），？（没有具体值），-（范围，例如10-12）</p>" +
        "</div> ",

      Task_TaskScheduleManual_HelpText: "没有重复计划，服务只能手动运行。",
      Task_Script_Creation_Disabled:
        '<i>管理员 - 执行脚本</i> 任务创建已禁用。',

      // Authentication section
      System_AuthenticationSettings_Username_FieldLabel: "用户名",
      System_AuthenticationSettings_Password_FieldLabel: "密码",
      System_AuthenticationSettings_WindowsNtlmHostname_FieldLabel:
        "Windows NTLM 主机名",
      System_AuthenticationSettings_WindowsNtlmDomain_FieldLabel:
        "Windows NTLM 域",
      System_AuthenticationSettings_Bearer_Token_FieldLabel: "令牌",
      System_AuthenticationSettings_Bearer_Token_HelpText:
        "仅包括令牌值，不要包括 Bearer 前缀。",

      // HTTP Request section
      System_HttpRequestSettings_UserAgentCustomization_FieldLabel:
        "用户代理自定义",
      System_HttpRequestSettings_UserAgentCustomization_HelpText:
        '自定义片段，附加到 HTTP 请求的 "User-Agent" 头部。',
      System_HttpRequestSettings_Timeout_FieldLabel: "连接/套接字超时",
      System_HttpRequestSettings_Timeout_HelpText:
        "等待活动的秒数，在停止并重试连接之前。",
      System_HttpRequestSettings_Attempts_FieldLabel: "连接/套接字重试次数",
      System_HttpRequestSettings_Attempts_HelpText:
        "如果初始连接尝试超时，则总共重试次数",

      // Admin -> System -> Licensing
      Licensing_Text: "许可",
      Licensing_Description: "PRO 功能需要有效的许可。请在此管理。",
      Licensing_LicensingDetails_Company_FieldLabel: "公司",
      Licensing_LicensingDetails_Name_FieldLabel: "姓名",
      Licensing_LicensingDetails_Email_FieldLabel: "电子邮件",
      Licensing_LicensingDetails_EffectiveDate_FieldLabel: "生效日期",
      Licensing_LicensingDetails_ExpirationDate_FieldLabel: "到期日期",
      Licensing_LicensingDetails_Type_FieldLabel: "许可类型",
      Licensing_LicensingDetails_LicensedUsers_FieldLabel: "已授权用户数量",
      Licensing_LicensingDetails_Fingerprint_FieldLabel: "指纹",
      Licensing_LicensingDetails_InstallLicense_Title: "安装许可",
      Licensing_LicensingDetails_InstallLicense_Html:
        "<p>安装新许可需要重启服务器才能生效</p>",
      Licensing_LicensingDetails_LicenseSelect_Button: "选择许可…",
      Licensing_LicensingDetails_LicenseInstall_Button: "安装许可",
      Licensing_LicenseAgreement_Title: "可信软件仓库许可协议",
      Licensing_LicenseAgreement_Yes_Button: "我接受",
      Licensing_LicenseAgreement_No_Button: "我不接受",
      Licensing_LicenseAgreement_Download_Button: "下载协议副本。",
      Licensing_Install_Success:
        "许可已安装。如果您启用了新的 PRO 功能，则需要重启。",
      Licensing_Authentication_Validation: "{0} 许可需要验证您的凭证。",

      Clm_ClmSettings_Permission_Error: "您没有配置 IQ Server 的权限",
      Clm_Text: "IQ 服务器",
      Clm_Description: "管理 IQ Server 配置",
      Clm_Connection_Success: "已验证与 IQ 服务器的连接：{0}",
      Clm_Dashboard_Link_Text:
        '<span class="x-fa fa-dashboard"></span>IQ 服务器仪表盘<span class="x-fa fa-external-link"></span>',
      Clm_Dashboard_Description: "打开 IQ 服务器仪表盘",
      Clm_Dashboard_Disabled_Tooltip: "必须先启用 IQ 服务器",
      ClmSettings_Html:
        '<p>IQ 服务器' +
        "可以评估应用程序和组织的策略。</p>" +
        "<p>要启用此功能，请配置 IQ 服务器的 URL、用户名和密码。</p>",

      Clm_SettingsTestResults_Title: "应用程序",
      Clm_SettingsTestResults_EmptyText: "未找到应用程序",
      Clm_SettingsTestResults_Id_Header: "ID",
      Clm_SettingsTestResults_Name_Header: "名称",

      ClmSettings_Enable_FieldLabel: "启用 IQ 服务器",
      ClmSettings_Enable_HelpText: "是否使用 IQ 服务器",
      ClmSettings_URL_FieldLabel: "IQ 服务器 URL",
      ClmSettings_URL_HelpText: "您的 IQ 服务器地址",
      ClmSettings_URL_EmptyText: "输入一个 URL",
      ClmSettings_AuthenticationType_FieldLabel: "认证方式",
      ClmSettings_AuthenticationType_Pki: "PKI 认证",
      ClmSettings_AuthenticationType_User: "用户认证",
      ClmSettings_Username_FieldLabel: "用户名",
      ClmSettings_Username_HelpText: "有权限访问 IQ 服务器的用户",
      ClmSettings_Username_EmptyText: "输入用户名",
      ClmSettings_Password_FieldLabel: "密码",
      ClmSettings_Password_HelpText: "IQ 服务器用户的凭证",
      ClmSettings_Password_EmptyText: "输入密码",
      ClmSettings_ConnectionTimeout_FieldLabel: "连接超时",
      ClmSettings_ConnectionTimeout_HelpText:
        "等待活动的秒数，在停止并重试连接之前。留空以使用全局定义的 HTTP 超时。",
      ClmSettings_ConnectionTimeout_EmptyText: "输入超时",
      ClmSettings_Properties_FieldLabel: "属性",
      ClmSettings_Properties_HelpText: "为 IQ 服务器配置的附加属性",
      ClmSettings_Properties_EmptyText: "输入属性",
      ClmSettings_Properties_Verify_Button: "验证连接",
      ClmSettings_Show_Link_FieldLabel: "显示 IQ 服务器链接",
      ClmSettings_Show_Link_HelpText:
        "当服务器启用时，在浏览菜单中显示 IQ 服务器链接",
    },

    /**
     * String bundles.
     *
     * @type {Object}
     */
    bundles: {
      "NX.coreui.migration.Controller": {
        Feature_Text: "升级",
        Feature_Description: "从可信软件仓库升级配置和内容",

        Activate_Mask: "加载中",

        Configure_Mask: "配置中",
        Configure_Message: "升级已配置",

        Cancel_Confirm_Title: "取消升级",
        Cancel_Confirm_Text: "您确定要取消升级吗？",
        Cancel_Mask: "正在取消",
        Cancel_Message: "升级已取消",

        IncompleteCancel_Title: "配置未完成",
        IncompleteCancel_Text: "升级已经部分配置，必须重置以继续。",
        IncompleteCancel_Mask: "正在重置",

        PlanStepDetail_Mask: "获取详情",
      },

      "NX.coreui.migration.NoUpgradeHAScreen": {
        Title: "检测到高可用性集群（HA-C）",
        Description:
          "<p>在运行 HA-C 时，从可信软件仓库升级不可用。</p>" +
          "<p>请以单节点模式运行可信软件仓库以继续。</p>",
      },

      "NX.coreui.migration.AgentScreen": {
        Title: "代理连接",
        Description:
          "<p>配置与远程服务器升级代理的连接。<br/>" +
          "远程服务器必须配置并启用升级代理。</p>",
        Endpoint_FieldLabel: "URL",
        Endpoint_HelpText: "远程服务器的基本 URL",
        Token_FieldLabel: "访问令牌",
        Token_HelpText: "来自远程服务器升级代理设置的访问令牌",
        FetchSize_FieldLabel: "提取大小",
        FetchSize_HelpText:
          "一次从 NXRM2 提取的更改批量大小。如果在同步步骤中遇到问题，请降低该值。",
      },

      "NX.coreui.migration.AgentStep": {
        Connect_Mask: "连接中",
        Connect_Message: "已连接",
      },

      "NX.coreui.migration.ContentScreen": {
        Title: "内容",
        Description:
          "<p>您希望从可信软件仓库转移哪些内容？</p>",
        Repositories_FieldLabel: "存储库配置和内容",
        Configuration_FieldLabel: "服务器配置",
      },

      "NX.coreui.migration.OverviewScreen": {
        Title: "概览",
        Description:
          "<p>此向导将帮助您从可信软件仓库升级。</p>" +
          "<p>许多服务器的方面可以通过此功能进行升级：" +
          "<ul>" +
          "<li>配置：安全（用户、角色和权限）以及其他适用的系统设置</li>" +
          "<li>受支持格式的存储库：maven2, nuget, npm, rubygems, site</li>" +
          "</ul>" +
          "</p>" +
          "<p>某些方面是 <strong>不兼容</strong> 的，不包含在升级中：" +
          "<ul>" +
          "<li>不支持的存储库格式：yum, p2, obr</li>" +
          "<li>计划任务</li>" +
          "<li>功能</li>" +
          "<li>临时存储库（Pro）</li>" +
          "</ul>" +
          "</p>" +
          "<p>请注意以下几点：" +
          "<ul>" +
          "<li>服务器配置可以多次传输，每次都会完全替换现有配置。</li>" +
          "<li>与服务器配置不同，此工具仅应在存储库上使用一次。批量升级或多次升级存储库存在已知问题，不建议也不支持。</li>" +
          "<li>存储库升级可能需要 <strong>相当长的时间</strong>。</li>" +
          "<li>在升级成功完成之前，不建议在可信软件仓库中进行任何配置更改，因为此时配置是易变的。</li>" +
          "<li>在升级过程中，可信软件仓库中的所有存储库将处于离线状态。</li>" +
          "</ul>" +
          "</p>",
      },

      "NX.coreui.migration.PhaseFinishScreen": {
        Title: "完成",
        Description: "<p>升级正在完成。</p>",
        Abort_Button: "中止",
        Done_Button: "完成",
      },

      "NX.coreui.migration.RepositoryDefaultsScreen": {
        $extend: "NX.coreui.migration.RepositoryCustomizeWindow",

        Title: "存储库默认设置",
        Description:
          "<p>配置用于存储库升级的默认设置。<br/>" +
          "可以在选择要升级的存储库时自定义每个存储库的设置。</p>",
        IngestMethod_HelpText:
          "选择存储库内容应如何传输。您选择的方法可能并非所有存储库都支持。",
      },

      "NX.coreui.migration.RepositoryCustomizeWindow": {
        Title: "自定义 {0}",

        DataStore_FieldLabel: "数据存储",
        DataStore_HelpText: "选择内容元数据应存储的位置",
        DataStore_EmptyText: "选择数据存储",

        BlobStore_FieldLabel: "Blob 存储",
        BlobStore_HelpText: "选择存储库内容应存储的位置",
        BlobStore_EmptyText: "选择 Blob 存储",

        IngestMethod_FieldLabel: "方法",
        IngestMethod_HelpText: "选择存储库内容应如何传输",
        IngestMethod_EmptyText: "选择存储库内容传输方法",
        IngestMethod_Link: "硬链接（最快）",
        IngestMethod_Copy: "文件系统复制（较慢）",
        IngestMethod_Download: "下载（最慢）",
      },

      "NX.coreui.migration.PlanStepDetailWindow": {
        Title: "{0}",
        EmptyLog: "没有进展",
        Timestamp_Column: "时间戳",
        Message_Column: "消息",
      },

      "NX.coreui.migration.PreviewScreen": {
        Title: "预览",
        Description: "<p>以下是升级配置的预览。</p>",
        Name_Column: "名称",
        State_Column: "状态",
        Begin_Button: "开始",
      },

      "NX.coreui.migration.PreviewStep": {
        Begin_Confirm_Title: "开始升级",
        Begin_Confirm_Text: "您确定要开始升级吗？",
        Begin_Mask: "升级开始中",
        Begin_Message: "升级已开始",
      },

      "NX.coreui.migration.ProgressScreenSupport": {
        Name_Column: "名称",
        Status_Column: "状态",
        State_Column: "状态",
        Complete_Column: "完成",
      },

      "NX.coreui.migration.ProgressStepSupport": {
        Loading_Mask: "加载中",
      },

      "NX.coreui.migration.RepositoriesScreen": {
        Title: "存储库",
        Description:
          "<p>选择要升级的存储库。<br/>" +
          "根据需要自定义每个存储库的升级高级配置。</p>",
        Repository_Column: "存储库",
        Type_Column: "类型",
        Format_Column: "格式",
        Supported_Column: "支持",
        Status_Column: "状态",
        Datastore_Column: "数据存储",
        Blobstore_Column: "Blob 存储",
        Method_Column: "方法",
        Action_Tooltip: "自定义存储库选项",
      },

      "NX.coreui.migration.RepositoriesStep": {
        $extend: "NX.coreui.migration.ProgressStepSupport",
      },

      "NX.coreui.migration.RepositoryDefaultsStep": {
        $extend: "NX.coreui.migration.ProgressStepSupport",
      },

      "NX.coreui.migration.PhasePrepareScreen": {
        Title: "准备中",
        Description: "<p>正在为升级做准备。</p>",
        Abort_Button: "中止",
        Continue_Button: "继续",
      },

      "NX.coreui.migration.PhasePrepareStep": {
        $extend: "NX.coreui.migration.ProgressStepSupport",

        Abort_Confirm_Title: "中止升级",
        Abort_Confirm_Text: "您确定要中止升级吗？",
        Abort_Mask: "正在中止升级",
        Abort_Message: "升级已中止",

        Continue_Confirm_Title: "继续升级",
        Continue_Confirm_Text: "您确定要继续升级吗？",
        Continue_Mask: "升级继续中",
        Continue_Message: "升级继续",
      },

      "NX.coreui.migration.PhaseSyncScreen": {
        Title: "同步中",
        Description: "<p>升级正在同步更改。</p>",
        Abort_Button: "中止",
        Continue_Button: "继续",
        Continue_Button_Pending:
          '<i class="fa fa-spinner fa-spin fa-fw"></i> 继续',
      },

      "NX.coreui.migration.PhaseSyncStep": {
        $extend: "NX.coreui.migration.ProgressStepSupport",

        Abort_Confirm_Title: "中止升级",
        Abort_Confirm_Text: "您确定要中止升级吗？",
        Abort_Mask: "正在中止升级",
        Abort_Message: "升级已中止",

        Stop_Waiting_Confirm_Title: "停止等待更改",
        Stop_Waiting_Confirm_Text: "将不再同步存储库的任何未来更改。继续吗？",
        Stop_Waiting_Confirm_Mask: "正在完成更改",
        Stop_Waiting_Confirm_Message: "更改已完成",

        Finish_Mask: "升级完成",
        Finish_Message: "升级完成",
      },

      "NX.coreui.migration.PhaseFinishStep": {
        $extend: "NX.coreui.migration.ProgressStepSupport",

        Abort_Confirm_Title: "中止升级",
        Abort_Confirm_Text: "您确定要中止升级吗？",
        Abort_Mask: "正在中止升级",
        Abort_Message: "升级已中止",

        Done_Mask: "确认中",
        Done_Message: "升级完成",
      },

      "NX.coreui.view.ldap.LdapSystemPasswordModal": {
        Title: "LDAP 服务器系统密码",
        Password_FieldLabel: "密码",
        Password_HelpText: "用于绑定的密码",
        Button_OK: "确定",
        Button_Cancel: "取消",
      },
    },
  },
  function (self) {
    NX.I18n.register(self);
  }
);
