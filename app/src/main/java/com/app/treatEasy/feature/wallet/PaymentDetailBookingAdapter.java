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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailBookingAdapter extends RecyclerView.Adapter<PaymentDetailBookingAdapter.MyViewHolder> {
    private Context mContext;
    //private List<PaymentDetailRes.Booking> mModelList = new ArrayList<>();
    ItemPaymentListenr listener;
    public PaymentDetailBookingAdapter(Context context, List<PaymentDetailRes> mModelList, ItemPaymentListenr listener) {
        this.mContext = context;
      //  this.mModelList = mModelList;
        this.listener = listener;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_booking_payment, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

       */
/* holder.txt_Title.setText(mModelList.get(position).getTitle());
        holder.tvDiscFee.setText(mModelList.get(position).getAmount());*//*


    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout,llName,llSpeciality;
        ImageView mDoctorImage;
        TextView txt_Title,tvDiscFee,txt_doctor_speciality,txt_hospital;

        MyViewHolder(View itemView) {
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            txt_Title = itemView.findViewById(R.id.txt_Title);
            tvDiscFee = itemView.findViewById(R.id.tvDiscFee);
            mMainLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                    listener.OnItemClick(v, getAdapterPosition(),"booking");
                    break;
            }
        }
    }

}
*/
