
import React from 'react';
import PropTypes from 'prop-types';

import './Information.scss';

/**
 * @since 3.22
 */
export default function Information({information}) {
  return <table className="nxrm-information">
    <tbody>
    {Object.entries(information).map(([name, value]) =>
        <InformationRow key={name}>
          <InformationName>{name}</InformationName>
          <InformationValue>{String(value)}</InformationValue>
        </InformationRow>
    )}
    </tbody>
  </table>;
}

Information.propTypes = {
  information: PropTypes.object.isRequired
}

function InformationRow({children}) {
  return <tr className="nxrm-information--row">{children}</tr>;
};

function InformationName({children}) {
  return <td className="nxrm-information--name">{children}</td>;
};

function InformationValue({children}) {
  return <td className="nxrm-information--value">{children}</td>;
};
