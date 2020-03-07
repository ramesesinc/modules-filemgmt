/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rameses.filemgmt.views;

/**
 *
 * @author wflores
 */
public class FileItemLoadPage extends javax.swing.JPanel {

    /**
     * Creates new form FileItemLoadPage
     */
    public FileItemLoadPage() {
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

        xLabel1 = new com.rameses.rcp.control.XLabel();
        xLabel2 = new com.rameses.rcp.control.XLabel();

        setLayout(new java.awt.BorderLayout());

        xLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 1, 1, 1));
        xLabel1.setDepends(new String[] {"loadingStatusMessage"});
        xLabel1.setExpression("<b>#{loadingStatusMessage}</b>");
        xLabel1.setFontStyle("font-size:18;");
        xLabel1.setForeground(new java.awt.Color(102, 102, 102));
        xLabel1.setUseHtml(true);
        add(xLabel1, java.awt.BorderLayout.NORTH);

        xLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xLabel2.setFontStyle("font-size:20;");
        xLabel2.setForeground(new java.awt.Color(102, 102, 102));
        xLabel2.setIconResource("com/rameses/rcp/icons/loading32.gif");
        xLabel2.setUseHtml(true);
        add(xLabel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XLabel xLabel2;
    // End of variables declaration//GEN-END:variables
}