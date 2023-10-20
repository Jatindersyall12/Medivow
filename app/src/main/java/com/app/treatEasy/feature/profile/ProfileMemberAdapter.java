package com.app.treatEasy.feature.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.feature.family_member.FamilyMemberAdapter;
import com.app.treatEasy.feature.family_member.FamilyMemberModel;
import com.app.treatEasy.feature.family_member.MemberDetailResponse;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.profile.GetProfileResponse;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileMemberAdapter extends RecyclerView.Adapter<ProfileMemberAdapter.MyViewHolder> {
    private Context mContext;
    private List<GetProfileResponse.FamilyMember> mModelList = new ArrayList<>();

    public ProfileMemberAdapter(Context context,
                               List<GetProfileResponse.FamilyMember> mModelList) {
        this.mContext = context;
        this.mModelList = mModelList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_member_profile, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //  FamilyMemberModel model = mModelList.get(position);

        holder.txt_name.setText(mModelList.get(position).getMemberName());
        holder.txt_relation.setText(mModelList.get(position).getRelation()+"( "+
                mModelList.get(position).getGender()+" ,"+mModelList.get(position).getDob()+" )" );
        // Log.d("Image",model.image);

        Glide.with(mContext)
                .load(mModelList.get(position).getMemberPhoto())
                .error(R.mipmap.medivow_logo)
                .into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView mMainLayout;
        ImageView item_image;
        TextView txt_name,txt_relation;
        MyViewHolder(View itemView) {
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            item_image = itemView.findViewById(R.id.item_image);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_relation = itemView.findViewById(R.id.txt_relation);
            mMainLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:

                    break;
            }
        }
    }

    public void updateData(ArrayList<FamilyMemberModel> data) {
        //  mModelList = data;
        notifyDataSetChanged();
    }
}
