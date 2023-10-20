/*
package com.app.treatEasy.feature.wallet;

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
import com.app.treatEasy.listeners.ItemPaymentListenr;
import com.app.treatEasy.payment.PaymentDetailRes;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailAdapter extends RecyclerView.Adapter<PaymentDetailAdapter.MyViewHolder> {
    private Context mContext;
   // private List<PaymentDetailRes.Doctor> mModelList = new ArrayList<>();
    ItemPaymentListenr listener;
    */
/* public PaymentDetailAdapter(Context context,List<PaymentDetailRes.Doctor> mModelList,ItemPaymentListenr listener) {
        this.mContext = context;
     //   this.mModelList = mModelList;
        this.listener = listener;
    }*//*


    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_payment_detail, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // DoctorModel model = mModelList.get(position);
        // holder.mDoctorImage.setImageResource(mModelList.get(position).getProfileImage());
       */
/* holder.mDoctorName.setText(mModelList.get(position).getDoctorName());
        holder.txt_doctor_speciality.setText(mModelList.get(position).getSpecialities());
        holder.tvDiscFee.setText(mModelList.get(position).getDiscountedFee());

        Glide.with(mContext)
                .load(mModelList.get(position).getProfileImage())
                .error(R.mipmap.medivow_logo)
                .into(holder.mDoctorImage);*//*


       */
/* holder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("id",mModelList.get(position).getId());
                intent.putExtra("title",mModelList.get(position).getDisplayName());
                mContext.startActivity(intent);
            }
        });*//*

    }
    @Override
    public int getItemCount() {
       // return (mModelList == null) ? 0 : mModelList.size();
    //}
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout,llName,llSpeciality;
        ImageView mDoctorImage;
        TextView mDoctorName,tvDiscFee,txt_doctor_speciality,txt_hospital;

        MyViewHolder(View itemView) {
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
          //  llSpeciality = itemView.findViewById(R.id.llSpeciality);
            mDoctorImage = itemView.findViewById(R.id.img_doctor);
            mDoctorName = itemView.findViewById(R.id.txt_doctor_name);
            tvDiscFee = itemView.findViewById(R.id.tvDiscFee);
           // tv_description = itemView.findViewById(R.id.tv_description);
           // llName = itemView.findViewById(R.id.llName);
            txt_doctor_speciality = itemView.findViewById(R.id.txt_doctor_speciality);
         //   txt_hospital = itemView.findViewById(R.id.txt_hospital);
            mMainLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                     listener.OnItemClick(v, getAdapterPosition(),"opd");
                    break;
            }
        }
    }

}*/
