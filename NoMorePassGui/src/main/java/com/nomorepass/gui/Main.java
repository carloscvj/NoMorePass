/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.gui;

import com.nomorepass.beans.NoMorePassBean;

/**
 *
 * @author becario
 */
public class Main {

    public static void main(String args[]) throws Exception {
        NoMorePassFrame nmpf = new NoMorePassFrame();
        NoMorePassBean nmpb = new NoMorePassBean();
        nmpf.setBean(nmpb);
        nmpf.setVisible(true);
    }
}
