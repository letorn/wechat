Ext.define('Page.controller.MenuController', {
  extend: 'Ext.app.Controller',
  views: [ 'menu.Main' ],
  stores: [ 'menu.Main' ],
  init: function() {
    this.control({
      'page-menu': {
        'render': function(panel, eOpts) {
          this.panel = panel;
          this.panel.buttonAdd = panel.down('#button-add');
          this.panel.buttonSave = panel.down('#button-save');
          this.panel.buttonRefresh = panel.down('#button-refresh');

          this.panel.on('selectionchange', this.onMenuTreeSelectionChange, this);
          this.panel.buttonAdd.on('click', this.onButtonAddClick, this);
          this.panel.buttonSave.on('click', this.onButtonSaveClick, this);
          this.panel.buttonRefresh.on('click', this.onButtonRefreshClick, this);

          this.panel.buttonAdd.disable();
        }
      }
    });
  },
  onMenuTreeSelectionChange: function(model, records, eOpts) {
    if (records[0] && (records[0].getDepth() == 0 || records[0].getDepth() == 1)) {
      this.panel.buttonAdd.enable();
      this.panel.selRecord = records[0];
    } else {
      this.panel.buttonAdd.disable();
      delete this.panel.selRecord;
    }
  },
  onButtonAddClick: function(button, e, eOpts) {
    var depth = this.panel.selRecord.getDepth();
    var length = this.panel.selRecord.childNodes.length;
    if (depth == 0 && length >= 3) {
      Ext.Msg.show({
        title: '提示',
        msg: '一级菜单最多支持3个！',
        icon: Ext.Msg.INFO,
        buttons: Ext.Msg.OK
      });
      return;
    } else if (depth == 1 && length >= 5) {
      Ext.Msg.show({
        title: '提示',
        msg: '二级菜单最多支持5个！',
        icon: Ext.Msg.INFO,
        buttons: Ext.Msg.OK
      });
      return;
    }

    if (this.panel.selRecord.isLeaf()) {
      var parentNode = this.panel.selRecord.parentNode;
      var newNode = parentNode.createNode({
        name: this.panel.selRecord.data.name,
        leaf: false
      });
      parentNode.replaceChild(newNode, this.panel.selRecord);
      this.panel.selRecord = newNode;
    }

    var newRecord = this.panel.selRecord.appendChild({
      name: '标题',
      type: 'click',
      value: 'key',
      leaf: true
    });
    this.panel.selRecord.expand();
    this.panel.selModel.select(newRecord);
  },
  onButtonSaveClick: function(button, e, eOpts) {
    var depth = this.menuTree.selRecord.getDepth();
    var length = this.menuTree.selRecord.childNodes.length;
    if (depth == 0 && length >= 3) {
      Ext.Msg.show({
        title: '提示',
        msg: '一级菜单最多支持3个！',
        icon: Ext.Msg.INFO,
        buttons: Ext.Msg.OK
      });
      return;
    } else if (depth == 1 && length >= 5) {
      Ext.Msg.show({
        title: '提示',
        msg: '二级菜单最多支持5个！',
        icon: Ext.Msg.INFO,
        buttons: Ext.Msg.OK
      });
      return;
    }

    if (this.menuTree.selRecord.isLeaf()) {
      var parentNode = this.menuTree.selRecord.parentNode;
      var newNode = parentNode.createNode({
        name: this.menuTree.selRecord.data.name,
        leaf: false
      });
      parentNode.replaceChild(newNode, this.menuTree.selRecord);
      this.menuTree.selRecord = newNode;
    }

    var newRecord = this.menuTree.selRecord.appendChild({
      name: '标题',
      type: 'click',
      value: 'key',
      leaf: true
    });
    this.menuTree.selRecord.expand();
    this.menuTree.selModel.select(newRecord);
  },
  onButtonSaveClick: function(button, e, eOpts) {
    var node = {
      children: []
    };
    var children = this.menuTree.store.getRootNode().childNodes;
    for (var i = 0; i < children.length; i++) {
      var child = {
        name: children[i].data.name
      };
      if (children[i].hasChildNodes()) {
        child.leaf = false;
        child.children = [];
        var subChildren = children[i].childNodes;
        for (var j = 0; j < subChildren.length; j++) {
          var subChild = {
            type: subChildren[j].data.type,
            name: subChildren[j].data.name,
            value: subChildren[j].data.value
          };
          subChild.leaf = true;
          child.children.push(subChild);
        }
      } else {
        child.type = children[i].data.type;
        child.value = children[i].data.value;
        child.leaf = true;
      }
      node.children.push(child);
    }
    this.menuTree.store.proxy.extraParams = {
      nodeString: Ext.encode(node)
    };
    this.menuTree.store.sync({
      success: function(batch, options) {
        Ext.Msg.show({
          title: '提示',
          msg: '保存成功！',
          icon: Ext.Msg.INFO,
          buttons: Ext.Msg.OK
        });
      },
      failure: function(batch, options) {
        Ext.Msg.show({
          title: '提示',
          msg: '保存失败！',
          icon: Ext.Msg.ERROR,
          buttons: Ext.Msg.OK
        });
      }
    });
  },
  onButtonRefreshClick: function(button, e, eOpts) {
    this.menuTree.store.reload();
  }
});