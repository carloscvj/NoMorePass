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
public class Test3 {

    public static void main(String args[]) throws Exception {
        NoMorePass nmp = new NoMorePass();
        nmp.init();
        nmp.convertAndSend("algunFichero.csv");
        
        System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("---------------------------------------------------");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");

        
    }
}
