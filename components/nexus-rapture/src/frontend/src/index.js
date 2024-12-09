import axios from 'axios';
import * as nxrmUiPlugin from 'nexus-ui-plugin';
import React from 'react';
import ReactDOM from 'react-dom';
import * as xstate from 'xstate';

import registerFeature from './registerFeature';

// Expose shared dependencies on the window object for plugins to declare as externals
window.axios = axios;
window.react = React;
window.ReactDOM = ReactDOM;
window.xstate = xstate;
window.nxrmUiPlugin = nxrmUiPlugin;

// Configure axios
axios.defaults.xsrfCookieName = axios.defaults.xsrfHeaderName = 'NX-ANTI-CSRF-TOKEN';
axios.defaults.baseURL = NX.app.baseUrl;
const axiosAdapter = axios.defaults.adapter;
axios.defaults.adapter = function(config) {
  // Generate a new cache buster for each request
  const timestamp = new Date().getTime();
  if (config.url.indexOf('?') !== -1) {
    config.url += '&_dc=' + timestamp;
  }
  else {
    config.url += '?_dc=' + timestamp;
  }

  return axiosAdapter(config);
};

// Declare an initial (empty) array for plugin configurations
window.plugins = [];
window.ReactComponents = {};

// A function for the ExtJS codebase to call to register React plugins
window.onStart = function() {
  window.plugins.forEach((plugin) => {
    if (plugin.features) {
      plugin.features.forEach(registerFeature);
    }
  })
};
