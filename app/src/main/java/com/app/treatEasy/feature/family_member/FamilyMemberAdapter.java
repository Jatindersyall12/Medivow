package com.app.treatEasy.feature.family_member;

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
import com.app.treatEasy.listeners.ItemClickListener;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinod Kumar.
 */
public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.MyViewHolder> {
    private Context mContext;
    private final ItemClickListener itemClickListener;
    private List<MemberDetailResponse.Datum> mModelList = new ArrayList<>();
    String gender;
    public FamilyMemberAdapter(Context context, ItemClickListener itemClickListener,
                               List<MemberDetailResponse.Datum> mModelList) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
        this.mModelList = mModelList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_member, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      //  FamilyMemberModel model = mModelList.get(position);

        if (mModelList.get(position).getGender().equals("1")){
            gender="M";
        }else if (mModelList.get(position).getGender().equals("2")){
            gender="F";
        }
        holder.txt_name.setText(mModelList.get(position).getMemberName());

        holder.txt_relation.setText(mModelList.get(position).getRelation()+" ("+gender+" ,"+mModelList.get(position).getAge()+
                " Years"+") " );
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
        ImageView item_image,imgEdit,imgDelete;
        TextView txt_name,txt_relation;
        MyViewHolder(View itemView) {
            super(itemView);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            item_image = itemView.findViewById(R.id.item_image);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_relation = itemView.findViewById(R.id.txt_relation);
            mMainLayout.setOnClickListener(this);
            imgEdit.setOnClickListener(this);
            imgDelete.setOnClickListener(this);

        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
                case R.id.imgEdit:
                    v.setTag("edit");
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
                case R.id.imgDelete:
                    v.setTag("delete");
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }

    public void updateData(ArrayList<FamilyMemberModel> data) {
      //  mModelList = data;
        notifyDataSetChanged();
    }
}