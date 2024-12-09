import React from 'react';
import PropTypes from 'prop-types';

import './HelpTile.scss';
import classNames from "classnames";

/**
 * @since 3.29
 */
export default function HelpTile({className, ...attrs}) {
  const classes = classNames('nxrm-help-tile', 'nx-tile', className);
  return <div className={classes} {...attrs}/>;
}

HelpTile.propTypes = {
  className: PropTypes.string
};
