package com

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Space
import com.example.wsmodule2.R

fun RelativeLayout.setContentView(resId: Int){
    val inflater: LayoutInflater = LayoutInflater.from(context)
    inflater.inflate(resId,this)
}

fun LinearLayout.addSpace(height: Int){
    val spaceParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            height
    )

    val space = Space(context)
    space.layoutParams = spaceParams
    addView(space)
}