package com.app.treatEasy.search;

import static com.app.treatEasy.apputils.AppConstants.KEY_BUNDLE_DATA;
import static com.app.treatEasy.apputils.AppConstants.PACKAGE_Cat_ID;
import static com.app.treatEasy.apputils.AppConstants.PACKAGE_ID;
import static com.app.treatEasy.apputils.AppConstants.PACKAGE_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.feature.packages.HospitalListActivity;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryAdapter;
import com.app.treatEasy.feature.packages.package_detail.PackageCatDetail;
import com.app.treatEasy.listeners.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchPackageAdapter extends RecyclerView.Adapter<SearchPackageAdapter.MyViewHolder> {
    private Context mContext;
    private List<SearchResModel.Datum> mModelList = new ArrayList<>();

    public SearchPackageAdapter(Context context,List<SearchResModel.Datum> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_package, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //DoctorsByCategoryModel model = mModelList.get(position);
        holder.mPackageName.setText(mModelList.get(position).getDisplayName());
        BaseUtils.setImage(mModelList.get(position).getImage(), holder.mImage, R.mipmap.medivow_logo);
        holder.mPackageDescription.setText(mModelList.get(position).getDescription());

        holder.btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PACKAGE_ID,mModelList.get(position).getId());
                bundle.putString(PACKAGE_NAME, mModelList.get(position).getDisplayName());
                bundle.putString(PACKAGE_Cat_ID, mModelList.get(position).getId());
                //  switchActivity(DoctorsByCategoryActivity.class, bundle);   // call after hospital list
                Intent intent = new Intent(mContext, HospitalListActivity.class);
                intent.putExtra(KEY_BUNDLE_DATA, bundle);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView mMainLayout;
        TextView mPackageName, mPackageDescription;
        ImageView mImage;
        Button btnBookAppointment;
        MyViewHolder(View itemView) {
            super(itemView);
            mPackageName = itemView.findViewById(R.id.txt_doctor_name);
            mPackageDescription = itemView.findViewById(R.id.tv_description);
            mImage = itemView.findViewById(R.id.img_doctor);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            btnBookAppointment = itemView.findViewById(R.id.btnBookAppointment);

        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }

   /* public void updateData(ArrayList<DoctorsByCategoryModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/
}