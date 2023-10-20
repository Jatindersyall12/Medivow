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
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.doctorscat.SurgeryDetailsDoctorsAdapter;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryModel;
import com.app.treatEasy.feature.packages.package_detail.DescriptionActivity;
import com.app.treatEasy.listeners.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchHospitalAdapter extends RecyclerView.Adapter<SearchHospitalAdapter.MyViewHolder> {
    private Context mContext;

    private List<SearchHospitalRes.Datum> mModelList;

    public SearchHospitalAdapter(Context context,List<SearchHospitalRes.Datum> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_hospital, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mPackageName.setText(mModelList.get(position).getDisplayName());
        BaseUtils.setImage(mModelList.get(position).getImage(), holder.mImage, R.mipmap.medivow_logo);
        holder.mPackageDescription.setText(mModelList.get(position).getDescription());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("id",mModelList.get(position).getId());
                intent.putExtra("title",mModelList.get(position).getDisplayName());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout llMain;
        TextView mPackageName, mPackageDescription;
        ImageView mImage;

        MyViewHolder(View itemView) {
            super(itemView);
            mPackageName = itemView.findViewById(R.id.txt_hospital_name);
            mPackageDescription = itemView.findViewById(R.id.tv_description);
            mImage = itemView.findViewById(R.id.img_doctor);
            llMain = itemView.findViewById(R.id.llMain);
          //  mMainLayout.setOnClickListener(this);
        }
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
              /*  case R.id.root_layout:
                    //itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;*/
            }
        }
    }
}

