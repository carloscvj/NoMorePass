/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.beans;

import com.nomorepass.lib.NoMorePass;

/**
 *
 * @author becario
 */
public class NoMorePassBean {
    
    private final NoMorePass lib = new NoMorePass();
    private String sitioWeb;
    private String qrText;

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void start() throws Exception {
        lib.init();
        setQrText(lib.getQrText(getSitioWeb()));
    }

    public String getQrText() {
        return qrText;
    }

    public void setQrText(String qrText) {
        this.qrText = qrText;
    }
    
}
