package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/22 10:39
 * @change
 * @chang time
 * @class describe
 */
public class ExplainDetailBean {

    /**
     * id : 1
     * title : 人脉收益说明书
     * content : ,<p>人脉收益说明书</p>
     * create_time : 2019-08-05 16:51:22
     */

    private int id;
    private String title;
    private String content;
    private String create_time;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
