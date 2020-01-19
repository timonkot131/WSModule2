package com.example.wsmodule2.POJO;

import com.google.gson.annotations.SerializedName;

public class CurrencyData {

    @SerializedName("id")
    public int id;

    @SerializedName("char_code")
    public String char_code;

    @SerializedName("name")
    public String name;

    @SerializedName("value")
    public double value;

    @SerializedName("value_sell")
    public double value_sell;

    public boolean isSellValueBiggerThenCB;

    public boolean isValueBiggerThenCB;
}
