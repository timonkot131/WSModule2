package com.example.wsmodule2

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

import android.os.Bundle
import android.widget.Button

import com.example.wsmodule2.Adapters.CardViewPagerAdapter
import com.example.wsmodule2.Utilities.ChangerAlert

class CardsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        val fill = findViewById<Button>(R.id.card_fill_button)
        val transit = findViewById<Button>(R.id.card_transit_button)

        fill.setBackgroundColor(resources.getColor(R.color.colorFill))
        transit.setBackgroundColor(resources.getColor(R.color.colorTransit))

        var position = 0

        val args = intent.extras

        if (args != null) {
            position = args.getInt("card_list_index")
        }

        val pager = findViewById<ViewPager>(R.id.card_viewpager)
        pager.adapter = CardViewPagerAdapter(applicationContext, StartActivity.cards)
        pager.currentItem = position

        findViewById<Button>(R.id.cards_button2).setOnClickListener {
            ChangerAlert.CreateAlert(this,"Блокировка карты","Вы уверены что хотите заблокировать карту?", "Пароль", "Заблокировать") {
                diag, id ->
            }
        }

        findViewById<Button>(R.id.cards_button3).setOnClickListener {
            ChangerAlert.CreateAlert(this,"Переименование карты","Введите новое название", "Имя" ) {
                diag, id ->
            }
        }

    }
}
