package com.app.treatEasy.doctorsDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;

import java.util.List;
import java.util.Objects;

public class ViewPagerDoctor extends PagerAdapter {

    private final Context context;
    private final LayoutInflater mLayoutInflater;
    private List<DoctorsDetailRes.Datum.Image> mData;


    public ViewPagerDoctor(Context context, List<DoctorsDetailRes.Datum.Image> mData) {
        this.context = context;
        this.mData = mData;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_slider_image, container, false);
        Objects.requireNonNull(container).addView(itemView);
        BaseUtils.setImage(mData.get(position).getClientImage(), itemView.findViewById(R.id.imageViewMain));
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
