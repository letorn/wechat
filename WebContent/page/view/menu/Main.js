Ext.define('Page.view.menu.Main', {
  extend: 'Ext.tree.Panel',
  alias: 'widget.page-menu',
  title: '自定义菜单',
  store: 'menu.Main',
  rootVisible: false,
  initComponent: function() {
    this.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
      clicksToEdit: 2,
      listeners: {
        beforeedit: function(editor, e, eOpts) {
          var depth = e.record.getDepth();
          if (depth == 0) {
            return false;
          } else if (depth == 1) {
            return e.record.isLeaf() || e.field == 'name';
          } else if (depth == 2) {
            return true;
          }
        }
      }
    });

    this.plugins = [ this.cellEditing ];

    this.tbar = [ {
      itemId: 'button-add',
      iconCls: 'add'
    }, {
      itemId: 'button-save',
      iconCls: 'save'
    }, '-', {
      itemId: 'button-refresh',
      iconCls: 'refresh'
    } ];

    this.columns = [ {
      xtype: 'treecolumn',
      text: '标题',
      dataIndex: 'name',
      editor: {
        xtype: 'textfield'
      },
      width: 200
    }, {
      text: '类型',
      dataIndex: 'type',
      editor: {
        xtype: 'combobox',
        store: [ 'click', 'view' ]
      }
    }, {
      text: '值',
      dataIndex: 'value',
      editor: {
        xtype: 'textfield'
      },
      width: 350
    }, {
      xtype: 'actioncolumn',
      align: 'center',
      width: 30,
      iconCls: 'delete',
      handler: function(view, rowIndex, colIndex, item, e, record, row) {
        var node = record.parentNode;
        record.remove();
        if (node.getDepth() == 1 && node.childNodes.length == 0) {
          var parentNode = node.parentNode;
          var newNode = parentNode.createNode({
            name: node.data.name,
            type: 'click',
            value: 'key',
            leaf: true
          });
          parentNode.replaceChild(newNode, node);
        }
      },
      isDisabled: function(view, rowIndex, colIndex, item, record) {
        return record.isRoot();
      }
    } ];
    this.callParent(arguments);
  }
});