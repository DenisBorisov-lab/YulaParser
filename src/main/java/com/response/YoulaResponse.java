package com.response;

import com.fasterxml.jackson.annotation.*;

public class YoulaResponse {
    private Data data;
    private Extensions extensions;

    @JsonProperty("data")
    public Data getData() { return data; }
    @JsonProperty("data")
    public void setData(Data value) { this.data = value; }

    @JsonProperty("extensions")
    public Extensions getExtensions() { return extensions; }
    @JsonProperty("extensions")
    public void setExtensions(Extensions value) { this.extensions = value; }
}
