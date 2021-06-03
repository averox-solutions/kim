/*
 * Kimios - Document Management System Software
 * Copyright (C) 2012-2013  DevLib'
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * Context Menu
 */
kimios.ContextMenu = new function () {
    /**
     * Initialize all context menus
     */
    this.init = function () {
        var config = {
            shadow: false,
            showSeparator: false,
            enableScrolling: false,
            allowOtherMenus: false
        };

        // init all menus
        this.initHomeMenu(config);
        this.initWorkspaceMenu(config);
        this.initFolderMenu(config);
        this.initDocumentMenu(config);
        this.initViewableDocumentMenu(config);
        this.initWorkspaceMultipleMenu(config);
        this.initDefaultMultipleMenu(config);
        this.initMyTasksMenu(config);
        this.initMyBonitaTasksMenu(config);
        this.initMyBonitaAssignedTasksMenu(config);
        this.initMyBonitaAllTasksMenu(config);
        this.initSearchRequestsMenu(config);
        this.initSearchRequestsContainerMenu(config);
        this.initBookmarksMenu(config);
        this.initBookmarksContainerMenu(config);
        this.initRecentItemsMenu(config);
        this.initRecentItemsContainerMenu(config);
        this.initGridMenu(config);
        this.initWGridMenu(config);
        this.initMyTasksContainerMenu(config);
        this.initMyBonitaTasksContainerMenu(config);
        this.initVersionsMenu(config);
        this.initCommentsMenu(config);
        this.initCommentsContainerMenu(config);
        this.initRelatedDocumentsMenu(config);
        this.initTreeContainerMenu(config);
        this.initUnknownMenu(config);
        this.initCartMenu(config);
        this.initCartContainerMenu(config);
        this.initShareMenu(config);
        this.initShareContainerMenu(config);


        this.initSymLinkDocumentMenu(config);
        this.initViewableSymLinkDocumentMenu(config);
    };

    /**
     * Return instance menu depending on context
     */
    this.getInstance = function (dmEntityPojo, context, records) {

        if (records && records.length > 1) {
            this.multipleRecords = records;
        }

        this.dmEntityPojo = dmEntityPojo;
        this.context = context;

        /* check context and return instance */
        // default
        if (context == undefined || context == 'default' || context == 'tree') {
            // single selection
            if (!(this.dmEntityPojo instanceof Array)) { //is not array
                this.multiple = false;
                switch (dmEntityPojo.type) {
                    case 1: // is workspace
                        return this.workspaceMenu;
                    case 2: // is folder
                        return this.folderMenu;
                    case 3: // is document
                        var ext = dmEntityPojo.extension.toLowerCase();
                        if (kimios.isViewableExtension(ext) || ext.toLowerCase() == 'pdf') {
                            return this.viewableDocumentMenu;
                        } else {
                            return this.documentMenu;
                        }
                    case 7:
                        var ext = dmEntityPojo.targetEntity.extension ? dmEntityPojo.targetEntity.extension.toLowerCase() : '';
                        if (kimios.isViewableExtension(ext) || ext.toLowerCase() == 'pdf') {
                            return this.viewableSymlinkDocumentMenu;
                        } else {
                            return this.symlinkDocumentMenu;
                        }
                    default: // is home
                        return this.homeMenu;
                }
            }

            // multiple selection
            else {
                this.multiple = true;
                switch (dmEntityPojo[0].type) {
                    case 1: // selected workspaces
                        return this.workspaceMultipleMenu;
                    default: // selected entities
                        return this.defaultMultipleMenu;
                }
            }
        }

        // search requests
        else if (context == 'searchRequests') {
            return this.searchRequestsMenu;
        }

        // bookmarks
        else if (context == 'bookmarks') {
            return this.bookmarksMenu;
        }

        // recent items
        else if (context == 'recentItems') {

            switch (dmEntityPojo.type) {
                case 1: //workspace
                    break;
                case 2: //folder
                    break;
                case 3: //document
                    var ext = dmEntityPojo.extension.toLowerCase();
                    if (kimios.isViewableExtension(ext) || ext.toLowerCase() == 'pdf') {
                        return this.viewableDocumentMenu;
                    } else {
                        return this.documentMenu;
                    }
                    break;
            }

            return this.recentItemsMenu;
        }

        // tasks
        else if (context == 'myTasks') {
            return this.myTasksMenu;
        }

        // bonita tasks
        else if (context == 'myBonitaTasks') {
            return this.myBonitaTasksMenu;
        }

        // bonita tasks
        else if (context == 'myBonitaAssignedTasks') {
            return this.myBonitaAssignedTasksMenu;
        }

        // bonita tasks
        else if (context == 'myBonitaAllTasks') {
            return this.myBonitaAllTasksMenu;
        }

        else if (context == 'cart') {
            return this.cartMenu;
        }
        else if(context == 'shares'){
            return this.shareMenu;
        }
        // versions
        else if (context == 'versions') {
            return this.versionsMenu;
        }

        // comments
        else if (context == 'comments') {
            return this.commentsMenu;
        }
        else if (context == 'commentsContainer') {
            return this.commentsContainerMenu;
        }

        // related documents
        else if (context == 'relatedDocuments') {
            return this.relatedDocumentsMenu;
        }

        // grid container
        else if (context == 'gridContainer') {
            switch (dmEntityPojo.type) {
                case 1:
                    return this.wgridMenu;
                case 2:
                    return this.gridMenu;
                default:
                    return this.homeMenu;
            }
        }

        // tasks container
        else if (context == 'myTasksContainer') {
            return this.myTasksContainerMenu;
        }

        // bonita tasks container
        else if (context == 'myBonitaTasksContainer') {
            return this.myBonitaTasksContainerMenu;
        }

        else if (context == 'cartContainer') {
            return this.cartContainerMenu;
        }

        // tree container
        else if (context == 'treeContainer') {
            return this.treeContainerMenu;
        }

        // search requests container
        else if (context == 'searchRequestsContainer') {
            return this.searchRequestsContainerMenu;
        }

        // bookmarks container
        else if (context == 'bookmarksContainer') {
            return this.bookmarksContainerMenu;
        }

        // recent items container
        else if (context == 'recentItemsContainer') {
            return this.recentItemsContainerMenu;
        } else if(context == 'sharesContainer') {
            return this.shareMenu;
        }

        // no context found
        else return this.unknownMenu;
    };

    /**
     * Show the corresponding menu
     */
    this.show = function (dmEntityPojo, e, context) {
        var menu = this.getInstance(dmEntityPojo, context);

        menu.items.each(function(item, index, length){
            if(item.filterType && item.filterType.length > 0 &&
                dmEntityPojo.extension && item.filterType.indexOf(dmEntityPojo.extension.toLowerCase()) == -1){
                item.hide();
            } else {
                item.show();
            }
        });

        menu.showAt(e.getXY());
    };

    this.showMultiple = function (dmEntityPojo, e, context, records) {
        this.getInstance(dmEntityPojo, context, records).showAt(e.getXY());
    };

    this.initHomeMenu = function (config) {
        this.homeMenu = new Ext.menu.Menu(config);
        if (kimios.explorer.getViewport().rights.isWorkspaceCreator) {
            this.homeMenu.add(this.getNewWorkspaceItem());
            this.homeMenu.addSeparator();
        }
        this.homeMenu.add(this.getRefreshItem());
    };

    this.initWorkspaceMenu = function (config) {
        this.workspaceMenu = new Ext.menu.Menu(config);
        this.workspaceMenu.add(this.getNewTabItem());
        this.workspaceMenu.addSeparator();
        this.workspaceMenu.add(this.getNewFolderItem());
        this.workspaceMenu.add(this.getDeleteItem());
        this.workspaceMenu.add(this.getAddToBookmarksItem());
        this.workspaceMenu.addSeparator();
        this.workspaceMenu.add(this.getRefreshItem());
        this.workspaceMenu.addSeparator();
        this.workspaceMenu.add(this.getPropertiesItem());
    };

    this.initFolderMenu = function (config) {
        this.folderMenu = new Ext.menu.Menu(config);
        this.folderMenu.add(this.getNewTabItem());
        this.folderMenu.addSeparator();
        this.folderMenu.add(this.getNewFolderItem());
        this.folderMenu.add(this.getImportDocumentItem());
        this.folderMenu.add(this.getMoveItem());
        this.folderMenu.add(this.getDeleteItem());
        this.folderMenu.add(this.getAddToBookmarksItem());
        this.folderMenu.addSeparator();
        this.folderMenu.add(this.getRefreshItem());
        this.folderMenu.addSeparator();
        this.folderMenu.add(this.getPropertiesItem());
        this.folderMenu.addSeparator();
        this.folderMenu.add(this.getCartItem());
    };

    this.initDocumentMenu = function (config) {
        this.documentMenu = new Ext.menu.Menu(config);
        this.getDocMenuItemsList(this.documentMenu);
        this.documentMenu.addSeparator();
        this.documentMenu.add(this.getGetCreateSymlinkItem());

    };
    this.initViewableDocumentMenu = function (config) {
        this.viewableDocumentMenu = new Ext.menu.Menu(config);
        this.getViewableDocMenuItemsList(this.viewableDocumentMenu);
        this.viewableDocumentMenu.addSeparator();
        this.viewableDocumentMenu.add(this.getGetCreateSymlinkItem());
    };

    this.initViewableSymLinkDocumentMenu = function (config) {
        this.viewableSymlinkDocumentMenu = new Ext.menu.Menu(config);

        /*
         add commons items for viewable document
         */
        this.getViewableDocMenuItemsList(this.viewableSymlinkDocumentMenu, ['delete', 'move']);

        this.viewableSymlinkDocumentMenu.addSeparator();
        this.viewableSymlinkDocumentMenu.add(this.getGetRemoveSymlinkItem());
    };

    this.initSymLinkDocumentMenu = function (config) {
        this.symlinkDocumentMenu = new Ext.menu.Menu(config);

        /*
         add commons items for document
         */
        this.getViewableDocMenuItemsList(this.symlinkDocumentMenu, ['delete', 'move']);


        this.symlinkDocumentMenu.addSeparator();
        this.symlinkDocumentMenu.add(this.getGetRemoveSymlinkItem());
    };

    this.getDocMenuItemsList = function (menu, hiddenItems) {
        menu.add(this.getGetDocumentItem());
        menu.add(this.getEditDocumentItem());
        menu.addSeparator();
        menu.add(this.getShareDocumentItem());
        menu.add(this.getUpdateCurrentVersionItem());
        menu.add(this.getSetVersionItem());
        menu.add(this.getCheckInCheckOutItem());
        if (bonitaEnabled) {
            menu.add(this.getStartProcessItem());
        } else {
            menu.add(this.getStartWorkflowItem());
        }



        if (!hiddenItems || hiddenItems.indexOf('move') == -1) {
            menu.add(this.getMoveItem());
        }
        if (!hiddenItems || hiddenItems.indexOf('delete') == -1) {
            menu.add(this.getDeleteItem());
        }
        menu.add(this.getAddToBookmarksItem());
        menu.addSeparator();
        menu.add(this.getRefreshItem());
        menu.addSeparator();
        menu.add(this.getCommentsItem());
        menu.add(this.getGetDocumentMailLinkItem());
        menu.add(this.getCartItem());
        menu.addSeparator();
        menu.add(this.getPropertiesItem());
    };

    this.getViewableDocMenuItemsList = function (menu, hiddenItems) {
        menu.add(this.getViewDocumentItem());
        menu.add(this.getEditDocumentItem());
        menu.add(this.getGetDocumentItem());
        menu.add(this.getBarcodeDocumentItem());
        menu.add(this.getShareDocumentItem());
        menu.addSeparator();
        menu.add(this.getUpdateCurrentVersionItem());
        menu.add(this.getSetVersionItem());
        menu.add(this.getCheckInCheckOutItem());

        if (bonitaEnabled) {
            menu.add(this.getStartProcessItem());
        } else {
            menu.add(this.getStartWorkflowItem());
        }
        if (!hiddenItems || hiddenItems.indexOf('move') == -1) {
            menu.add(this.getMoveItem());
        }
        if (!hiddenItems || hiddenItems.indexOf('delete') == -1) {
            menu.add(this.getDeleteItem());
        }
        menu.add(this.getAddToBookmarksItem());
        menu.addSeparator();
        menu.add(this.getRefreshItem());
        menu.addSeparator();
        menu.add(this.getCommentsItem());
        menu.add(this.getGetDocumentMailLinkItem());
        menu.add(this.getCartItem());
        menu.addSeparator();
        menu.add(this.getPropertiesItem());
    }


    this.initWorkspaceMultipleMenu = function (config) {
        this.workspaceMultipleMenu = new Ext.menu.Menu(config);
        this.workspaceMultipleMenu.add(this.getDeleteItem());
        this.workspaceMultipleMenu.add(this.getAddToBookmarksItem());
        this.workspaceMultipleMenu.addSeparator();
        this.workspaceMultipleMenu.add(this.getPropertiesItem());
    };

    this.initDefaultMultipleMenu = function (config) {
        this.defaultMultipleMenu = new Ext.menu.Menu(config);
        this.defaultMultipleMenu.add(this.getShareDocumentItem());
        this.defaultMultipleMenu.add(this.getMoveItem());
        this.defaultMultipleMenu.add(this.getDeleteItem());
        this.defaultMultipleMenu.add(this.getAddToBookmarksItem());
        this.defaultMultipleMenu.addSeparator();
        this.defaultMultipleMenu.add(this.getCartItem());
        this.defaultMultipleMenu.add(this.getPropertiesItem());
    };

    this.initMyTasksMenu = function (config) {
        this.myTasksMenu = new Ext.menu.Menu(config);
        this.myTasksMenu.add(this.getViewDocumentItem());
        this.myTasksMenu.add(this.getGetDocumentItem());
        this.myTasksMenu.addSeparator();
        this.myTasksMenu.add(this.getAcceptStatusItem());
        this.myTasksMenu.add(this.getRejectStatusItem());
        this.myTasksMenu.addSeparator();
        this.myTasksMenu.add(this.getRefreshItem());
        this.myTasksMenu.addSeparator();
        this.myTasksMenu.add(this.getPropertiesItem());
    };

    this.initMyBonitaTasksMenu = function (config) {
        this.myBonitaTasksMenu = new Ext.menu.Menu(config);
        this.myBonitaTasksMenu.add(this.getCheckBonitaTask());
        this.myBonitaTasksMenu.addSeparator();
        this.myBonitaTasksMenu.add(this.getTakeBonitaTask());
        this.myBonitaTasksMenu.add(this.getHideBonitaTask());
        this.myBonitaTasksMenu.addSeparator();
        this.myBonitaTasksMenu.add(this.getRefreshItem());
        this.myBonitaTasksMenu.addSeparator();
        this.myBonitaTasksMenu.add(this.getPropertiesBonitaTask());
    };

    this.initMyBonitaAssignedTasksMenu = function (config) {
        this.myBonitaAssignedTasksMenu = new Ext.menu.Menu(config);
        this.myBonitaAssignedTasksMenu.add(this.getCheckBonitaTask());
        this.myBonitaAssignedTasksMenu.addSeparator();
        this.myBonitaAssignedTasksMenu.add(this.getReleaseBonitaTask());
        this.myBonitaAssignedTasksMenu.addSeparator();
        this.myBonitaAssignedTasksMenu.add(this.getRefreshItem());
        this.myBonitaAssignedTasksMenu.addSeparator();
        this.myBonitaAssignedTasksMenu.add(this.getPropertiesBonitaTask());
    };

    this.initMyBonitaAllTasksMenu = function (config) {
        this.myBonitaAllTasksMenu = new Ext.menu.Menu(config);
        this.myBonitaAllTasksMenu.add(this.getCheckBonitaTask());
        this.myBonitaAllTasksMenu.addSeparator();
        this.myBonitaAllTasksMenu.add(this.getPropertiesBonitaTask());
    };

    this.initCartMenu = function (config) {
        this.cartMenu = new Ext.menu.Menu(config);
        this.cartMenu.add(this.getViewDocumentItem());
        this.cartMenu.add(this.getGetDocumentItem());
        this.cartMenu.addSeparator();
        this.cartMenu.add(this.getRemoveCartSimpleItem());
        this.cartMenu.add(this.getRemoveCartItem());
        this.cartMenu.addSeparator();
//        this.cartMenu.add(this.getRefreshItem());
//        this.cartMenu.addSeparator();
        this.cartMenu.add(this.getPropertiesItem());
    };

    this.initSearchRequestsMenu = function (config) {
        this.searchRequestsMenu = new Ext.menu.Menu(config);
//        this.searchRequestsMenu.add(this.getEditSearchRequestsItem());
        this.searchRequestsMenu.add(this.getRemoveSearchRequestsItem());
        this.searchRequestsMenu.addSeparator();
        this.searchRequestsMenu.add(this.getRefreshItem());
    };

    this.initShareMenu = function (config) {
        this.shareMenu = new Ext.menu.Menu(config);
        this.shareMenu.add(this.getGetDocumentItem());
        this.shareMenu.add(this.getUpdateCurrentVersionItem());
        this.shareMenu.add(this.getPropertiesItem());
        this.shareMenu.add(this.getRefreshItem());
    };

    


    this.initShareContainerMenu = function(config){
        this.shareContainerMenu = new Ext.menu.Menu(config);
        this.shareContainerMenu.add(this.getRefreshItem());
    }


    this.initBookmarksMenu = function (config) {
        this.bookmarksMenu = new Ext.menu.Menu(config);
//        this.bookmarksMenu.add(this.getViewDocumentItem());
        this.bookmarksMenu.add(this.getGetDocumentItem());
        this.bookmarksMenu.addSeparator();
        this.bookmarksMenu.add(this.getRemoveBookmarkItem());
        this.bookmarksMenu.addSeparator();
        this.bookmarksMenu.add(this.getRefreshItem());
        this.bookmarksMenu.addSeparator();
        this.bookmarksMenu.add(this.getPropertiesItem());
    };

    this.initMyTasksContainerMenu = function (config) {
        this.myTasksContainerMenu = new Ext.menu.Menu(config);
        this.myTasksContainerMenu.add(this.getRefreshItem());
    };

    this.initMyBonitaTasksContainerMenu = function (config) {
        this.myBonitaTasksContainerMenu = new Ext.menu.Menu(config);
        this.myBonitaTasksContainerMenu.add(this.getRefreshItem());
    };

    this.initCartContainerMenu = function (config) {
        this.cartContainerMenu = new Ext.menu.Menu(config);
        this.cartContainerMenu.add(this.getRemoveCartItem());
//        this.cartContainerMenu.add(this.getRefreshItem());
    };

    this.initRecentItemsMenu = function (config) {
        this.recentItemsMenu = new Ext.menu.Menu(config);
//        this.recentItemsMenu.add(this.getViewDocumentItem());
        this.recentItemsMenu.add(this.getGetDocumentItem());
        this.recentItemsMenu.addSeparator();
        this.recentItemsMenu.add(this.getRefreshItem());
        this.recentItemsMenu.addSeparator();
        this.recentItemsMenu.add(this.getPropertiesItem());
    };

    this.initVersionsMenu = function (config) {
        this.versionsMenu = new Ext.menu.Menu(config);
        this.versionsMenu.add(this.getSetVersionItem());
        this.versionsMenu.add(this.getGetDocumentItem());
        this.versionsMenu.addSeparator();
        this.versionsMenu.add(this.getPropertiesItem());
    };

    this.initCommentsMenu = function (config) {
        this.commentsMenu = new Ext.menu.Menu(config);
        this.commentsMenu.add(this.getEditCommentItem());
        this.commentsMenu.add(this.getRemoveCommentItem());
        this.commentsMenu.addSeparator();
        this.commentsMenu.add(this.getAddCommentItem());
        this.commentsMenu.addSeparator();
        this.commentsMenu.add(this.getRefreshCommentItem());
        this.commentsMenu.addSeparator();
        this.commentsMenu.add(this.getCloseCommentItem());
    };

    this.initCommentsContainerMenu = function (config) {
        this.commentsContainerMenu = new Ext.menu.Menu(config);
        this.commentsContainerMenu.add(this.getAddCommentItem());
        this.commentsContainerMenu.addSeparator();
        this.commentsContainerMenu.add(this.getRefreshCommentItem());
        this.commentsContainerMenu.addSeparator();
        this.commentsContainerMenu.add(this.getCloseCommentItem());
    };

    this.initRelatedDocumentsMenu = function (config) {
        this.relatedDocumentsMenu = new Ext.menu.Menu(config);
        this.relatedDocumentsMenu.add(this.getGetDocumentItem());
        this.relatedDocumentsMenu.add(this.getRemoveRelatedItem());
        this.relatedDocumentsMenu.addSeparator();
        this.relatedDocumentsMenu.add(this.getPropertiesItem());
    };

    this.initGridMenu = function (config) {
        this.gridMenu = new Ext.menu.Menu(config);
        if (kimios.explorer.getViewport().rights.isWorkspaceCreator)
            this.gridMenu.add(this.getNewWorkspaceItem());
        this.gridMenu.add(this.getNewFolderItem());
        this.gridMenu.add(this.getImportDocumentItem());
        this.gridMenu.addSeparator();
        this.gridMenu.add(this.getRefreshItem());
        this.gridMenu.addSeparator();
        this.gridMenu.add(this.getPropertiesItem());
        this.gridMenu.addSeparator();
        this.gridMenu.add(this.getCartItem());
    };

    this.initWGridMenu = function (config) {
        this.wgridMenu = new Ext.menu.Menu(config);
        if (kimios.explorer.getViewport().rights.isWorkspaceCreator)
            this.wgridMenu.add(this.getNewWorkspaceItem());
        this.wgridMenu.add(this.getNewFolderItem());
        this.wgridMenu.addSeparator();
        this.wgridMenu.add(this.getRefreshItem());
        this.wgridMenu.addSeparator();
        this.wgridMenu.add(this.getPropertiesItem());
    };

    this.initTreeContainerMenu = function (config) {
        this.treeContainerMenu = new Ext.menu.Menu(config);
        if (kimios.explorer.getViewport().rights.isWorkspaceCreator) {
            this.treeContainerMenu.add(this.getNewWorkspaceItem());
            this.treeContainerMenu.addSeparator();
        }
        this.treeContainerMenu.add(this.getRefreshItem());
    };

    this.initBookmarksContainerMenu = function (config) {
        this.bookmarksContainerMenu = new Ext.menu.Menu(config);
        this.bookmarksContainerMenu.add(this.getRefreshItem());
    };

    this.initSearchRequestsContainerMenu = function (config) {
        this.searchRequestsContainerMenu = new Ext.menu.Menu(config);
        this.searchRequestsContainerMenu.add(this.getAddSearchRequestItem());
        this.searchRequestsContainerMenu.addSeparator();
        this.searchRequestsContainerMenu.add(this.getRefreshItem());
    };

    this.initRecentItemsContainerMenu = function (config) {
        this.recentItemsContainerMenu = new Ext.menu.Menu(config);
        this.recentItemsContainerMenu.add(this.getRefreshItem());
    };

    this.initUnknownMenu = function (config) {
        this.unknownMenu = new Ext.menu.Menu(config);
        this.unknownMenu.add(this.getRefreshItem());
    };

    this.getNewTabItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('OpenNewTab'),
            iconCls: 'role-workspace',
            scope: this,
            handler: function () {
                var centerPanel = Ext.getCmp('kimios-center-panel');
                var dmEntityGridPanel = new kimios.explorer.DMEntityGridPanel({});
                centerPanel.add(dmEntityGridPanel);
                dmEntityGridPanel.loadEntity({
                    uid: this.dmEntityPojo.uid,
                    type: this.dmEntityPojo.type
                });
            }
        });
    };

    this.getViewDocumentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('ViewDocument'),
            iconCls: 'eye',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                kimios.viewDoc(entity);
            }
        });
    };

    this.getBarcodeDocumentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('WithBarcode'),
            iconCls: 'barcode',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                kimios.barcode(entity);
            }
        });
    };

    this.getGetDocumentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('GetDocument'),
            iconCls: 'reindex-run',
            scope: this,
            handler: function () {
                if (this.context == 'versions') {
                    kimios.util.download(
                        kimios.util.getDocumentVersionLink(this.dmEntityPojo.uid, this.dmEntityPojo.versionUid)
                    );
                } else {
                    var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                    kimios.util.download(
                        kimios.util.getDocumentLink(entity.uid)
                    );
                }
            }
        });
    };

    this.getGetCreateSymlinkItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('CreateSymlink'),
            iconCls: 'add',
            scope: this,
            handler: function () {
                var picker = new kimios.picker.DMEntityPicker({
                    title: kimios.lang('CreateSymlink'),
                    iconCls: 'move',
                    buttonText: kimios.lang('CreateSymlink')
                });
                picker.on('entitySelected', function (node) {
                    if (this.multiple == true) {
                        return false;
                    }
                    if (node.attributes.type == undefined) {
                        Ext.MessageBox.alert(kimios.lang('CreateSymlink'), kimios.lang('AlertMoveDocToUnknownJS'));
                        return false;
                    }

                    kimios.request.createSymbolicLink(
                        this.dmEntityPojo.uid,
                        this.dmEntityPojo.type,
                        node.attributes.dmEntityUid,
                        node.attributes.type
                    );
                }, this);
                picker.show();
            }
        });
    };

    this.getGetRemoveSymlinkItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('RemoveSymlink'),
            iconCls: 'trash',
            scope: this,
            handler: function () {
                if (this.multiple == true) {
                    return;
                } else {
                    kimios.request.deleteDMEntity(this.dmEntityPojo.target.uid, this.dmEntityPojo.type, this.dmEntityPojo.name, null, this.dmEntityPojo.parentUid);
                }
            }
        });
    };


    this.getGetDocumentMailLinkItem = function () {
        var item = new Ext.menu.Item({
            text: kimios.lang('GetDocumentDownloadLink'),
            iconCls: 'attach',
            scope: this,
        });
        item.addListener('afterrender', function(me){
                    var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                    var mailLink = fullServerUrl + kimios.util.getDocumentVersionLink(entity.uid, entity.lastVersionId);
                    //set mail link
                    me.getEl().dom.setAttribute('data-clipboard-text', mailLink);
                    var clp = new Clipboard(me.getEl().dom);
                    clp.on('success', function(e) {
                        e.clearSelection();
                    });
                }, this);

        return item;
    };

    this.getCartItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('AddToZIP'),
            iconCls: 'cart',
            scope: this,
            handler: function () {
                var cartWindow = Ext.getCmp('kimios-cart');
                var cartGrid = cartWindow.cartGrid;
                var cartStore = cartGrid.getStore();

                if (this.multiple == true) {
                    for (var i = 0; i < this.dmEntityPojo.length; ++i) {
                        var entity = this.dmEntityPojo[i].type == 7 ? this.dmEntityPojo[i].targetEntity : this.dmEntityPojo[i];
                        cartStore.insert(0, new Ext.data.Record(entity));
                    }

                } else {
                    var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                    cartStore.insert(0, new Ext.data.Record(entity));
                }

                var vp = kimios.explorer.getViewport();
                vp.westPanel.setActiveGroup(5);     // cart panel
                vp.westPanel.activeGroup.setActiveTab(0);
            }
        });
    };

    this.getCommentsItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Comments'),
            iconCls: 'comment',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                var p = kimios.explorer.getActivePanel().commentsPanel;
                if (p.enableComments == false) {
                    p.setVisible(true);
                    p.expand();
                    p.loadComments(entity);
                    p.enableComments = true;
                }
            }
        });
    };

    this.getAddCommentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('AddComment'),
            iconCls: 'add',
            scope: this,
            handler: function () {
                Ext.MessageBox.prompt(
                    kimios.lang('AddComment'),
                    kimios.lang('Comment'),
                    function (button, comment) {
                        var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                        if (button == 'ok') {
                            kimios.request.addComment(kimios.explorer.getActivePanel().commentsPanel.lastVersionUid, comment, function () {
                                kimios.Info.msg(kimios.lang('Comment'), kimios.lang('AddComment') + ' ' + kimios.lang('Completed'));
                                kimios.explorer.getActivePanel().commentsPanel.getStore().reload();
                            });
                        }
                    },
                    this,
                    true);
            }
        });
    };

    this.getNewWorkspaceItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('NewWorkspace'),
            iconCls: 'newworkspace',
            scope: this,
            handler: function () {
                new kimios.properties.PropertiesWindow({
                    createMode: true,
                    dmEntityPojo: new kimios.DMEntityPojo({
                        type: 1
                    })
                }).show();
            }
        });
    };

    this.getNewFolderItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('NewFolder'),
            iconCls: 'newfolder',
            scope: this,
            handler: function () {
                new kimios.properties.PropertiesWindow({
                    createMode: true,
                    dmEntityPojo: new kimios.DMEntityPojo({
                        parentType: this.dmEntityPojo.type,
                        parentUid: this.dmEntityPojo.uid,
                        path: this.dmEntityPojo.path,
                        type: 2
                    })
                }).show();
            }
        });
    };

    this.getImportDocumentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('ImportDocument'),
            iconCls: 'import',
            scope: this,
            handler: function () {
                new kimios.properties.PropertiesWindow({
                    createMode: true,
                    dmEntityPojo: new kimios.DMEntityPojo({
                        parentType: this.dmEntityPojo.type,
                        parentUid: this.dmEntityPojo.uid,
                        path: this.dmEntityPojo.path,
                        type: 3
                    })
                }).show();
            }
        });
    };

    this.getUpdateCurrentVersionItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('CreateNewVersion'),
            iconCls: 'studio-cls-wf-up',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                new kimios.UploaderWindow({
                    context: 'createNewVersion',
                    dmEntityPojo: entity
                }).show();
            }
        });
    };

    this.getCheckInCheckOutItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('CheckInCheckOut'),
            iconCls: 'checked-out',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                if (entity.checkedOut == false) {
                    kimios.request.checkOut(entity.uid);
                } else {
                    new kimios.UploaderWindow({
                        context: 'checkIn',
                        dmEntityPojo: entity
                    }).show();
                }
            }
        });
    };

    this.getEditDocumentItem = function(){
        var menuItem = new Ext.menu.Item({
            text: kimios.lang('StartEdit'),
            iconCls: 'editor',
            scope: this,
            handler: function(){
                //check if data available
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity: this.dmEntityPojo;
                kimios.editors.startDocumentEdit(entity);
            }
        });
        menuItem.filterType = ['txt', 'adoc', 'asciidoc', 'js', 'c', 'htm', 'html'];
        return menuItem;
    }

    this.getStartWorkflowItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('StartWorkflow'),
            iconCls: 'studio-cls-wf',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                new kimios.picker.WorkflowPicker({
                    documentUid: entity.uid,
                    documentType: entity.type
                }).show();
            }
        });
    };

    this.getStartProcessItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('StartWorkflow'),
            iconCls: 'studio-cls-wf',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                new kimios.properties.PropertiesWindow({
                    dmEntityPojo: entity,
                    versionsMode: this.context == 'versions' ? true : false,
                    switchBonitaTab: true

                }).show();

//                new kimios.picker.BonitaPicker({
//                    documentUid: this.dmEntityPojo.uid,
//                    instances: this.dmEntityPojo.dmEntityAddonData
//                }).show();
            }
        });
    };

//    this.getListProcessItem = function () {
//        return new Ext.menu.Item({
//            text: 'List Processes',
//            iconCls: 'studio-cls-wf',
//            scope: this,
//            handler: function () {
//                new kimios.picker.BonitaPicker({
//                    documentUid: this.dmEntityPojo.uid,
//                    instances: this.dmEntityPojo.dmEntityAddonData
//                }).show();
//            }
//        });
//    };

    this.getMoveItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Move'),
            iconCls: 'move',
            scope: this,
            handler: function () {
                var picker = new kimios.picker.DMEntityPicker({
                    title: kimios.lang('Move'),
                    iconCls: 'move',
                    buttonText: kimios.lang('Move')
                });
                picker.on('entitySelected', function (node) {
                    if (node.attributes.type == undefined) {
                        Ext.MessageBox.alert(kimios.lang('Move'), kimios.lang('AlertMoveDocToUnknownJS'));
                        return false;
                    }
                    if (this.multiple == true) {
                        kimios.request.moveDMEntities(
                            this.dmEntityPojo,
                            node.attributes.dmEntityUid,
                            node.attributes.type
                        );
                    } else {
                        kimios.request.moveDMEntity(
                            this.dmEntityPojo.uid,
                            this.dmEntityPojo.type,
                            node.attributes.dmEntityUid,
                            node.attributes.type
                        );
                    }
                }, this);
                picker.show();
            }
        });
    };

    this.getDeleteItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Delete'),
            iconCls: 'trash',
            scope: this,
            handler: function () {
                if (this.multiple == true) {
                    kimios.request.deleteDMEntities(this.dmEntityPojo);
                } else {
                    kimios.request.deleteDMEntity(this.dmEntityPojo.uid, this.dmEntityPojo.type, this.dmEntityPojo.name);
                }
            }
        });
    };

    this.getShareDocumentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Share'),
            iconCls: 'share',
            scope: this,
            handler: function () {
                //show window

                var docs = this.multiple ? this.dmEntityPojo : [this.dmEntityPojo];
                new kimios.share.MailPanel({
                    documents: docs
                }).showInWindow();
            }
        });
    };

    this.getAddToBookmarksItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('AddToBookmark'),
            iconCls: 'qaction-bookmarks',
            scope: this,
            handler: function () {
                Ext.MessageBox.confirm(
                    kimios.lang('AddToBookmark'),
                    kimios.lang('AddToBookmark') + '?',
                    function (btn) {

                        if (btn == 'yes') {
                            if (this.multiple == true) {
                                var it = [];
                                for (var u = 0; u < this.dmEntityPojo.length; u++)
                                    it.push(this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo);
                                kimios.request.addAllToBookmarks(it);
                            } else {
                                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                                kimios.request.addToBookmarks(entity.uid, entity.type);
                            }
                        }
                    },
                    this
                );
            }
        });
    };

    this.getAddSearchRequestItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('SearchRequestAdd'),
            iconCls: 'search',
            scope: this,
            handler: function () {
                var searchRequest = this.dmEntityPojo;
                var asp = kimios.explorer.getActivePanel().advancedSearchPanel;
//                asp.loadForm(searchRequest);
                asp.showPanel();

                asp.searchRequestId = null;
                asp.searchRequestName = null;
                asp.saveButton.setText(
                    asp.searchRequestId ? kimios.lang('Update') : kimios.lang('Create')
                );
                asp.nameField.setValue("");
                asp.uidField.setValue("");
                asp.textField.setValue("");
                asp.locationField.setValue("");
                asp.documentDateFromField.setValue("");
                asp.documentDateToField.setValue("");
                asp.documentTypeField.setValue("");
                asp.ownerField.setValue("");
                asp.form2.removeAll();
            }
        });
    };

    this.getRemoveSearchRequestsItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('SearchRequestDelete'),
            iconCls: 'trash',
            scope: this,
            handler: function () {
                var _t = this;
                Ext.MessageBox.confirm(
                    kimios.lang('Delete'),
                    kimios.lang('ConfirmDelete'),
                    function (btn) {
                        if (btn == 'yes') {
                            if (_t.multipleRecords) {
                                for (var i = 0; i < _t.multipleRecords.length; i++) {
                                    kimios.ajaxRequest('Search', {
                                            action: 'DeleteQuery',
                                            queryId: _t.multipleRecords[i].data.id
                                        },
                                        function () {
                                            kimios.explorer.getSearchRequestsPanel().refresh();
                                            kimios.explorer.getPublicSearchRequestsPanel().refresh();
                                            kimios.explorer.getPublishedSearchRequestsPanel().refresh();
                                        }
                                    );
                                }

                            } else
                                kimios.ajaxRequest('Search', {
                                        action: 'DeleteQuery',
                                        queryId: _t.dmEntityPojo.id
                                    },
                                    function () {
                                        kimios.explorer.getSearchRequestsPanel().refresh();
                                        kimios.explorer.getPublicSearchRequestsPanel().refresh();
                                        kimios.explorer.getPublishedSearchRequestsPanel().refresh();
                                    }
                                );
                        }
                    },
                    this
                );
            }
        });
    };

    this.getRemoveBookmarkItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('RemoveBookmark'),
            iconCls: 'delete',
            scope: this,
            handler: function () {
                kimios.request.removeBookmarks(this.dmEntityPojo.uid, this.dmEntityPojo.type);
            }
        });
    };

    this.getRemoveCartItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('ClearCart'),
            iconCls: 'trash',
            scope: this,
            handler: function () {
                Ext.getCmp('kimios-cart').cartGrid.getStore().removeAll();
            }
        });
    };

    this.getRemoveCartSimpleItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('ClearCartItem'),
            iconCls: 'delete',
            scope: this,
            handler: function () {
                Ext.getCmp('kimios-cart').cartGrid.getStore().remove(
                    Ext.getCmp('kimios-cart').cartGrid.getSelectionModel().getSelected()
                );

//                var grid = Ext.getCmp('kimios-cart').cartGrid;
//                var store = grid.getStore();
//                var rec = grid.getSelectionModel().getSelected();
//                store.remove({
//                    uid: rec.data.uid
//                });
            }
        });
    };

    this.getAcceptStatusItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('AcceptStatus'),
            iconCls: 'accept-status',
            scope: this,
            handler: function () {
                Ext.MessageBox.prompt(
                    kimios.lang('AcceptStatus'),
                    kimios.lang('Comment'),
                    function (button, comment) {
                        if (button == 'ok') {
                            kimios.request.TasksRequest.acceptWorkflowRequest(
                                this.dmEntityPojo.uid,
                                this.dmEntityPojo.workflowStatusUid,
                                this.dmEntityPojo.statusUserName,
                                this.dmEntityPojo.statusUserSource,
                                this.dmEntityPojo.statusDate,
                                comment
                            );
                        }
                    },
                    this,
                    true);
            }
        });
    };

    this.getRejectStatusItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('RejectStatus'),
            iconCls: 'reject-status',
            scope: this,
            handler: function () {
                Ext.MessageBox.prompt(
                    kimios.lang('RejectStatus'),
                    kimios.lang('Comment'),
                    function (button, comment) {
                        if (button == 'ok') {
                            kimios.request.TasksRequest.rejectWorkflowRequest(
                                this.dmEntityPojo.uid,
                                this.dmEntityPojo.workflowStatusUid,
                                this.dmEntityPojo.statusUserName,
                                this.dmEntityPojo.statusUserSource,
                                this.dmEntityPojo.statusDate,
                                comment
                            );
                        }
                    },
                    this,
                    true);
            }
        });
    };

    this.getEditCommentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('ModifyComment'),
            iconCls: 'comment',
            scope: this,
            handler: function () {
                Ext.MessageBox.prompt(
                    kimios.lang('ModifyComment'),
                    kimios.lang('Comment'),
                    function (button, comment) {
                        if (button == 'ok') {
                            kimios.request.updateComment(this.dmEntityPojo.comment.documentVersionUid, this.dmEntityPojo.comment.uid, comment, this.dmEntityPojo.comment.store);
                        }
                    },
                    this,
                    true,
                    this.dmEntityPojo.comment.comment);
            }
        });
    };

    this.getRemoveCommentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('DeleteComment'),
            iconCls: 'trash',
            scope: this,
            handler: function () {
                Ext.MessageBox.confirm(
                    kimios.lang('Delete'),
                    kimios.lang('ConfirmDelete'),
                    function (btn) {
                        if (btn == 'yes') {
                            kimios.request.removeComment(this.dmEntityPojo.comment.uid, this.dmEntityPojo.comment.store);
                        }
                    },
                    this
                );
            }
        });
    };

    this.getRefreshCommentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Refresh'),
            iconCls: 'refresh',
            scope: this,
            handler: function () {
                kimios.explorer.getActivePanel().commentsPanel.getStore().reload();
            }
        });
    };

    this.getCloseCommentItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Close'),
            iconCls: 'close',
            scope: this,
            handler: function () {
                var p = kimios.explorer.getActivePanel().commentsPanel;
                p.collapse();
                p.setVisible(false);
                p.enableComments = false;
                kimios.explorer.getViewport().doLayout();
            }
        });
    };

    this.getRemoveRelatedItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Unlink'),
            iconCls: 'delete',
            scope: this,
            handler: function () {
                Ext.MessageBox.confirm(
                    kimios.lang('Delete'),
                    kimios.lang('ConfirmDelete'),
                    function (btn) {
                        if (btn == 'yes') {
                            kimios.request.removeRelatedDocument(
                                this.dmEntityPojo.current.uid,
                                this.dmEntityPojo.uid,
                                this.dmEntityPojo.current.store
                            );
                        }
                    }, this);
            }
        });
    };

    this.getCheckBonitaTask = function () {
        return new Ext.menu.Item({
            text: kimios.lang('BonitaDoIt'),
            iconCls: 'studio-cls-wf',
            scope: this,
            handler: function (btn, evt) {
                var context = this.context;
                var tasksPanel = kimios.explorer.getTasksPanel();
                var activePanel = kimios.explorer.getActivePanel();

                var url = this.dmEntityPojo.url;


                /*
                 Take it before doing it
                 */
                kimios.ajaxRequest('Workflow', {
                        action: 'takeTask',
                        taskId: this.dmEntityPojo.id
                    },
                    function () {
                        Ext.getCmp('kimios-tasks-panel').refresh();
                        Ext.getCmp('kimios-assigned-tasks-panel').refresh();

                        var iframe = new Ext.Window({
                            width: 640,
                            height: 480,
                            layout: 'fit',
                            border: false,
                            title: kimios.lang('WorkflowStatus'),
                            maximizable: true,
                            modal: true,
                            autoScroll: true,
                            items: [
                                {
                                    html: '<iframe id="reportframe" border="0" width="100%" height="100%" ' +
                                    'frameborder="0" marginheight="12" marginwidth="16" scrolling="auto" ' +
                                    'style="padding: 16px" ' +
                                    'src="' + url + '"></iframe>'
                                }
                            ],
                            fbar: [
                                {
                                    text: kimios.lang('Close'),
                                    scope: this,
                                    handler: function () {
                                        if (console) { console.log('cloooose') };
                                        iframe.close();
                                        Ext.getCmp('kimios-tasks-panel').refresh();
                                        Ext.getCmp('kimios-assigned-tasks-panel').refresh();
                                        if (this.context == 'myBonitaAllTasks') {
                                            Ext.getCmp('propInstancesPanelId').getSelectionModel().clearSelections(false);
                                            Ext.getCmp('propTasksPanelId').getStore().removeAll();
                                        }
                                    }
                                }
                            ]
                        }).show();

                    }
                );


            }
        });
    }

    this.getTakeBonitaTask = function () {
        return new Ext.menu.Item({
            text: kimios.lang('BonitaTake'),
            iconCls: 'studio-cls-wf-down',
            scope: this,
            handler: function (btn, evt) {

                kimios.ajaxRequest('Workflow', {
                        action: 'takeTask',
                        taskId: this.dmEntityPojo.id
                    },
                    function () {
                        Ext.getCmp('kimios-tasks-panel').refresh();
                        Ext.getCmp('kimios-assigned-tasks-panel').refresh();
                    }
                );

            }
        });
    }

    this.getReleaseBonitaTask = function () {
        return new Ext.menu.Item({
            text: kimios.lang('BonitaRelease'),
            iconCls: 'studio-wf-expand',
            scope: this,
            handler: function (btn, evt) {

                kimios.ajaxRequest('Workflow', {
                        action: 'releaseTask',
                        taskId: this.dmEntityPojo.id
                    },
                    function () {
                        Ext.getCmp('kimios-tasks-panel').refresh();
                        Ext.getCmp('kimios-assigned-tasks-panel').refresh();
                    }
                );
            }
        });
    }

    this.getHideBonitaTask = function () {
        return new Ext.menu.Item({
            text: kimios.lang('BonitaHide'),
            iconCls: 'delete',
            scope: this,
            handler: function (btn, evt) {

                kimios.ajaxRequest('Workflow', {
                        action: 'hideTask',
                        taskId: this.dmEntityPojo.id
                    },
                    function () {
                        Ext.getCmp('kimios-tasks-panel').refresh();
                        Ext.getCmp('kimios-assigned-tasks-panel').refresh();
                    }
                );
            }
        });
    }

    this.getPropertiesBonitaTask = function () {
        return new Ext.menu.Item({
            text: kimios.lang('TaskProperties'),
            iconCls: 'properties',
            scope: this,
            handler: function (btn, evt) {
                if (this.context == 'myBonitaTasks') {
                    Ext.getCmp('kimios-assigned-tasks-panel').getTaskWindow(this.dmEntityPojo, true, false).show();
                } else if (this.context == 'myBonitaAssignedTasks') {
                    Ext.getCmp('kimios-assigned-tasks-panel').getTaskWindow(this.dmEntityPojo, false, true).show();
                } else if (this.context == 'myBonitaAllTasks') {
                    Ext.getCmp('kimios-assigned-tasks-panel').getTaskWindow(this.dmEntityPojo, false, false).show();
                }

            }
        });
    }

    this.getRefreshItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Refresh'),
            iconCls: 'refresh',
            scope: this,
            handler: function (btn, evt) {
                var context = this.context;
                var toolbar = kimios.explorer.getToolbar();
                var treePanel = kimios.explorer.getTreePanel();
                var legacyTasksPanel = Ext.getCmp('kimios-tasks-panel-legacy');
                var tasksPanel = Ext.getCmp('kimios-tasks-panel');
                var assignedTasksPanel = Ext.getCmp('kimios-assigned-tasks-panel');
                var activePanel = kimios.explorer.getActivePanel();
                var bookmarks = kimios.explorer.getBookmarksPanel();
                var recentItems = kimios.explorer.getRecentItemsPanel();
                var searchRequests = kimios.explorer.getSearchRequestsPanel();
                var searchRequests3 = kimios.explorer.getPublicSearchRequestsPanel();
                var searchRequests2 = kimios.explorer.getPublishedSearchRequestsPanel();
                var cartPanel = kimios.explorer.getCartPanel();
                var sharePanel = kimios.explorer.getSharesPanel();

                if (context == undefined || context == 'default'
                    || context == 'gridContainer') {
                    activePanel.loadEntity();
                } else if (context == 'searchRequests'
                    || context == 'searchRequestsContainer') {
                    searchRequests.refresh();
                    searchRequests2.refresh();
                    searchRequests3.refresh();
                } else if (context == 'bookmarks'
                    || context == 'bookmarksContainer') {
                    bookmarks.refresh();
                } else if (context == 'recentItems'
                    || context == 'recentItemsContainer') {
                    recentItems.refresh();
                } else if (context == 'tree' || context == 'treeContainer') {
                    treePanel.refresh();
                    activePanel.loadEntity();
                } else if (context == 'myTasks'
                    || context == 'myTasksContainer') {
                    legacyTasksPanel.refresh();
                } else if (context == 'myBonitaTasks'
                    || context == 'myBonitaTasksContainer') {
                    tasksPanel.refresh();
                } else if (context == 'myBonitaAssignedTasks'
                    || context == 'myBonitaAssignedTasksContainer') {
                    assignedTasksPanel.refresh();
                } else if (context == 'cart' || context == 'cartContainer') {
                    cartPanel.refresh();
                } else if (context == 'shares' || context == 'sharesContainer') {
                    sharePanel.refresh();
                }

            }
        });
    };

    this.getPropertiesItem = function () {
        return new Ext.menu.Item({
            text: kimios.lang('Properties'),
            iconCls: 'properties',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                new kimios.properties.PropertiesWindow({
                    dmEntityPojo: entity,
                    versionsMode: this.context == 'versions' ? true : false
                }).show();
            }
        });
    };
    
    this.getSetVersionItem = function(){
        return new Ext.menu.Item({
            text: kimios.lang('UpdateCustomVersionTitle'),
            iconCls: 'setversion',
            scope: this,
            handler: function () {
                var entity = this.dmEntityPojo.type == 7 ? this.dmEntityPojo.targetEntity : this.dmEntityPojo;
                new kimios.picker.CustomVersionPicker({
                    dmEntityPojo: entity
                }).show();
            }
        });
    }

};
