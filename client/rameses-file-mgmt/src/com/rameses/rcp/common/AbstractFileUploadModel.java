/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rameses.rcp.common;

import com.rameses.rcp.framework.Binding;

/**
 *
 * @author wflores
 */
public class AbstractFileUploadModel {
    
    public static interface Provider { 
        Binding getBinding(); 
    }

    private Provider provider; 

    public void setProvider( Provider provider ) { 
        this.provider = provider; 
    } 
    
    public Binding getBinding() {
        return (provider == null ? null : provider.getBinding()); 
    }    
}
