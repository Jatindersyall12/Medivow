package com.app.treatEasy.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.treatEasy.R;

import java.util.List;

public class CityAdapter extends BaseAdapter {

    Context mContext;
    List<CityResponseModel.Datum> stateList;
    private LayoutInflater layoutInflater;
    boolean flag = false;
    ViewHolder holder;
    public CityAdapter(Context c, List<CityResponseModel.Datum> stateList) {
        mContext = c;
        this.stateList = stateList;
        layoutInflater = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return stateList.size();
    }
    @Override
    public Object getItem(int position) {
        return stateList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_spin_state, null);
            holder = new ViewHolder();
            holder.textViewJacob=convertView.findViewById(R.id.tvStateName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewJacob.setText(stateList.get(position).getCityName());
        return convertView;
    }

    public class ViewHolder {
        TextView textViewJacob;
    }
}