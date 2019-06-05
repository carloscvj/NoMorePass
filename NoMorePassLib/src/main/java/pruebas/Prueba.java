/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import com.google.gson.Gson;
import java.util.Date;


/**
 *
 * @author becario
 */
public class Prueba {

    public static void main(String args[]) {
        Gson gson = new Gson();
        String json = gson.toJson(new Data("string", 10, null, new Date()));
        System.out.println(json);
    }
}
