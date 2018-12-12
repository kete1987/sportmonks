package com.kete.sportmonks.library.model;

import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("data")
    private GroupsData data;

    public Group() {
    }

    public GroupsData getData() {
        return data;
    }
}
