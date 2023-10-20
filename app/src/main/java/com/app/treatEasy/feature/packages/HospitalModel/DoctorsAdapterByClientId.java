package com.app.treatEasy.feature.packages.HospitalModel;

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
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.home.DoctorResponseModel;
import com.app.treatEasy.listeners.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoctorsAdapterByClientId extends RecyclerView.Adapter<DoctorsAdapterByClientId.MyViewHolder> {
    private Context mContext;
    private final ItemClickListener itemClickListener;
    private List<DoctorResponseModel.Datum> mModelList = new ArrayList<>();

    public DoctorsAdapterByClientId(Context context, ItemClickListener itemClickListener,List<DoctorResponseModel.Datum> mModelList) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
        this.mModelList = mModelList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       // DoctorsByCategoryModel model = mModelList.get(position);

        holder.mPackageName.setText(mModelList.get(position).getDoctorName());
        BaseUtils.setImage(mModelList.get(position).getProfileImage(), holder.mImage, R.mipmap.medivow_logo);
        holder.mPackageDescription.setText(mModelList.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout;
        TextView mPackageName, mPackageDescription;
        ImageView mImage;

        MyViewHolder(View itemView) {
            super(itemView);
            mPackageName = itemView.findViewById(R.id.txt_doctor_name);
            mPackageDescription = itemView.findViewById(R.id.tv_description);
            mImage = itemView.findViewById(R.id.img_doctor);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            mMainLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }

   /* public void updateData(ArrayList<DoctorsByCategoryModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/
}