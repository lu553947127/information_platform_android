package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.model.bean$
 * @class MaterialCollectBean$
 * @class describe
 * @time 2019/9/25 9:15
 * @change
 * @class describe
 */
public class MaterialCollectBean {
    public int page;
    public int count;
    public List<ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }


    public class ListBean {
        public int id;
        //图片
        public String images;
        //产品名称
        @SerializedName("material_name")
        public String materialName;
        //分类名称
        public String category;
        //名称规格型号
        public String spec;
        //供应商公司
        public String company;
        //方式 1 出租 2 售卖
        public int method;
        //单价
        @SerializedName("guidance_price")
        public String guidancePrice;
        //单位
        public String unit;
        //库存
        public String stock;
        //状态 1可预订 0已失效
        public int status;

        //是否合作过
        @SerializedName("is_order")
        public int isOrder;

        //浏览人数
        @SerializedName("browse_count")
        public String browseCount;

        //地址
        public String address;

    }
}
