package com.example.wsmodule2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val userAgreementTextView = findViewById<TextView>(R.id.info_user_agreement)

        //делает нижнее подчеркивание
        var spannedString = SpannableString(userAgreementTextView.text)
        spannedString.setSpan(UnderlineSpan(), 25, 6, 0)
        userAgreementTextView.text = spannedString
    }
}