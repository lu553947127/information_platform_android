package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.adminManage.bean.TurnoverDetailBean;
import com.shuangduan.zcy.adminManage.repository.TurnoverRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.vm$
 * @class TurnoverDetailVm$
 * @class describe
 * @time 2019/10/31 14:23
 * @change
 * @class describe
 */
public class TurnoverDetailVm extends BaseViewModel {

    private int userId;
    public MutableLiveData<TurnoverDetailBean> turnoverDetailLiveData;
    public MutableLiveData<String> pageStateLiveData;

    public TurnoverDetailVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        turnoverDetailLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    //后台管理 --- 周转材料名称类别二级
    public void getTurnoverDetail(int id) {
        new TurnoverRepository().getTurnoverDetail(turnoverDetailLiveData, pageStateLiveData, userId, id);
    }
}
