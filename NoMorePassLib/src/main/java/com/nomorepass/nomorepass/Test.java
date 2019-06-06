/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.nomorepass;

import java.io.IOException;

/**
 *
 * @author becario
 */
public class Test {
    
    public static void main(String args[]) throws IOException {
        NoMorePass nmp = new NoMorePass();
        nmp.init();
        String res = nmp.getQrText("Sitio Web");
        System.out.println(res);
        nmp.start();
    }
}
