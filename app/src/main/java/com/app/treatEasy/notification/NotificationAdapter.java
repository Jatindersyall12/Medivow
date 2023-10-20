package com.app.treatEasy.notification;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context mContext;

    List<NotificationRes.Datum> memberList;

    public NotificationAdapter(Context context,List<NotificationRes.Datum> memberList) {
        this.mContext = context;
        this.memberList = memberList;

        //  getMemberData(AppPreferences.getPreferenceInstance(mContext).getUserId());
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notification, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_Hospital_name.setText(memberList.get(position).getTitle());
        holder.tv_description.setText(memberList.get(position).getDescription());
        holder.tv_time.setText(memberList.get(position).getSentOn());

        if (memberList.get(position).getIsRead().equals("0")){
            holder.rlNotification.setVisibility(View.VISIBLE);
        }else {
            holder.rlNotification.setVisibility(View.GONE);
        }
  if (!memberList.get(position).getImage().equals("")){
      BaseUtils.setImage(memberList.get(position).getImage(), holder.imgNotification);
  }
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotification(memberList.get(position).getUserId(),memberList.get(position).getId());
            }
        });

    }
    @Override
    public int getItemCount() {
        return (memberList == null) ? 0 : memberList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_Hospital_name, tv_description,tv_time;
        RelativeLayout rlNotification;
        LinearLayout llMain;
        ImageView imgNotification;
        MyViewHolder(View itemView) {
            super(itemView);
            txt_Hospital_name = itemView.findViewById(R.id.txt_Hospital_name);
            tv_description = itemView.findViewById(R.id.tv_description);
            rlNotification = itemView.findViewById(R.id.rlNotification);
            imgNotification = itemView.findViewById(R.id.imgNotification);
            tv_time = itemView.findViewById(R.id.tv_time);
            llMain = itemView.findViewById(R.id.llMain);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }
    public void getNotification(String userId,String id){

        Call<NotificationDetailRes> call = RetrofitClient.getInstance().getMyApi().getNotificationDetail(userId,id);
        call.enqueue(new Callback<NotificationDetailRes>(){
            @Override
            public void onResponse(Call<NotificationDetailRes> call, Response<NotificationDetailRes> response) {
                if (response.body().getStatusCode()==200){
                    showNotificationDialog(response.body().getData().getTitle(),
                            response.body().getData().getDescription());
                }
            }
            @Override
            public void onFailure(Call<NotificationDetailRes> call, Throwable t) {
               // dismissProgressDialog();
                Toast.makeText(mContext, "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showNotificationDialog(String name,String discription){
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_notification_detail);

        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        TextView txt_Hospital_name =dialog.findViewById(R.id.txt_Hospital_name);
        TextView tv_description =dialog.findViewById(R.id.tv_description);

        txt_Hospital_name.setText(name);
        tv_description.setText(discription);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}