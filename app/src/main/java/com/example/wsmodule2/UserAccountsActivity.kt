package com.example.wsmodule2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.example.wsmodule2.Adapters.AccountPagerAdapter
import com.example.wsmodule2.Adapters.CardViewPagerAdapter
import com.example.wsmodule2.StartActivity.Companion.accounts
import com.example.wsmodule2.Utilities.ChangerAlert

class UserAccountsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)

        var position = 0

        val args = intent.extras

        if (args != null) {
            position = args.getInt("useraccount_list_index")
        }

        val pager = findViewById<ViewPager>(R.id.userAccount_viewpager)
        pager.adapter = AccountPagerAdapter(applicationContext, accounts )
        pager.currentItem = position

        findViewById<Button>(R.id.useraccount_button1).setOnClickListener{v ->
            startActivity(Intent(this@UserAccountsActivity,  OperationsActivity::class.java))
        }

        findViewById<Button>(R.id.useraccount_button2).setOnClickListener { v ->
            ChangerAlert.CreateAlert(this, "Переименовать счет", "Введите новое название", "Имя") {diag, id ->  } }
    }
}
