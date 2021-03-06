package com.rameses.filemgmt.components;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class FileViewPanelModel extends ComponentBean {

    @Binding
    def binding;
    
    String name; 
    
    def handlerProxy; 
    def cache = [:]; 
    def self = this; 

    def selectedItem; 
    
    void updateNameValue( item ) {
        if ( name ) { 
            setValue( name, item ); 
        }
    }
    
    void setSelectedItem( item ) { 
        this.selectedItem = item; 
        if ( item == null ) { 
            updateNameValue( item ); 
            return; 
        } 
        
        if ( item.objid ) {
            if ( item._cardname != 'view' ) {
                cache.remove( item.objid.toString() ); 
            }

            def o = cache.get( item.objid ); 
            if ( o ) {
                updateNameValue( item ); 
                return; 
            }

            try {
                o = handlerProxy.getItem( item ); 
            } catch(Throwable t) { 
                o = null; 
                MsgBox.err( t ); 
            } 
            
            if ( o ) { 
                o.items?.each{ c-> 
                    c.filetype = o.filetype; 
                }
                item._cardname = 'view'; 
                cache.put( item.objid, o ); 
            } else { 
                item._cardname = 'blank';
                cache.remove( item.objid ); 
            } 
        } 
        else {
            item._cardname = 'blank';
            cache.remove( item.objid ); 
        }
        
        updateNameValue( item ); 
    } 
    
    boolean isAllowAddPermitted() {
        if ( handlerProxy == null ) return true; 
        return ( handlerProxy.editable && handlerProxy.allowAdd );
    }
    boolean isAllowRemovePermitted() {
        if ( handlerProxy == null ) return true; 
        return ( handlerProxy.editable && handlerProxy.allowRemove );
    }

    
    def listHandler = [
        isEditable: {
            return ( handlerProxy ? handlerProxy.isEditable() : true );
        }, 
        isAllowAdd: {
            return isAllowAddPermitted(); 
        }, 
        isAllowRemove: {
            return isAllowRemovePermitted();
        }, 
        fetchList: {
            if ( handlerProxy ) {
                return handlerProxy.fetchList( it ); 
            } else {
                return null; 
            } 
        }, 
        removeItem: { o-> 
            if ( handlerProxy ) {
                return handlerProxy.removeItem( o ); 
            } else {
                return false; 
            }
        }
    ] as ListPaneModel; 
    
    void addItem( Object item ) {
        listHandler.addItem( item ); 
    }

    def getCardName() { 
        if ( !selectedItem ) return 'blank'; 
        
        def o = selectedItem?._cardname; 
        return (o ? o : 'loading'); 
    }
    
    def getHeaderMessage() {
        if ( !selectedItem ) return " ";
        
        def o = cache.get( selectedItem.objid ); 
        def buff = new StringBuilder(); 
        buff.append('<html>');
        buff.append('<b>'+ selectedItem.title.toString() +'</b>');
        if ( o ) {
            buff.append('<br/>');
            buff.append('Posted By : '+ o.createdby?.name +'<br/>');
            buff.append('Date Posted : '+ o.dtcreated +'<br/>');
        }
        buff.append('</html>');
        return buff.toString(); 
    }
    
    def selectedThumbnail; 
    
    void setSelectedThumbnail( value ) {
        this.selectedThumbnail = value; 
        if ( handlerProxy instanceof FileViewModel ) { 
            handlerProxy.workspace.thumbnailSelectionChanged( value ); 
        } 
    } 
    
    def thumbnailListHandler = [
        fetchList: {
            if ( !selectedItem?.objid ) return null; 
            
            def o = cache.get( selectedItem.objid ); 
            return ( o ? o.items : null ); 
        }, 
        
        openItem: { o-> 
            if ( !o ) return null; 
            
            o.parent = selectedItem; 
            try {
                def op = handlerProxy.openItem( o ); 
                if ( op ) return op; 
            } catch(com.rameses.util.BreakException be) {
                return null; 
            } 
            
            if ( handlerProxy.eventMap.onchangeItem ) { 
                handlerProxy.eventMap.onchangeItem( o ); 
                return null; 
            } 
            return Inv.lookupOpener('sys_fileitem:open', [ fileitem: o, eventMap: handlerProxy.eventMap ]); 
        }
    ] as ThumbnailViewModel;     
    
    
    def addFile() {
        def params = [:]; 
        def item = handlerProxy.createItem();
        if ( item instanceof Map ) {
            params.putAll( item ); 
        }
        
        params.handler = { o-> 
            o.remove('items'); 
            handlerProxy.addItem([ objid: o.objid, title: o.title ]); 
        } 
        return Inv.lookupOpener('sys_file:create', params ); 
    }
    
    def removeFile() { 
        if ( !selectedItem ) return null; 
        if ( MsgBox.confirm('You are about to remove the selected item. Proceed?')) {
            boolean pass = handlerProxy.fireRemoveItem( selectedItem ); 
            if ( pass ) listHandler.removeSelectedItem(); 
            
        } else { 
            return null; 
        }
    }
}
