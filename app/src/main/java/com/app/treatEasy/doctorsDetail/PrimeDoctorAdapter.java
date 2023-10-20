package com.app.treatEasy.doctorsDetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.appointment.AppointmentBookingActivity;
import com.app.treatEasy.feature.home_screen.DoctorAdapter;
import com.app.treatEasy.home.DoctorResponseModel;
import com.app.treatEasy.profile.doctorprofile.DoctorProfileActivity;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PrimeDoctorAdapter extends RecyclerView.Adapter<PrimeDoctorAdapter.MyViewHolder> {
    private Context mContext;
    private List<PrimeDoctorRes.Datum> mModelList;

    public PrimeDoctorAdapter(Context context,List<PrimeDoctorRes.Datum> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;
    }

    @NotNull

    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_prime_doctor, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //DoctorModel model = mModelList.get(position);
        //holder.mDoctorImage.setImageResource(mModelList.get(position).getProfileImage());
        holder.mDoctorName.setText(mModelList.get(position).getDoctorName());
        holder.txt_hospital.setText(mModelList.get(position).getHospitals());
        //holder.mDoctorName.setText(mModelList.get(position).getDoctorName());
        holder.ratingBar.setRating(Float.parseFloat(mModelList.get(position).getRating()));
        /*if (mModelList.get(position).getDescription().equals("")){
            holder.tv_description.setText(mModelList.get(position).getDescription());
        } */
        Glide.with(mContext)
                .load(mModelList.get(position).getProfileImage())
                .error(R.mipmap.medivow_logo)
                .into(holder.mDoctorImage);
        holder.mDoctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DoctorProfileActivity.class);
                intent.putExtra("doctor_id",mModelList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        holder.btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AppointmentBookingActivity.class);
                intent.putExtra("doctorId",mModelList.get(position).getId());
                intent.putExtra("doctorName",mModelList.get(position).getDoctorName());
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
        ImageView mDoctorImage;
        TextView mDoctorName,txt_hospital;
        RatingBar ratingBar;
        Button btnBookAppointment;
        MyViewHolder(View itemView){
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            mDoctorImage = itemView.findViewById(R.id.img_doctor);
            mDoctorName = itemView.findViewById(R.id.txt_doctor_name);
            txt_hospital = itemView.findViewById(R.id.txt_hospital);
            //txt_doctor_speciality = itemView.findViewById(R.id.txt_doctor_speciality);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            btnBookAppointment = itemView.findViewById(R.id.btnBookAppointment);

           // mMainLayout.setOnClickListener(this);
        }
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
              /*  case R.id.root_layout:
                    //  itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;*/
            }
        }
    }

   /* public void updateData(ArrayList<DoctorModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/
}