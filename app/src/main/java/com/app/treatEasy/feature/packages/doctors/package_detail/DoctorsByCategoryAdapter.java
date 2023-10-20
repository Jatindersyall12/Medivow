package com.app.treatEasy.feature.packages.doctors.package_detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.feature.packages.package_detail.PackageCatDetail;
import com.app.treatEasy.listeners.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinod Kumar.
 */
public class DoctorsByCategoryAdapter extends RecyclerView.Adapter<DoctorsByCategoryAdapter.MyViewHolder> {
    private Context mContext;
    private final ItemClickListener itemClickListener;
    private List<PackageCatDetail.Datum> mModelList = new ArrayList<>();

    public DoctorsByCategoryAdapter(Context context, ItemClickListener itemClickListener,List<PackageCatDetail.Datum> mModelList) {
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
      //  DoctorsByCategoryModel model = mModelList.get(position);

        holder.mPackageName.setText(mModelList.get(position).getTitle());
        BaseUtils.setImage(mModelList.get(position).getImage(), holder.mImage, R.mipmap.medivow_logo);
        holder.mPackageDescription.setText(mModelList.get(position).getDescription());
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
            mMainLayout.setOnClickListener(this);
            btnBookAppointment.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
                case R.id.btnBookAppointment:
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