/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.beans;

import java.util.Locale;

/**
 *
 * @author becario
 */
public class IdiomaBean {

    private Idiomas idioma;

    public Idiomas getIdioma() {
        if (idioma == null) {
            idioma = Idiomas.catalan;
            setIdioma(idioma);
        }
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        if (idioma != null) {
            System.out.println(Locale.getDefault());
            Locale.setDefault(new Locale(idioma.getDialecto(), Locale.getDefault().getCountry()));
            System.out.println(Locale.getDefault());
        }
        this.idioma = idioma;
    }

}
