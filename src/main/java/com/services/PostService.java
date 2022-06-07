package com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.Request;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostService {
    private final URL url;
    private Request model;
    private final ObjectMapper mapper;

    public PostService(Request model) throws MalformedURLException {
        this.model = model;
        url = new URL("https://api-gw.youla.io/federation/graphql");
        mapper = new ObjectMapper();
    }

    @SneakyThrows
    public String sendRequest() {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        String body = javaObjetToString();
        try (OutputStream os = connection.getOutputStream()){
            byte[] input = body.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }


    @SneakyThrows
    private String javaObjetToString() {
        return mapper.writeValueAsString(model);
    }
}
