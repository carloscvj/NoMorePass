/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.beans;

/**
 *
 * @author becario
 */
public class IdiomaBean {

    private Idiomas idioma;

    public Idiomas getIdioma() {
        if (idioma == null) {
            idioma = Idiomas.castellano;
        }
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
    }

}
