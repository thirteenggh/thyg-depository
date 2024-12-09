import React from 'react';

import SystemInformationSection from "./SystemInformationSection";
import NestedSystemInformationSection from "./NestedSystemInformationSection";

/**
 * @since 3.24
 * @param systemInformation - a map with objects representing each section of system information
 */
export default function SystemInformationBody({systemInformation}) {
  return <>
    <SystemInformationSection
        sectionName="nexus-status"
        information={systemInformation['nexus-status']}
    />
    <SystemInformationSection
        sectionName="nexus-node"
        information={systemInformation['nexus-node']}
    />
    <SystemInformationSection
        sectionName="nexus-configuration"
        information={systemInformation['nexus-configuration']}
    />
    <SystemInformationSection
        sectionName="nexus-properties"
        information={systemInformation['nexus-properties']}
    />
    <SystemInformationSection
        sectionName="nexus-license"
        information={systemInformation['nexus-license']}
    />
    <NestedSystemInformationSection
        sectionName="nexus-bundles"
        sectionInformation={systemInformation['nexus-bundles']}
    />
    <SystemInformationSection
        sectionName="system-time"
        information={systemInformation['system-time']}
    />
    <SystemInformationSection
        sectionName="system-properties"
        information={systemInformation['system-properties']}
    />
    <SystemInformationSection
        sectionName="system-environment"
        information={systemInformation['system-environment']}
    />
    <SystemInformationSection
        sectionName="system-runtime"
        information={systemInformation['system-runtime']}
    />
    <NestedSystemInformationSection
        sectionName="system-network"
        sectionInformation={systemInformation['system-network']}
    />
    <NestedSystemInformationSection
        sectionName="system-filestores"
        sectionInformation={systemInformation['system-filestores']}
    />
  </>;
}
