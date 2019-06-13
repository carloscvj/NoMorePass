/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.beans;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nomorepass.lib.NoMorePass;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author becario
 */
public class NoMorePassBean {

    private final NoMorePass lib = new NoMorePass();
    private String sitioWeb;
    private String qrText;
    private Image image;
    private String usuario;
    private String password;
    private String extra;
    private Boolean iniciable;
    private Boolean enviable;
    private String archivoCSV;
    private Boolean cvsable;

    private void limpia() {
        this.sitioWeb = null;
        this.qrText = null;
        this.image = null;
        this.usuario = null;
        this.password = null;
        this.extra = null;
        this.archivoCSV = null;
    }

    private Image generateQR(String text, int h, int w) throws Exception {

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, w, h);

        BufferedImage miImage = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        miImage.createGraphics();

        Graphics2D graphics = (Graphics2D) miImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrix.getWidth(); i++) {
            for (int j = 0; j < matrix.getHeight(); j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        return miImage;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void start() throws Exception {
        lib.init();
        this.qrText = null;
        this.image = null;
        this.usuario = null;
        this.password = null;
        this.extra = null;
        this.setQrText(lib.getQrText(getSitioWeb()));
        this.setImage(this.generateQR(getQrText(), 350, 350));
    }

    public String getQrText() {
        return qrText;
    }

    public void setQrText(String qrText) {
        this.qrText = qrText;
    }

    public void stop() {
        lib.stop();
        this.limpia();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public void scaneando() throws Exception {
        lib.start();
        this.setUsuario(lib.getUser());
        this.setPassword(lib.getPassword());
        this.setExtra(lib.getExtra());
        this.image = null;
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection ss = new StringSelection(this.getPassword());
        cb.setContents(ss, ss);
    }

    public void enviar() throws Exception {
        lib.init();
        this.setQrText(lib.getQrSend(this.getSitioWeb(), this.getUsuario(), this.getPassword(), "{\"type\":\"pwd\"}"));
        this.setImage(this.generateQR(this.getQrText(), 350, 350));
    }

    public void ping() throws Exception {
        lib.ping();
    }

    public Boolean getIniciable() {
        this.iniciable = !(sitioWeb == null || sitioWeb.equals(""));
        return iniciable;
    }

    public void setIniciable(Boolean iniciable) {
        this.iniciable = iniciable;
    }

    public Boolean getEnviable() {
        enviable = !((usuario == null || usuario.equals("")) && (password == null || password.equals("")));
        return enviable;
    }

    public void setEnviable(Boolean enviable) {
        this.enviable = enviable;
    }

    public String getArchivoCSV() {
        if (archivoCSV == null) {
            archivoCSV = "";
        }
        return archivoCSV.trim();
    }

    public void setArchivoCSV(String archivoCSV) {
        this.archivoCSV = archivoCSV;
    }

    public Boolean getCvsable() {
        this.cvsable = new File(getArchivoCSV()).exists();
        return cvsable;
    }

    public void setCvsable(Boolean cvsable) {
        this.cvsable = cvsable;
    }
    
    public void enviarCsv() throws Exception {
        lib.init();
        this.setQrText(lib.convertAndSend(getArchivoCSV()));
        this.setImage(this.generateQR(this.getQrText(), 350, 350));        
    }

}
