package com.app.treatEasy.appointmentlist;

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
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.listeners.ItemClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {
    final private Context mContext;

    List<AppointmentListResponse.Datum> appointmentList;
    private final ItemClickListener itemClickListener;
    private boolean isCancelButtonShow;

    public AppointmentListAdapter(Context context, List<AppointmentListResponse.Datum> appointmentList,
                                  ItemClickListener itemClickListener, boolean isCancelButtonShow) {
        this.mContext = context;
        this.appointmentList = appointmentList;
        this.itemClickListener = itemClickListener;
        this.isCancelButtonShow = isCancelButtonShow;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_appointment_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvDoctorName.setText(appointmentList.get(position).getDoctor_name());
        holder.tvDoctorDetail.setText(appointmentList.get(position).getSpecialities() + " | " + appointmentList.get(position).getClient_name());
        if (!appointmentList.get(position).getDoctor_profile_image().isEmpty()) {
            Picasso.get()
                    .load(appointmentList.get(position).getDoctor_profile_image())
                    .into(holder.imgDoctor, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    });
        }
        String detail;
        detail = appointmentList.get(position).getMember_name();
        if (appointmentList.get(position).getGender().equals("1"))
            detail = detail + "(" + "M" + "," + appointmentList.get(position).getAge() + ")";
        else if (appointmentList.get(position).getGender().equals("2"))
            detail = detail + "(" + "F" + "," + appointmentList.get(position).getAge() + ")";
        if (appointmentList.get(position).getGender().equals("3"))
            detail = detail + "(" + "O" + "," + appointmentList.get(position).getAge() + ")";

        holder.tvPatientDetail.setText(detail);

        holder.tvDate.setText(appointmentList.get(position).getAppointment_date());
        holder.tvTime.setText(appointmentList.get(position).getApproximate_time());

        if (isCancelButtonShow)
            holder.btnCancel.setVisibility(View.VISIBLE);
        else
            holder.btnCancel.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return (appointmentList == null) ? 0 : appointmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDoctorName, tvDoctorDetail, tvPatientDetail,tvDate,tvTime;
        LinearLayout layMain;

        Button btnCancel;

        ImageView imgDoctor;

        MyViewHolder(View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            layMain = itemView.findViewById(R.id.layMain);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            imgDoctor = itemView.findViewById(R.id.imgDoctor);
            tvDoctorDetail = itemView.findViewById(R.id.tvDoctorDetail);
            tvPatientDetail = itemView.findViewById(R.id.tvPatientDetail);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            layMain.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layMain:
                    v.setTag("Detail");
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
                case R.id.btnCancel:
                    v.setTag("Cancel");
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }
}
