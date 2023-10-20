package com.app.treatEasy.doctorsDetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoctorsDetailAdapter extends RecyclerView.Adapter<DoctorsDetailAdapter.MyViewHolder> {
    private Context mContext;
    private List<DoctorsDetailRes.Datum.Doctor> mModelList = new ArrayList<>();

    public DoctorsDetailAdapter(Context context,List<DoctorsDetailRes.Datum.Doctor> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;

    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doctor_detail, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // holder.tvAddress.setText(mModelList.get(position).getAddress());
        BaseUtils.setImage(mModelList.get(position).getProfileImage(), holder.mImage, R.mipmap.medivow_logo);
        holder.txt_doctor_name.setText(mModelList.get(position).getDoctorName());
        holder.txt_city_name.setText(mModelList.get(position).getSpecialities());
        holder.tvFee.setText(mModelList.get(position).getFee());
        holder.tvFee.setPaintFlags(holder.tvFee.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvDiscFee.setText(mModelList.get(position).getDiscountedFee());
    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mMainLayout;
        TextView txt_doctor_name, txt_city_name,tvFee,tvDiscFee;
        ImageView mImage;


        MyViewHolder(View itemView) {
            super(itemView);
            txt_doctor_name = itemView.findViewById(R.id.txt_doctor_name);
            txt_city_name = itemView.findViewById(R.id.txt_city_name);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvDiscFee = itemView.findViewById(R.id.tvDiscFee);
            mImage = itemView.findViewById(R.id.img_doctor);


        }
    }
}
