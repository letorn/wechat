Ext.define('Platform.controller.DashboardController', {
  extend: 'Ext.app.Controller',
  views: [ 'Dashboard' ],
  stores: [ 'DashboardStore' ],
  init: function() {
    this.control({
      'viewport > dashboard': {
        'afterrender': function(view, eOpts) {
          view.getSelectionModel().select(0);
        },
        'select': function(view, record, index, eOpts) {
          var view = Platform.iframetab.queryById(record.raw.id);
          if (!view) {
            view = Ext.widget(record.raw.xtype, {
              id: record.raw.id,
              closable: true
            });
            Platform.iframetab.add(view);
          }
          Platform.iframetab.setActiveTab(view);
        }
      }
    });
  }
});