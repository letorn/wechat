Ext.define('Platform.view.StatusBar', {
  extend: 'Ext.toolbar.Toolbar',
  alias: 'widget.statusbar',
  region: 'south',
  initComponent: function() {
    this.items = [ {
      xtype: 'button',
      text: '状态'
    } ]
    this.callParent(arguments);
  }
});