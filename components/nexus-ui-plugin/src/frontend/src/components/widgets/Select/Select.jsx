import classNames from 'classnames';
import React from 'react';

import './Select.scss';
import FieldErrorMessage from "../FieldErrorMessage/FieldErrorMessage";
import PropTypes from "prop-types";
import Textfield from "../Textfield/Textfield";
import {getFirstValidationError, hasValidationErrors} from "@sonatype/react-shared-components/util/validationUtil";

/**
 * @since 3.21
 */
export default function Select({value, children, className, name, id, isPristine, validatable, validationErrors, ...rest}) {
  const isInvalid = hasValidationErrors(validationErrors);
  const classes = classNames('nxrm-select', className, {
    'invalid': isInvalid
  });

  return <>
    <select id={id || name} name={name} className={classes} value={value} {...rest}>
      {children}
    </select>
    {!isPristine && validatable && isInvalid ? <FieldErrorMessage message={getFirstValidationError(validationErrors)}/> : null}
  </>;
}

Select.propTypes = {
  validationErrors: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
};
