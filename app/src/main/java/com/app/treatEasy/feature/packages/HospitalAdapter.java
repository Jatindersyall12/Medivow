package com.app.treatEasy.feature.packages;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.booking.BookingActivity;
import com.app.treatEasy.feature.family_member.MemberDetailResponse;
import com.app.treatEasy.feature.family_member.add_family_member.AddFamilyMemberActivity;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.packages.package_detail.DescriptionActivity;
import com.app.treatEasy.feature.packages.package_detail.DoctorPackageList;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.new_feature.BookingRes;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalAdapter  extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder> {
    private Context mContext;
    String memberId;
    ProgressDialog pDialog;
    private final ItemClickListener itemClickListener;
    List<MemberDetailResponse.Datum>memberList;
    private List<PackageDeatil.PriceDetail> mModelList = new ArrayList<>();
    String title;
    public HospitalAdapter(Context context, ItemClickListener itemClickListener,List<PackageDeatil.PriceDetail> mModelList,String title) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
        this.mModelList = mModelList;
        this.title = title;

        getMemberData(AppPreferences.getPreferenceInstance(mContext).getUserId());

    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hospital_list, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //  DoctorsByCategoryModel model = mModelList.get(position);
        holder.mPackageName.setText(mModelList.get(position).getClientName());
        holder.txt_city_name.setText(mModelList.get(position).getCityName());
        holder.tvDiscFee.setText(mModelList.get(position).getDiscountedFee());
        holder.tvFee.setText(mModelList.get(position).getFee());
        holder.tvFee.setPaintFlags(holder.tvFee.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (mModelList.get(position).getIsBooked()==0){
            holder.btnBook.setVisibility(View.VISIBLE);
            holder.btnBooked.setVisibility(View.GONE);
        }else {
            holder.btnBook.setVisibility(View.GONE);
            holder.btnBooked.setVisibility(View.VISIBLE);
        }

       // holder.tvAddress.setText(mModelList.get(position).getAddress());
        BaseUtils.setImage(mModelList.get(position).getClientImage(), holder.mImage, R.mipmap.medivow_logo);
        if (mModelList.get(position).getDoctors()!=null&&mModelList.get(position).getDoctors().size()>0){
            holder.recycler_view.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
            DoctorPackageList mAdapter = new DoctorPackageList(mContext,mModelList.get(position).getDoctors(),
                    mModelList.get(position).getClientImage());
            holder.recycler_view.setAdapter(mAdapter);
        }
        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookingDialog(position,mModelList.get(position).getClientName());
            }
        });
        holder.llMainHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("id",mModelList.get(position).getClientId());
                intent.putExtra("title",mModelList.get(position).getClientName());
                mContext.startActivity(intent);
            }
        });
       // holder.mPackageDescription.setText(mModelList.get(position).getDescription());
    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout,llMainHospital;
        TextView mPackageName, txt_city_name,tvFee,tvDiscFee,tvAddress,mPackageDescription;
        ImageView mImage;
        RecyclerView recycler_view;
        Button btnCall,btnBook,btnBooked;

        MyViewHolder(View itemView) {
            super(itemView);
            llMainHospital = itemView.findViewById(R.id.llMainHospital);
            mPackageName = itemView.findViewById(R.id.txt_doctor_name);
            txt_city_name = itemView.findViewById(R.id.txt_city_name);
            recycler_view = itemView.findViewById(R.id.recycler_view);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvDiscFee = itemView.findViewById(R.id.tvDiscFee);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            mPackageDescription = itemView.findViewById(R.id.tv_description);
            mImage = itemView.findViewById(R.id.img_doctor);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnBook = itemView.findViewById(R.id.btnBook);
            btnBooked = itemView.findViewById(R.id.btnBooked);

            mMainLayout.setOnClickListener(this);
            btnCall.setOnClickListener(this);
           // btnBook.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                   itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
                case R.id.btnCall:
                    showCallDialog();
                    break;
               /* case R.id.btnBook:
                    showBookingDialog();
                    break;*/
            }
        }
    }

    public void showCallDialog(){

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_help_call);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9643691869"));
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /*booking dialog*/
    public void showBookingDialog(int pos,String clientName){
        MemberDetailResponse.Datum data=new MemberDetailResponse.Datum();

         data.setDob("");
         data.setCraetedAt("");
         data.setId("0");
         data.setDob("");
         data.setMemberName("Self");
         data.setGender("");
         data.setMemberPhoto("");
         data.setPatientid("0");
         data.setRelation("Self");
         data.setStatus("0");
         memberList.add(0,data);

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext,R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_booking);
        TextView et_surgery=dialog.findViewById(R.id.et_surgery);
        Spinner spMember=dialog.findViewById(R.id.spMember);
        TextView button_next=dialog.findViewById(R.id.txt_next);
        TextView tvPackageName=dialog.findViewById(R.id.tvPackageName);
        TextView tvBookingName=dialog.findViewById(R.id.tvBookingName);
        TextView tvSelectMember=dialog.findViewById(R.id.tvSelectMember);
        TextView tvAddMemebr=dialog.findViewById(R.id.tvAddMemebr);

        tvPackageName.setText(title);
        tvBookingName.setText("you are booking with "+clientName);

        tvAddMemebr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =new Intent(mContext, AddFamilyMemberActivity.class);
               mContext.startActivity(intent);
            }
        });


        tvSelectMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectMember.setVisibility(View.GONE);
                spMember.setVisibility(View.VISIBLE);
                spMember.performClick();
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookSurgery(AppPreferences.getPreferenceInstance(mContext).getUserId(),
                       mModelList.get(pos).getPackageId(),mModelList.get(pos).getClientId(),
                        mModelList.get(pos).getDoctorsid(),memberId,et_surgery.getText().toString(),
                        mModelList.get(pos).getDiscountedFee());

                dialog.dismiss();
            }
        });
        spMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                memberId=memberList.get(i).getId();
               // Log.d("model","pincode"+pinCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        et_surgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(et_surgery);
            }
        });

        BookingMemberAdapter adapter=new BookingMemberAdapter(mContext,memberList);
        spMember.setAdapter(adapter);
        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }
   /* public void updateData(ArrayList<DoctorsByCategoryModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }*/

    public void openDatePicker(TextView mTvDate) {
        final Calendar c = Calendar.getInstance();
         int   mYear = c.get(Calendar.YEAR);
        int  mMonth = c.get(Calendar.MONTH);
        int   mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                (view, year, monthOfYear, dayOfMonth) -> {
                    mTvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }, mYear, mMonth, mDay);
        //   datePickerDialog.getDatePicker().setMaxDate();
        datePickerDialog.show();

    }

    public void getMemberData(String userId) {
        memberList=new ArrayList<>();
       /* GetProfileSend model=new GetProfileSend();
        model.setUserid(userId);*/
        Call<MemberDetailResponse> call = RetrofitClient.getInstance().getMyApi().getFamilyMember(userId);
        call.enqueue(new Callback<MemberDetailResponse>() {
            @Override
            public void onResponse(Call<MemberDetailResponse> call, Response<MemberDetailResponse> response) {
                if (response.body().getStatusCode()==200){
                    memberList=response.body().getData();
                    if (memberList.size()>0){
                    }else{

                    }
                }else {
                   // Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<MemberDetailResponse> call, Throwable t) {

              //  Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void bookSurgery(String userId,String packageId,String clientId,String doctorId,
                            String memberId,String surgeryDate,String amount) {

        showProgressDialog();

        Call<BookingRes> call = RetrofitClient.getInstance().getMyApi().bookSurgery(userId,packageId,clientId,
                doctorId,memberId,surgeryDate,amount);
        call.enqueue(new Callback<BookingRes>() {
            @Override
            public void onResponse(Call<BookingRes> call, Response<BookingRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    confirmBookingDialog(title);
                   // Toast.makeText(mContext,"Booking Confirmed!" , Toast.LENGTH_LONG).show();
                }else {
                    // Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<BookingRes> call, Throwable t) {
                dismissProgressDialog();
                //  Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showProgressDialog() {
        if (pDialog == null)
            pDialog = new ProgressDialog(mContext, R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Please wait");
        pDialog.show();
    }

    public void dismissProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void confirmBookingDialog(String packageName){

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext,R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_booking_confirm);
        TextView message=dialog.findViewById(R.id.message);
        Button btnOk=dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, BookingActivity.class);
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });
        message.setText("Thank You for choosing Treateasy Surgery "+packageName+".\n" +
                "We will get back to you shortly after getting confirmation of your surgery date from hospital.");
        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }
}