package com.shuangduan.zcy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.model.bean
 * @class describe
 * @time 2019/7/9 13:04
 * @change
 * @chang time
 * @class describe
 */
public class ProvinceBean extends BaseSelectorBean implements Parcelable {
    /**
     * id : 110000
     * name : 北京市
     */
    private int id;
    private String name;
    private int pid;
    private List<CityBean> cityList;

    public ProvinceBean(int id, String name, int pid, List<CityBean> cityList) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.cityList = cityList;
    }

    protected ProvinceBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        pid = in.readInt();
        isSelect = in.readInt();
        cityList = in.createTypedArrayList(CityBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(pid);
        dest.writeInt(isSelect);
        dest.writeTypedList(cityList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProvinceBean> CREATOR = new Creator<ProvinceBean>() {
        @Override
        public ProvinceBean createFromParcel(Parcel in) {
            return new ProvinceBean(in);
        }

        @Override
        public ProvinceBean[] newArray(int size) {
            return new ProvinceBean[size];
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<CityBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityBean> cityList) {
        this.cityList = cityList;
    }
}
