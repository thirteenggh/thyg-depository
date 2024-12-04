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
  "NX.s3blobstore.app.PluginStrings",
  {
    "@aggregate_priority": 90,

    singleton: true,
    requires: ["NX.I18n"],

    keys: {
      S3Blobstore_Help:
        '<em>S3 Blob 存储需要特定的权限才能通过 私有弹性云计算软件平台 完成完整的配置和功能支持。<a href="https://links.sonatype.com/products/nexus/blobstores/s3/docs" target="_blank">请参阅我们的文档</a>了解所需的特定权限。</em>',
      S3Blobstore_Region_FieldLabel: "区域",
      S3Blobstore_Region_HelpText: "要使用的 AWS 区域",
      S3Blobstore_Bucket_FieldLabel: "存储桶",
      S3Blobstore_Bucket_HelpText:
        "S3 存储桶名称（必须介于 3 到 63 个字符之间，仅包含小写字母、数字、句点和连字符）",
      S3Blobstore_Prefix_FieldLabel: "前缀",
      S3Blobstore_Prefix_HelpText: "S3 路径前缀",
      S3Blobstore_Expiration_FieldLabel: "过期天数",
      S3Blobstore_Expiration_HelpText:
        "已删除的 blob 从 S3 存储桶中完全删除之前的天数（-1 表示禁用）",
      S3Blobstore_Authentication_Title: "身份验证",
      S3Blobstore_Authentication_AccessKeyId: "访问密钥 ID",
      S3Blobstore_Authentication_SecretAccessKey: "秘密访问密钥",
      S3Blobstore_Authentication_AssumeRoleArn: "假设角色 ARN（可选）",
      S3Blobstore_Authentication_SessionToken: "会话令牌 ARN（可选）",
      S3Blobstore_EncryptionSettings_Title: "加密",
      S3Blobstore_EncryptionSettings_Type_FieldLabel: "加密类型",
      S3Blobstore_EncryptionSettings_Type_HelpText:
        "S3 Blob 存储中对象的加密类型",
      S3Blobstore_EncryptionSettings_KeyID_FieldLabel: "KMS 密钥 ID（可选）",
      S3Blobstore_EncryptionSettings_KeyID_HelpText:
        "如果使用 KMS 加密，可以提供密钥 ID。如果留空，将使用默认值",
      S3Blobstore_AdvancedConnectionSettings_Title: "高级连接设置",
      S3Blobstore_AdvancedConnectionSettings_EndPointUrl_FieldLabel: "端点 URL",
      S3Blobstore_AdvancedConnectionSettings_EndPointUrl_HelpText:
        "用于第三方对象存储的 S3 API 的自定义端点 URL",
      S3Blobstore_AdvancedConnectionSettings_SignatureVersion_FieldLabel:
        "签名版本",
      S3Blobstore_AdvancedConnectionSettings_SignatureVersion_HelpText:
        "第三方对象存储可能要求的 API 签名版本",
      S3Blobstore_AdvancedConnectionSettings_PathStyleAccess_FieldLabel:
        "使用路径样式访问",
      S3Blobstore_AdvancedConnectionSettings_PathStyleAccess_HelpText:
        "设置此标志将使所有请求使用路径样式访问",
    },
  },
  function (obj) {
    NX.I18n.register(obj);
  }
);
