import React from 'react';
import PropTypes from "prop-types";
import {faKey} from "@fortawesome/free-solid-svg-icons";
import {FieldWrapper, NxButton, NxFontAwesomeIcon, NxLoadWrapper, Select, Textfield, Utils} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';
import './AnalyzeApplicationModal.scss';
import {useMachine} from "@xstate/react";
import AnalyzeApplicationModalMachine from "./AnalyzeApplicationModalMachine";

export default function AnalyzeApplicationModal(props) {
  const [state, send] = useMachine(AnalyzeApplicationModalMachine, {
    context: {
      componentModel: props.componentModel
    },
    actions: {
      onClose: () => props.onCancel(),
      onAnalyzed: () => props.onAnalyzed()
    },
    devTools: true
  });

  const {validationErrors} = state.context;
  const assetMap = state.context.data !== undefined ? state.context.data.assetMap : undefined;

  // Only show the asset selection if there are more than one asset to select
  const enableAssetSelection = assetMap !== undefined ? Object.entries(assetMap).length > 1 : false;
  const isLoading = state.matches('loading');

  function handleAssetChange({target}) {
    send('ASSET', {data: {[target.name]: target.value}});
  }

  function handleUpdate(event) {
    send('UPDATE', {data: {[event.target.name]: event.target.value}});
  }

  function handleAnalyze() {
    send('ANALYZE');
  }

  function handleCancel() {
    send('CANCEL');
  }

  function retry() {
    send('RETRY');
  }

  return <NxLoadWrapper loading={isLoading} retryHandler={retry}>
      <header className="nx-modal-header">
        <h2 className="nx-h2">
          <NxFontAwesomeIcon icon={faKey} fixedWidth/>
          <span>{UIStrings.ANALYZE_APPLICATION.HEADER}</span>
        </h2>
      </header>

      <div className="nx-modal-content">
        <p dangerouslySetInnerHTML={{__html: UIStrings.ANALYZE_APPLICATION.MAIN}}></p>
        <div className="nx-form-row">
          <FieldWrapper
              labelText={UIStrings.ANALYZE_APPLICATION.EMAIL.LABEL}
              descriptionText={UIStrings.ANALYZE_APPLICATION.EMAIL.DESCRIPTION}>
            <Textfield {...Utils.fieldProps('emailAddress', state)} onChange={handleUpdate}/>
          </FieldWrapper>
          <FieldWrapper
              labelText={UIStrings.ANALYZE_APPLICATION.PASSWORD.LABEL}
              descriptionText={UIStrings.ANALYZE_APPLICATION.PASSWORD.DESCRIPTION}>
            <Textfield {...Utils.fieldProps('password', state)} type='password' onChange={handleUpdate}/>
          </FieldWrapper>
        </div>

        <div className='nx-form-row'>
          <FieldWrapper
              labelText={UIStrings.ANALYZE_APPLICATION.PACKAGES.LABEL}
              descriptionText={UIStrings.ANALYZE_APPLICATION.PACKAGES.DESCRIPTION}
              isOptional>
            <Textfield {...Utils.fieldProps('packages', state)} onChange={handleUpdate}
                       className='nx-text-input--long'/>
          </FieldWrapper>
        </div>

        {assetMap &&
        <div className='nx-form-row'>
          <FieldWrapper
              labelText={UIStrings.ANALYZE_APPLICATION.SELECT_ASSET.LABEL}
              descriptionText={UIStrings.ANALYZE_APPLICATION.SELECT_ASSET.DESCRIPTION}>
            <Select {...Utils.fieldProps('selectedAsset', state)} disabled={!enableAssetSelection}
                    onChange={handleAssetChange}>
              {Object.entries(assetMap).map(([key, value]) =>
                  <option key={key} value={key}>{value}</option>
              )}
            </Select>
          </FieldWrapper>
          <FieldWrapper
              labelText={UIStrings.ANALYZE_APPLICATION.REPORT.LABEL}
              descriptionText={UIStrings.ANALYZE_APPLICATION.REPORT.DESCRIPTION}>
            <Textfield {...Utils.fieldProps('reportLabel', state)} onChange={handleUpdate}/>
          </FieldWrapper>
        </div>
        }
      </div>
      <footer className="nx-footer">
        <NxButton variant="primary" className={Utils.isInvalid(validationErrors) && 'disabled'}
                  onClick={handleAnalyze}>
          {UIStrings.ANALYZE_APPLICATION.BUTTONS.ANALYZE}
        </NxButton>
        <NxButton onClick={handleCancel}>
          {UIStrings.ANALYZE_APPLICATION.BUTTONS.CANCEL}
        </NxButton>
      </footer>

    </NxLoadWrapper>
}

AnalyzeApplicationModal.propTypes = {
  componentModel: PropTypes.object,
  onCancel: PropTypes.func,
  onAnalyzed: PropTypes.func
}
