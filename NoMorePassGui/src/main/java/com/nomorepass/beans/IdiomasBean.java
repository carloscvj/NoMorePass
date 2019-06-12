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
public class IdiomasBean {

    private IdiomasEnum idioma;

    public IdiomasEnum getIdioma() {
        if (idioma == null) {
            idioma = IdiomasEnum.getInicial(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
            setIdioma(idioma);
        }
        return idioma;
    }

    public void setIdioma(IdiomasEnum idioma) {
        if (idioma != null) {
            Locale.setDefault(new Locale(idioma.getDialecto(), idioma.getPais()));
            System.out.println("Puesto en:"+Locale.getDefault());
        }
        this.idioma = idioma;
    }

}
