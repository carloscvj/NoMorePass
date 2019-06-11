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
public enum Idiomas {

    castellano("Castellano", "es"),
    catalan("Català", "ca");
    private final String descripcion;
    private final String idioma;

    private Idiomas(String descripcion, String idioma) {
        this.descripcion = descripcion;
        this.idioma = idioma;
    }

}
