package com.rameses.rcp.common;


public class SingleFileUploadModel {
    
    public def upload() { 
        def result = null; 
        def param = [:]; 
        param.handler = { o-> 
            o.remove('items'); 
            result = o; 
        }
        Modal.show("sys_file:create", param ); 
        return result; 
    } 
    
    public void open( Map param ) {
        if ( !param.objid ) throw new Exception('objid parameter is required'); 
        
        def sysfile = com.rameses.filemgmt.FileManager.instance.dbProvider.read([ objid: param.objid ]); 
        if ( !sysfile ) throw new Exception('Attachment not found'); 
        if ( !sysfile.items ) throw new Exception('No item found for this attachment'); 
        
        def fileitem = sysfile.items.first(); 
        if ( !fileitem.filetype ) {
            fileitem.filetype = sysfile.filetype; 
        } 
        Modal.show('sys_fileitem:open', [ fileitem: fileitem ]); 
    }
    
}