/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author becario
 */
public enum Idiomas {

    castellano("Castellano", "es", "ES"),
    catalan("Català", "ca", "ES");
    private final String descripcion;
    private final String dialecto;
    private final String pais;

    private Idiomas(String descripcion, String dialecto, String pais) {
        this.descripcion = descripcion;
        this.dialecto = dialecto;
        this.pais = pais;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    public List<Idiomas> getTodos() {
        List<Idiomas> ret = new ArrayList();
        int lon = Idiomas.values().length;
        for (int i = 0; i < lon; i++) {
            ret.add(Idiomas.values()[i]);
        }
        return ret;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDialecto() {
        return dialecto;
    }

    public String getPais() {
        return pais;
    }
}
