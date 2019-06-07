/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.lib;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String token;
    private String ticket;
    private String user;
    private String password;
    private String extra;

    private String charlando(String url, List<NameValuePair> nvps) throws UnsupportedEncodingException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
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
        System.out.println("\nDIGO: " + httpPost);
        String responde = httpclient.execute(httpPost, responseHandler);
        System.out.println("RESPONDE: " + responde + "\n");
        return responde;
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

    private void npm_check() throws Exception {
        while (!this.stopped) {
            String json = getApiCheck();
            String resultado = recupera("resultado", json);
            if (resultado.equals("ok")) {
                String grant = recupera("grant", json);
                switch (grant) {
                    case "deny":
                        this.stopped = true;
                        break;
                    case "grant":
                        this.user = recupera("usuario", json);
                        this.password = desencriptar(recupera("password", json), this.token);
                        this.extra = recupera("extra", json);

                        this.stopped = true;
                        break;
                    case "inicial": {
                        try {
                            Thread.sleep(3000); //3 Segundos y seguimos
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NoMorePass.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }

                    case "expired":
                        this.stopped = true;
                        break;
                    default: {
                        try {
                            Thread.sleep(3000); //3 Segundos y seguimos
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NoMorePass.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
            }
        }
    }

    private String recupera(String esto, String json) {
        JsonParser parser = new JsonParser();
        JsonElement elementObject = parser.parse(json);
        String ret = "";
        if (elementObject != null) {
            JsonObject jo = elementObject.getAsJsonObject();
            if (jo != null) {
                JsonElement get = jo.get(esto);
                if (get != null) {
                    ret = get.getAsString();
                }
            }
        }
        return ret;
    }

    private String desencriptar(String recupera, String token) throws Exception {
        return OpenSslAes.decrypt(token, recupera);
    }

    private String getApiId(String site) throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("site", site));

        return charlando("https://www.nomorepass.com/api/getid.php", nvps);
    }

    private String getApiCheck() throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("ticket", this.ticket));

        return charlando("https://www.nomorepass.com/api/check.php", nvps);
    }

    private String getApiReference(String device) throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("device", device));

        return charlando("https://www.nomorepass.com/api/check.php", nvps);
    }

    public void init() {
        this.stopped = false;
        this.token = null;
        this.ticket = null;
        this.user = null;
        this.password = null;
        this.extra = null;
    }

    public String getQrText(String site) throws IOException {
        String json = getApiId(site);
        String resultado = recupera("resultado", json);
        if (resultado.equals("ok")) {
            this.token = nmp_newtoken();
            this.ticket = recupera("ticket", json);
            return "nomorepass://" + this.token + this.ticket + site;
        }
        return null;
    }

    public void start() throws Exception {
        npm_check();
    }

    public void stop() {
        this.stopped = true;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getExtra() {
        return extra;
    }

    public String getQrSend(String site, String user, String pass, String extra) throws IOException {
        if (site == null) {
            // site is the id device of origin, if null use generic WEBDEVICE
            site = "WEBDEVICE";
        }
        String device = site;
        String json = getApiReference(device);
        String resultado = recupera("resultado", json);
        if (resultado.equals("ok")) {
        }

        return null;
    }
}
