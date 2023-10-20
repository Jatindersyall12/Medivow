package com.app.treatEasy.feature.tutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.app.treatEasy.R;

import java.util.Objects;

public class TutorialViewPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater mLayoutInflater;
    private final int[] images = {R.mipmap.intro1, R.mipmap.intro4, R.mipmap.intro5};
    private final String[] imagesText = {"Now scan and pay your \u000BHospital Bills, Lab Test, Doctor fees \u000Band much more...",
            "Diagnostic Test", "Book your \u000BDoctor's Appointment"};


    public TutorialViewPagerAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_tutorial_slider, container, false);

        ((ImageView) itemView.findViewById(R.id.imageViewMain)).setImageResource(images[position]);
        ((TextView) itemView.findViewById(R.id.txt_message)).setText(imagesText[position]);

        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}