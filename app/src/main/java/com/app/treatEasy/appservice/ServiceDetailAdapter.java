package com.app.treatEasy.appservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailAdapter extends RecyclerView.Adapter<ServiceDetailAdapter.MyViewHolder> {
    private Context mContext;
    private List<ServiceDetailRes.PriceDetail> mModelList = new ArrayList<>();

    public ServiceDetailAdapter(Context context,List<ServiceDetailRes.PriceDetail> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;

    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_service_detail, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_city_name.setText(mModelList.get(position).getCityName());
        holder.tvFee.setText(mModelList.get(position).getPrice());
        holder.tvFee.setPaintFlags(holder.tvFee.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvDiscFee.setText(mModelList.get(position).getDiscountPrice());
    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_city_name,tvFee,tvDiscFee;

        MyViewHolder(View itemView) {
            super(itemView);
            txt_city_name = itemView.findViewById(R.id.txt_city_name);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvDiscFee = itemView.findViewById(R.id.tvDiscFee);
        }
    }
}
