package com.shuangduan.zcy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/22 15:20
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSubConfirmBean implements Parcelable {

    /**
     * order_id : 37
     * id : 2891
     * title : 黑龙江跨江大桥
     * time : 3个月
     * order_sn : sdapp201908225471856
     * price : 25.00
     */

    private int order_id;
    private int id;
    private String title;
    private String time;
    private String order_sn;
    private String price;

    protected ProjectSubConfirmBean(Parcel in) {
        order_id = in.readInt();
        id = in.readInt();
        title = in.readString();
        time = in.readString();
        order_sn = in.readString();
        price = in.readString();
    }

    public static final Creator<ProjectSubConfirmBean> CREATOR = new Creator<ProjectSubConfirmBean>() {
        @Override
        public ProjectSubConfirmBean createFromParcel(Parcel in) {
            return new ProjectSubConfirmBean(in);
        }

        @Override
        public ProjectSubConfirmBean[] newArray(int size) {
            return new ProjectSubConfirmBean[size];
        }
    };

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(order_sn);
        dest.writeString(price);
    }
}
