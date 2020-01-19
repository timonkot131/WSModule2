package com.example.wsmodule2.tasks;

import android.os.AsyncTask;

import com.example.wsmodule2.StartActivity;

public class DeleteLogoutTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        StartActivity.token = null;
        return null;
    }
}
