package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.ContactBean;
import com.shuangduan.zcy.model.bean.ContactTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/7/31 17:06
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseVm extends BaseViewModel {

    private int userId;
    public MutableLiveData<String> pageStateLiveData;
    public MutableLiveData<List<ContactBean>> contactLiveData;
    public MutableLiveData<List<ContactTypeBean>> contactTypeLiveData;
    public MutableLiveData releaseProjectLiveData;
    public MutableLiveData releaseLocusLiveData;
    public int type = 1;//项目类型，1工程2轨迹
    public int editContactTypePos = 0;
    public int editContactAddressPos = 0;
    public String longitude, latitude, start_time, end_time;
    public int province, city, phases, types;

    public ReleaseVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        pageStateLiveData = new MutableLiveData<>();
        contactLiveData = new MutableLiveData<>();
        List<ContactBean> list = new ArrayList<>();
        list.add(new ContactBean());
        contactLiveData.postValue(list);
        contactTypeLiveData = new MutableLiveData<>();
        releaseProjectLiveData = new MutableLiveData();
        releaseLocusLiveData = new MutableLiveData();
    }

    /**
     * 添加联系人
     */
    public void addContact(){
        List<ContactBean> list = contactLiveData.getValue();
        if (list != null){
            list.add(new ContactBean());
            contactLiveData.postValue(list);
        }
    }

    /**
     * 删除联系人
     */
    public void delContact(int i){
        List<ContactBean> list = contactLiveData.getValue();
        if (list != null && list.size() > 1){
            list.remove(i);
            contactLiveData.postValue(list);
        }
    }

    public void getContactType(){
        new ProjectRepository().getContactType(contactTypeLiveData, pageStateLiveData, userId);
    }

    public void clickContactType(int position){
        List<ContactTypeBean> list = contactTypeLiveData.getValue();
        if (list != null) {
            for (ContactTypeBean bean : list) {
                bean.setIsSelect(0);
            }
            ContactTypeBean contactTypeBean = list.get(position);
            contactTypeBean.setIsSelect(1);
            contactTypeLiveData.postValue(list);
        }
    }

    public void releaseProject(String title, String acreage, String valuation, String intro, String materials){
        if (StringUtils.isTrimEmpty(title)){
            ToastUtils.showShort("请输入项目名称");
            return;
        }if (province == 0 || city == 0){
            ToastUtils.showShort("请选择项目地址");
            return;
        }if (phases == 0){
            ToastUtils.showShort("请选择项目阶段 ");
            return;
        }if (types == 0){
            ToastUtils.showShort("请选择项目类别");
            return;
        }if (StringUtils.isTrimEmpty(start_time)){
            ToastUtils.showShort("请选择项目起始时间");
            return;
        }if (StringUtils.isTrimEmpty(end_time)){
            ToastUtils.showShort("请选择项目结束时间");
            return;
        }if (StringUtils.isTrimEmpty(acreage)){
            ToastUtils.showShort("请输入项目面积");
            return;
        }if (StringUtils.isTrimEmpty(valuation)){
            ToastUtils.showShort("请输入项目估价");
            return;
        }if (StringUtils.isTrimEmpty(intro)){
            ToastUtils.showShort("请输入项目详情");
            return;
        }if (StringUtils.isTrimEmpty(materials)){
            ToastUtils.showShort("请输入项目用材");
            return;
        }
        List<ContactBean> list = contactLiveData.getValue();
        if (list == null) return;
        String[] contact = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ContactBean bean = list.get(i);
            if (bean.getType() == null || bean.getProvince() == 0 || bean.getCity() == 0 || StringUtils.isTrimEmpty(bean.getName()) || StringUtils.isTrimEmpty(bean.getTel()) || StringUtils.isTrimEmpty(bean.getCompany())){
                ToastUtils.showShort("联系人信息不完整");
                return;
            }
            String json = new Gson().toJson(bean);
            contact[i] = json;
        }
        new ProjectRepository().addProject(releaseProjectLiveData, pageStateLiveData,userId, title, province, city, phases, types, start_time, end_time, acreage, valuation, intro, materials, longitude, latitude, contact);
    }
}
