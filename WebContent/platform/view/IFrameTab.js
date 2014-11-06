Ext.define('Platform.view.IFrameTab', {
  extend: 'Ext.tab.Panel',
  alias: 'widget.iframetab',
  region: 'center',
  initComponent: function() {
    this.callParent(arguments);
  }
});