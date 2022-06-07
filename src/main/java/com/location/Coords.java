package com.location;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coords {
    private double latitude;
    private double longitude;

    @JsonProperty("latitude")
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(double value) {
        this.latitude = value;
    }

    @JsonProperty("longitude")
    public double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(double value) {
        this.longitude = value;
    }
}

