/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.nomorepass;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author becario
 */
public class NoMorePass {

    private boolean stopped;
    private String ticket;

    private String charlando(String url, String param, String value) throws UnsupportedEncodingException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(param, value));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };
        return httpclient.execute(httpPost, responseHandler);
    }

    private String nmp_newtoken() {
        int length = 12;
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String retVal = "";
        for (int i = 0, n = charset.length(); i < length; ++i) {
            retVal += charset.charAt((int) Math.floor(Math.random() * n));
        }
        return retVal;
    }

    private void npm_check() throws IOException {
        if (this.stopped) {
            this.init();
        } else {
            String json = getApiCheck();
            System.out.println(json);
            String resultado = recupera("resultado", json);
            if(resultado.equals("ok")) {
                String grant = recupera("grant", json);
                switch(grant) {
                    case "deny" :break;
                    case "grant" : break;
                    case "expired" : break;
                    default : break;
                }
            }
        }
    }

    private String recupera(String esto, String json) {
        JsonParser parser = new JsonParser();
        JsonElement elementObject = parser.parse(json);
        String ret = elementObject.getAsJsonObject().get(esto).getAsString();
        return ret;
    }

    private String getApiId() throws IOException {
        return charlando("https://www.nomorepass.com/api/getid.php", "site", "WEBDEVICE");
    }

    private String getApiCheck() throws IOException {
        return charlando("https://www.nomorepass.com/api/check.php", "ticket", this.ticket);
    }

    public void init() {
        this.stopped = false;
        this.ticket = null;
    }

    public String getQrText(String site) throws IOException {
        String json = getApiId();
        String resultado = recupera("resultado", json);
        if (resultado.equals("ok")) {
            String tk = nmp_newtoken();
            this.ticket = recupera("ticket", json);
            return "nomorepass://" + tk + this.ticket + site;
        }
        return null;
    }

    public void start() throws IOException {
        npm_check();
    }
}
