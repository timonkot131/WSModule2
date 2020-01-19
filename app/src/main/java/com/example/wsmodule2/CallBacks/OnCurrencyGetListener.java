package com.example.wsmodule2.CallBacks;

import com.example.wsmodule2.POJO.CurrencyData;

import java.util.List;

public interface OnCurrencyGetListener {
    void onCurrenciesGet(List<CurrencyData> currencyDataList);
}
