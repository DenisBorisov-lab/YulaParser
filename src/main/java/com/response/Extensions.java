package com.response;

import com.fasterxml.jackson.annotation.*;

public class Extensions {
    private Tracing tracing;

    @JsonProperty("tracing")
    public Tracing getTracing() { return tracing; }
    @JsonProperty("tracing")
    public void setTracing(Tracing value) { this.tracing = value; }
}