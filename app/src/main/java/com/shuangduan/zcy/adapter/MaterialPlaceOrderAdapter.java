package com.shuangduan.zcy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialPlaceOrderBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: MaterialPlaceOrderAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 17:48
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 17:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialPlaceOrderAdapter extends RecyclerView.Adapter<MaterialPlaceOrderAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<MaterialPlaceOrderBean> list;
    private OnItemClickListener mItemClickListener;

    public MaterialPlaceOrderAdapter(Context context, List<MaterialPlaceOrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_material_place_order,parent,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_num.setText(String.valueOf(list.get(position).getNum()));
        holder.tv_name.setText(list.get(position).getMaterial_name());
        holder.itemView.setTag(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    // 添加数据
    public void addData(int position,int num, int id,int materialId, String material_name) {
        //在list中添加数据，并通知条目加入一条
        list.add(position, new MaterialPlaceOrderBean(num,id,materialId,material_name));
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_num,tv_name;
        ViewHolder(View itemView) {
            super(itemView);
            tv_num=itemView.findViewById(R.id.tv_num);
            tv_name=itemView.findViewById(R.id.tv_material_id);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view ,int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }
}
