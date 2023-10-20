package com.app.treatEasy.feature.packages.package_detail;

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
import com.app.treatEasy.apputils.BaseUtils;
import com.app.treatEasy.listeners.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Vinod Kumar.
 */
public class PackageDetailAdapter extends RecyclerView.Adapter<PackageDetailAdapter.MyViewHolder> {
    private Context mContext;
    private final ItemClickListener itemClickListener;
    private ArrayList<PackageDetailModel> mModelList = new ArrayList<>();

    public PackageDetailAdapter(Context context, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surgery_packages, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PackageDetailModel model = mModelList.get(position);

        holder.mPackageName.setText(model.title);
        BaseUtils.setImage(model.image, holder.mImage, R.mipmap.medivow_logo);
        //holder.mPackageDescription.setText(model.description);
    }


    @Override
    public int getItemCount() {
        return (mModelList == null) ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView mMainLayout;
        TextView mPackageName, mPackageDescription;
        ImageView mImage;

        MyViewHolder(View itemView) {
            super(itemView);
            mPackageName = itemView.findViewById(R.id.txt_package_name);
            mImage = itemView.findViewById(R.id.item_image);
            mMainLayout = itemView.findViewById(R.id.root_layout);
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

    public void updateData(ArrayList<PackageDetailModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }
}