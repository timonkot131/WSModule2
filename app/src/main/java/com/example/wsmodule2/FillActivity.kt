package com.example.wsmodule2

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Space
import com.addSpace
import com.example.wsmodule2.StartActivity.Companion.accounts
import com.example.wsmodule2.StartActivity.Companion.cards
import com.setContentView
import kotlinx.android.synthetic.main.activity_fill.*
import kotlinx.android.synthetic.main.page_card.view.*
import kotlinx.android.synthetic.main.page_useraccount.view.*
import java.util.jar.Attributes

class FillActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill)

        fill_account_toogle.setTextColor(resources.getColor(R.color.toogled_text_color))
        fill_card_toogle.setTextColor(resources.getColor(R.color.toogled_text_color))

        fill_card_toogle.isChecked = true

        fill_card_toogle.setOnCheckedChangeListener{ compoundButton, b ->
            if(b){
                fill_account_toogle.isChecked = false
                showCards()
            }
        }

        fill_account_toogle.setOnCheckedChangeListener{ compoundButton, b ->
            if(b){
                fill_card_toogle.isChecked = false
                showAccounts()
            }
        }
    }

    fun showCards(){
        cards.forEach{
            val card = RelativeLayout(this)

            card.setContentView(R.layout.page_card)
            card.page_card_code.text = it.code
            card.page_card_image.setImageBitmap(it.bitmap)
            card.page_card_money.text = it.money.toString()
            card.page_card_title.text = it.type

            fill_container.addView(card)
            fill_container.addSpace(50)
        }
    }

    fun showAccounts(){
        accounts.forEach{
            val account = RelativeLayout(this)

            account.setContentView(R.layout.page_useraccount)
            account.page_useraccount_money.text = it.money.toString()
            account.page_useraccount_num.text = it.code
            account.page_useraccount_type.text = it.type

            fill_container.addView(account)
            fill_container.addSpace(50)
        }
    }
}
