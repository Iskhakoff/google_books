package com.example.machine_time.googlebooks.net.model;

import com.google.gson.annotations.SerializedName;

public class Epub {
    @SerializedName("isAvailable")
    private Boolean isAvailable;
    @SerializedName("acsTokenLink")
    private String acsTokenLink;
    @SerializedName("downloadLink")
    private String downloadLink;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getAcsTokenLink() {
        return acsTokenLink;
    }

    public void setAcsTokenLink(String acsTokenLink) {
        this.acsTokenLink = acsTokenLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}
