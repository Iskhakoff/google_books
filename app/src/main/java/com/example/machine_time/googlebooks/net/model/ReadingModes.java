package com.example.machine_time.googlebooks.net.model;

import com.google.gson.annotations.SerializedName;

public class ReadingModes {
    @SerializedName("text")
    private Boolean text;
    @SerializedName("image")
    private Boolean image;

    public Boolean getText() {
        return text;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }
}
