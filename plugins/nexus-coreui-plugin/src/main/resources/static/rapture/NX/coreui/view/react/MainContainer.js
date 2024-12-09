/*global Ext, NX, REACT_COMPONENTS, react, ReactDOM */

/**
 * React Main Container
 *
 * @since 3.21
 */
Ext.define('NX.coreui.view.react.MainContainer', {
  extend: 'Ext.Component',
  alias: 'widget.nx-coreui-react-main-container',

  reactView: undefined,

  reactViewProps: null,

  scrollable: true,

  listeners: {
    afterrender: 'initReactView',
    beforedestroy: 'destroyReactView'
  },

  initReactView: function() {
    var reactElement = react.createElement(this.reactView, this.reactViewProps, null);
    ReactDOM.render(reactElement, this.getEl().dom);
  },

  destroyReactView: function() {
    if (this.reactView) {
      ReactDOM.unmountComponentAtNode(this.getEl().dom);
    }
  },

  refresh: function() {
    this.destroyReactView();
    this.initReactView();
  }
});
