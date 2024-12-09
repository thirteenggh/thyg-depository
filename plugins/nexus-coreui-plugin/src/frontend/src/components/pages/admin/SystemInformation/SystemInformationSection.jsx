import React from 'react';

import {Information, Section} from 'nexus-ui-plugin';

/**
 * @since 3.24
 * @param sectionName - the name of the section
 * @param information - a key value map of information to display
 */
export default function SystemInformationSection({sectionName, information}) {
  return <Section>
    <h2>{sectionName}</h2>
    <Information information={information}/>
  </Section>;
}
