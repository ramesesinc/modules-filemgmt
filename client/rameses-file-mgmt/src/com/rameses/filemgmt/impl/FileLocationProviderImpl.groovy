package com.rameses.filemgmt.impl;

import com.rameses.rcp.annotations.*;
import com.rameses.filemgmt.FileLocationProvider;

class FileLocationProviderImpl implements FileLocationProvider {
    
    @Service('PersistenceService') 
    def persistenceSvc; 
    
    @Service('QueryService') 
    def qrySvc; 
    
    Map getDefaultLocation() {
        def m = [_schemaname: 'sys_fileloc', findBy: [defaultloc: 1]]; 
        def o = persistenceSvc.read( m ); 
        resolveData( o ); 
        return o; 
    }

    Map getLocation( String locationId ) {
        def m = [_schemaname: 'sys_fileloc', findBy: [objid: locationId]]; 
        def o = persistenceSvc.read( m ); 
        resolveData( o ); 
        return o; 
    }
    
    public List getLocations() {
        def list = null; 
        try { 
            def m = [ _schemaname: 'sys_fileloc', where:[' 1=1 ']]; 
            list = qrySvc.getList( m ); 
        } catch(Throwable t) {
            list = []; 
        } 

        list.each{ resolveData(it) } 
        return list; 
    } 
    
    void resolveData( o ) {
        if ( o ) {
            o.name = o.objid;
            o.type = o.loctype; 
            o.username = o.user?.name;
            o.password = o.user?.pwd; 
            o.defaulted = (o.defaultloc.toString().matches('1|true')); 
        }
    }
}