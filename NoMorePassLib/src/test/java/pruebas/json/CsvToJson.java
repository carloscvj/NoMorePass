/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas.json;

import com.nomorepass.lib.CSV;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author becario
 */
public class CsvToJson {

    public static void main(String args[]) throws Exception {
        String nameFile = "algo.csv";

        InputStream in = new FileInputStream(nameFile);
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
        mapper.writeValue(System.out, list);
        OutputStream ou = new FileOutputStream(nameFile + ".json");
        mapper.writeValue(ou, list);

    }
}
