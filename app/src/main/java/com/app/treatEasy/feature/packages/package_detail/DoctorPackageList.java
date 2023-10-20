package com.app.treatEasy.feature.packages.package_detail;

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
import com.app.treatEasy.feature.packages.PackageDeatil;
import com.app.treatEasy.new_feature.doctors.DoctorDeatil;
import com.app.treatEasy.profile.doctorprofile.DoctorProfileActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoctorPackageList extends RecyclerView.Adapter<DoctorPackageList.MyViewHolder> {
private Context mContext;
private List<PackageDeatil.Doctor> mModelList = new ArrayList<>();
private String imageUrl;
public DoctorPackageList(Context context,List<PackageDeatil.Doctor> mModelList,String imageUrl) {
        this.mContext = context;
        this.mModelList = mModelList;
        this.imageUrl = imageUrl;
        }
@NotNull
@Override
public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_package_doctor, parent, false);
        return new MyViewHolder(v);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //  DoctorsByCategoryModel model = mModelList.get(position);
        holder.mPackageName.setText(mModelList.get(position).getDoctorName());

    holder.mPackageName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           /* Intent intent=new Intent(mContext, DoctorDeatil.class);
            intent.putExtra("id",mModelList.get(position).getId());
            mContext.startActivity(intent);*/

            Intent intent=new Intent(mContext, DoctorProfileActivity.class);
            intent.putExtra("name",mModelList.get(position).getDoctorName());
            intent.putExtra("imageUrl",imageUrl);
            intent.putExtra("doctor_id",mModelList.get(position).getId());
            mContext.startActivity(intent);

        }
    });

   /* holder.imgDoctor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(mContext, DoctorProfileActivity.class);

            intent.putExtra("name",mModelList.get(position).getDoctorName());
            intent.putExtra("imageUrl",imageUrl);
            intent.putExtra("doctorId",mModelList.get(position).getId());
            mContext.startActivity(intent);
        }
    });*/
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
