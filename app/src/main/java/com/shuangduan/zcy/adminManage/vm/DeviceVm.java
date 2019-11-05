package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.base.BaseViewModel;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: DeviceVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/5 16:28
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/5 16:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceVm extends BaseViewModel {

    private int userId;
    private int page = 1;
    public MutableLiveData<String> pageStateLiveData;

    public DeviceVm(){
        pageStateLiveData = new MutableLiveData<>();
    }


}
