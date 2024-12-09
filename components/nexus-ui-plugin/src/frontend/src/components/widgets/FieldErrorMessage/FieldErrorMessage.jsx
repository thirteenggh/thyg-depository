import React from 'react';
import classNames from 'classnames';
import {NxFontAwesomeIcon} from "@sonatype/react-shared-components";
import {faExclamationCircle} from '@fortawesome/free-solid-svg-icons';

import './FieldErrorMessage.scss';

import UIStrings from '../../../constants/UIStrings';

/**
 * @since 3.21
 */
export default function FieldErrorMessage({message, className}) {
  const classes = classNames('nxrm-error-message', className);
  return <span className={classes}>
    <NxFontAwesomeIcon icon={faExclamationCircle}/>
    <span className='nxrm-error-message-text' tabIndex="-1"> {message || UIStrings.ERROR.FIELD_REQUIRED} </span>
  </span>;
}
