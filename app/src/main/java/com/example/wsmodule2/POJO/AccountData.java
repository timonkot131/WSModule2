package com.example.wsmodule2.POJO;

import android.graphics.Bitmap;

import com.example.wsmodule2.Abstractions.ICardable;

public class AccountData implements ICardable {
    public String code;

    public String type;

    public double money;

    public AccountData(String code, String type, double money){
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
        StringBuilder sb = new StringBuilder();
        sb.append(code);
        sb.replace(4, 7, "***");
        return sb.toString();
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
