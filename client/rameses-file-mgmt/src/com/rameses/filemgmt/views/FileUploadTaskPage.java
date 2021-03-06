/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rameses.filemgmt.views;

import com.rameses.osiris2.themes.OKCancelPage;
import com.rameses.rcp.ui.annotations.Template;
import java.awt.Dimension;

/**
 *
 * @author ramesesinc
 */
@Template(OKCancelPage.class)
public class FileUploadTaskPage extends javax.swing.JPanel { 

    /**
     * Creates new form FileUploadTaskPage
     */
    public FileUploadTaskPage() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileUploadPanel1 = new com.rameses.filemgmt.components.FileUploadPanel();
        jPanel1 = new javax.swing.JPanel();
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xFormPanel1 = new com.rameses.rcp.control.XFormPanel();
        xTextField1 = new com.rameses.rcp.control.XTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new java.awt.Dimension(579, 221));
        setLayout(new java.awt.BorderLayout());

        fileUploadPanel1.setDepends(new String[] {"files"});
        fileUploadPanel1.setHandler("uploadHandler");
        add(fileUploadPanel1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        xLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        xLabel1.setFontStyle("font-size: 14; font-weight: bold;");
        xLabel1.setForeground(new java.awt.Color(80, 80, 80));
        xLabel1.setText("Uploading Files");
        jPanel1.add(xLabel1, java.awt.BorderLayout.PAGE_START);

        xFormPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 50, 1));
        xFormPanel1.setCaptionWidth(120);
        xFormPanel1.setPadding(new java.awt.Insets(0, 0, 5, 5));
        xFormPanel1.setPreferredSize(new java.awt.Dimension(10, 70));

        xTextField1.setCaption("Title");
        xTextField1.setName("entity.title"); // NOI18N
        xTextField1.setPreferredSize(new java.awt.Dimension(0, 20));
        xFormPanel1.add(xTextField1);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 38));

        xTextArea1.setCaption("Description");
        xTextArea1.setName("entity.description"); // NOI18N
        jScrollPane1.setViewportView(xTextArea1);

        xFormPanel1.add(jScrollPane1);

        jPanel1.add(xFormPanel1, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.rameses.filemgmt.components.FileUploadPanel fileUploadPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextField xTextField1;
    // End of variables declaration//GEN-END:variables
}
