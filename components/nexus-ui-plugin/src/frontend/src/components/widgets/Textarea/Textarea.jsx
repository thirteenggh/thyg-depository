import React, {forwardRef} from 'react';
import PropTypes from 'prop-types';
import {NxTextInput} from "@sonatype/react-shared-components";

/**
 * @since 3.22
 */
const Textarea = forwardRef(({name, id, onChange, ...attrs}, ref) => {
  const handleChange = (value) => {
    if (onChange) {
      onChange({
        target: {
          id: id || name,
          name: name,
          type: "textarea",
          value
        }
      });
    }
  };
  return <NxTextInput id={id || name}
                      name={name}
                      ref={ref}
                      type="textarea"
                      isPristine={false}
                      onChange={handleChange}
                      {...attrs} />;
});

Textarea.propTypes = {
  validationErrors: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
};

export default Textarea;
