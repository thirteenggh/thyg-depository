
import React, {useState} from 'react';

import './CheckboxControlledWrapper.scss';
import {NxCheckbox} from "@sonatype/react-shared-components";
import PropTypes from "prop-types";

/**
 * @since 3.29
 */
export default function CheckboxControlledWrapper({isChecked, children, onChange}) {
  const [checked, setChecked] = useState(!!isChecked),
      toggle = () => setChecked(!checked);

  function handleChange(event) {
    toggle();
    if (onChange) {
      onChange(event.target.checked);
    }
  }

  return <div className='checkbox-controlled-wrapper'>
    <div className='checkbox-control'>
      <NxCheckbox isChecked={checked} onChange={handleChange}/>
    </div>
    <div className='checkbox-children'>
      {children}
    </div>
  </div>;
};

CheckboxControlledWrapper.propTypes = {
  isChecked: PropTypes.bool,
  children: PropTypes.oneOfType([PropTypes.element, PropTypes.arrayOf(PropTypes.element)]),
  onChange: PropTypes.func
};
