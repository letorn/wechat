Ext.define('Platform.view.Dashboard', {
  extend: 'Ext.tree.Panel',
  alias: 'widget.dashboard',
  region: 'west',
  width: 180,
  title: '菜单',
  collapsible: true,
  rootVisible: false,
  store: 'DashboardStore',
  initComponent: function() {
    this.callParent(arguments);
  }
});