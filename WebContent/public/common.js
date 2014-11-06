Platform = {}, Page = {};

// load dashboard file
Page.dashboard = {};
Ext.Ajax.request({
  url: baseDir + '/public/dashboard.json',
  async: false,
  success: function(response, options) {
    Page.dashboard = eval('(' + response.responseText + ')');
  }
});

Ext.application({
  name: 'Page',
  appFolder: 'page',
  controllers: Object.keys(Page.dashboard)
});

Ext.application({
  name: 'Platform',
  appFolder: 'platform',
  controllers: [ 'DashboardController', 'IFrameTabController', 'StatusBarController' ],
  launch: function() {
    Platform.dashboard = Ext.widget('dashboard');
    Platform.iframetab = Ext.widget('iframetab');
    Platform.statusbar = Ext.widget('statusbar');

    Ext.create('Ext.container.Viewport', {
      layout: 'border',
      items: [ Platform.dashboard, Platform.iframetab, Platform.statusbar ]
    });
  }
});