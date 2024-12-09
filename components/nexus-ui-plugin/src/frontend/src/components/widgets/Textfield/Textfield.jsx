import PropTypes from 'prop-types';
import React from 'react';
import {NxTextInput} from '@sonatype/react-shared-components';

/**
 * @since 3.21
 */
export default function Textfield({id, name, type = "text", onChange, isPristine = false, validatable = true, ...attrs}) {
  function handleChange(value) {
    if (onChange) {
      onChange({
        target: {
          id: id || name,
          name,
          type,
          value
        }
      });
    }
  }

  const inputAttrs = {
    ...attrs,
    id: id || name,
    name,
    type: type === 'number' ? 'text' : type,
    isPristine,
    validatable,
    onChange: handleChange
  };

  return <NxTextInput {...inputAttrs} />;
}

Textfield.propTypes = {
  id: PropTypes.string,
  name: PropTypes.string,
  value: PropTypes.string,
  validationErrors: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
};
