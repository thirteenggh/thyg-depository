import React from 'react';
import UIStrings from '../../../../constants/UIStrings';
import {FieldWrapper, NxButton, NxFontAwesomeIcon, SectionFooter, Textfield} from "nexus-ui-plugin";
import {faDownload} from '@fortawesome/free-solid-svg-icons';

export default function SupportZipResponse({response, download}) {
  return <div className='nxrm-support-zip-response'>
    <h2>{UIStrings.SUPPORT_ZIP.CREATED_TITLE}</h2>
    <p>支持 ZIP 已创建<br/>
    您可以在文件系统上引用此文件，也可以从浏览器下载该文件。</p>
    <FieldWrapper labelText={UIStrings.SUPPORT_ZIP.CREATED_NAME_LABEL}>
      <Textfield
        name='name'
        disabled={true}
        value={response.name}
      />
    </FieldWrapper>
    <FieldWrapper labelText={UIStrings.SUPPORT_ZIP.CREATED_SIZE_LABEL}>
      <Textfield
        name='size'
        disabled={true}
        value={response.size}
      />
    </FieldWrapper>
    <FieldWrapper labelText={UIStrings.SUPPORT_ZIP.CREATED_PATH_LABEL}>
      <Textfield
        name='file'
        disabled={true}
        value={response.file}
      />
    </FieldWrapper>
    <SectionFooter>
      <NxButton variant="primary" onClick={(event) => download(event, response.name)} type="submit">
        <NxFontAwesomeIcon icon={faDownload}/>
        <span>{UIStrings.SUPPORT_ZIP.CREATED_DOWNLOAD_BUTTON}</span>
      </NxButton>
    </SectionFooter>
  </div>;
}
