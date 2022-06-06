package com.response;

import com.fasterxml.jackson.annotation.*;

public class ProductAnalytics {
    private Long promotionType;
    private String typename;

    @JsonProperty("promotionType")
    public Long getPromotionType() { return promotionType; }
    @JsonProperty("promotionType")
    public void setPromotionType(Long value) { this.promotionType = value; }

    @JsonProperty("__typename")
    public String getTypename() { return typename; }
    @JsonProperty("__typename")
    public void setTypename(String value) { this.typename = value; }
}
