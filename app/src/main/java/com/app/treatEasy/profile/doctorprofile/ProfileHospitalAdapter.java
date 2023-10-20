package com.app.treatEasy.profile.doctorprofile;

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
import com.app.treatEasy.feature.packages.HospitalListActivity;
import com.app.treatEasy.feature.packages.PackageDeatil;
import com.app.treatEasy.feature.packages.package_detail.DescriptionActivity;
import com.app.treatEasy.feature.packages.package_detail.DoctorPackageList;
import com.app.treatEasy.feature.packages.package_detail.PackageDetailActivity;
import com.app.treatEasy.new_feature.doctors.DoctorDeatil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileHospitalAdapter extends RecyclerView.Adapter<ProfileHospitalAdapter.MyViewHolder> {
    private Context mContext;
    private List<DoctorProfileRes.Hospital> mModelList = new ArrayList<>();

    public ProfileHospitalAdapter(Context context,List<DoctorProfileRes.Hospital> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doctor_profile, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //  DoctorsByCategoryModel model = mModelList.get(position);
        holder.mPackageName.setText(mModelList.get(position).getClientName());

        holder.mPackageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("id",mModelList.get(position).getClientId());
                intent.putExtra("title",mModelList.get(position).getClientName());
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout;
        TextView mPackageName;
        ImageView mImage,imgDoctor;

        MyViewHolder(View itemView) {
            super(itemView);
            mPackageName = itemView.findViewById(R.id.txt_doctor_name);
            imgDoctor = itemView.findViewById(R.id.imgDoctor);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                    //itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }

   /* public void updateData(ArrayList<DoctorsByCategoryModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/
}

