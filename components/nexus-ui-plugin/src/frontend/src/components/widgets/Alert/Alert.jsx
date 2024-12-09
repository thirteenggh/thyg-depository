import React from 'react';
import classNames from 'classnames';
import {NxFontAwesomeIcon} from "@sonatype/react-shared-components";
import {faExclamationCircle} from '@fortawesome/free-solid-svg-icons';

import './Alert.scss';

import PropTypes from "prop-types";

/**
 * @since 3.22
 */
export default function Alert({type, children, className, ...attrs}) {
  const classes = classNames('nxrm-alert', className, `nxrm-alert--${type}`);
  return <div className={classes} role="alert" {...attrs}>
    <NxFontAwesomeIcon icon={faExclamationCircle}/>
    {children}
  </div>;
}

Alert.propTypes = {
  type: PropTypes.oneOf(['error', 'info']).isRequired
}
