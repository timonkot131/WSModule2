package com.example.wsmodule2.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wsmodule2.CallBacks.OnAuthGetListener;
import com.example.wsmodule2.StartActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://"+ StartActivity.IP+":3000/login");

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("login", login));
            nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
