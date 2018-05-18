package com.example.machine_time.googlebooks.net.model;

import com.google.gson.annotations.SerializedName;

public class ListPrice_ {
    @SerializedName("amountInMicros")
    private String amountInMicros;
    @SerializedName("currencyCode")
    private String currencyCode;

    public String getAmountInMicros() {
        return amountInMicros;
    }

    public void setAmountInMicros(String amountInMicros) {
        this.amountInMicros = amountInMicros;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
