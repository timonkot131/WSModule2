package com.example.wsmodule2

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.example.wsmodule2.CallBacks.OnAuthGetListener
import com.example.wsmodule2.POJO.CardData
import com.example.wsmodule2.POJO.CurrencyData
import com.example.wsmodule2.StartActivity
import com.example.wsmodule2.Utilities.GrandToster
import com.example.wsmodule2.tasks.GetValuteTask
import com.example.wsmodule2.tasks.PostLoginTask
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StartActivity : AppCompatActivity(), OnAuthGetListener, DialogInterface.OnClickListener {
    private var dialogBox: AppCompatDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val currentDate = findViewById<TextView>(R.id.start_date)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        currentDate.text = dateFormat.format(Calendar.getInstance().time)
        val usd = findViewById<TextView>(R.id.start_usd_text)
        val eur = findViewById<TextView>(R.id.start_eur_text)
        GetValuteTask(Calendar.getInstance().time).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, { currentDateList: List<CurrencyData> ->
            for (currencyData in currentDateList) {
                if (currencyData.char_code == "USD") {
                    usd.text = currencyData.value.toString()
                }
                if (currencyData.char_code == "EUR") {
                    eur.text = currencyData.value.toString()
                }
            }
        })
        val bankomats = findViewById<RelativeLayout>(R.id.start_bankomats)
        bankomats.setOnClickListener { v: View? ->
            val intent = Intent(this@StartActivity, BankomatsActivity::class.java)
            startActivity(intent)
        }
        val currencies = findViewById<RelativeLayout>(R.id.start_currency)
        currencies.setOnClickListener { v: View? ->
            val intent = Intent(this@StartActivity, CurrenciesActivity::class.java)
            startActivity(intent)
        }
        val enter = findViewById<Button>(R.id.start_button)
        enter.setOnClickListener { v: View? ->
            val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Dialog))
            val inflater = this.layoutInflater
            builder.setView(inflater.inflate(R.layout.alert_signin, null))
                    .setPositiveButton(R.string.signin, this)
                    .setNegativeButton(R.string.cancel) { diag: DialogInterface, id: Int -> diag.cancel() }
            val dialog = builder.create()
            dialog.show()
            dialogBox = dialog
        }
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        val login = dialogBox!!.findViewById<EditText>(R.id.diag_login)
        val pwd = dialogBox!!.findViewById<EditText>(R.id.diag_pwd)
      //  PostLoginTask(login!!.text.toString(), pwd!!.text.toString())
      //          .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this)
        token = "3##EEddas#@@DDSDS"
        startActivity(Intent(this@StartActivity, MainJavaActivity::class.java))
        dialog.cancel()
    }

    override fun onAuthPost(code: Int, token: String) {
        if (code == 200) {
            Companion.token = token
            startActivity(Intent(this@StartActivity, MainJavaActivity::class.java))
        } else {
            GrandToster.MakeToast("Неправильно ввели пароль", applicationContext)
        }
    }

    companion object {
        const val IP = "10.0.3.2"
        @JvmField
        var token: String? = null
        @JvmField
        var cards: ArrayList<CardData> = ArrayList<CardData>()
    }
}