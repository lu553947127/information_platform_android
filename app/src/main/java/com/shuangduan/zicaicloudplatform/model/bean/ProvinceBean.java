package com.shuangduan.zicaicloudplatform.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

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
public class ProvinceBean implements Parcelable {
    /**
     * id : 110000
     * name : 北京市
     */

    private int id;
    private String name;

    protected ProvinceBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
