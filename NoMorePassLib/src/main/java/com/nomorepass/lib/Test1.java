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
public class Test1 {

    public static void main(String args[]) throws Exception {
        NoMorePass nmp = new NoMorePass();
        nmp.init();
        String res = nmp.getQrText("gmail.com");
        System.out.println(res);
        nmp.start();
        System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("user:" + nmp.getUser() + ", password:" + nmp.getPassword() + " extra:" + nmp.getExtra());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
        //res = nmp.getQrSend("gmail.com", nmp.getUser(), nmp.getPassword(), nmp.getExtra());
        //System.out.println(res);
    }
}
