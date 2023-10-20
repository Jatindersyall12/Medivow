package com.app.treatEasy.booking;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.treatEasy.R;
import com.app.treatEasy.feature.services.ServicesActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.payment.PackageFeeActivity;
import com.app.treatEasy.payment.surgery_package.PaymentPackageActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {
    private Context mContext;
    String memberId;
    ProgressDialog pDialog;
    private final ItemClickListener itemClickListener;
    List<BookingResponse.Datum> memberList;

    public BookingAdapter(Context context, ItemClickListener itemClickListener,List<BookingResponse.Datum> memberList) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
        this.memberList = memberList;
      //  getMemberData(AppPreferences.getPreferenceInstance(mContext).getUserId());
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_booking, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_Hospital_name.setText(memberList.get(position).getClientName());
        holder.txt_doctor_name.setText(memberList.get(position).getDoctorName());
        holder.txt_patient_name.setText(""+memberList.get(position).getMemberName());
        holder.txt_date.setText(memberList.get(position).getSurgeryDate());

        if (memberList.get(position).getPartialPaymentOptionToShow()==0&&memberList.get(position).getFullPaymentOptionToShow()==0){
            holder.btnProcessPayment.setVisibility(View.GONE);
        }else {
            holder.btnProcessPayment.setVisibility(View.VISIBLE);
        }

        holder.btnProcessPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookingDialog(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return (memberList == null) ? 0 : memberList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_Hospital_name, txt_doctor_name,txt_patient_name,txt_date;
          LinearLayout llMain;
          Button btnProcessPayment;
          MyViewHolder(View itemView) {
            super(itemView);
            btnProcessPayment = itemView.findViewById(R.id.btnProcessPayment);
            txt_Hospital_name = itemView.findViewById(R.id.txt_Hospital_name);
            txt_doctor_name = itemView.findViewById(R.id.txt_doctor_name);
            txt_patient_name = itemView.findViewById(R.id.txt_patient_name);
            txt_date = itemView.findViewById(R.id.txt_date);
            llMain = itemView.findViewById(R.id.llMain);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
               /* case R.id.root_layout:
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
                case R.id.btnCall:
                    showCallDialog();
                    break;*/
               /* case R.id.btnBook:
                    showBookingDialog();
                    break;*/
            }
        }
    }

    public void showBookingDialog(int position) {

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext,R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_booking_detail);

        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        TextView tvHospitalName =  dialog.findViewById(R.id.tvHospitalName);
        TextView tvdoctor =  dialog.findViewById(R.id.tvdoctor);
        TextView tvPatient =  dialog.findViewById(R.id.tvPatient);
        TextView tvPrice =  dialog.findViewById(R.id.tvPrice);
        TextView tvSurgery =  dialog.findViewById(R.id.tvSurgery);
        TextView tvDate =  dialog.findViewById(R.id.tvDate);
        TextView tvFullAmount =  dialog.findViewById(R.id.tvFullAmount);

        Button btnFullPayment =  dialog.findViewById(R.id.btnFullPayment);
        Button btnPartialPayment =  dialog.findViewById(R.id.btnPartialPayment);

         btnFullPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PackageFeeActivity.class);
                intent.putExtra("clientId",memberList.get(position).getClientId());
                intent.putExtra("bookingId",memberList.get(position).getId());
                intent.putExtra("doctorId",memberList.get(position).getDoctorId());
                intent.putExtra("hospital",memberList.get(position).getClientName());
                intent.putExtra("status","1");
                mContext.startActivity(intent);
            }
        });

        btnPartialPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PackageFeeActivity.class);
                intent.putExtra("clientId",memberList.get(position).getClientId());
                intent.putExtra("bookingId",memberList.get(position).getId());
                intent.putExtra("doctorId",memberList.get(position).getDoctorId());
                intent.putExtra("hospital",memberList.get(position).getClientName());
                intent.putExtra("status","2");
                mContext.startActivity(intent);
            }
        });

        if (memberList.get(position).getFullPaymentOptionToShow()==0){
            btnFullPayment.setVisibility(View.GONE);
        }else {
            btnFullPayment.setVisibility(View.VISIBLE);
        }

        if (memberList.get(position).getPartialPaymentOptionToShow()==0){
            btnPartialPayment.setVisibility(View.GONE);
        }else {
            btnPartialPayment.setVisibility(View.VISIBLE);
        }

        double amount=Double.parseDouble(memberList.get(position).getAmount())-Double.parseDouble(memberList.get(position).getAmountPaid());
        tvHospitalName.setText(memberList.get(position).getClientName());
        tvdoctor.setText(memberList.get(position).getDoctorName());
        tvPatient.setText(""+memberList.get(position).getMemberName());
        tvPrice.setText(""+memberList.get(position).getPartialAmount());
        tvFullAmount.setText(""+amount);
        tvSurgery.setText(memberList.get(position).getTitle());
        tvDate.setText(memberList.get(position).getSurgeryDate());

        /*btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        dialog.show();
    }
}