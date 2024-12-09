import classNames from 'classnames';
import React, {Children, cloneElement, useState} from 'react';
import PropTypes from 'prop-types';

import Master from './Master';
import Detail from './Detail';

import ExtJS from '../../../../interface/ExtJS';

/**
 * @since 3.24
 */
export default function MasterDetail({className, children, path, ...attrs}) {
  const {location: {pathname}} = ExtJS.useHistory({basePath: path});
  const [isCreate, setCreate] = useState(false);

  const classes = classNames('nxrm-master-detail', className);
  const isMasterRoute = pathname === '';
  const itemId = isMasterRoute ? '' : pathname.substring(1);

  function onCreate() {
    setCreate(true);
  }

  function onEdit(itemId) {
    window.location.hash = `${path}:${itemId}`;
  }

  function onDone() {
    setCreate(false);
    window.location.hash = path;
  }

  const childrenArray = Children.toArray(children);
  const master = cloneElement(childrenArray.filter(child => child.type === Master)[0], {onCreate, onEdit});
  const detail = cloneElement(childrenArray.filter(child => child.type === Detail)[0], {itemId, onDone});

  return (
      <div className={classes} {...attrs}>
        {isMasterRoute && !isCreate ? master : null}
        {!isMasterRoute || isCreate ? detail : null}
      </div>
  );
}

MasterDetail.propTypes = {
  path: PropTypes.string.isRequired
};
