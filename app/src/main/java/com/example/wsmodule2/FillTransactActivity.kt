package com.example.wsmodule2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wsmodule2.Abstractions.ICardable
import com.example.wsmodule2.POJO.CardData
import com.example.wsmodule2.StartActivity.Companion.accounts
import com.example.wsmodule2.StartActivity.Companion.cards
import com.example.wsmodule2.Utilities.ChangerAlert
import kotlinx.android.synthetic.main.activity_fill_transact.*

class FillTransactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_transact)

        var payer: ICardable
        var getter: ICardable

        val args = intent.extras

        args?.let {

            if(it.getString("type") == "card"){
                payer = cards[it.getInt("payer")]
                getter = cards[it.getInt("getter")]
            }
            else{
                payer = accounts[it.getInt("payer")]
                getter = accounts[it.getInt("getter")]
            }

            payer_cardnum.text = payer.code
            payer_summ.text = payer.money.toString()

            getter_cardnum.text = getter.code
            getter_summ.text = getter.money.toString()
        }

        filltransact_pay.setOnClickListener{
            ChangerAlert.CreateAlert(this,"Подтверждение","","Пароль", "Подтвердить"){diag, id ->  }
        }
    }
}
