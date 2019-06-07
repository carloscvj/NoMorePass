/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.nomorepass;

/**
 *
 * @author becario
 */
public class Prueba {

    public static void main(String[] args) throws Exception {
        final String secretKey = "ssshhhhhhhhhhh!!!!";

        String originalString = "howtodoinjava.com";
        String encryptedString = OpenSslAes.encrypt(secretKey, originalString);
        String decryptedString = OpenSslAes.decrypt(secretKey, encryptedString);

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }
}
