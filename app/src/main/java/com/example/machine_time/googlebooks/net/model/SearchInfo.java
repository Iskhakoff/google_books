package com.example.machine_time.googlebooks.net.model;

import com.google.gson.annotations.SerializedName;

public class SearchInfo {
    @SerializedName("textSnippet")
    private String textSnippet;

    public String getTextSnippet() {
        return textSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        this.textSnippet = textSnippet;
    }
}
