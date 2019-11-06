package com.shuangduan.zcy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialPlaceOrderBean;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

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
public class MaterialPlaceOrderAdapter extends SwipeRecyclerView.Adapter<MaterialPlaceOrderAdapter.ViewHolder> implements View.OnClickListener {

    private final int method;
    private Context context;
    private List<MaterialPlaceOrderBean> list;
    private OnItemClickListener mItemClickListener;

    public MaterialPlaceOrderAdapter(Context context, List<MaterialPlaceOrderBean> list, int method) {
        this.context = context;
        this.list = list;
        this.method = method;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_material_place_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_num.setText(String.valueOf(list.get(position).getNum()));
        holder.tv_name.setText(list.get(position).getMaterial_name());
        holder.itemView.setTag(position);
//        holder.ll_day.setVisibility(method == 1 ? View.VISIBLE : View.GONE);
//        holder.tv_day.setText(list.get(position).getDay() + "天");
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
    public void addData(int position, int num, int id, int materialId, String material_name) {
        //在list中添加数据，并通知条目加入一条
        list.add(position, new MaterialPlaceOrderBean(num, id, materialId, material_name));
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

    public class ViewHolder extends SwipeRecyclerView.ViewHolder {
        private LinearLayout ll_day;
        private TextView tv_num, tv_name, tv_day;
        private ImageView iv_del;

        ViewHolder(View itemView) {
            super(itemView);
            ll_day = itemView.findViewById(R.id.ll_day);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_name = itemView.findViewById(R.id.tv_material_id);
            iv_del = itemView.findViewById(R.id.iv_del);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }
}
