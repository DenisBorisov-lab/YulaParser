package com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.location.LocationModel;
import lombok.SneakyThrows;

public class JsonService {
    @SneakyThrows
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"isConfirmed\":false,\"lat\":0,\"lng\":0,\"r\":0,\"title\":\"\",\"city\":{\"coords\":{\"latitude\":55.750718,\"longitude\":37.617661},\"id\":\"576d0612d53f3d80945f8b5d\",\"level\":4,\"name\":\"\\u041c\\u043e\\u0441\\u043a\\u0432\\u0430\",\"parentId\":\"576d0612d53f3d80945f8b7d\",\"productsCnt\":77266475,\"slug\":\"moskva\",\"namePp\":\"\\u041c\\u043e\\u0441\\u043a\\u0432\\u0435\",\"codeRb\":5506,\"parentName\":\"\"},\"citySlug\":\"moskva\",\"cityLocation\":true,\"pointLocation\":false,\"defaultRadius\":false}";
        LocationModel location = mapper.readValue(json, LocationModel.class);
        System.out.println("ok");
    }
}
