package com.example.wsmodule2.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wsmodule2.CallBacks.OnBankomatGetListener;
import com.example.wsmodule2.POJO.Bankomat;
import com.example.wsmodule2.StartActivity;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class GetBankomatsTask extends AsyncTask<OnBankomatGetListener, Void, List<Bankomat>> {

    OnBankomatGetListener listener;

    @Override
    protected List<Bankomat> doInBackground(OnBankomatGetListener... onBankomatGetListeners) {
        listener = onBankomatGetListeners[0];
        List<Bankomat> bankomats = new ArrayList<>();


        try{
            URL url = new URL("http://"+ StartActivity.IP+":3000/bankomats");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responceStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) !=null){
                responceStrBuilder.append(inputStr);
            }

            JSONArray jsonArray = new JSONArray(responceStrBuilder.toString());
            Gson gson = new Gson();
            for(int i = 0; i<jsonArray.length(); i++){
                String jsonString = jsonArray.getJSONObject(i).toString(0);
                Bankomat bankomat = gson.fromJson(jsonString, Bankomat.class);
                bankomats.add(bankomat);
            }
        }catch (Exception e){
            Log.e("TASK",e.toString());
        }

        return bankomats;
    }

    @Override
    protected void onPostExecute(List<Bankomat> bankomats) {
        super.onPostExecute(bankomats);
        listener.onBankomatGet(bankomats);
    }
}
