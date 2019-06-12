/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.gui;

import com.nomorepass.beans.IdiomaBean;
import com.nomorepass.beans.Idiomas;
import com.nomorepass.beans.NoMorePassBean;
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

    public static Properties leerConfiguracion() throws Exception {
        Properties misprop = new Properties();
        File ficheroprop = new File(getTotalFileConfiguracion());
        if (ficheroprop.exists()) {
            misprop.load(new FileInputStream(ficheroprop));
            //Logger.getLogger(ConfiguracionCBLAbs.class.getName()).log(Level.INFO, "configuracion de " + getBase() + " leidas desde:{0}", ficheroprop.getAbsolutePath());
        } else {
            ResourceBundle bunle = ResourceBundle.getBundle("configuracion");
            if (bunle != null) {
                for (String cada : bunle.keySet()) {
                    misprop.setProperty(cada, bunle.getString(cada));
                }
                //Logger.getLogger(ConfiguracionCBLAbs.class.getName()).log(Level.INFO, "configuracion de " + getBase() + " leidas desde resource:{0}", "configuracion");
            }
        }
        return misprop;
    }

    public static void guardarConfiguracion(Properties prop) throws Exception {
        File ficheroprop = new File(getTotalFileConfiguracion());
        FileOutputStream fo = new FileOutputStream(ficheroprop);
        prop.store(fo, ficheroprop.getName());
    }

    public static void main(String args[]) throws Exception {
        Properties prop = leerConfiguracion();
        IdiomaBean ib = new IdiomaBean();
        ib.setIdioma(Idiomas.getInicial(prop.getProperty("dialecto"), prop.getProperty("pais")));

        NoMorePassFrame nmpf = new NoMorePassFrame();
        NoMorePassBean nmpb = new NoMorePassBean();
        nmpf.setBean(nmpb);
        nmpf.setIdiomaBean(ib);
        nmpf.setVisible(true);
    }

    public static void restart(NoMorePassFrame frm) throws Exception {
        Properties prop = leerConfiguracion();
        prop.setProperty("dialecto", frm.getIdiomaBean().getIdioma().getDialecto());
        prop.setProperty("pais", frm.getIdiomaBean().getIdioma().getPais());
        guardarConfiguracion(prop);
        frm.setVisible(false);
        String[] args = {""};
        Main.main(args);

    }

}
