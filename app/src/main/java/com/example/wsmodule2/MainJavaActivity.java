package com.example.wsmodule2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.wsmodule2.POJO.AccountData;
import com.example.wsmodule2.POJO.CardData;
import com.example.wsmodule2.POJO.CreditData;
import com.example.wsmodule2.tasks.DeleteLogoutTask;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_logout).setOnClickListener((View v) -> {
            new DeleteLogoutTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        });

        findViewById(R.id.main_profile).setOnClickListener((View v) ->{
            startActivity(new Intent(MainJavaActivity.this, ProfileActivity.class));
        });

        LinearLayout container = findViewById(R.id.main_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                40
        );

        TextView myCard = new TextView(getApplicationContext());
        myCard.setBackgroundColor(getResources().getColor(R.color.colorGray));
        myCard.setLayoutParams(params);
        myCard.setText("МОИ КАРТЫ");

        TextView myAccount = new TextView(getApplicationContext());
        myAccount.setBackgroundColor(getResources().getColor(R.color.colorGray));
        myAccount.setLayoutParams(params);
        myAccount.setText("МОИ СЧЕТА");

        TextView myCredit = new TextView(getApplicationContext());
        myCredit.setBackgroundColor(getResources().getColor(R.color.colorGray));
        myCredit.setLayoutParams(params);
        myCredit.setText("МОИ КРЕДИТЫ");

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.matercard_96);
        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                10
        );

        container.addView(myCard);
        for(int i = 0; i < 3; i++)
        {
            CardData cardData = new CardData("22223144444", "Дебетовая карта "+i , bmp, 33.3333);
            AccountView accountView = new AccountView(getApplicationContext(),
                    cardData);
            accountView.setBackgroundColor(getResources().getColor(R.color.colorWhiteSmoke));
            accountView.setTag(cardData);
            container.addView(accountView);
            Space space = new Space(getApplicationContext());
            space.setLayoutParams(spaceParams);
            container.addView(space);
            AccountView accountView1 = container.findViewWithTag(cardData);
            StartActivity.cards.add(cardData);
            accountView1.setOnClickListener((View v) ->{
                Intent intent = new Intent(MainJavaActivity.this, CardsActivity.class);
                intent.putExtra("card_list_index", StartActivity.cards.indexOf(cardData));
                startActivity(intent);
            });
        }

        container.addView(myAccount);
        for(int i = 0; i<3; i++)
        {
            AccountView accountView = new AccountView(getApplicationContext(),
                    new AccountData("22223144444", "Дебетовая карта" , 33.3333));
            accountView.setBackgroundColor(getResources().getColor(R.color.colorWhiteSmoke));
            container.addView(accountView);
            Space space = new Space(getApplicationContext());
            space.setLayoutParams(spaceParams);
            container.addView(space);
        }

        container.addView(myCredit);
        for(int i = 0; i<3; i++)
        {
            AccountView accountView = new AccountView(getApplicationContext(),
                    new CreditData("Платеж аааа", "Дебетовая карта" , 33.3333));
            accountView.setBackgroundColor(getResources().getColor(R.color.colorWhiteSmoke));
            container.addView(accountView);
            Space space = new Space(getApplicationContext());
            space.setLayoutParams(spaceParams);
            container.addView(space);
        }
    }
}
