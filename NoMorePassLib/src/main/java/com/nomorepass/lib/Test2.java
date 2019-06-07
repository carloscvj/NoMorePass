/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.lib;

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
        
    }
}
