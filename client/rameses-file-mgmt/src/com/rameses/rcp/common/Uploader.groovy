package com.rameses.rcp.common;

public class Uploader {
    
    private String connection;
    
    public Uploader( String connection ) { 
        this.connection = connection; 
    }
    
    public void uploadFiles( files, Map param ) {
        if ( !connection ) throw new Exception('Please set the connection name in the Uploader constructor'); 
        if ( !files ) throw new Exception("Please specify at least one file to upload"); 
        
        def arg0 = files; 
        if ( arg0 instanceof java.io.File ) {
            files = [ arg0 ]; 
        }
        
        def filetype = param.filetype; 
        files.each{ o-> 
            if ( o instanceof java.io.File ) {
                if ( !filetype ) {
                    int idx = o.name.lastIndexOf('.'); 
                    if ( idx > 0 ) { 
                        filetype = o.name.toLowerCase().substring( idx+1 ); 
                    }
                }
            }
            else {
                throw new Exception("Please specify a valid File object"); 
            }
        }
        
        if ( !filetype ) 
            throw new Exception("Please specify the filetype in the 'param' argument"); 
        
        def locconf = com.rameses.filemgmt.FileManager.instance.getDefaultLocation( connection ); 
        if ( !locconf ) throw new Exception('No active location config available'); 
        
        def fum = com.rameses.filemgmt.FileUploadManager.FileUploadManager.instance; 
        def tempdir = fum.getTempDir(); 
        int fileIndexNo = 0; 
        
        def entity = [ objid: 'FILE'+ new java.rmi.server.UID(), info: [:]]; 
        entity.filetype = filetype;
        
        def uploadHandler = new FileUploadModel(); 
        
        files.each{ o-> 
            fileIndexNo += 1; 
            
            def item = [:];
            def encstr = com.rameses.util.Encoder.MD5.encode( entity.objid ); 
            item.objid = encstr + fileIndexNo.toString().padLeft(2, '0'); 
            item.filesize = fum.helper.getFileSize( o ); 
            item.filetype = entity.filetype; 
            item.filelocid = locconf.name; 
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
            fum.schedule( fui ); 
        } 
    }
}