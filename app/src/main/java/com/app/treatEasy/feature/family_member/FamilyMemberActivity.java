package com.app.treatEasy.feature.family_member;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.edit_family_member.EditFamilyMemberActivity;
import com.app.treatEasy.feature.family_member.add_family_member.AddFamilyMemberActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyMemberActivity extends BaseActivity implements ItemClickListener {

    List<MemberDetailResponse.Datum> memberList;
    RecyclerView mRecycler;
    Button btnAddmember;
    TextView tvNoMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member);

        initView();

    }

    private void initView() {

        setUpToolBar(getString(R.string.add_family_member), true);
        mRecycler = findViewById(R.id.recycler_view);
        btnAddmember = findViewById(R.id.btnAddmember);
        tvNoMember = findViewById(R.id.tvNoRecord);
        //getMemberData(AppPreferences.getPreferenceInstance(this).getUserId());
        btnAddmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(AddFamilyMemberActivity.class);
            }
        });

        //  mAdapter.updateData(familyMemberData());
    }

    @SuppressLint("ResourceType")
    @Override
    public void OnItemClick(View view, int position) {
        if (view.getTag() == "edit") {
            Intent intent = new Intent(FamilyMemberActivity.this, EditFamilyMemberActivity.class);
            intent.putExtra("member_id", memberList.get(position).getId());
            startActivity(intent);
        } else if (view.getTag() == "delete") {
            showDeleteDialog(position);
        } else {
            //showFamilyDialog(position);
            switchActivity(AddFamilyMemberActivity.class);
            Intent intent = new Intent(FamilyMemberActivity.this, MemberProfileActivity.class);
            intent.putExtra("image", memberList.get(position).getMemberPhoto());
            intent.putExtra("name", memberList.get(position).getMemberName());
            intent.putExtra("username", memberList.get(position).getMemberName());
            intent.putExtra("relation", memberList.get(position).getRelation());
            intent.putExtra("gender", memberList.get(position).getGender());
            intent.putExtra("dob", memberList.get(position).getDob());
            startActivity(intent);
        }
    }

    public void deleteFamilyMember(String userId, String memberId) {
        showProgressDialog();
        Call<DeleteMemberModel> call = RetrofitClient.getInstance().getMyApi().deleteFamilyMember(userId, memberId);
        call.enqueue(new Callback<DeleteMemberModel>() {
            @Override
            public void onResponse(Call<DeleteMemberModel> call, Response<DeleteMemberModel> response) {
                if (response.body().getStatusCode() == 200) {

                    Toast.makeText(getApplicationContext(), "Family member deleted successfully", Toast.LENGTH_LONG).show();
                    getMemberData(AppPreferences.getPreferenceInstance(getApplicationContext()).getUserId());


                } else {
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteMemberModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getMemberData(String userId) {
        showProgressDialog();
        memberList = new ArrayList<>();
       /* GetProfileSend model=new GetProfileSend();
        model.setUserid(userId);*/
        Call<MemberDetailResponse> call = RetrofitClient.getInstance().getMyApi().getFamilyMember(userId);
        call.enqueue(new Callback<MemberDetailResponse>() {
            @Override
            public void onResponse(Call<MemberDetailResponse> call, Response<MemberDetailResponse> response) {
                if (response.body().getStatusCode() == 200) {

                    dismissProgressDialog();

                    memberList = response.body().getData();
                    if (memberList.size() > 0) {
                        mRecycler.setVisibility(View.VISIBLE);
                        tvNoMember.setVisibility(View.GONE);
                        AdapterSet(memberList);
                    } else {
                        mRecycler.setVisibility(View.GONE);
                        tvNoMember.setVisibility(View.VISIBLE);
                    }

                } else {
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MemberDetailResponse> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void AdapterSet(List<MemberDetailResponse.Datum> list) {
        FamilyMemberAdapter mAdapter = new FamilyMemberAdapter(this, this, list);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemberData(AppPreferences.getPreferenceInstance(this).getUserId());
    }

    @SuppressLint("ResourceType")
    public void showDeleteDialog(int position) {
        final BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete_and_logout);

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
                dialog.dismiss();
                deleteFamilyMember(AppPreferences.getPreferenceInstance(getApplicationContext()).getUserId(), memberList.get(position).getId());
            }
        });

        dialog.show();
    }
}