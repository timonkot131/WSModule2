package com.example.wsmodule2.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.wsmodule2.POJO.AccountData
import com.example.wsmodule2.R
import kotlinx.android.synthetic.main.page_useraccount.view.*

class AccountPagerAdapter: PagerAdapter {
    var context: Context
    var accounts: MutableList<AccountData>

    constructor(context: Context, accounts: MutableList<AccountData>){
        this.context = context
        this.accounts = accounts
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val layout: ViewGroup = inflater.inflate(R.layout.page_useraccount, container, false) as ViewGroup

        val accountData: AccountData = accounts[position]

        layout.page_useraccount_num.text = accountData.code
        layout.page_useraccount_money.text = accountData.money.toString()
        layout.page_useraccount_type.text = accountData.type

        container.addView(layout)
        return layout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
      return accounts.count()
    }
}