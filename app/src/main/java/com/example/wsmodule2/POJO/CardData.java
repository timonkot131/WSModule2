package com.example.wsmodule2.POJO;

import android.graphics.Bitmap;

import com.example.wsmodule2.Abstractions.ICardable;

public class CardData implements ICardable {
    public String code;

    public String type;

    public Bitmap bitmap;

    public double money;

    public CardData(String code, String type, Bitmap bitmap, double money){
        this.code = code;
        this.type = type;
        this.bitmap = bitmap;
        this.money = money;
    }

    @Override
    public Bitmap getImage() {
        return bitmap;
    }

    @Override
    public String getCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(code);
        sb.replace(0, 4, "****");
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
