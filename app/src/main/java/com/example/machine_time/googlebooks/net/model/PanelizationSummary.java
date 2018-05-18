package com.example.machine_time.googlebooks.net.model;

import com.google.gson.annotations.SerializedName;

public class PanelizationSummary {
    @SerializedName("containsEpubBubbles")
    private Boolean containsEpubBubbles;
    @SerializedName("containsImageBubbles")
    private Boolean containsImageBubbles;

    public Boolean getContainsEpubBubbles() {
        return containsEpubBubbles;
    }

    public void setContainsEpubBubbles(Boolean containsEpubBubbles) {
        this.containsEpubBubbles = containsEpubBubbles;
    }

    public Boolean getContainsImageBubbles() {
        return containsImageBubbles;
    }

    public void setContainsImageBubbles(Boolean containsImageBubbles) {
        this.containsImageBubbles = containsImageBubbles;
    }
}
