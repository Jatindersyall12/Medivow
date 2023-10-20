package com.app.treatEasy.feature.packages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.treatEasy.R;
import com.app.treatEasy.feature.family_member.MemberDetailResponse;

import java.util.List;

public class BookingMemberAdapter extends BaseAdapter {

    Context mContext;
    List<MemberDetailResponse.Datum> memberList;
    private LayoutInflater layoutInflater;
    boolean flag = false;
    ViewHolder holder;
    public BookingMemberAdapter(Context c, List<MemberDetailResponse.Datum> memberList) {
        mContext = c;
        this.memberList = memberList;
        layoutInflater = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return memberList.size();
    }
    @Override
    public Object getItem(int position) {
        return memberList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_family_member, null);
            holder = new ViewHolder();
            holder.tvMemberName=convertView.findViewById(R.id.tvMemberName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvMemberName.setText(memberList.get(position).getMemberName());
        return convertView;
    }

    public class ViewHolder {
        TextView tvMemberName;
    }
}