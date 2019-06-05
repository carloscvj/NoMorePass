/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author becario
 */
public class Prueba {

    public static void main(String args[]) {
        Gson gson = new Gson();
        String json = gson.toJson(new Data("string", 10, null, new Date()));
        System.out.println(json);

        Data[] dataArray = new Data[3];
        for (int i = 0; i < dataArray.length; i++) {
            dataArray[i] = new Data("a String " + i, i, i + 2, new Date());
        }
        json = gson.toJson(dataArray);
        System.out.println(json);

        List<Data> dataList = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add(new Data("a String " + i, i, i + 2, new Date()));
        }
        json = gson.toJson(dataList);
        System.out.println(json);

        //gson = new GsonBuilder().setDateFormat("dd/MM/yy HH:mm:ss").create();
        //json = gson.toJson(new Date());
        //System.out.println(json);

        json = "{'aString':'from Parsed String','aInt':33,'aInteger':null,'aDate':'Feb 26, 2014 7:35:23 PM'}";
        Data parsedData = gson.fromJson(json, Data.class);
        System.out.println("-------------------");
        System.out.println(parsedData.getaDate());
        System.out.println(parsedData.getaInt());
        System.out.println(parsedData.getaInteger());
        System.out.println(parsedData.getaString());
        System.out.println("-------------------");

    }
}
