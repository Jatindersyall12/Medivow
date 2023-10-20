package com.app.treatEasy.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.MyViewHolder> {
    private Context mContext;

    List<RechargeHistoryRes.Datum> memberList;

    public RechargeHistoryAdapter(Context context,List<RechargeHistoryRes.Datum> memberList) {
        this.mContext = context;
        this.memberList = memberList;

        //  getMemberData(AppPreferences.getPreferenceInstance(mContext).getUserId());
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recharge_history, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_Title.setText(memberList.get(position).getRechargeId());
        holder.tvDiscFee.setText(memberList.get(position).getAmount());
        holder.tvDate.setText(memberList.get(position).getRechargeTime()+","+
                memberList.get(position).getRechargeDate());
    }
    @Override
    public int getItemCount() {
        return (memberList == null) ? 0 : memberList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_Title, tvDiscFee,tvDate;
        RelativeLayout rlNotification;
        LinearLayout llMain;
        MyViewHolder(View itemView) {
            super(itemView);
            txt_Title = itemView.findViewById(R.id.txt_Title);
            tvDiscFee = itemView.findViewById(R.id.tvDiscFee);
            tvDate = itemView.findViewById(R.id.tvDate);
            //   llMain = itemView.findViewById(R.id.llMain);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }
}