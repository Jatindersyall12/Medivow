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
import com.app.treatEasy.listeners.ItemClickListener;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Vinod Kumar.
 */
public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {
    private Context mContext;
    private final ItemClickListener itemClickListener;
    private List<DoctorResponseModel.Datum> mModelList = new ArrayList<>();

    public DoctorListAdapter(Context context, ItemClickListener itemClickListener,List<DoctorResponseModel.Datum> mModelList) {
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
       // DoctorModel model = mModelList.get(position);
       // holder.mDoctorImage.setImageResource(mModelList.get(position).getProfileImage());
        holder.mDoctorName.setText(mModelList.get(position).getDoctorName());
        holder.tv_description.setText(mModelList.get(position).getDescription());
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
        TextView mDoctorName,tv_description;

        MyViewHolder(View itemView) {
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            mDoctorImage = itemView.findViewById(R.id.img_doctor);
            mDoctorName = itemView.findViewById(R.id.txt_doctor_name);
            tv_description = itemView.findViewById(R.id.tv_description);
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

   /* public void updateData(ArrayList<DoctorModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/
}