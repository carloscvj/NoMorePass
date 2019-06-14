/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.gui;

import com.nomorepass.beans.IdiomasBean;
import com.nomorepass.beans.IdiomasEnum;
import com.nomorepass.beans.NoMorePassBean;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author becario
 */
public class Main {

    private static String getCarpetaTrabajo() {
        String ret = new File("").getAbsolutePath();
        return ret;
    }

    private static String getCarpetaApps() {
        return System.getProperty("user.home", getCarpetaTrabajo()) + "/.config/nomorepass";
    }

    private static String getTotalFileConfiguracion() throws Exception {
        String ret;
        ret = getCarpetaApps() + "/NoMorePass.properties";
        String padre = new File(ret).getParentFile().getAbsolutePath();
        new File(padre).mkdirs();
        return ret;
    }

    private static Properties leerConfiguracion() throws Exception {
        Properties misprop = new Properties();
        File ficheroprop = new File(getTotalFileConfiguracion());
        if (ficheroprop.exists()) {
            misprop.load(new FileInputStream(ficheroprop));
        } else {
            ResourceBundle bunle = ResourceBundle.getBundle("NoMorePass");
            if (bunle != null) {
                bunle.keySet().forEach((cada) -> {
                    misprop.setProperty(cada, bunle.getString(cada));
                });
            }
        }
        return misprop;
    }

    private static void guardarConfiguracion(Properties prop) throws Exception {
        File ficheroprop = new File(getTotalFileConfiguracion());
        FileOutputStream fo = new FileOutputStream(ficheroprop);
        prop.store(fo, ficheroprop.getName());
    }

    private static void save(NoMorePassFrame frm) throws Exception {
        Properties prop = leerConfiguracion();
        prop.setProperty("dialecto", frm.getIdiomaBean().getIdioma().getDialecto());
        prop.setProperty("pais", frm.getIdiomaBean().getIdioma().getPais());
        Point location = frm.getLocation();
        prop.setProperty("x", Integer.toString(location.x));
        prop.setProperty("y", Integer.toString(location.y));
        prop.setProperty("tabindex", Integer.toString(frm.getTabIndex()));
        guardarConfiguracion(prop);
    }

    public static void main(String args[]) throws Exception {
        Properties prop = leerConfiguracion();
        IdiomasBean ib = new IdiomasBean();
        ib.setIdioma(IdiomasEnum.getInicial(prop.getProperty("dialecto"), prop.getProperty("pais")));
        NoMorePassFrame nmpf = new NoMorePassFrame();
        NoMorePassBean nmpb = new NoMorePassBean();
        nmpf.setBean(nmpb);
        nmpf.setIdiomaBean(ib);
        String x = prop.getProperty("x", "0");
        String y = prop.getProperty("y", "0");
        nmpf.setLocation(Integer.parseInt(x), Integer.parseInt(y));
        nmpf.setTabIndex(Integer.parseInt(prop.getProperty("tabindex", "0")));
        nmpf.setVisible(true);
    }

    public static void restart(NoMorePassFrame frm) throws Exception {
        save(frm);
        frm.setVisible(false);
        String[] args = {""};
        Main.main(args);
    }

    public static void fin(NoMorePassFrame frm) throws Exception {
        save(frm);
    }

}
