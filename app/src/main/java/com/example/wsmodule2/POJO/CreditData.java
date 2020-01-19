package com.example.wsmodule2.POJO;

import android.graphics.Bitmap;

import com.example.wsmodule2.Abstractions.ICardable;

public class CreditData implements ICardable {
    public String code;

    public String type;

    public double money;

    public CreditData(String code, String type, double money){
        this.code = code;
        this.type = type;
        this.money = money;
    }

    @Override
    public Bitmap getImage() {
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMoney() {
        return money + " рублей";
    }

    @Override
    public String getType() {
        return type;
    }
}
