package com.rameses.filemgmt.impl;

import com.rameses.rcp.annotations.*;
import com.rameses.filemgmt.FileManager;

class FileTypeProviderImpl implements FileManager.FileTypeProvider {
    
    final def _types = [ 
        [ objid: "jpg",  title: "JPEG Images (*.jpg)", multiselect: true, image: true ],
        [ objid: "png",  title: "PNG Images (*.png)", multiselect: true, image: true ],
        [ objid: "pdf",  title: "PDF Document (*.pdf)", multiselect: true ],
        [ objid: "doc",  title: "Word Document (*.doc)", multiselect: true ],
        [ objid: "docx", title: "Word Document (*.docx)", multiselect: true ]
    ];

    public List getTypes() { return _types; } 

    public Map getType( String name ) { 
        def sid = name.toLowerCase(); 
        return _types.find{( it.objid == sid )} 
    }
}