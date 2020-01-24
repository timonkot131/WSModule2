package com.example.wsmodule2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wsmodule2.Abstractions.ICardable;


public class AccountView extends RelativeLayout {

    private TextView accountType;
    private LinearLayout container;
    private ImageView imageView;
    private TextView accountCode;
    private TextView accountMoney;
    private ICardable account;


    public AccountView(Context context, ICardable account) {
        super(context);
        this.account = account;
        init();
    }

    public AccountView(Context context){
        super(context);
    }

    public AccountView(Context context, AttributeSet attributeSet){
        super(context);
    }


    public AccountView(Context context, AttributeSet attributeSet, int id){
        super(context);
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_account, this);

        accountType = findViewById(R.id.account_type);
        accountCode = findViewById(R.id.accout_code);
        accountMoney = findViewById(R.id.account_money);
        imageView = findViewById(R.id.account_image);

        Bitmap bitmap = account.getImage();
        if(bitmap!=null){
            LayoutParams params = new LayoutParams( 30, 30);
            imageView.setImageBitmap(bitmap);
        }

        accountType.setText(account.getType());
        accountMoney.setText(account.getMoney());
        accountCode.setText(account.getCode());
    }
}
