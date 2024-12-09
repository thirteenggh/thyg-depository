import React from 'react';
import classNames from 'classnames';
import {useService} from '@xstate/react';
import {NxErrorAlert, NxLoadWrapper} from "@sonatype/react-shared-components";

/**
 * @since 3.29
 */
export default function Form({
                               children,
                               className,
                               onKeyPress,
                               onSubmit,
                               isLoading,
                               loadError,
                               alert,
                               ...attrs}) {
  const classes = classNames('nxrm-form', className);

  function handleSubmit(event) {
    onSubmit(event);
  }

  function handleKeyPress(event) {
    if (event.key === 'Enter') {
      handleSubmit(event);
    }
    else {
      onKeyPress(event);
    }
  }

  return <div className={classes} onKeyPress={handleKeyPress} {...attrs}>
    <NxLoadWrapper loading={isLoading} error={loadError && String(loadError)}>
      {children}
    </NxLoadWrapper>
  </div>;
}
