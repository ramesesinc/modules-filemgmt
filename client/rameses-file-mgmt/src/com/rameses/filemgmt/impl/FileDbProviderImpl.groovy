package com.rameses.filemgmt.impl;

import com.rameses.rcp.annotations.*;
import com.rameses.filemgmt.FileManager;

class FileDbProviderImpl implements FileManager.DbProvider {
    
    @Service('FileUploadService') 
    def fileUploadSvc;

    @Service('SysFileService') 
    def fileSvc; 

    Map create( Map data ) { 
        return fileSvc.create( data );  
    } 

    Map save( Map o ) { 
        return fileUploadSvc.upload( o ); 
    } 

    Map read( Map params ) {
        def m = [ objid: params.objid ]; 
        return fileSvc.read( m ); 
    } 

    Map remove( Map params ) { 
        return fileSvc.remove( params ); 
    }

}