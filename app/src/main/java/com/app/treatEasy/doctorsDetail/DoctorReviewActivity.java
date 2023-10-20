package com.app.treatEasy.doctorsDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.booking.BookingActivity;
import com.app.treatEasy.feature.add_money.UpdateWalletRes;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.profile.doctorprofile.DoctorProfileActivity;
import com.app.treatEasy.profile.doctorprofile.DoctorProfileRes;
import com.app.treatEasy.profile.doctorprofile.ProfileHospitalAdapter;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorReviewActivity extends BaseActivity {

     private ImageView imgClientImage;
     private TextView tvDoctorName;
     private EditText etComment;
     private RatingBar ratingBar;
     String name,imageUrl,doctorId;
     private Button btnUpdated;
     int userRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_review);

        setUpToolBar("Doctor Review",true);

        name=getIntent().getStringExtra("name");
        imageUrl=getIntent().getStringExtra("imageUrl");
        doctorId=getIntent().getStringExtra("doctorId");
        init();

        BaseUtils.setImage(imageUrl,imgClientImage,
                R.mipmap.medivow_logo);
        tvDoctorName.setText(name);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
              //  userRating=Integer.parseInt(String.valueOf(rating));
                userRating=Math.round(rating);
             //   Toast.makeText(DoctorReviewActivity.this, userRating, Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                updateReview(AppPreferences.getPreferenceInstance(DoctorReviewActivity.this).getUserId(),doctorId,
                        userRating,etComment.getText().toString());

            }
        });
    }

    public void init(){

        imgClientImage=findViewById(R.id.imgClientImage);
        tvDoctorName=findViewById(R.id.tvDoctorName);
        etComment=findViewById(R.id.etComment);
        ratingBar=findViewById(R.id.ratingBar);
        btnUpdated=findViewById(R.id.btnUpdated);

    }

    public void updateReview(String userId,String doctorID,int rating,String comment){

        showProgressDialog();

        Call<DoctorReviewRes> call = RetrofitClient.getInstance().getMyApi().writeReview(userId,doctorID,
                rating,comment);
        call.enqueue(new Callback<DoctorReviewRes>(){
            @Override
            public void onResponse(Call<DoctorReviewRes> call, Response<DoctorReviewRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                      confirmDialog();
                }
            }
            @Override
            public void onFailure(Call<DoctorReviewRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void confirmDialog(){

        final BottomSheetDialog dialog = new BottomSheetDialog(DoctorReviewActivity.this,R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_booking_confirm);
        TextView message=dialog.findViewById(R.id.message);
        Button btnOk=dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        message.setText("Review Updated Successfully");
        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }
}