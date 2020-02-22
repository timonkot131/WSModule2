package com.example.wsmodule2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.addSpace
import com.example.wsmodule2.POJO.AccountData
import com.example.wsmodule2.POJO.CardData
import com.example.wsmodule2.StartActivity.Companion.accounts
import com.example.wsmodule2.StartActivity.Companion.cards
import com.setContentView
import kotlinx.android.synthetic.main.activity_fill.*
import kotlinx.android.synthetic.main.page_card.view.*
import kotlinx.android.synthetic.main.page_useraccount.view.*
import sharp_like_view_event.OnCheckChange
import sharp_like_view_event.OnClick
import sharp_like_view_event.plusAssign

class FillActivity : AppCompatActivity() {

    var fillId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill)

        val args: Bundle? = intent.extras

        args?.let{
            fillId = it.getInt("id")
        }
    }

    override fun onStart() {
        super.onStart()

        fill_account_toogle.setTextColor(resources.getColor(R.color.colorPrimary))
        fill_card_toogle.setTextColor(resources.getColor(R.color.colorWhite))

        fill_card_toogle.isChecked = true
        ShowCards()

        fill_buttons.OnClick += ::fill_card_click
        fill_card_toogle.OnCheckChange += ::fill_card_toogle_checkedChanged

    }

    override fun onStop() {
        super.onStop()
    }

    private fun fill_card_click(v: View){
        fill_container.removeAllViews()

        //reversing switch states
        fill_card_toogle.isChecked = !fill_card_toogle.isChecked
        fill_account_toogle.isChecked = !fill_account_toogle.isChecked
    }

    private fun fill_card_toogle_checkedChanged(compoundButton: CompoundButton, b: Boolean){
        if(b){
            fill_card_toogle.setTextColor(resources.getColor(R.color.colorWhite))
            fill_account_toogle.setTextColor(resources.getColor(R.color.colorPrimary))
            ShowCards()
        }
        else{
            fill_card_toogle.setTextColor(resources.getColor(R.color.colorPrimary))
            fill_account_toogle.setTextColor(resources.getColor(R.color.colorWhite))
            ShowAccounts()
        }
    }



    fun ShowCards(){
        cards.forEach{
            val card = RelativeLayout(this)

            card.setContentView(R.layout.page_card)
            card.page_card_code.text = it.code
            card.page_card_image.setImageBitmap(it.bitmap)
            card.page_card_money.text = it.money.toString()
            card.page_card_title.text = it.type

            val cardData: CardData = it
            card.OnClick += {
                val intent: Intent = Intent(this@FillActivity, FillTransactActivity::class.java)
                intent.putExtra("payer", cards.indexOf(cardData))
                intent.putExtra("getter", fillId)
                intent.putExtra("type", "card")
                startActivity(intent)
            }

            fill_container.addView(card)
            fill_container.addSpace(50)
        }
    }

    fun ShowAccounts(){
        accounts.forEach{
            val account = RelativeLayout(this)

            account.setContentView(R.layout.page_useraccount)
            account.page_useraccount_money.text = it.money.toString()
            account.page_useraccount_num.text = it.code
            account.page_useraccount_type.text = it.type

            val accountData: AccountData = it
            account.OnClick +={
                val intent: Intent = Intent(this@FillActivity, FillTransactActivity::class.java)
                intent.putExtra("payer", accounts.indexOf(accountData))
                intent.putExtra("getter", fillId)
                intent.putExtra("type", "account")
                startActivity(intent)
            }

            fill_container.addView(account)
            fill_container.addSpace(50)
        }
    }


}
