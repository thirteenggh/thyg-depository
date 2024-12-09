import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import Textfield from './Textfield';
import UIStrings from "../../../constants/UIStrings";

describe('Textfield', () => {
  it('renders correctly without an error message', () => {
    const {container, queryByText} = render(<Textfield />);

    expect(queryByText(UIStrings.ERROR.FIELD_REQUIRED)).not.toBeInTheDocument();

    expect(container).toMatchSnapshot();
  });

  it('renders with a single error message', () => {
    const {container, queryByText} = render(<Textfield validationErrors={UIStrings.ERROR.FIELD_REQUIRED} />);

    expect(queryByText(UIStrings.ERROR.FIELD_REQUIRED)).toBeInTheDocument();

    expect(container).toMatchSnapshot();
  });

  it('shows the first error', () => {
    const {queryByText} = render(<Textfield validationErrors={[UIStrings.ERROR.FIELD_REQUIRED, "ERROR_MESSAGE"]}/>);

    expect(queryByText(UIStrings.ERROR.FIELD_REQUIRED)).toBeInTheDocument();
    expect(queryByText("ERROR_MESSAGE")).not.toBeInTheDocument();
  });
});
