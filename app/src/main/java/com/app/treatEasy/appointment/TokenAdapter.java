package com.app.treatEasy.appointment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.notification.NotificationAdapter;
import com.app.treatEasy.notification.NotificationDetailRes;
import com.app.treatEasy.notification.NotificationRes;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.MyViewHolder> {
    private Context mContext;

    List<TokenModel.Datum> tokenList;
    int selectedPosition = -1;

    public TokenAdapter(Context context, List<TokenModel.Datum> tokenList) {
        this.mContext = context;
        this.tokenList = tokenList;

    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_token, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvToken.setText("" + tokenList.get(position).getTokenNo());

        if (tokenList.get(position).getIsAvailable() == 0) {
            holder.tvToken.setEnabled(false);
            holder.tvToken.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_disabled_btn));
           // holder.tvToken.setAlpha(.2f);
        } else {
            holder.tvToken.setEnabled(true);
            holder.tvToken.setBackgroundResource(R.drawable.bg_cancel_btn);
        }

        if (tokenList.get(position).getSelectedStatus()) {
            holder.tvToken.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_green));
        } /*else {
            holder.tvToken.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_cancel_btn));
        }*/

        holder.tvToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                for (int i = 0; i < tokenList.size(); i++) {
                    if (selectedPosition == i) {
                        tokenList.get(i).setSelectedStatus(true);
                    } else {
                        tokenList.get(i).setSelectedStatus(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (tokenList == null) ? 0 : tokenList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvToken;
        RelativeLayout rlNotification;
        LinearLayout llMain;
        ImageView imgNotification;

        MyViewHolder(View itemView) {
            super(itemView);
            tvToken = itemView.findViewById(R.id.tvToken);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}