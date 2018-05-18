package com.example.machine_time.googlebooks.net.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {
    @SerializedName("kind")
    private String kind;
    @SerializedName("totalItems")
    private String totalItems;
    @SerializedName("items")
    private List<Item> items;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
