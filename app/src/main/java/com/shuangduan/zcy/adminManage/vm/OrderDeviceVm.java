package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: OrderDeviceVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/15 9:41
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/15 9:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OrderDeviceVm extends BaseViewModel {

    public int userId;
    //选择弹窗区分类别
    public String type;

    public MutableLiveData<String> pageStateLiveData;

    public OrderDeviceVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
    }
}
