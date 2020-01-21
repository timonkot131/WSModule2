package com.example.wsmodule2

import androidx.appcompat.app.AppCompatActivity

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.wsmodule2.POJO.CurrencyData
import com.example.wsmodule2.tasks.GetValuteTask

import java.text.SimpleDateFormat
import java.util.Calendar

class CurrenciesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)

        val currentDate = findViewById<TextView>(R.id.currency_current_date)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        currentDate.text = dateFormat.format(Calendar.getInstance().time)

        GetValuteTask(Calendar.getInstance().time).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, { currencyDataList: List<CurrencyData> ->
            val linearLayout = findViewById<LinearLayout>(R.id.currency_container)

            val inflater = this.layoutInflater
            for (currencyData in currencyDataList) {
                val params = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 180
                )

                val relativeLayout = inflater.inflate(R.layout.currency_layout, null, false) as RelativeLayout
                relativeLayout.layoutParams = params

                val code = relativeLayout.findViewById<TextView>(R.id.currency_valute)
                code.text = currencyData.char_code

                val name = relativeLayout.findViewById<TextView>(R.id.currency_name)
                name.text = currencyData.name

                val buy = relativeLayout.findViewById<TextView>(R.id.currency_buy)
                buy.text = currencyData.value.toString()

                val sell = relativeLayout.findViewById<TextView>(R.id.currency_sell)
                sell.text = currencyData.value_sell.toString()

                val imageView1 = relativeLayout.findViewById<ImageView>(R.id.currency_buy_arrow)
                imageView1.scaleType = ImageView.ScaleType.CENTER
                if (currencyData.isValueBiggerThenCB) {
                    imageView1.setImageResource(R.drawable.mir_96)
                } else {
                    imageView1.setImageResource(R.drawable.matercard_96)
                }

                val imageView2 = relativeLayout.findViewById<ImageView>(R.id.currency_sell_arrow)
                imageView2.scaleType = ImageView.ScaleType.CENTER

                if (currencyData.isSellValueBiggerThenCB) {
                    imageView2.setImageResource(R.drawable.mir_96)
                } else {
                    imageView2.setImageResource(R.drawable.matercard_96)
                }
                linearLayout.addView(relativeLayout)
            }
        })
    }
}
