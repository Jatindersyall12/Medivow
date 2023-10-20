package com.app.treatEasy.feature.add_money;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.treatEasy.R;
import com.app.treatEasy.feature.home_screen.DoctorModel;
import com.app.treatEasy.listeners.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Vinod Kumar.
 */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.MyViewHolder> {
    private Context mContext;
    private final ItemClickListener itemClickListener;
    private ArrayList<DoctorModel> mModelList = new ArrayList<>();

    public CardListAdapter(Context context, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DoctorModel model = mModelList.get(position);

    }


    @Override
    public int getItemCount() {
        return (mModelList == null) ? 5 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mMainLayout;

        MyViewHolder(View itemView) {
            super(itemView);
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

    public void updateData(ArrayList<DoctorModel> data) {
        mModelList = data;
        notifyDataSetChanged();
    }
}