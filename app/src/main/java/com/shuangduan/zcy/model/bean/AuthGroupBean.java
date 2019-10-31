package com.shuangduan.zcy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.model.bean$
 * @class AuthGroupBean$
 * @class describe
 * @time 2019/10/23 16:28
 * @change
 * @class 基建物资列表 授权组ID数组
 */
public class AuthGroupBean implements Parcelable {

    public List<String> auth_group = new ArrayList<>();



    @Override
    public String toString() {
        return "AuthGroupBean{" +
                "auth_group=" + auth_group +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.auth_group);
    }

    public AuthGroupBean() {
    }

    protected AuthGroupBean(Parcel in) {
        this.auth_group = in.createStringArrayList();
    }

    public static final Creator<AuthGroupBean> CREATOR = new Creator<AuthGroupBean>() {
        @Override
        public AuthGroupBean createFromParcel(Parcel source) {
            return new AuthGroupBean(source);
        }

        @Override
        public AuthGroupBean[] newArray(int size) {
            return new AuthGroupBean[size];
        }
    };
}
