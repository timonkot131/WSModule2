package com.example.wsmodule2.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.wsmodule2.StartActivity;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;

public class DeleteLogoutTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        StartActivity.token = null;
        return null;


    }
}



