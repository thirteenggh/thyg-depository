import {UIStrings} from 'nexus-ui-plugin';

export default {
  ...UIStrings,

  FORMAT_PLACEHOLDER: '格式',

  ANONYMOUS_SETTINGS: {
    MENU: {
      text: '匿名访问',
      description: '配置对服务器内容的匿名访问'
    },
    ENABLED_CHECKBOX_LABEL: '访问权限：',
    ENABLED_CHECKBOX_DESCRIPTION: '允许匿名用户访问服务器',
    USERNAME_TEXTFIELD_LABEL: '用户名：',
    REALM_SELECT_LABEL: '领域：',
    MESSAGES: {
      LOAD_ERROR: '加载匿名设置时发生错误，请查看控制台以获取更多详细信息',
      SAVE_SUCCESS: '匿名安全设置已更新',
      SAVE_ERROR: '更新匿名设置时发生错误，请查看控制台以获取更多详细信息'
    }
  },

  CONTENT_SELECTORS: {
    MENU: {
      text: '内容选择器'
    },

    EMPTY_MESSAGE: '未找到内容选择器',

    HELP_TITLE: '什么是内容选择器？',
    HELP_TEXT: `\
内容选择器为您提供从存储库中选择特定内容的方法。\
存储库内容根据用 CSEL（内容选择表达式语言）编写的表达式进行评估。\
有关更多信息，<a href="http://links.sonatype.com/products/nxrm3/docs/content-selector" target="_blank" rel="noopener noreferrer">请查看文档</a>。`,

    CREATE_BUTTON: '创建选择器',
    FILTER_PLACEHOLDER: '筛选',

    NAME_LABEL: '名称',
    TYPE_LABEL: '类型',
    DESCRIPTION_LABEL: '描述',
    EXPRESSION_LABEL: '搜索表达式',
    EXPRESSION_DESCRIPTION: '使用以下查询来识别存储库内容',

    PREVIEW: {
      TITLE: '预览内容选择器结果',
      REPOSITORY_LABEL: '预览存储库',
      REPOSITORY_DESCRIPTION: '选择一个存储库以评估内容选择器，并查看可用的内容',
      BUTTON: '预览',
      RESULTS: '预览结果',
      NAME_COLUMN: '名称',
      EMPTY: '存储库中没有内容与表达式匹配'
    },

    MESSAGES: {
      DUPLICATE_ERROR: (name) => `已存在另一个名为 ${name} 的内容选择器`,
      SAVE_ERROR: '保存内容选择器时发生错误',
      DELETE_ERROR: (name) => `内容选择器 ${name} 正在使用中，无法删除`,

      CONFIRM_DELETE: {
        TITLE: '删除内容选择器',
        MESSAGE: (name) => `删除名为 ${name} 的内容选择器？`,
        YES: '删除',
        NO: '取消'
      }
    }
  },

  LOGGING: {
    MENU: {
      text: '日志记录',
      description: '控制日志级别'
    },

    CREATE_BUTTON: '创建日志记录器',
    RESET_ALL_BUTTON: '重置为默认级别',
    RESET_BUTTON: '重置为默认级别',

    FILTER_PLACEHOLDER: '按日志记录器名称筛选',

    NAME_LABEL: '日志记录器名称',
    LEVEL_LABEL: '日志级别',

    MESSAGES: {
      SAVE_ERROR: '保存日志记录器时发生错误',
      RESETTING: '正在重置为默认级别...',
      RESET_ERROR: '重置所有日志记录器时发生错误，请查看控制台以获取更多详细信息'
    },

    CONFIRM_UPDATE: {
      TITLE: '确认更新？',
      MESSAGE: ({name, level}) => `日志记录器 ${name} 已配置。是否将其级别更新为 "${level}"？`,
      CONFIRM_BUTTON: '更新日志级别'
    },

    CONFIRM_RESET_ALL: {
      TITLE: '确认重置？',
      MESSAGE: '是否将所有日志记录器重置为默认级别？',
      CONFIRM_BUTTON: '重置所有日志记录器'
    },

    CONFIRM_RESET: {
      TITLE: '确认重置？',
      MESSAGE: '是否将此日志记录器重置为默认级别？如果是自定义日志记录器，它将被移除。',
      CONFIRM_BUTTON: '重置日志记录器'
    }
  },

  LOG_VIEWER: {
    MENU: {
      text: '日志查看器',
      description: '查看当前日志内容'
    },
    REFRESH: {
      TEXT: '刷新间隔',
      MANUAL_ITEM: '手动',
      TWENTY_SECONDS_ITEM: '每20秒',
      MINUTE_ITEM: '每分钟',
      TWO_MINUTES_ITEM: '每2分钟',
      FIVE_MINUTES_ITEM: '每5分钟'
    },
    SIZE: {
      LAST25KB_ITEM: '最近25KB',
      LAST50KB_ITEM: '最近50KB',
      LAST100KB_ITEM: '最近100KB'
    },
    DOWNLOAD: '下载',
    INSERT_MARK: '插入标记',
    MARK_PLACEHOLDER: '要插入日志的标记'
  },

  ROUTING_RULES: {
    MENU: {
      text: '路由规则',
      description: '限制哪些请求由存储库处理'
    },

    LIST: {
      PREVIEW_BUTTON: '全局路由预览',
      CREATE_BUTTON: '创建路由规则',
      FILTER_PLACEHOLDER: '按名称或描述筛选',
      NAME_LABEL: '名称',
      DESCRIPTION_LABEL: '描述',
      USED_BY_LABEL: '使用者',
      NEEDS_ASSIGNMENT: '0 个存储库，请将其分配给存储库',
      USED_BY: (count) => count === 1 ? '1 个存储库' : `${count} 个存储库`,
      EMPTY_LIST: '尚未创建路由规则',
      HELP_TITLE: '什么是路由规则？',
      HELP_TEXT: `\
路由规则就像可以应用于组的过滤器，涉及安全访问和组件获取。\
它们可以减少访问组内存储库的数量，从而获取组件。\
有关更多信息，<a href="http://links.sonatype.com/products/nxrm3/docs/routing-rule" target="_blank" rel="noopener noreferrer">请查看文档</a>。`
    },

    PREVIEW: {
      TITLE: '全局路由预览',
      REPOSITORIES_LABEL: '存储库',
      REPOSITORIES_DESCRIPTION: '选择一组存储库进行测试',
      REPOSITORIES: {
        ALL: '所有存储库',
        GROUPS: '所有组存储库',
        PROXIES: '所有代理存储库'
      },
      PATH_LABEL: '路径',
      PATH_DESCRIPTION: '输入请求路径以检查是否会被阻止或允许。请求始终以斜杠开头。',
      COLUMNS: {
        REPOSITORY: '存储库',
        TYPE: '类型',
        FORMAT: '格式',
        RULE: '路由规则',
        STATUS: '状态'
      },
      NO_RULE: '无',
      EMPTY_PREVIEW: '未找到结果或预览尚未提交',
      DETAILS_TITLE: (ruleName) => `规则 ${ruleName} 的详细信息`
    },

    FORM: {
      CREATE_TITLE: '创建路由规则',
      EDIT_TITLE: '编辑路由规则',
      UNUSED: `要使用此规则，<a href="#admin/repository/repositories">请将其分配给存储库</a>`,
      USED_BY: (repositoryNames) => {
        const repositoryLinks = repositoryNames.map(name =>
            `<a href="#admin/repository/repositories:${window.encodeURIComponent(name)}">${name}</a>`);
        const repository = repositoryNames.length === 1 ? '存储库' : '存储库';
        return `此规则正在 ${repositoryNames.length} 个 ${repository} 中使用（${repositoryLinks.join(', ')}）`;
      },
      SAVE_ERROR: '保存路由规则时发生错误',
      NAME_LABEL: '名称',
      DESCRIPTION_LABEL: '描述',
      MODE_LABEL: '模式',
      MODE: {
        ALLOW: '允许',
        BLOCK: '阻止'
      },
      PREVIEW: {
        ALLOWED: '此请求将被允许',
        BLOCKED: '此请求将被阻止'
      },
      MODE_DESCRIPTION: '当其路径与以下匹配器匹配时的请求',
      MATCHERS_LABEL: '匹配器',
      MATCHER_LABEL: (index) => `匹配器 ${index}`,
      MATCHERS_DESCRIPTION: '输入正则表达式，用于识别要允许或阻止的请求路径（取决于上面的模式）',
      NAME_IS_NONE_ERROR: '规则不得命名为 None',
      DELETE_MATCHER_BUTTON: '删除此匹配器',
      ADD_MATCHER_BUTTON: '添加另一个匹配器',
      CREATE_BUTTON: '创建路由规则',
      CANNOT_DELETE: (repositoryNames) => `\
此规则正在 ${repositoryNames.length} 个存储库中使用（${repositoryNames.join(', ')}）`
    },

    MESSAGES: {
      CONFIRM_DELETE: {
        TITLE: '删除路由规则',
        MESSAGE: (name) => `删除名为 ${name} 的路由规则？`,
        YES: '删除',
        NO: '取消'
      },
      DELETE_ERROR: (name) => `无法删除名为 ${name} 的路由规则`
    },

    ALLOWED: '允许',
    BLOCKED: '阻止',

    PATH_LABEL: '路径',
    PATH_DESCRIPTION: '输入请求路径以检查是否会被阻止或允许。请求始终以斜杠开头。',
    TEST_BUTTON: '测试'
  },

  SYSTEM_INFORMATION: {
    MENU: {
      text: '系统信息'
    },
    ACTIONS: {
      download: '下载为 JSON'
    },
    LOAD_ERROR: '获取系统信息时发生错误'
  },

  SUPPORT_REQUEST: {
    MENU: {
      text: '支持请求',
      description: '提交 TianheCloud 支持请求'
    },
    ACTIONS: {
      submitRequest: '提交请求'
    },
    DESCRIPTION: '请提供您问题的完整描述和复现步骤（如果有）。',
    ATTACH_SUPPORT_ZIP: '附加支持 ZIP 文件将帮助我们的工程师更快地回复您。'
  },

  USER_ACCOUNT: {
    MENU: {
      text: '账户',
      description: '管理您的账户',
    },
    ACTIONS: {
      changePassword: '更改密码',
      discardChangePassword: '放弃密码更改',
    },
    MESSAGES: {
      LOAD_ERROR: '加载用户账户时发生错误，请查看控制台以获取更多详细信息',
      UPDATE_SUCCESS: '用户账户设置已更新',
      UPDATE_ERROR: '更新用户账户设置时发生错误',
      PASSWORD_NO_MATCH_ERROR: '密码不匹配',
      PASSWORD_MUST_DIFFER_ERROR: '新密码必须不同',
    },
    ID_FIELD_LABEL: '用户名',
    FIRST_FIELD_LABEL: '名字',
    LAST_FIELD_LABEL: '姓氏',
    EMAIL_FIELD_LABEL: '电子邮件',
    PASSWORD_CURRENT_FIELD_LABEL: '当前密码',
    PASSWORD_NEW_FIELD_LABEL: '新密码',
    PASSWORD_NEW_CONFIRM_FIELD_LABEL: '确认新密码',
  },

  NUGET_API_KEY: {
    MENU: {
      text: 'NuGet API 密钥',
      description: '配置 NuGet 存储库的凭证'
    },
    INSTRUCTIONS: '第一次访问时将创建一个新的 API 密钥。重置 API 密钥将使当前密钥失效。',
    AUTH_INSTRUCTIONS: '访问 NuGet API 密钥需要验证您的凭证。',
    AUTH_ERROR: '身份验证失败',
    CLOSE: '关闭',
    ACCESS: {
      BUTTON: '访问 API 密钥',
      HELP_TEXT: '访问 NuGet API 密钥需要验证您的凭证。',
      ERROR: '访问 API 密钥失败'
    },
    RESET: {
      BUTTON: '重置 API 密钥',
      HELP_TEXT: '重置 NuGet API 密钥需要验证您的凭证。',
      ERROR: '重置 API 密钥失败'
    },
    DETAILS: {
      MAIN: '您的 NuGet API 密钥允许使用 nuget.exe 推送包。',
      WARNING: '请保密此密钥！',
      API_KEY_TEXT: '您的 NuGet API 密钥是：',
      REGISTER_TEXT: '您可以使用以下命令为给定存储库注册此密钥：',
      REGISTER_COMMAND: 'nuget setapikey {0} -source {1}',
      AUTO_CLOSE: '此窗口将在一分钟后自动关闭。'
    }
  },

  METRIC_HEALTH: {
    MENU: {
      text: '状态',
      description: '系统状态检查'
    },
    NAME_HEADER: '名称',
    MESSAGE_HEADER: '消息',
    ERROR_HEADER: '错误'
  },

  SUPPORT_ZIP: {
    MENU: {
      text: '支持 ZIP',
      description: '创建一个包含有关您的服务器的有用支持信息的 ZIP 文件'
    },
    DESCRIPTION: '<p>创建支持 ZIP 文件时不会向 TianheCloud 发送任何信息。</p>' +
        '<p>创建支持 ZIP 可能需要几分钟时间。</p>',
    REPORT_LABEL: '系统信息报告',
    DUMP_LABEL: 'JVM 线程转储',
    CONFIGURATION_LABEL: '配置文件',
    SECURITY_LABEL: '安全配置文件',
    LOGFILES_LABEL: '日志文件',
    TASKLOGFILES_LABEL: '任务日志文件',
    AUDITLOGFILES_LABEL: '审计日志文件',
    METRICS_LABEL: '系统和组件指标',
    JMX_LABEL: 'JMX 信息',
    LIMITFILESIZES_LABEL: '将 ZIP 档案中的文件大小限制为每个 30 MB',
    LIMITZIPSIZE_LABEL: '将 ZIP 档案的大小限制为 20 MB',
    CREATED_TITLE: '已创建支持 ZIP',
    CREATED_DESCRIPTION: '支持 ZIP 已创建。<br/>您可以在文件系统中引用此文件或通过浏览器下载文件。',
    CREATED_NODEID_LABEL: '节点：',
    CREATED_NAME_LABEL: '名称：',
    CREATED_SIZE_LABEL: '大小：',
    CREATED_PATH_LABEL: '路径：',
    CREATED_DOWNLOAD_BUTTON: '下载',
    AUTHENTICATE_TEXT: '下载支持 ZIP 需要验证您的凭证。'
  },

  HEALTHCHECK_EULA: {
    HEADER: 'Trust Repository IQ 服务器使用条款',
    BUTTONS: {
      ACCEPT: '我接受',
      DECLINE: '我不接受'
    }
  },

  ANALYZE_APPLICATION: {
    HEADER: '分析应用程序',
    MAIN: '应用程序分析对该应用程序进行深度检查，识别潜在的风险。<br/>更多信息请访问 <a href=\"http://links.sonatype.com/products/insight/ac/home\">这里</a>',
    EMAIL: {
      LABEL: '电子邮件地址',
      DESCRIPTION: '报告将发送到该地址'
    },
    PASSWORD: {
      LABEL: '报告密码',
      DESCRIPTION: '访问详细报告的密码'
    },
    PACKAGES: {
      LABEL: '专有包',
      DESCRIPTION: '以逗号分隔的专有包列表'
    },
    REPORT: {
      LABEL: '报告标签',
      DESCRIPTION: '报告将使用的名称'
    },
    SELECT_ASSET: {
      LABEL: '选择资产',
      DESCRIPTION: '选择一个资产来进行分析'
    },
    BUTTONS: {
      ANALYZE: '分析',
      CANCEL: '取消'
    }
  },

  CLEANUP_POLICIES: {
    MENU: {
      text: '清理策略',
      description: '管理组件删除配置'
    },

    CREATE_TITLE: '创建清理策略',
    EDIT_TITLE: '编辑清理策略',

    HELP_TITLE: '什么是清理策略？',
    HELP_TEXT: `\
清理策略可用于从您的存储库中删除内容。这些策略将根据配置的频率执行。\
创建后，必须将清理策略分配给一个存储库，您可以在\
<a href="#admin/repository/repositories">存储库配置页面</a>进行分配。更多信息，请查看\
<a href="http://links.sonatype.com/products/nxrm3/docs/cleanup-policy" target="_blank" rel="noopener noreferrer">文档</a>。`,
    EMPTY_MESSAGE: '未找到清理策略',
    CREATE_BUTTON: '创建清理策略',
    FILTER_PLACEHOLDER: '筛选',

    NAME_LABEL: '名称',
    FORMAT_LABEL: '格式',
    NOTES_LABEL: '备注',
    CRITERIA_LABEL: '清理标准',
    LAST_UPDATED_LABEL: '组件年龄',
    LAST_DOWNLOADED_LABEL: '组件使用情况',
    RELEASE_TYPE_LABEL: '发布类型',
    ASSET_NAME_LABEL: '资产名称匹配器',
    FORMAT_SELECT: '选择格式...',
    RELEASE_TYPE_SELECT: '选择发布类型...',
    REPOSITORY_SELECT: '选择存储库...',

    NAME_DESCRIPTION: '使用唯一名称为清理策略命名',
    FORMAT_DESCRIPTION: '此清理策略可应用的格式',
    LAST_UPDATED_DESCRIPTION: '删除发布超过以下天数的组件：',
    LAST_DOWNLOADED_DESCRIPTION: '删除超过以下天数未下载的组件：',
    RELEASE_TYPE_DESCRIPTION: '删除以下发布类型的组件：',
    ASSET_NAME_DESCRIPTION: '删除至少有一个资产名称匹配以下正则表达式模式的组件：',

    LAST_UPDATED_SUFFIX: '天前',
    LAST_DOWNLOADED_SUFFIX: '天',

    RELEASE_TYPE_RELEASE: '发布版本',
    RELEASE_TYPE_PRERELEASE: '预发布/快照版本',

    PREVIEW: {
      TITLE: '清理策略预览',
      REPOSITORY_LABEL: '预览存储库',
      REPOSITORY_DESCRIPTION: '选择一个存储库来预览如果应用此策略可能被清理的内容',
      BUTTON: '预览',
      RESULTS: '预览结果',
      NAME_COLUMN: '名称',
      GROUP_COLUMN: '组',
      VERSION_COLUMN: '版本',
      EMPTY: '存储库中没有资产符合标准'
    },

    MESSAGES: {
      SAVE_ERROR: '保存清理策略时发生错误',
      DELETE_ERROR: (name) => `清理策略 ${name} 正在使用中，无法删除`,

      CONFIRM_DELETE: {
        TITLE: '删除清理策略',
        MESSAGE: (inUseCount) => inUseCount ? 
            `此清理策略正在 ${inUseCount} 个存储库中使用` : 
            '此清理策略未被任何存储库使用',
        YES: '删除',
        NO: '取消'
      }
    }
  }
};
