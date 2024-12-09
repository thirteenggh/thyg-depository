import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import Select from "./Select";
import UIStrings from "../../../constants/UIStrings";

describe('Select', () => {
  it('renders correctly', () => {
    const {container} = render(
        <Select>
          <option value="1">1</option>
          <option value="2">2</option>
        </Select>
    );
    expect(container).toMatchSnapshot();
  });

  it('renders the first error message', () => {
    const {queryByText} = render(
        <Select validatable validationErrors={[UIStrings.ERROR.FIELD_REQUIRED, "ERROR_MESSAGE"]}/>
    );

    expect(queryByText(UIStrings.ERROR.FIELD_REQUIRED)).toBeInTheDocument();
    expect(queryByText("ERROR_MESSAGE")).not.toBeInTheDocument();
  });
});
