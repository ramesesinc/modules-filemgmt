package com.rameses.filemgmt.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.filemgmt.impl.*;

public class FileManagerLoader {  

    void doStart() {
        def ctx = com.rameses.rcp.framework.ClientContext.currentContext;
        def value = (ctx.appEnv ? ctx.appEnv.get('filemgmt.enabled') : null).toString();
        def enabled = (value == 'false' ? false : true); 

        def fm = com.rameses.filemgmt.FileManager.instance; 
        fm.loadFileLocTypeProviders( ctx.classLoader ); 
        fm.fileTypeProvider = ManagedObjects.instance.create( FileTypeProviderImpl.class ); 
        fm.locationProvider = ManagedObjects.instance.create( FileLocationProviderImpl.class ); 
        fm.dbProvider = ManagedObjects.instance.create( FileDbProviderImpl.class ); 
        fm.enabled = enabled; 
        fm.start(); 
    } 
} 