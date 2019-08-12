package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.repository.SupplierRepository;
import com.shuangduan.zcy.model.bean.SupplierBean;
import com.shuangduan.zcy.model.bean.SupplierDetailBean;
import com.shuangduan.zcy.model.bean.SupplierJoinImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/12 10:22
 * @change
 * @chang time
 * @class describe
 */
public class SupplierVm extends BaseViewModel {
    public MutableLiveData<SupplierBean> supplierLiveData;
    public MutableLiveData<SupplierDetailBean> detailLiveData;
    public MutableLiveData joinLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    private int page;
    private List<Integer> imageIds;

    public SupplierVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        supplierLiveData = new MutableLiveData<>();
        detailLiveData = new MutableLiveData<>();
        joinLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
    }

    public void getSupplier(){
        page = 1;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new SupplierRepository().getSupplier(supplierLiveData, pageStateLiveData, userId, page);
    }

    public void getMoreSupplier(){
        page ++;
        pageStateLiveData.postValue(PageState.PAGE_REFRESH);
        new SupplierRepository().getSupplier(supplierLiveData, pageStateLiveData, userId, page);
    }

    public void getDetail(int id){
        new SupplierRepository().getSupplierDetail(detailLiveData, pageStateLiveData, userId, id);
    }

    /**
     * 添加图片
     */
    public void addImage(int id){
        if (imageIds == null){
            imageIds = new ArrayList<>();
        }
        imageIds.add(id);
    }

    /**
     * 删除图片
     */
    public void delImage(int position){
        if (imageIds != null){
            imageIds.remove(position);
        }
    }

    public void join(){
        if (imageIds == null || imageIds.size() == 0){
            ToastUtils.showShort("请上传营业执照以及相关文件资料");
            return;
        }
        SupplierJoinImageBean supplierJoinImageBean = new SupplierJoinImageBean();
        supplierJoinImageBean.setImages(imageIds);
        new SupplierRepository().getSupplierJoin(joinLiveData, pageStateLiveData, userId, supplierJoinImageBean);
    }
}
