package com.example.wsmodule2.POJO;

import com.google.gson.annotations.SerializedName;

public class Bankomat {

    @SerializedName("place")
    public String place;

    @SerializedName("type")
    public String type;

    @SerializedName("is_working")
    public boolean is_working;

    @SerializedName("work_from")
    public long work_from;

    @SerializedName("work_to")
    public long work_to;

    @SerializedName("latitude")
    public double latitude;

    @SerializedName("longtitude")
    public double longtitude;
}
