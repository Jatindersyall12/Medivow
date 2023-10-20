package com.app.treatEasy.feature.home_screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.home.DoctorResponseModel;
import com.app.treatEasy.home.HomeDoctorAdapter;
import com.app.treatEasy.listeners.ItemClickListener;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {
    private Context mContext;
    private List<DoctorResponseModel.Datum> mModelList;

    public DoctorAdapter(Context context,List<DoctorResponseModel.Datum> mModelList) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // DoctorModel model = mModelList.get(position);
        // holder.mDoctorImage.setImageResource(mModelList.get(position).getProfileImage());
        holder.mDoctorName.setText(mModelList.get(position).getDoctorName());
        holder.txt_doctor_speciality.setText(mModelList.get(position).getSpecialities());
        holder.txt_hospital.setText(mModelList.get(position).getDepartment());
        holder.mDoctorName.setText(mModelList.get(position).getDoctorName());
       /* if (mModelList.get(position).getDescription().equals("")){
            holder.tv_description.setText(mModelList.get(position).getDescription());
        }*/
        Glide.with(mContext)
                .load(mModelList.get(position).getProfileImage())
                .error(R.mipmap.medivow_logo)
                .into(holder.mDoctorImage);
    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout;
        ImageView mDoctorImage;
        TextView mDoctorName,txt_hospital,txt_doctor_speciality;

        MyViewHolder(View itemView) {
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            mDoctorImage = itemView.findViewById(R.id.img_doctor);
            mDoctorName = itemView.findViewById(R.id.txt_doctor_name);
            txt_hospital = itemView.findViewById(R.id.txt_hospital);
            txt_doctor_speciality = itemView.findViewById(R.id.txt_doctor_speciality);
            mMainLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                  //  itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }

   /* public void updateData(ArrayList<DoctorModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/
}