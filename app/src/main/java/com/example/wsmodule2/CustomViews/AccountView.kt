package com.example.wsmodule2.CustomViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.wsmodule2.Abstractions.ICardable
import com.example.wsmodule2.R
import com.setContentView
import kotlinx.android.synthetic.main.view_account.view.*

class AccountView : RelativeLayout {
    private lateinit var account: ICardable

    constructor(context: Context?, account: ICardable) : super(context) {
        this.account = account
        init()
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attributeSet: AttributeSet?) : super(context)
    constructor(context: Context?, attributeSet: AttributeSet?, id: Int) : super(context)

    private fun init() {
        setContentView(R.layout.view_account)

        if (account_image != null) {
            account_image.setImageBitmap(account.image)
        }

        account_type.text = account.type
        account_money.text = account.money
        accout_code.text = account.code
    }
}