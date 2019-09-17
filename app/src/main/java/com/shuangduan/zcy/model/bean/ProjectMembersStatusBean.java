package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: ProjectMembersStatusBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/17 14:30
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/17 14:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProjectMembersStatusBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1568701813
     * data : {"groupId":10,"groupName":"黑龙江跨江大桥1"}
     */

    private String code;
    private String msg;
    private int time;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * groupId : 10
         * groupName : 黑龙江跨江大桥1
         */

        private String groupId;
        private String groupName;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
    }
}
