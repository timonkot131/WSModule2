package com.example.wsmodule2.Utilities

import android.content.Context
import android.content.DialogInterface
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.example.wsmodule2.R

object ChangerAlert {
    fun CreateAlert(context: Context,
                    title1: String,
                    title2: String,
                    defaultText: String,
                    positiveText: String = "Изменить",
                    negativeText: String = "Отмена",
                    listener: (diag: DialogInterface, id: Int) -> Unit) {

        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Dialog))
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        builder.setView(inflater.inflate(R.layout.alert_change, null))
                .setPositiveButton(positiveText, listener)
                .setNegativeButton(negativeText) { diag: DialogInterface, id: Int -> diag.cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        val compatDialog = dialog as AppCompatDialog
        compatDialog.findViewById<TextView>(R.id.diag_title1)!!.text = title1
        compatDialog.findViewById<TextView>(R.id.diag_title2)!!.text = title2
        compatDialog.findViewById<TextView>(R.id.diag_msg)!!.text = defaultText
    }
}