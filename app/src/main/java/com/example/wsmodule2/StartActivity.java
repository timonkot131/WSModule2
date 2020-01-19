package com.example.wsmodule2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wsmodule2.CallBacks.OnAuthGetListener;
import com.example.wsmodule2.POJO.CardData;
import com.example.wsmodule2.POJO.CurrencyData;
import com.example.wsmodule2.Utilities.GrandToster;
import com.example.wsmodule2.tasks.GetValuteTask;
import com.example.wsmodule2.tasks.PostLoginTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StartActivity extends AppCompatActivity implements OnAuthGetListener , DialogInterface.OnClickListener {
    static public final String IP = "10.0.3.2";
    public static String token;
    public static List<CardData> cards = new ArrayList<>();
    private AppCompatDialog dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView currentDate = findViewById(R.id.start_date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        currentDate.setText(dateFormat.format(Calendar.getInstance().getTime()));

        TextView usd = findViewById(R.id.start_usd_text);
        TextView eur = findViewById(R.id.start_eur_text);

        new GetValuteTask(Calendar.getInstance().getTime()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (List<CurrencyData> currentDateList) ->{
           for(CurrencyData currencyData : currentDateList){
               if(currencyData.char_code.equals("USD")){
                   usd.setText(String.valueOf(currencyData.value));
               }
               if(currencyData.char_code.equals("EUR")){
                   eur.setText(String.valueOf(currencyData.value));
               }
           }
        });

        RelativeLayout bankomats = findViewById(R.id.start_bankomats);
        bankomats.setOnClickListener((View v) -> {
            Intent intent = new Intent(StartActivity.this, BankomatsActivity.class);
            startActivity(intent);
        });

        RelativeLayout currencies = findViewById(R.id.start_currency);
        currencies.setOnClickListener((View v) -> {
            Intent intent = new Intent(StartActivity.this, CurrenciesActivity.class);
            startActivity(intent);
        });

        Button enter = findViewById(R.id.start_button);
        enter.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat_Dialog));

            LayoutInflater inflater = this.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.alert_signin, null))
                    .setPositiveButton(R.string.signin, this)
                    .setNegativeButton(R.string.cancel, (DialogInterface diag, int id) ->{
                        diag.cancel();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialogBox = dialog;
    });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        EditText login = dialogBox.findViewById(R.id.diag_login);
        EditText pwd = dialogBox.findViewById(R.id.diag_pwd);
        new PostLoginTask(login.getText().toString(), pwd.getText().toString())
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
        dialog.cancel();
    }

    @Override
    public void onAuthPost(int code, String token) {
        if(code==200){
           StartActivity.token = token;
           startActivity(new Intent(StartActivity.this, MainJavaActivity.class));
        }
        else{
            GrandToster.MakeToast("Неправильно ввели пароль", getApplicationContext());
        }
    }
}
