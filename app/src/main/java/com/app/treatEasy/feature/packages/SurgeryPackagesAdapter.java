package com.app.treatEasy.feature.packages;

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
public class SurgeryPackagesAdapter extends RecyclerView.Adapter<SurgeryPackagesAdapter.MyViewHolder> {
    private Context mContext;
    private final ItemClickListener itemClickListener;
    private List<SurgeryPackagesModel.Datum> mModelList = new ArrayList<>();

    public SurgeryPackagesAdapter(Context context, ItemClickListener itemClickListener,List<SurgeryPackagesModel.Datum> mModelList) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
        this.mModelList = mModelList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_package, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       // SurgeryPackagesModel model = mModelList.get(position);

        holder.mPackageName.setText(mModelList.get(position).getTitle());
        Glide.with(mContext)
                .load(mModelList.get(position).getImage())
                .error(R.mipmap.medivow_logo)
                .into(holder.item_image);
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

    public void updateData(ArrayList<SurgeryPackagesModel> data) {
        //mModelList = data;
        notifyDataSetChanged();
    }
}