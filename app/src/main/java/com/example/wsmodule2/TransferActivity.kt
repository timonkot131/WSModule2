package com.example.wsmodule2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.addSpace
import com.example.wsmodule2.POJO.CardData
import com.setContentView
import kotlinx.android.synthetic.main.activity_fill.*
import kotlinx.android.synthetic.main.page_card.view.*

class TransferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        var args = intent.extras

        args?.let{
            val fillId = it.getInt("id")
            ShowCards(fillId)
        }


    }

    fun ShowCards(id: Int){
        StartActivity.cards.forEach{
            val card = RelativeLayout(this)

            card.setContentView(R.layout.page_card)
            card.page_card_code.text = it.code
            card.page_card_image.setImageBitmap(it.bitmap)
            card.page_card_money.text = it.money.toString()
            card.page_card_title.text = it.type

            val cardData: CardData = it
            card.setOnClickListener{
                val intent: Intent = Intent(this@TransferActivity, FillTransactActivity::class.java)
                intent.putExtra("payer", StartActivity.cards.indexOf(cardData))
                intent.putExtra("getter", id)
                intent.putExtra("type", "card")
                startActivity(intent)
            }

            fill_container.addView(card)
            fill_container.addSpace(50)
        }
    }
}
