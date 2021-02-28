package com.rameses.filemgmt.impl;

import com.rameses.rcp.annotations.*;
import com.rameses.filemgmt.FileManager;

class FileDbProviderImpl implements FileManager.DbProvider {
    
    @Service( dynamic = true ) 
    def dynaSvc; 
    
    @Service('FileUploadService') 
    def fileUploadSvc;

    @Service('SysFileService') 
    def fileSvc; 

    Map create( Map data, String conn ) { 
        def svc = dynaSvc.lookup( 'SysFileService', conn ); 
        return svc.create( data );  
    } 

    Map save( Map data, String conn ) { 
        def svc = dynaSvc.lookup( 'FileUploadService', conn ); 
        return svc.upload( data ); 
    } 

    Map read( Map params, String conn ) {
        def m = [ objid: params.objid ]; 
        def svc = dynaSvc.lookup( 'SysFileService', conn ); 
        return svc.read( m ); 
    } 

    Map remove( Map params, String conn ) { 
        def svc = dynaSvc.lookup( 'SysFileService', conn ); 
        return svc.remove( params ); 
    }
}