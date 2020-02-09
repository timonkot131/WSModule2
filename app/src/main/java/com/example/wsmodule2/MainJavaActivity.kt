package com.example.wsmodule2

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import com.addSpace
import com.example.wsmodule2.CustomViews.AccountView
import com.example.wsmodule2.POJO.AccountData
import com.example.wsmodule2.POJO.CardData
import com.example.wsmodule2.POJO.CreditData
import com.example.wsmodule2.StartActivity.Companion.accounts
import com.example.wsmodule2.StartActivity.Companion.cards
import com.example.wsmodule2.tasks.DeleteLogoutTask

class MainJavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.main_logout).setOnClickListener { v: View? -> DeleteLogoutTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) }
        findViewById<View>(R.id.main_profile).setOnClickListener { v: View? -> startActivity(Intent(this@MainJavaActivity, ProfileActivity::class.java)) }
        val container = findViewById<LinearLayout>(R.id.main_container)
        val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                40
        )

        val bmp = BitmapFactory.decodeResource(resources, R.drawable.matercard_96)
        val spaceParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                10
        )

        val card_container = findViewById<LinearLayout>(R.id.main_cards_container)
        val accountContainer = findViewById<LinearLayout>(R.id.main_bills_container)
        val credit_container = findViewById<LinearLayout>(R.id.main_credits_container)

        //adding cards
        for (i in 0..2) {
            val cardData = CardData("22223144444", "Дебетовая карта $i", bmp, 33.3333)

            val accountView = AccountView(applicationContext,
                    cardData)
            accountView.setBackgroundColor(resources.getColor(R.color.colorWhiteSmoke))


            accountView.setOnClickListener { v: View? ->
                val intent = Intent(this@MainJavaActivity, CardsActivity::class.java)
                intent.putExtra("card_list_index", StartActivity.cards.indexOf(cardData))
                startActivity(intent)
            }

            card_container.addView(accountView)
            card_container.addSpace(10)

            cards.add(cardData)
        }

        //adding accounts
        for (i in 0..2) {
            val accountData = AccountData("22223144444", "Дебетовая карта", 33.3333)

            val accountView = AccountView(applicationContext,
                   accountData)
            accountView.setBackgroundColor(resources.getColor(R.color.colorWhiteSmoke))

            accountView.setOnClickListener { view ->
                val intent = Intent(this@MainJavaActivity, UserAccountsActivity::class.java)
                intent.putExtra("useraccount_list_index", accounts.indexOf(accountData))
                startActivity(intent)
            }

            accountContainer.addView(accountView)
            accountContainer.addSpace(10)

            accounts.add(accountData)
        }

        //adding credits
        for (i in 0..2) {
            val accountView = AccountView(applicationContext,
                    CreditData("Платеж аааа", "Дебетовая карта", 33.3333))
            accountView.setBackgroundColor(resources.getColor(R.color.colorWhiteSmoke))

            credit_container.addView(accountView)

            credit_container.addSpace(10)
        }

    }
}