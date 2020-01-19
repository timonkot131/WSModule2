package com.example.wsmodule2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;

import com.example.wsmodule2.Adapters.CardViewPagerAdapter;

public class CardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        Button fill = findViewById(R.id.card_fill_button);
        Button transit = findViewById(R.id.card_transit_button);

        fill.setBackgroundColor(getResources().getColor(R.color.colorFill));
        transit.setBackgroundColor(getResources().getColor(R.color.colorTransit));

        int position = 0;

        Bundle args = getIntent().getExtras();

        if(args != null){
            position = args.getInt("card_list_index");
        }

        ViewPager pager = findViewById(R.id.card_viewpager);
        pager.setAdapter(new CardViewPagerAdapter(getApplicationContext(), StartActivity.cards));
        pager.setCurrentItem(position);

    }
}
