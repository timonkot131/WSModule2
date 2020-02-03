package com.example.wsmodule2.CustomViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.wsmodule2.R
import com.example.wsmodule2.Visit
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Attributes
import java.util.zip.Inflater

class VisitView: RelativeLayout {

    constructor(context: Context):super(context)

    constructor(context: Context, visit: Visit):super(context){
        init(context, visit)
    }

    constructor(context: Context, attributes: AttributeSet):super(context, attributes)

    constructor(context: Context, attributes: AttributeSet, id: Int):super(context, attributes, id)

    private fun init(context: Context, visit: Visit){

        var viewDate = findViewById<TextView>(R.id.visitview_date)
        var viewTime = findViewById<TextView>(R.id.visitview_time)

        var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_visit, this)
        var dateFormatting : DateFormat = SimpleDateFormat("dd.MM.yyyy")
        var timeFormatting : DateFormat = SimpleDateFormat("hh:mm")

        viewDate.text = dateFormatting.format(visit.date)
        viewTime.text = timeFormatting.format(visit.date)

    }
}