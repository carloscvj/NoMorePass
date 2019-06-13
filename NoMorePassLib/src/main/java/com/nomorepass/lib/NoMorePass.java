/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomorepass.lib;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
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

    private String subiendoUnFile(String url, String fileName) throws Exception {
        String ret = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost(url);

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addTextBody("token", this.token)
                    .addTextBody("device", "WEBDEVICE")
                    .addBinaryBody("file", new File(fileName), ContentType.create("application/octet-stream"), fileName)
                    .build();

            httppost.setEntity(entity);

            //System.out.println(httppost.toString());
            //System.out.println(EntityUtils.toString(httppost.getEntity()));
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    ret = EntityUtils.toString(resEntity);
                }
                EntityUtils.consume(resEntity);
            }
        }
        return ret;
    }

    private String charlando(String url, List<NameValuePair> nvps) throws UnsupportedEncodingException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };
        //System.out.println("\nDIGO: " + httpPost + " " + nvps);
        String responde = httpclient.execute(httpPost, responseHandler);
        //System.out.println("RESPONDE: " + responde + "\n");
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

    private String encriptar(String recupera, String token) throws Exception {
        return OpenSslAes.encrypt(token, recupera);
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
        nvps.add(new BasicNameValuePair("fromdevice", device));

        return charlando("https://www.nomorepass.com/api/reference.php", nvps);
    }

    private String getApiPing() throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("device", "WEBDEVICE"));
        nvps.add(new BasicNameValuePair("ticket", this.ticket));

        return charlando("https://www.nomorepass.com/api/ping.php", nvps);
    }

    private String getApiGrant() throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("grant", "grant"));
        nvps.add(new BasicNameValuePair("ticket", this.ticket));
        nvps.add(new BasicNameValuePair("user", this.user));
        nvps.add(new BasicNameValuePair("password", this.password));
        nvps.add(new BasicNameValuePair("extra", this.extra));

        return charlando("https://www.nomorepass.com/api/grant.php", nvps);
    }

    private String getApiSendFile(String nameFile) throws Exception {
        this.token = nmp_newtoken();
        return subiendoUnFile("https://nomorepass.com/api/sendfile.php", nameFile);
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

    public String getQrSend(String site, String user, String pass, String extra) throws Exception {
        if (site == null) {
            // site is the id device of origin, if null use generic WEBDEVICE
            site = "WEBDEVICE";
        }
        String device = "WEBDEVICE";
        String json = getApiReference(device);
        String resultado = recupera("resultado", json);
        if (resultado.equals("ok")) {
            String json1 = getApiId(site);
            String resultado1 = recupera("resultado", json1);
            if (resultado1.equals("ok")) {
                this.token = recupera("token", json1);
                this.ticket = recupera("ticket", json1);
                this.user = user;
                this.password = encriptar(pass, this.token);
                this.extra = extra;
                String json2 = getApiGrant();
                return "nomorepass://SENDPASS" + this.token + this.ticket + site;
            }
        }

        return null;
    }

    public void ping() throws Exception {
        while (!this.stopped) {
            String json = getApiPing();
            String resultado = recupera("resultado", json);
            String ping = recupera("ping", json);
            if (resultado.equals("ok") && ping.equals("ok")) {
                try {
                    Thread.sleep(3000); //3 Segundos y seguimos
                } catch (InterruptedException ex) {
                    Logger.getLogger(NoMorePass.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //System.out.println("\n********************************************");
                //System.out.println("El dispositivo YA lo ha recogido");
                //System.out.println("********************************************\n");
                break;
            }
        }
    }

    private void csvToJson(String fileName) throws Exception {
        InputStream in = new FileInputStream(fileName);
        CSV csv = new CSV(true, ',', in);
        List<String> fieldNames = null;
        if (csv.hasNext()) {
            fieldNames = new ArrayList<>(csv.next());
        }
        List<Map<String, String>> list = new ArrayList<>();
        while (csv.hasNext()) {
            List< String> x = csv.next();
            Map< String, String> obj = new LinkedHashMap<>();
            for (int i = 0; i < fieldNames.size(); i++) {
                obj.put(fieldNames.get(i), x.get(i));
            }
            list.add(obj);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        OutputStream ou = new FileOutputStream(fileName + ".json");
        mapper.writeValue(ou, list);
    }

    public String convertAndSend(String fileName) throws Exception {
        csvToJson(fileName);
        String json = getApiSendFile(fileName + ".json");
        //System.out.println(json);
        String resultado = recupera("resultado", json);
        if (resultado.equals("ok")) {
            this.token = recupera("token", json);
            this.ticket = recupera("ticket", json);
            return "nomorepass://SENDFILE" + this.token + this.ticket;
        }
        return null;

    }
}
