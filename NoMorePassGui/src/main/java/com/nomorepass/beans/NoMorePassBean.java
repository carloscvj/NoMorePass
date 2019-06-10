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
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private void limpia() {
        this.sitioWeb = null;
        this.qrText = null;
        this.image = null;
        this.usuario = null;
        this.password = null;
        this.extra = null;
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
        setQrText(lib.getQrText(getSitioWeb()));
        setImage(generateQR(getQrText(), 350, 350));
    }

    public String getQrText() {
        return qrText;
    }

    public void setQrText(String qrText) {
        this.qrText = qrText;
    }

    public void stop() {
        lib.stop();
        lib.init();
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

    }

}
