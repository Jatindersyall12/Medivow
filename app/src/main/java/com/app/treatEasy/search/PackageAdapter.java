package com.app.treatEasy.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.new_feature.doctors.DoctorDeatil;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyViewHolder> {
    private Context mContext;
    private List<SerachResponse.Datum> mModelList = new ArrayList<>();

    public PackageAdapter(Context context,List<SerachResponse.Datum> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doctor_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // DoctorModel model = mModelList.get(position);
        // holder.mDoctorImage.setImageResource(mModelList.get(position).getProfileImage());
        holder.mDoctorName.setText(mModelList.get(position).getDisplayName());
        holder.llName.setVisibility(View.GONE);
        holder.llSpeciality.setVisibility(View.GONE);
        if (!mModelList.get(position).getDescription().equals("")){
            holder.tv_description.setVisibility(View.VISIBLE);
            holder.tv_description.setText(mModelList.get(position).getDescription());
        }
        Glide.with(mContext)
                .load(mModelList.get(position).getImage())
                .error(R.mipmap.medivow_logo)
                .into(holder.mDoctorImage);

        holder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DoctorDeatil.class);
                intent.putExtra("id",mModelList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout,llName,llSpeciality;
        ImageView mDoctorImage;
        TextView mDoctorName,tv_description,txt_doctor_speciality,txt_hospital;

        MyViewHolder(View itemView) {
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            llSpeciality = itemView.findViewById(R.id.llSpeciality);
            mDoctorImage = itemView.findViewById(R.id.img_doctor);
            mDoctorName = itemView.findViewById(R.id.txt_doctor_name);
            tv_description = itemView.findViewById(R.id.tv_description);
           // llName = itemView.findViewById(R.id.llName);
            txt_doctor_speciality = itemView.findViewById(R.id.txt_doctor_speciality);
            txt_hospital = itemView.findViewById(R.id.txt_hospital);
            mMainLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                   // itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }

   /* public void updateData(ArrayList<DoctorModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/
}