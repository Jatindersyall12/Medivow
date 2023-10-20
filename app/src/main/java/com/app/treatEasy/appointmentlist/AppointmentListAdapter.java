package com.app.treatEasy.appointmentlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.payment.surgery_package.paymentPackageRes;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {
    final private Context mContext;

    List<AppointmentListResponse.Datum> appointmentList;
    private final ItemClickListener itemClickListener;

    public AppointmentListAdapter(Context context, List<AppointmentListResponse.Datum> appointmentList,
                                  ItemClickListener itemClickListener) {
        this.mContext = context;
        this.appointmentList = appointmentList;
        this.itemClickListener = itemClickListener;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_appointment_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.tvDoctorName.setText(appointmentList.get(position).getBookingId());
    }

    @Override
    public int getItemCount() {
        return (appointmentList == null) ? 0 : 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDoctorName;
        LinearLayout layMain;

        Button btnCancel;

        MyViewHolder(View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            layMain = itemView.findViewById(R.id.layMain);
            btnCancel = itemView.findViewById(R.id.btnCancel);
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
