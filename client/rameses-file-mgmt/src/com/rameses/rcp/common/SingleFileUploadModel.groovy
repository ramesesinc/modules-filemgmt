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
    
    public def upload() { 
        def result = null; 
        def param = [:]; 
        param.connection = getConnection();
        param.handler = { o-> 
            o.remove('items'); 
            result = o; 
        }
        Modal.show("sys_file:create", param ); 
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