/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.lib;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author becario
 */
public class Test1 {

    public static void main(String args[]) throws Exception {
        NoMorePass nmp = new NoMorePass();
        nmp.init();
        String res = nmp.getQrText("gmail.com");
        System.out.println(res);
        /*
        Thread t = new Thread(() -> {
            try {
                nmp.start();
            } catch (Exception ex) {
                Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();      
        Thread.sleep(9000); //9 segundos y cortamos;
        nmp.stop();
        */
        
        nmp.start();
        
        System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("user:" + nmp.getUser() + ", password:" + nmp.getPassword() + " extra:" + nmp.getExtra());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
    }
}
