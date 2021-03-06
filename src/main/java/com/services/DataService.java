package com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.location.City;
import com.location.LocationModel;
import com.request.*;
import com.response.YoulaResponse;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class DataService {
    private final String domain;
    private final String[] attributes;
    private final String json;
    private final ObjectMapper mapper;


    public DataService(String domain, String attributes, String json) {
        this.domain = domain;
        this.attributes = attributes.split("&");
        this.json = json;
        mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public Request getModelRequest() {
        Request request = new Request();

        request.setOperationName(getOperationName());
        request.setVariables(getVariables());
        request.setExtensions(getExtensions());

        return request;
    }

    @SneakyThrows
    public YoulaResponse getResultedObject(String json){
        return mapper.readValue(json, YoulaResponse.class);
    }

    private Variables getVariables() {
        Variables variables = new Variables();
        variables.setSort(getSort());
        variables.setAttributes(getAttributes());
        variables.setDatePublished(getDatePublished());
        variables.setLocation(getLocation());
        variables.setSearch(getSearch());
        variables.setCursor("");
        return variables;
    }

    private String getOperationName() {
        return "catalogProductsBoard";
    }

    public Extensions getExtensions() {
        Extensions extensions = new Extensions();

        extensions.setPersistedQuery(getPersistedQuery());

        return extensions;
    }

    public PersistedQuery getPersistedQuery() {
        PersistedQuery persistedQuery = new PersistedQuery();

        persistedQuery.setVersion(1L);
        persistedQuery.setSha256Hash("6e7275a709ca5eb1df17abfb9d5d68212ad910dd711d55446ed6fa59557e2602");

        return persistedQuery;
    }

    @SneakyThrows
    private static String getHashCode() {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(("uuid=" + UUID.randomUUID()).getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String getSort() {
        String value = "";
        for (String attribute : attributes) {
            if (attribute.charAt(0) == 'a') {
                if (attribute.startsWith("sort_field", 11)) {
                    String[] splintedAttribute = attribute.split("=");
                    value = splintedAttribute[1];
                }
            }
        }

        switch (value) {
            case "price":
                value = "PRICE_ASC";
                break;
            case "date_published":
                value = "DATE_PUBLISHED_DESC";
                break;
            default:
                value = "DEFAULT";
                break;
        }

        return value;
    }

    public String getSearch() {
        String value = "";
        for (String attribute : attributes) {
            if (attribute.charAt(0) == 'q' && attribute.length() > 1) {
                value = attribute.split("=")[1];
            }
        }

        return value;
    }

    public DatePublished getDatePublished() {
        DatePublished datePublished = new DatePublished();

        for (String attribute : attributes) {
            if (attribute.charAt(0) == 'a') {
                if (attribute.substring(11).split("]\\[")[0].equals("term_of_placement")) {
                    if (attribute.charAt(30) == 't') {
                        datePublished.setTo(System.currentTimeMillis() / 1000L);
                    } else {
                        datePublished.setFrom(System.currentTimeMillis() / 1000L - ((long) Integer.parseInt(attribute.substring(37, 38)) * 3600 * 24));
                    }
                }
            }
        }

        return datePublished.getTo() == null ? null : datePublished;

    }

    public Attribute[] getAttributes() {
        List<Attribute> result = new ArrayList<>();
        Set<String> slugs = new HashSet<>();

        for (String attribute : attributes) {
            if (attribute.charAt(0) == 'a') {
                String slug = null;
                String index = null;
                String value = null;
                String[] slugAndParams = attribute.substring(11).split("]\\[");
                slug = slugAndParams[0];
                String[] indexAndValue = slugAndParams[1].split("]=");
                index = indexAndValue[0];
                if (indexAndValue.length == 2) {
                    value = indexAndValue[1];
                }

                if (!slug.equals("term_of_placement")) {
                    if (slugs.contains(slug)) {
                        // ???????? ???????? ???????????? ?????? ??????
                        for (Attribute object : result) {
                            if (object.getSlug().equals(slug)) {
                                if (index.equals("to") && value != null) {
                                    object.setTo(Long.parseLong(value));
                                } else if (index.equals("from") && value != null) {
                                    object.setFrom(Long.parseLong(value));
                                } else if (value != null) {
                                    String[] values = object.getValue();
                                    object.setValue(getValues(values, value));
                                }
                            }
                        }
                    } else {
                        slugs.add(slug);
                        Attribute object = new Attribute();

                        object.setSlug(slug);
                        if (index.equals("to") && value != null) {
                            object.setTo(Long.parseLong(value));
                        } else if (index.equals("from") && value != null) {
                            object.setFrom(Long.parseLong(value));
                        } else if (value != null) {
                            String[] values = new String[1];
                            values[0] = value;
                            object.setValue(values);
                        }

                        result.add(object);

                    }
                }
            }
        }

        Attribute[] list = new Attribute[result.size()];
        result.toArray(list);
        return list;
    }

    public String[] getValues(String[] values, String value) {
        String[] res = new String[values.length + 1];
        for (int i = 0; i < values.length; i++) {
            res[i] = values[i];
        }
        res[res.length - 1] = value;
        return res;

    }

    @SneakyThrows
    public Location getLocation(){
        LocationModel location = mapper.readValue(json, LocationModel.class);

        Location result = new Location();
        result.setCity(location.getCity().getID());
        result.setDistanceMax(location.getR());
        result.setLatitude(location.getCity().getCoords().getLatitude());
        result.setLongitude(location.getCity().getCoords().getLongitude());

        return result;
    }


}
