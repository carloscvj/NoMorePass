/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.nomorepass;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 *
 * @author becario
 */
public class NoMorePass {

    private String nmp_newtoken() {
        int length = 12;
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String retVal = "";
        for (int i = 0, n = charset.length(); i < length; ++i) {
            retVal += charset.charAt((int) Math.floor(Math.random() * n));
        }
        return retVal;
    }

    private String recuperaTicket(String json) {
        JsonParser parser = new JsonParser();
        JsonElement elementObject = parser.parse(json);
        String ticket = elementObject.getAsJsonObject().get("ticket").getAsString();
        return ticket;
    }

    private String getData() {
        return "{'resultado':'ok','ticket':'qué coño pongo yo aquí'}";
    }

    public String getQrText(String site) {
        String json = getData();
        String tk = nmp_newtoken();
        String ticket = recuperaTicket(json);
        return "nomorepass://" + tk + ticket + site;
    }
}
