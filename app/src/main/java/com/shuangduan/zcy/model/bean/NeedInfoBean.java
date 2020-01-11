package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: NeedInfoBean
 * @Description: 找方案、找物流、找基地 列表 详细信息JAVABEAN
 * @Author: 徐玉
 * @CreateDate: 2020/1/11 13:33
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public class NeedInfoBean {
    public int id;
    public String remark;
    @SerializedName("start_time")
    public String startTime;
    @SerializedName("end_time")
    public String endTime;
    public String state;

    //项目名称
    @SerializedName("project_name")
    public String projectName;

    //物资名称
    @SerializedName("material_name")
    public String materialName;
    //物资数量
    @SerializedName("material_count")
    public String materialCount;
    //计量单位
    public String unit;
    //联系人手机号
    public String tel;
    //联系人姓名
    @SerializedName("personal_name")
    public String personalName;
    //项目地点
    @SerializedName("project_location")
    public String projectLocation;

    //发货地点
    @SerializedName("delivery_address")
    public String deliveryAddress;
    //接收地点
    @SerializedName("receiving_address")
    public String receivingAddress;

    //最晚接收时间
    @SerializedName("receiving_time")
    public String receivingTime;

    //取消状态 1可取消 0不可取消
    @SerializedName("opera_status")
    public int operaStatus;
}
