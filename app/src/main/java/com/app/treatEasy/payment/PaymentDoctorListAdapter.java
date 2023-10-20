package com.app.treatEasy.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.listeners.ItemClickListener;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaymentDoctorListAdapter extends RecyclerView.Adapter<PaymentDoctorListAdapter.MyViewHolder> {
    private Context mContext;

    List<PaymentDoctorRes.Datum> memberList;
    private final ItemClickListener itemClickListener;
    public PaymentDoctorListAdapter(Context context,List<PaymentDoctorRes.Datum> memberList,
                                    ItemClickListener itemClickListener) {
        this.mContext = context;
        this.memberList = memberList;
        this.itemClickListener = itemClickListener;
        //  getMemberData(AppPreferences.getPreferenceInstance(mContext).getUserId());
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_payment_doctor, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvDoctorName.setText(memberList.get(position).getDoctorName());
        holder.tvDoctorSpecialityName.setText(memberList.get(position).getSpecialities());

       /* holder.rlDoctorFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.OnItemClick(v, position);
            }
        });*/

        Glide.with(mContext)
                .load(memberList.get(position).getProfileImage())
                .error(R.mipmap.medivow_logo)
                .into(holder.imgDoctorImage);
    }
    @Override
    public int getItemCount() {
        return (memberList == null) ? 0 : memberList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDoctorName, tvDoctorSpecialityName,tvDate;
        RelativeLayout rlDoctorFee;
        LinearLayout llMain;
        ImageView imgDoctorImage;
        MyViewHolder(View itemView) {
            super(itemView);
            imgDoctorImage = itemView.findViewById(R.id.imgDoctorImage);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            tvDoctorSpecialityName = itemView.findViewById(R.id.tvDoctorSpecialityName);
            rlDoctorFee = itemView.findViewById(R.id.rlDoctorFee);
            //   llMain = itemView.findViewById(R.id.llMain);

            rlDoctorFee.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.rlDoctorFee:
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;

            }
        }
    }
}
