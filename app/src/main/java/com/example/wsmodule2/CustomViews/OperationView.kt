package com.example.wsmodule2.CustomViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.wsmodule2.CardOperation
import com.example.wsmodule2.R
import com.setContentView
import kotlinx.android.synthetic.main.view_operation.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class OperationView: RelativeLayout {

    constructor(context: Context):super(context)

    constructor(context: Context, operation: CardOperation):super(context){
        init(operation)
    }

    private fun init(operation: CardOperation){
        setContentView(R.layout.view_operation)

        var formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.CANADA)

        findViewById<TextView>(R.id.operationview_type).text = operation.type
        findViewById<TextView>(R.id.operationview_date).text = formatter.format(operation.date)
        findViewById<TextView>(R.id.operationview_money).text = operation.money.toString()
        
    }
}