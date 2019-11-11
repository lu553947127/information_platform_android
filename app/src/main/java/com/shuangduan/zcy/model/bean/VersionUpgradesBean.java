package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: VersionUpgradesBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/20 10:04
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/20 10:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class VersionUpgradesBean {
    /**
     * id : 1
     * type : 1
     * version : 1.0.0
     * download_url : http://www.baidu.com
     * content : 紫菜云信息平台：
     * create_time : 2019-08-22 10:09:15
     * status : 0
     * update_time : 2019-09-17 19:56:18
     */

    private int id;
    private int type;
    private String version;
    private String download_url;
    private String content;
    private String create_time;
    private String status;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
