import React, {forwardRef} from 'react';
import classNames from 'classnames';

import './Code.scss';

/**
 * @since 3.27
 */
export default forwardRef(({id, name, className, ...attrs}, ref) => {
  return <textarea id={id || name} name={name} className={classNames('nxrm-code', className)} ref={ref} {...attrs} />;
});
