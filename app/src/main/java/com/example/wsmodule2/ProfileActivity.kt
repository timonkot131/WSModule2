package com.example.wsmodule2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<Button>(R.id.profile_button4).setOnClickListener {v -> startActivity(Intent(this@ProfileActivity, InfoActivity::class.java))}

    }
}
