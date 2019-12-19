package com.shuangduan.zcy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReceivingAddressBean {

    public int page;
    public int count;
    public List<ReceivingAddressBean.Address> list;

    public static class Address implements Parcelable {
        //地址ID
        public int id;

        //姓名
        @SerializedName("real_name")
        public String name;
        //手机号
        @SerializedName("tel")
        public String phone;
        //公司
        public String company;
        //省
        @SerializedName("province_name")
        public String province;
        @SerializedName("province")
        public int provinceId;
        //市
        @SerializedName("city_name")
        public String city;
        @SerializedName("city")
        public int cityId;
        //地址
        public String address;
        //是否默认 是否默认 1是 0否
        @SerializedName("is_default")
        public int state;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.phone);
            dest.writeString(this.company);
            dest.writeString(this.province);
            dest.writeInt(this.provinceId);
            dest.writeString(this.city);
            dest.writeInt(this.cityId);
            dest.writeString(this.address);
            dest.writeInt(this.state);
        }

        public Address() {
        }

        protected Address(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.phone = in.readString();
            this.company = in.readString();
            this.province = in.readString();
            this.provinceId = in.readInt();
            this.city = in.readString();
            this.cityId = in.readInt();
            this.address = in.readString();
            this.state = in.readInt();
        }

        public static final Creator<Address> CREATOR = new Creator<Address>() {
            @Override
            public Address createFromParcel(Parcel source) {
                return new Address(source);
            }

            @Override
            public Address[] newArray(int size) {
                return new Address[size];
            }
        };
    }
}
