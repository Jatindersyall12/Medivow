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

import java.text.SimpleDateFormat;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.MyViewHolder> {
    private Context mContext;

    List<PaymentHistoryRes.Datum> memberList;

    public PaymentHistoryAdapter(Context context,List<PaymentHistoryRes.Datum> memberList) {
        this.mContext = context;
        this.memberList = memberList;
        //  getMemberData(AppPreferences.getPreferenceInstance(mContext).getUserId());
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_payment_history, parent, false);

        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_Title.setText( memberList.get(position).getClientName()+" for "+ memberList.get(position).getPaymentForDisplay());
        holder.tvDiscFee.setText(memberList.get(position).getAmount());
        String day[]= memberList.get(position).getTxnOn().split(",");
        String date=day[0];
        String time=day[1];
        String dayDate[]=date.split("-");
        holder.tvDay.setText(dayDate[0]);
        if (dayDate[1].equals("01")){
            holder.tvMonth.setText("Jan");
        }else if (dayDate[1].equals("02")){
            holder.tvMonth.setText("Feb");
        }else if (dayDate[1].equals("03")){
            holder.tvMonth.setText("March");
        }else if (dayDate[1].equals("04")){
            holder.tvMonth.setText("Apr");
        }else if (dayDate[1].equals("05")){
            holder.tvMonth.setText("May");
        }else if (dayDate[1].equals("06")){
            holder.tvMonth.setText("Jun");
        }else if (dayDate[1].equals("07")){
            holder.tvMonth.setText("Jul");
        }else if (dayDate[1].equals("08")){
            holder.tvMonth.setText("Aug");
        }else if (dayDate[1].equals("09")){
            holder.tvMonth.setText("Sep");
        }else if (dayDate[1].equals("10")){
            holder.tvMonth.setText("Oct");
        }else if (dayDate[1].equals("11")){
            holder.tvMonth.setText("Nov");
        }else if (dayDate[1].equals("12")){
            holder.tvMonth.setText("Dec");
        }
         holder.tvTime.setText(time);
       // holder.tvDate.setText(memberList.get(position).getTxnOn());
       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.LLL.yyyy");
       // String  dateTime = simpleDateFormat.format(memberList.get(position).getTxnOn()).toString();
       // holder.tvDate.setText(dateTime);
       // format4.setText(dateTime);
    }
    @Override
    public int getItemCount() {
        return (memberList == null) ? 0 : memberList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_Title, tvDiscFee,tvDate,tvTime,tvMonth,tvDay;
        RelativeLayout rlNotification;
        LinearLayout llMain;
        MyViewHolder(View itemView) {
            super(itemView);
            txt_Title = itemView.findViewById(R.id.txt_Title);
            tvDiscFee = itemView.findViewById(R.id.tvDiscFee);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvDay = itemView.findViewById(R.id.tvDay);
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