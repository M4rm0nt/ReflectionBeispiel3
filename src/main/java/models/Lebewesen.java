package models;

import interfaces.JsonProperty;

public class Lebewesen {
    @JsonProperty
    protected String art;

    public Lebewesen(String art) {
        this.art = art;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }
}