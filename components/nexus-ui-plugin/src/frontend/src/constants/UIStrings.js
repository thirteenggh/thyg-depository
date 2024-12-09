/**
 * @since 3.21
 */
export default {
  SETTINGS: {
    CANCEL_BUTTON_LABEL: '取消',
    DISCARD_BUTTON_LABEL: '放弃',
    SAVE_BUTTON_LABEL: '保存',
    DELETE_BUTTON_LABEL: '删除'
  },

  SAVING: '保存中...',
  FILTER: '筛选',
  CLOSE: '关闭',

  PRISTINE_TOOLTIP: '没有任何更改',
  INVALID_TOOLTIP: '存在验证错误',

  ERROR: {
    FIELD_REQUIRED: '此字段为必填项',
    NAN: '此字段必须包含一个数字值',
    MIN: (min) => `此字段的最小值为 ${min}`,
    MAX: (max) => `此字段的最大值为 ${max}`,
    LOAD_ERROR: '加载表单时发生错误',
    SAVE_ERROR: '保存表单时发生错误',
    INVALID_NAME_CHARS: '仅允许字母、数字、下划线(_)、连字符(-)和点(.)，且不能以下划线或点开头。',
    MAX_CHARS: (max) => `此字段的字符限制为 ${max} 个`
  },

  SAVE_SUCCESS: '表单已成功保存',

  USER_TOKEN: {
    MESSAGES: {
      ACCESS_ERROR: '您必须成功验证身份才能访问您的令牌',
      RESET_SUCCESS: '您的用户令牌已重置',
      RESET_ERROR: '您必须成功验证身份才能重置您的令牌'
    }
  }
};
