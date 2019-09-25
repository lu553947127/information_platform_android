package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.MaterialBean;
import com.shuangduan.zcy.model.bean.MaterialCategoryBean;
import com.shuangduan.zcy.model.bean.MaterialDepositingPlaceBean;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/8/7 10:52
 * @change
 * @chang time
 * @class describe
 */
public class MaterialRepository extends BaseRepository {
    public void materialList(MutableLiveData<MaterialBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int type,
                             int materialId, String spec, int supplierId, int page) {
        request(apiService.materialList(userId, type, materialId, spec, supplierId, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void materialDetail(MutableLiveData<MaterialDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.materialDetail(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getCategory(MutableLiveData<List<MaterialCategoryBean>> liveData, MutableLiveData<String> pageStateLiveData, int userId, int category_id) {
        request(apiService.getCategory(userId, category_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    public void collected(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id){
        request(apiService.collected(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void collect(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.collectNew(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void getAddress(MutableLiveData<List<MaterialDepositingPlaceBean>> liveData, MutableLiveData<String> pageStateLiveData, int userId, int id) {
        request(apiService.addressList(userId, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
