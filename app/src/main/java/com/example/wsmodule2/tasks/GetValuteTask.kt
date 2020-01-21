package com.example.wsmodule2.tasks

import android.os.AsyncTask
import android.util.Log

import com.example.wsmodule2.CallBacks.OnCurrencyGetListener
import com.example.wsmodule2.POJO.CurrencyData
import com.example.wsmodule2.StartActivity
import com.google.gson.Gson
import com.example.wsmodule2.StartActivity.Companion.IP
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils

import org.json.JSONArray
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.HashMap

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class GetValuteTask(currentDate: Date) : AsyncTask<(List<CurrencyData>) -> Unit, Void, List<CurrencyData>>() {


    var date: String
    var listener = {currencies: List<CurrencyData> -> Unit}
  //  var listene1 : (List<CurrencyData>) -> Unit //а так по какой-то причине нельзя

    init {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        date = simpleDateFormat.format(currentDate)

    }

    override fun doInBackground(vararg onCurrencyGetListeners: (List<CurrencyData>) -> Unit): List<CurrencyData> {
        listener = onCurrencyGetListeners[0]
        val currenciesAndValues = HashMap<String, Double>()
        val currencyDataList = ArrayList<CurrencyData>()
        try {

            //getting values from cb
            val httpClient = DefaultHttpClient()
            val httpGet = HttpGet("http://www.cbr.ru/scripts/XML_daily.asp?date_req=$date")
            val response: HttpResponse = httpClient.execute(httpGet)
            val xml = EntityUtils.toString(response.getEntity())

            val factory = DocumentBuilderFactory.newInstance()
            val builder: DocumentBuilder
            val `is`: InputSource
            builder = factory.newDocumentBuilder()
            `is` = InputSource(StringReader(xml))
            val doc = builder.parse(`is`)
            val list = doc.getElementsByTagName("Valute")
            for (i in 0 until list.length) {
                val node = list.item(i)
                val nodeList = node.childNodes
                val key = nodeList.item(1).firstChild.nodeValue

                //replacing ',' to '.' for parsing double
                val sb = StringBuilder()
                sb.append(nodeList.item(4).firstChild.nodeValue)
                var index = sb.indexOf(",")
                sb.replace(index++, index, ".")

                val value = java.lang.Double.parseDouble(sb.toString())
                currenciesAndValues[key] = value
            }

            //getting values from WS
            val httpClient2 = DefaultHttpClient()
            val httpGet2 = HttpGet("http://$IP:3000/valute")
            val response2 = httpClient2.execute(httpGet2)
            val jsonArray = JSONArray(EntityUtils.toString(response2.getEntity()))

            val gson = Gson()
            for (i in 0 until jsonArray.length()) {
                val jsonString = jsonArray.getJSONObject(i).toString(0)
                val currencyData = gson.fromJson<CurrencyData>(jsonString, CurrencyData::class.java)
                currencyDataList.add(currencyData)
            }

        } catch (e: Exception) {
            Log.e("TASK", e.toString())
        }

        return CorrectValues(currencyDataList, currenciesAndValues)
    }

    //determine bigger or smaller selling and buying values
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

    override fun onPostExecute(currencyDataList: List<CurrencyData>) {
        super.onPostExecute(currencyDataList)
        listener.invoke(currencyDataList)
    }
}
