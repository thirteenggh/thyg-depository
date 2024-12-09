import React from 'react';
import classNames from 'classnames';

/**
 * @since 3.22
 */
export default function Section({className, children, ...rest}) {
  return <div className={classNames("nxrm-section", "nx-tile", className)} {...rest}>{children}</div>;
}
