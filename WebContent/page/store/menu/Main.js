Ext.define('Page.store.menu.Main', {
  extend: 'Ext.data.TreeStore',
  fields: [ 'name', 'type', 'value' ],
  root: {
    name: '平台菜单'
  },
  proxy: {
    type: 'ajax',
    api: {
      create: 'menu.do?action=save',
      read: 'menu.do?action=get',
      update: 'menu.do?action=save',
      destroy: 'menu.do?action=save'
    }
  }
});