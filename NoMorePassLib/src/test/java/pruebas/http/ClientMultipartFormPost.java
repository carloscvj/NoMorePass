/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas.http;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Example how to use multipart/form encoded POST request. 
 */
public class ClientMultipartFormPost {

    public static void main(String[] args) throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost("https://nomorepass.com/api/sendfile.php");

            HttpEntity entity = MultipartEntityBuilder
                .create()
                .addTextBody("token", "OZg1Hw26P72n")
                .addTextBody("device", "WEBDEVICE")
                .addBinaryBody("file", new File("algo.json"), ContentType.create("application/octet-stream"), "algo.json")
                .build();
            
            httppost.setEntity(entity);

            System.out.println(httppost.toString());
            System.out.println(EntityUtils.toString(httppost.getEntity()));
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println(EntityUtils.toString(resEntity));
                }
                EntityUtils.consume(resEntity);
            }
        }
    }

}
