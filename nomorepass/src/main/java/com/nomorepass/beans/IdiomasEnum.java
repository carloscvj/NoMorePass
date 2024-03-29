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
public enum IdiomasEnum {

    castellano("Castellano", "es", "ES"),
    catalan("Català", "ca", "ES"),
    chino("中國","ch","CH"),
    aleman("Deutsch", "de", "DE"),
    ingles("English", "en", "US"),
    euskera("Euskara", "eu", "ES"),
    frances("Francais", "fr", "FR"),
    gallego("Galego", "gl", "ES"),   
    italiano("Italiano", "it", "IT"),
    japones("日本人の","ja","JA"),
    portugues("Portugues", "pt", "PT"),
    ruso("Pусский","ru","RU");

    public static IdiomasEnum getInicial(String language, String country) {
        IdiomasEnum ret = null;
        int lon = IdiomasEnum.values().length;
        for (int i = 0; i < lon; i++) {
            String idi = IdiomasEnum.values()[i].dialecto;
            String pai = IdiomasEnum.values()[i].pais;
            if (idi.equals(language) && pai.equals(country)) {
                ret = IdiomasEnum.values()[i];
                break;
            }
        }
        return ret;
    }

    private final String descripcion;
    private final String dialecto;
    private final String pais;

    private IdiomasEnum(String descripcion, String dialecto, String pais) {
        this.descripcion = descripcion;
        this.dialecto = dialecto;
        this.pais = pais;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    public List<IdiomasEnum> getTodos() {
        List<IdiomasEnum> ret = new ArrayList();
        int lon = IdiomasEnum.values().length;
        for (int i = 0; i < lon; i++) {
            ret.add(IdiomasEnum.values()[i]);
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
