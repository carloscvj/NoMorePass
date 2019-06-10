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
public class Test2 {

    public static void main(String args[]) throws Exception {
        NoMorePass nmp = new NoMorePass();
        nmp.init();
        String res = nmp.getQrSend(null, "carloscvj", "tiroriro", "{\"type\":\"pwd\"}");
        
        System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(res);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");

        Thread t = new Thread(() -> {
            try {
                nmp.ping();
            } catch (Exception ex) {
                Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();      
        Thread.sleep(60000); //1 minuto y cortamos
        nmp.stop();        
        //nmp.ping();
        
    }
}
