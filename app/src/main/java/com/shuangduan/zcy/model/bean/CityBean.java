package com.shuangduan.zcy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.model.bean
 * @class describe
 * @time 2019/7/9 13:06
 * @change
 * @chang time
 * @class describe
 */
public class CityBean extends BaseSelectorBean implements Parcelable {

    /**
     * amount : 110100
     * name : 北京市
     */
    private int id;
    private String name;

    public CityBean() {
    }

    public CityBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected CityBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        isSelect = in.readInt();
    }

    public static final Creator<CityBean> CREATOR = new Creator<CityBean>() {
        @Override
        public CityBean createFromParcel(Parcel in) {
            return new CityBean(in);
        }

        @Override
        public CityBean[] newArray(int size) {
            return new CityBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(isSelect);
    }
}
