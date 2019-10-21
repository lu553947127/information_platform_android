package com.shuangduan.zcy.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.base$
 * @class TrackDateilBean$
 * @class describe
 * @time 2019/10/21 9:48
 * @change
 * @class describe
 */
public class TrackDateilBean {
    public String title;
    @SerializedName("create_time")
    public String createTime;
    public String name;
    public String tel;
    public String remarks;
    public int status;
    @SerializedName("project_id")
    public int projectId;
    @SerializedName("reject_reason")
    public String rejectReason;
    @SerializedName("image_json")
    public List<ImageJson> imageJson;

    public class ImageJson {
        public String source;
        public String thumbnail;

        @Override
        public String toString() {
            return "ImageJson{" +
                    "source='" + source + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TrackDateilBean{" +
                "title='" + title + '\'' +
                ", createTime='" + createTime + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", remarks='" + remarks + '\'' +
                ", state=" + status +
                ", reject_reason='" + rejectReason + '\'' +
                ", imageJson=" + imageJson +
                '}';
    }
}
