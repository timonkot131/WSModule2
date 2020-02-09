package com.example.wsmodule2.CustomViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.wsmodule2.R
import com.example.wsmodule2.Visit
import com.setContentView
import kotlinx.android.synthetic.main.view_visit.view.*
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

        setContentView(R.layout.view_visit)

        var dateFormatting : DateFormat = SimpleDateFormat("dd.MM.yyyy")
        var timeFormatting : DateFormat = SimpleDateFormat("hh:mm")

        visitview_date.text = dateFormatting.format(visit.date)
        visitview_date.text = timeFormatting.format(visit.date)

    }
}