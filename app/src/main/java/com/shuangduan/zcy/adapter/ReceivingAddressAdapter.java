package com.shuangduan.zcy.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class ReceivingAddressAdapter extends SwipeRecyclerView.Adapter<ReceivingAddressAdapter.ViewHolder> implements View.OnClickListener {


    private Context context;
    private List<ReceivingAddressBean.Address> list;
    private ReceivingAddressAdapter.OnItemClickListener mItemClickListener;
    private int currentPosition;


    public ReceivingAddressAdapter(Context context, List<ReceivingAddressBean.Address> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_receiving_address_item, parent, false);
        ReceivingAddressAdapter.ViewHolder holder = new ReceivingAddressAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.currentPosition = position;
//        holder.tvName.setText(list.get(position).name);
//        holder.tvPhone.setText(list.get(position).phone);
//        holder.tvCompany.setText(list.get(position).company);
//        holder.tvAddress.setText(list.get(position).address);
        holder.ivEdit.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list == null ? 3 : list.size();
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<ReceivingAddressBean.Address> data) {
        if (data != null && data.size() > 0) {
            this.list = data;
            notifyDataSetChanged();
        }
    }

    // 添加数据
    public void addData(int position, ReceivingAddressBean.Address address) {
        //在list中添加数据，并通知条目加入一条
        list.add(position, address);
        //添加动画
        notifyItemInserted(position);
    }

    //删除item
    public void removeData(int position) {
        list.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, currentPosition);
        }
    }


    public void setOnItemClickListener(ReceivingAddressAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends SwipeRecyclerView.ViewHolder {

        private final TextView tvName;
        private final TextView tvPhone;
        private final TextView tvCompany;
        private final ImageView ivEdit;
        private final TextView tvAddress;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvCompany = itemView.findViewById(R.id.tv_company);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }


}
