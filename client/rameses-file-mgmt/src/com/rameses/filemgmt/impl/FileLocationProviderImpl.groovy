package com.rameses.filemgmt.impl;

import com.rameses.rcp.annotations.*;
import com.rameses.filemgmt.FileLocationProvider;

class FileLocationProviderImpl implements FileLocationProvider {
    
    @Service( dynamic = true )
    def dynaSvc; 

    Map getDefaultLocation( String connection ) {
        def m = [_schemaname: 'sys_fileloc', findBy: [defaultloc: 1]]; 
        def svc = dynaSvc.lookup('PersistenceService', connection ); 
        def o = svc.read( m ); 
        resolveData( o ); 
        return o; 
    }

    Map getLocation( String connection, String locationId ) {
        def m = [_schemaname: 'sys_fileloc', findBy: [objid: locationId]]; 
        def svc = dynaSvc.lookup('PersistenceService', connection ); 
        def o = svc.read( m ); 
        resolveData( o ); 
        return o; 
    }
    
    public List getLocations( String connection ) {
        def list = null; 
        try { 
            def m = [ _schemaname: 'sys_fileloc', where:[' 1=1 ']]; 
            def svc = dynaSvc.lookup('QueryService', connection ); 
            list = svc.getList( m ); 
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