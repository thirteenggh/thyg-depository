import React from 'react';

import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import {faInfo} from '@fortawesome/free-solid-svg-icons';

import PageTitle from './PageTitle';

describe('PageTitle', () => {
  it('renders the text with an icon', () => {
    const {container} = render(
        <PageTitle icon={faInfo} text="text"/>
    );
    expect(container).toMatchSnapshot();
  });

  it('renders the text without an icon', () => {
    const {container} = render(
        <PageTitle text="text"/>
    );
    expect(container).toMatchSnapshot();
  });

  it('renders the text with a description', () => {
    const {container} = render(
        <PageTitle text="text" description="description"/>
    );
    expect(container).toMatchSnapshot();
  });
});
