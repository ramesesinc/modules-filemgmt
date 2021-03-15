/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.rameses.ftp.FtpException;
import com.rameses.ftp.FtpLocationConf;
import com.rameses.ftp.FtpSession;
import junit.framework.TestCase;

/**
 *
 * @author ramesesinc
 */
public class TestFtp extends TestCase {
    
    public TestFtp(String testName) {
        super(testName);
    }

    public void testDeleteFile() throws Exception {
        FtpLocationConf conf = FtpLocationConf.add("default");
        conf.setHost("192.168.1.25");
        conf.setUser("ftpdata");
        conf.setPassword("P@ssw0rd#");
        
        boolean pass = false; 
        FtpSession sess = null; 
        try {
            sess = new FtpSession( conf ); 
            sess.connect(); 
            pass = true; 
        }
        finally {
            if ( !pass ) {
                disconnect( sess ); 
            }
        }
        
        try {
            sess.deleteFile("4b51742c288cc1cce94901233fe91e1f01.jpg"); 
        }
        catch(FtpException fe) {
            System.out.println("FtpSession.deleteFile: "+ fe.getMessage());
        }
        finally {
            disconnect( sess ); 
        }
    }
    
    private void disconnect( FtpSession sess ) {
        if ( sess != null ) {
            try {
                sess.disconnect(); 
            } catch(Throwable t) {;} 
        }
    }
}
