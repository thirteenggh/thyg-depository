
import React from 'react';

import classNames from 'classnames';

/**
 * @since 3.26
 */
export default function Page({className, ...attrs}) {
  return <main className={classNames("nx-page-main", className)} {...attrs}/>;
}
