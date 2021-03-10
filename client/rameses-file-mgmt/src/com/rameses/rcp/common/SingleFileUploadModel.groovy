package com.rameses.rcp.common;


public class SingleFileUploadModel {
    
    private String _connection; 
    
    public String getConnection() {
        return _connection;
    }
    public void setConnection( String name ) {
        this._connection = name; 
    }
    
    public void afterUpload( o ) {
    }
    public void cancelledUpload() {
    }


    
    public def uploadFile( java.io.File file, Map param ) {
        if ( !file ) throw new Exception("file parameter is required in SingleFileUploadModel.uploadFile"); 

        if ( param == null ) param = [:]; 
        
        def filetype = param.filetype; 
        if ( !filetype ) {
            def sname = file.name.toString().toLowerCase(); 
            int idx = sname.lastIndexOf('.'); 
            if ( idx > 0 ) { 
                filetype = sname.substring( idx+1 ); 
            }
            param.filetype = filetype; 
        }

        return uploadFiles([ file ], param ); 
    }

    public def uploadFiles( def files, Map param ) {
        if ( !files ) throw new Exception("Please specify at least one file to upload"); 
        
        def conn = getConnection();
        if ( !conn ) throw new Exception('Please set the connection name in the Uploader constructor'); 
        
        if ( param == null ) param = [:]; 

        def filetype = param.filetype; 
        files.each{ o-> 
            if ( o instanceof java.io.File ) {
                if ( !filetype ) {
                    def sname = file.name.toString().toLowerCase(); 
                    int idx = sname.lastIndexOf('.'); 
                    if ( idx > 0 ) { 
                        filetype = sname.substring( idx+1 ); 
                    }
                    param.filetype = filetype; 
                }
            }
            else {
                throw new Exception("Please specify a valid File object"); 
            }
        }
        
        if ( !filetype ) 
            throw new Exception("Please specify the filetype in the 'param' argument"); 

        def res = null; 

        def map = [:]; 
        map.param = param; 
        map.files = files;
        map.filetype = filetype;
        map.connection = conn; 
        map.handler = { o-> 
            o.remove('items'); 
            o.remove('param'); 
            res = o; 
        } 
        Modal.show("sys_fileupload_task", map ); 
        
        if ( res ) {
            afterUpload( res ); 
        } else {
            cancelledUpload(); 
        }
        return res; 
    }
    

    public def upload( Map param ) { 
        def result = null; 
        def map = [:]; 
        map.param = param; 
        map.connection = getConnection();
        map.handler = { o-> 
            o.remove('items'); 
            o.remove('param'); 
            result = o; 
        }
        
        Modal.show("sys_file:create", map ); 

        if ( result ) {
            afterUpload( result ); 
        } else {
            cancelledUpload(); 
        }
        return result; 
    } 

    public void open( Map param ) {
        if ( !param.objid ) throw new Exception('objid parameter is required'); 
        
        def sysfile = com.rameses.filemgmt.FileManager.instance.dbProvider.read([ objid: param.objid ], getConnection() ); 
        if ( !sysfile ) throw new Exception('Attachment not found'); 
        if ( !sysfile.items ) throw new Exception('No item found for this attachment'); 
        
        def fileitem = sysfile.items.first(); 
        if ( !fileitem.filetype ) {
            fileitem.filetype = sysfile.filetype; 
        } 
        
        def m = [ fileitem: fileitem ]; 
        m.connection = getConnection();
        m.fileitem.connection = m.connection;
        Modal.show('sys_fileitem:open', m ); 
    }
    
}