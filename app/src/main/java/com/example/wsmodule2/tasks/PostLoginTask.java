package com.example.wsmodule2.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wsmodule2.CallBacks.OnAuthGetListener;
import com.example.wsmodule2.StartActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;

public class PostLoginTask extends AsyncTask<OnAuthGetListener, Void, Integer>{

    String token;
    String login;
    String pwd;
    OnAuthGetListener listener;

    public PostLoginTask(String login, String pwd){
        this.login = login;
        this.pwd = pwd;
    }

    @Override
    protected Integer doInBackground(OnAuthGetListener... onAuthGetListeners) {
        listener = onAuthGetListeners[0];
        try {
            HttpClient httpClient = HttpClients.createDefault();

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("login", login);
            jsonBody.put("pwd", pwd);

            StringEntity entity = new StringEntity(jsonBody.toString(0));
            HttpPost httpPost = new HttpPost("http://"+ StartActivity.IP+":3000/login");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == 200){
                JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                token = jsonObject.getString("token");
                return 200;
            }else{
                token=null;
                return 404;
            }

        }catch (Exception e){
            Log.e("TASK", e.toString());
        }
        return 500;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        listener.onAuthPost(integer, token);
    }
}
