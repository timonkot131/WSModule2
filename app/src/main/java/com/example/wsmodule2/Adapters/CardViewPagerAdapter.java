package com.example.wsmodule2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.wsmodule2.POJO.CardData;
import com.example.wsmodule2.R;

import java.util.List;

public class CardViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<CardData> cards;

    public CardViewPagerAdapter(Context context, List<CardData> cards){
        this.context = context;
        this.cards = cards;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardData card = cards.get(position);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.page_card, container, false);
        TextView type = layout.findViewById(R.id.page_card_title);
        TextView code = layout.findViewById(R.id.page_card_code);
        TextView money = layout.findViewById(R.id.page_card_money);
        ImageView imageView = layout.findViewById(R.id.page_card_image);

        type.setText(card.type);
        code.setText(card.getCode());
        money.setText(card.getMoney());
        imageView.setImageBitmap(card.bitmap);
        container.addView(layout);
        return layout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return cards.size();
    }
}
