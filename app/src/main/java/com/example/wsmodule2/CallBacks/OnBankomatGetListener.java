package com.example.wsmodule2.CallBacks;

import com.example.wsmodule2.POJO.Bankomat;

import java.util.List;

public interface OnBankomatGetListener {
    void onBankomatGet(List<Bankomat> bankomats);
}
