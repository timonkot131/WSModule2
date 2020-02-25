package com

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Space

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

fun (((v: View) -> Unit)->Unit).sub(func: (v:View)->Unit){
    this(func)
}

fun ((v:View)->Unit).subTo(func: ((v: View) -> Unit)->Unit){
    func(this)
}
//fun ((v: View.OnClickListener)->Unit).sub(func: (v:View)->Unit){

//}