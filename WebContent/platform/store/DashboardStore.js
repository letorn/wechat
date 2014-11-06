Ext.define('Platform.store.DashboardStore', {
  extend: 'Ext.data.TreeStore',
  root: {
    expanded: true,
    children: function() {
      var children = [];
      Ext.Object.each(Page.dashboard, function(key, value, object) {
        children.push({
          id: value.view,
          text: value.name,
          xtype: value.view,
          leaf: true
        });
      });
      return children;
    }.call()
  }
});