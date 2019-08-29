package com.shuangduan.zcy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/29 9:27
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendSearchBean {

    /**
     * page : 1
     * count : 1
     * list : [{"id":21,"username":"李彤","tel":"15665892858","image":"http://information-api.oss-cn-qingdao.aliyuncs.com/d8599639c512386c19c893e1038eca63.png"}]
     */

    private int page;
    private int count;
    private List<ListBean> list;

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

    public static class ListBean implements Parcelable {
        /**
         * id : 21
         * username : 李彤
         * tel : 15665892858
         * image : http://information-api.oss-cn-qingdao.aliyuncs.com/d8599639c512386c19c893e1038eca63.png
         */

        private int id;
        private String username;
        private String tel;
        private String image;

        protected ListBean(Parcel in) {
            id = in.readInt();
            username = in.readString();
            tel = in.readString();
            image = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(username);
            dest.writeString(tel);
            dest.writeString(image);
        }
    }
}
