package com.example.wsmodule2.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wsmodule2.CallBacks.OnCurrencyGetListener;
import com.example.wsmodule2.POJO.CurrencyData;
import com.example.wsmodule2.StartActivity;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GetValuteTask extends AsyncTask<OnCurrencyGetListener, Void, List<CurrencyData>> {

    String date;

    OnCurrencyGetListener listener;
    public GetValuteTask(Date currentDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(currentDate);

    }

    @Override
    protected List<CurrencyData> doInBackground(OnCurrencyGetListener... onCurrencyGetListeners) {
        listener = onCurrencyGetListeners[0];
        HashMap<String, Double> currenciesAndValues = new HashMap<>();
        List<CurrencyData> currencyDataList = new ArrayList<>();
        try {

            //getting values from cb
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://www.cbr.ru/scripts/XML_daily.asp?date_req="+date);
            HttpResponse response = httpClient.execute(httpGet);
            String xml = EntityUtils.toString(response.getEntity());

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            InputSource is;
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            NodeList list = doc.getElementsByTagName("Valute");
            for(int i = 0; i < list.getLength(); i++)
            {
               Node node = list.item(i);
               NodeList nodeList = node.getChildNodes();
               String key = nodeList.item(1).getFirstChild().getNodeValue();

               //replacing ',' to '.' for parsing double
               StringBuilder sb = new StringBuilder();
               sb.append(nodeList.item(4).getFirstChild().getNodeValue());
               int index = sb.indexOf(",");
               sb.replace(index++, index, "." );

               double value = Double.parseDouble(sb.toString());
               currenciesAndValues.put(key, value);
            }

            //getting values from WS
            HttpClient httpClient2 = new DefaultHttpClient();
            HttpGet httpGet2 = new HttpGet("http://"+ StartActivity.IP+":3000/valute");
            HttpResponse response2 = httpClient2.execute(httpGet2);
            JSONArray jsonArray = new JSONArray(EntityUtils.toString(response2.getEntity()));

            Gson gson = new Gson();
            for(int i = 0; i<jsonArray.length(); i++){
                String jsonString = jsonArray.getJSONObject(i).toString(0);
                CurrencyData currencyData = gson.fromJson(jsonString, CurrencyData.class);
                currencyDataList.add(currencyData);
            }

        }catch (Exception e){
            Log.e("TASK", e.toString());
        }

        List<CurrencyData> correctedValues = CorrectValues(currencyDataList, currenciesAndValues);
        return correctedValues;
    }

    //determine bigger or smaller selling and buying values
    private List<CurrencyData> CorrectValues(List<CurrencyData> WSValute, HashMap<String, Double> currenciesAndValues){

        for(CurrencyData currencyData : WSValute){
            double valuteValCB = 0;
            try {
                valuteValCB = currenciesAndValues.get(currencyData.char_code);
            }catch (NullPointerException e){
                Log.e("TASK", "where is no valute code in CB");
            }

            double valuteValWS = currencyData.value;
            currencyData.isValueBiggerThenCB = valuteValWS > valuteValCB;

            double valuteSellValWS = currencyData.value_sell;
            currencyData.isValueBiggerThenCB = valuteSellValWS > valuteValCB;
        }

        return WSValute;
    }

    @Override
    protected void onPostExecute(List<CurrencyData> currencyDataList) {
        super.onPostExecute(currencyDataList);
        listener.onCurrenciesGet(currencyDataList);
    }
}
