package com.rameses.filemgmt.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.util.Base64Cipher;

public class FileUploadTaskModel  {

    def connection;
    def filetype;
    def files;
    def param;

    def handler;
    
    def locationConf;
    def entity;

    def base64 = new com.rameses.util.Base64Cipher(); 
    def fileTypeProvider = com.rameses.filemgmt.FileManager.instance.fileTypeProvider; 

    void init() {
        if ( !connection ) throw new Exception('connection is required before FileUploadTaskModel.init'); 
        if ( !filetype ) throw new Exception('filetype is required before FileUploadTaskModel.init'); 
        if ( !files ) throw new Exception('files is required before FileUploadTaskModel.init'); 
        
        locationConf = com.rameses.filemgmt.FileManager.instance.getDefaultLocation( connection ); 
        if ( !locationConf ) throw new Exception('No active location config available'); 
        
        entity = [ objid: 'FILE'+ new java.rmi.server.UID() ]; 
        entity.filetype = filetype; 
        entity.info = [:]; 
        
        if ( param == null ) { 
            param = [:]; 
        }
    }
    
    
    def self = this; 
    def uploadHandler = [ 
        providerChanged: { o-> 
            buildData( o ); 
        }, 
        afterRemoveItem: {
            self.refreshFiles(); 
        }
    ] as FileUploadModel;
    
    void refreshFiles() {
        if ( uploadHandler?.binding ) {
            uploadHandler.binding.notifyDepends('files');
        } 
    }
    
    void buildData( prov ) {
        if ( !prov ) return; 
        
        def fum = com.rameses.filemgmt.FileUploadManager.instance; 
        def tempdir = fum.getTempDir(); 
        int fileIndexNo = 0; 
        
        println '\n*****'
        println 'tempdir -> '+ tempdir;
        
        files.each{ o-> 
            fileIndexNo += 1; 
            
            def item = [:];
            def encstr = com.rameses.util.Encoder.MD5.encode( entity.objid ); 
            item.objid = encstr + fileIndexNo.toString().padLeft(2, '0'); 
            item.filesize = fum.helper.getFileSize( o ); 
            item.filetype = entity.filetype; 
            item.filelocid = locationConf.name; 
            item.fileid = item.objid; 
            item.source = o.canonicalPath; 
            item.caption = o.name; 
            item.file = o;
            item.immediate = true; 
            item.connection = connection;

            def folder = new java.io.File( tempdir, item.fileid ); 
            def fui = com.rameses.filemgmt.FileUploadItem.create( folder, item ); 
            uploadHandler.add( fui, item.caption, item.filesize, item ); 
            item.uploaditem = fui;
            println 'scheduling '+ item.caption +' using temp folder '+ folder.absolutePath; 
            fum.schedule( fui ); 
        } 
    }
    
    
    def doOk() {
        def items = uploadHandler.getItems(); 
        if ( !items ) return '_close'; 

        if ( items.find{( it.completed == false )} ) 
            throw new Exception('Wait until all items are uploaded'); 
        
        def fm = com.rameses.filemgmt.FileManager.instance;

        def scaler = new ImageScaler();

        entity.param = param; 
        if ( !entity.title ) {
            entity.title = param.caption; 
            if ( !entity.title ) {
                entity.title = com.rameses.util.Encoder.MD5.encode( entity.objid ); 
            }
        }
        
        entity.items = []; 
        items.each{ o-> 
            def data = o.data; 

            def m = [:];
            m.objid = data.objid; 
            m.parentid = entity.objid;
            m.state = 'COMPLETED';
            m.caption = data.caption;
            m.filesize = data.filesize;
            m.filetype = data.filetype;
            m.filelocid = data.filelocid;
            m.bytestransferred = data.filesize;
            
            def image = null; 
            def aaa = fileTypeProvider.getType( m.filetype );
            if ( aaa?.image && aaa.image.toString().matches('true|1')) {
                image = scaler.createThumbnail( data.file );  
            } else {
                def icon = fm.getFileTypeIcon( m.filetype ); 
                if ( icon ) image = icon.image; 
            }
            
            m.thumbnail = base64.encode((Object) scaler.getBytes( image )); 
            entity.items << m; 
        } 
        
        def db = fm.getDbProvider(); 
        if ( db ) {
            def o = db.create( entity, connection ); 
            if ( o ) entity.putAll( o ); 
        }
        
        try { 
            if ( handler ) handler( entity ); 
        } finally {
            return "_close";
        }        
    }
    
    def doCancel() {
        return "_close";
    }    
}