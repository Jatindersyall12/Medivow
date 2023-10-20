package com.app.treatEasy.feature.family_member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.family_member.add_family_member.AddFamilyMemberActivity;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberProfileActivity extends BaseActivity {
    private CircleImageView img_user_image;
    private TextView tvPatientName,tv_user_name,tvRelation,tvGender,tvDob;

    String image,patientName,userName,relation,gender,dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        getIntentValue();

        initView();
    }

    private void getIntentValue(){
        image=getIntent().getStringExtra("image");
        patientName=getIntent().getStringExtra("name");
        userName=getIntent().getStringExtra("username");
        relation=getIntent().getStringExtra("relation");
        gender=getIntent().getStringExtra("gender");
        dob=getIntent().getStringExtra("dob");

    }

    private void initView(){

        setUpToolBar("Member Profile", true);

        img_user_image=findViewById(R.id.img_user_image);
        tvPatientName=findViewById(R.id.tvPatientName);
        tv_user_name=findViewById(R.id.tv_user_name);
        tvRelation=findViewById(R.id.tvRelation);
        tvGender=findViewById(R.id.tvGender);
        tvDob=findViewById(R.id.tvDob);


        Glide.with(MemberProfileActivity.this)
                .load(image)
                .error(R.mipmap.medivow_logo)
                .into(img_user_image);
        tvPatientName.setText(patientName);
        tv_user_name.setText(patientName);
        tvRelation.setText(relation);
        if (gender.equals("1")){
            tvGender.setText("Male");
        }else if (gender.equals("2")){
            tvGender.setText("Female");
        }

        tvDob.setText(dob);

    }
}