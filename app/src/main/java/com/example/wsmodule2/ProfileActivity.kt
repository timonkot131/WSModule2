package com.example.wsmodule2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.wsmodule2.Utilities.ChangerAlert

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<Button>(R.id.profile_button4).setOnClickListener {v -> startActivity(Intent(this@ProfileActivity, InfoActivity::class.java))}

        findViewById<Button>(R.id.profile_button).setOnClickListener {v->
            ChangerAlert.CreateAlert(this, "Изменение пароля", "Введите новый пароль", "Пароль") {diag, id ->  } }

        findViewById<Button>(R.id.profile_button2).setOnClickListener {v->
            ChangerAlert.CreateAlert(this, "Изменение логина", "Введите новый логин", "Логин") {diag, id ->  } }
    }
}
