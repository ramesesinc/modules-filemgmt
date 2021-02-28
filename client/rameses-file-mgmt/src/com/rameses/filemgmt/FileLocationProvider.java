/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rameses.filemgmt;

import java.util.List;
import java.util.Map;

/**
 *
 * @author wflores
 */
public interface FileLocationProvider {
    
    public List<Map> getLocations( String connection );
    
    public Map getDefaultLocation( String connection );
    
    public Map getLocation( String connection, String locationId );
}
