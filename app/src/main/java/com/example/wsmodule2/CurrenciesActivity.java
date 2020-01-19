package com.example.wsmodule2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wsmodule2.POJO.CurrencyData;
import com.example.wsmodule2.tasks.GetValuteTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CurrenciesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);

        TextView currentDate = findViewById(R.id.currency_current_date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        currentDate.setText(dateFormat.format(Calendar.getInstance().getTime()));

        new GetValuteTask(Calendar.getInstance().getTime()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (List<CurrencyData> currencyDataList) -> {
            LinearLayout linearLayout = findViewById(R.id.currency_container);

            LayoutInflater inflater = this.getLayoutInflater();
            for(CurrencyData currencyData : currencyDataList){
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 180
                );

                RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.currency_layout, null , false);
                relativeLayout.setLayoutParams(params);

                TextView code = relativeLayout.findViewById(R.id.currency_valute);
                code.setText(currencyData.char_code);

                TextView name = relativeLayout.findViewById(R.id.currency_name);
                name.setText(currencyData.name);

                TextView buy = relativeLayout.findViewById(R.id.currency_buy);
                buy.setText(String.valueOf(currencyData.value));

                TextView sell= relativeLayout.findViewById(R.id.currency_sell);
                sell.setText(String.valueOf(currencyData.value_sell));

                ImageView imageView1 = relativeLayout.findViewById(R.id.currency_buy_arrow);
                imageView1.setScaleType(ImageView.ScaleType.CENTER);
                if(currencyData.isValueBiggerThenCB){
                    imageView1.setImageResource(R.drawable.mir_96);
                }
                else{
                    imageView1.setImageResource(R.drawable.matercard_96);
                }

                ImageView imageView2 = relativeLayout.findViewById(R.id.currency_sell_arrow);
                imageView2.setScaleType(ImageView.ScaleType.CENTER);

                if(currencyData.isSellValueBiggerThenCB){
                    imageView2.setImageResource(R.drawable.mir_96);
                }
                else{
                    imageView2.setImageResource(R.drawable.matercard_96);
                }
                linearLayout.addView(relativeLayout);
            }
        });
    }
}
