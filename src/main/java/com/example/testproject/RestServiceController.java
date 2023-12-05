package com.example.testproject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.InsertAllRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class RestServiceController {

    private static InsertAllRequest build;
    static BigQuery bigquery;
    static Map<String, String> gkeVersionMap = new HashMap<>();
    static Map<String, String> cloudSQLVersionMap = new HashMap<>();


    @CrossOrigin
    @RequestMapping("/example")
    public String exampleProject() throws IOException {
        String rt = ConsumeRest("http://backend-server-2.default.svc.cluster.local:8080/trend");
        return rt;
        // return "I am cluster ip example service !!";
    }

    @RequestMapping("/trend")
    public String exampleProject1() throws IOException {
        /*String rt = ConsumeRest("https://catfact.ninja/fact?max_length=140");
        return rt;*/
        return "I am external service trend api !!";
    }

    public String ConsumeRest(String url){
        String rt = "I am not good !!";
        try {
            // URL url = new URL("http://backend-server-2.default.svc.cluster.local/trend");//your url i.e fetch data from .
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                rt = output;
            }
            conn.disconnect();
            return rt;
        } catch (Exception e) {
            rt = "some error occured";
            System.out.println("Exception in NetClientGet:- " + e);
            return rt;
        }
    }
}
