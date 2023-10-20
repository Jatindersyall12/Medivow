package com.app.treatEasy.feature.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.appservice.ServiceDetailActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinod Kumar.
 */
public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder>{

    private Context mContext;
    private final ItemClickListener itemClickListener;
    private List<ServicesModel.Datum> mModelList;

    public ServicesAdapter(Context context, ItemClickListener itemClickListener,List<ServicesModel.Datum> mModelList) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
        this.mModelList = mModelList;
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surgery_packages, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //ServicesModel model = mModelList.get(position);
        holder.mPackageName.setText(mModelList.get(position).getTitle());
       // Log.d("Image",model.image);
        Glide.with(mContext)
                .load(mModelList.get(position).getImage())
                .error(R.mipmap.medivow_logo)
                .into(holder.item_image);

       /* holder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ServiceDetailActivity.class);
                intent.putExtra("id",mModelList.get(position).getId());
                intent.putExtra("title",mModelList.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });*/
      /*  if (!model.image.equals("")){
            BaseUtils.setImage(model.image, holder.itemView.findViewById(R.id.item_image));
        }*/
    }
    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView mMainLayout;
        TextView mPackageName;
        ImageView item_image;
        MyViewHolder(View itemView) {
            super(itemView);
            mPackageName = itemView.findViewById(R.id.txt_package_name);
            mMainLayout = itemView.findViewById(R.id.root_layout);
            item_image = itemView.findViewById(R.id.item_image);
            mMainLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:

                    itemClickListener.OnItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }

    public void updateData(ArrayList<ServicesModel> data) {
       // mModelList = data;
        notifyDataSetChanged();
    }
}