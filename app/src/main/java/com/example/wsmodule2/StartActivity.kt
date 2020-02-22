package com.example.wsmodule2

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.example.wsmodule2.CallBacks.OnAuthGetListener
import com.example.wsmodule2.POJO.AccountData
import com.example.wsmodule2.POJO.CardData
import com.example.wsmodule2.POJO.CurrencyData
import com.example.wsmodule2.Utilities.GrandToster
import com.example.wsmodule2.tasks.GetValuteTask
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONArray
import org.xml.sax.InputSource
import sharp_like_view_event.OnClick
import sharp_like_view_event.plusAssign
import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis

class StartActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) ,OnAuthGetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val currentDate = findViewById<TextView>(R.id.start_date)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        currentDate.text = dateFormat.format(Calendar.getInstance().time)
        val usd = findViewById<TextView>(R.id.start_usd_text)
        val eur = findViewById<TextView>(R.id.start_eur_text)

        SetValutes()

        start_bankomats.OnClick += { v: View? ->
            val intent = Intent(this@StartActivity, BankomatsActivity::class.java)
            startActivity(intent)
        }

        start_currency.OnClick += { v: View? ->
            val intent = Intent(this@StartActivity, CurrenciesActivity::class.java)
            startActivity(intent)
        }

        start_button.OnClick += ::BuildDialog
    }

    fun SetValutes() = launch {
        val cbrCurrencyToValuesListTask = async{ GetCbrValutes()}
        val wsValutesTask = async{GetWsValutes()}

        val currencyList: List<CurrencyData> = CorrectValues(wsValutesTask.await(), cbrCurrencyToValuesListTask.await())


        runOnUiThread {
            for (currencyData in currencyList) {
                if (currencyData.char_code == "USD") {
                    start_usd_text.text = currencyData.value.toString()
                }
                if (currencyData.char_code == "EUR") {
                    start_eur_text.text = currencyData.value.toString()
                }
            }
        }
    }

    private fun CorrectValues(WSValute: List<CurrencyData>, currenciesAndValues: HashMap<String, Double>): List<CurrencyData> {

        for (currencyData in WSValute) {
            var valuteValCB = 0.0
            try {
                valuteValCB = currenciesAndValues[currencyData.char_code]!!
            } catch (e: NullPointerException) {
                Log.e("TASK", "where is no valute code in CB")
            }

            val valuteValWS = currencyData.value
            currencyData.isValueBiggerThenCB = valuteValWS > valuteValCB

            val valuteSellValWS = currencyData.value_sell
            currencyData.isValueBiggerThenCB = valuteSellValWS > valuteValCB
        }

        return WSValute
    }

    private fun GetCbrValutes() :HashMap<String, Double>{
        var client: OkHttpClient = OkHttpClient()
        var request: Request = Request.Builder()
                .url(BuildCbrValuteHttpUrl())
                .get()
                .build()

        var response : Response = client.newCall(request).execute()
        var xmlString: String = response.body!!.string()

        return ParseXml(xmlString)
    }

    private fun GetWsValutes() : MutableList<CurrencyData>{

        var client: OkHttpClient = OkHttpClient()
        var request: Request = Request.Builder()
                .url(BuildWsValuteHttpUrl())
                .get()
                .build()

        var response : Response = client.newCall(request).execute()
        var jsonString: String = response.body!!.string()

        return ParseJson(jsonString)
    }

    private fun ParseJson(jsonString: String): MutableList<CurrencyData>{
        var currencyDataList: MutableList<CurrencyData> = mutableListOf<CurrencyData>()

        var jsonArray = JSONArray(jsonString)

        val gson = Gson()
        for (i in 0 until jsonArray.length()) {
            val jsonString = jsonArray.getJSONObject(i).toString(0)
            val currencyData = gson.fromJson<CurrencyData>(jsonString, CurrencyData::class.java)
            currencyDataList.add(currencyData)
        }

        return currencyDataList
    }

    private fun ParseXml(xmlString: String): HashMap<String, Double>{

        val currenciesAndValues = HashMap<String, Double>()

        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()

        val `is` = InputSource(StringReader(xmlString))
        val doc = builder.parse(`is`)

        val list = doc.getElementsByTagName("Valute")
        for (i in 0 until list.length) {

            val node = list.item(i)
            val nodeList = node.childNodes
            val key = nodeList.item(1).firstChild.nodeValue

            //replacing ',' to '.' for parsing double
            val sb = StringBuilder().apply {
                append(nodeList.item(4).firstChild.nodeValue)
                replace(indexOf(","), indexOf(",") + 1, ".")
            }

            val value = java.lang.Double.parseDouble(sb.toString())
            currenciesAndValues[key] = value
        }

        return currenciesAndValues
    }

    fun BuildDialog(v: View){
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Dialog))
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.alert_signin, null))
                .setPositiveButton(R.string.signin, ::onPositiveButtonClick)
                .setNegativeButton(R.string.cancel) { diag: DialogInterface, id: Int -> diag.cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun BuildCbrValuteHttpUrl(): HttpUrl{
        var time = Calendar.getInstance().time
        val formating : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

        return HttpUrl.Builder()
                .scheme("http")
                .host("www.cbr.ru")
                .addPathSegment("scripts")
                .addPathSegment("XML_daily.asp")
                .addQueryParameter("date", formating.format(time))
                .build()
    }

    fun BuildWsValuteHttpUrl():HttpUrl{
        return HttpUrl.Builder()
                .scheme("http")
                .host(IP)
                .port(3000)
                .addPathSegment("valute")
                .build()
    }

     fun onPositiveButtonClick(dialog: DialogInterface, which: Int) {
        val compatDialog = dialog as AppCompatDialog

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
        @JvmField
        var accounts: ArrayList<AccountData> = ArrayList<AccountData>()
    }
}