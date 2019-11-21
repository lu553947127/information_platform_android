package com.shuangduan.zcy.vm;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.AddTrackBean;
import com.shuangduan.zcy.model.bean.ContactBean;
import com.shuangduan.zcy.model.bean.ContactListBean;
import com.shuangduan.zcy.model.bean.ContactTypeBean;
import com.shuangduan.zcy.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author 徐玉 QQ:876885613
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
    public int type = 1;//项目类型，1工程2动态
    public int editContactTypePos = 0;
    public int editContactAddressPos = 0;
    public String longitude, latitude, start_time, end_time;
    public int province, city, phases;
    public List<Integer> types = new ArrayList<>();
    public int projectId;
    private List<Integer> imageIds;
    public String todayTime;

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

        //设置时间为今天
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        todayTime = DateUtils.formatTime(year, month, day);
    }

    /**
     * 添加联系人
     */
    public void addContact() {
        List<ContactBean> list = contactLiveData.getValue();
        if (list != null) {
            list.add(new ContactBean());
            contactLiveData.postValue(list);
        }
    }

    /**
     * 删除联系人
     */
    public void delContact(int i) {
        List<ContactBean> list = contactLiveData.getValue();
        if (list != null && list.size() > 1) {
            list.remove(i);
            contactLiveData.postValue(list);
        }
    }

    public void getContactType() {
        new ProjectRepository().getContactType(contactTypeLiveData, pageStateLiveData, userId);
    }

    public void clickContactType(int position) {
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

    /**
     * 添加图片
     */
    public void addImage(int id) {
        if (imageIds == null) {
            imageIds = new ArrayList<>();
        }
        imageIds.add(id);
    }

    /**
     * 删除图片
     */
    public void delImage(int position) {
        if (imageIds != null) {
            imageIds.remove(position);
        }
    }

    public void releaseProject(String title, String company, String acreage, String valuation, String intro, String materials) {
        if (StringUtils.isTrimEmpty(title)) {
            ToastUtils.showShort("请输入项目名称");
            return;
        }
//        if (StringUtils.isTrimEmpty(company)) {
//            ToastUtils.showShort("请输入甲方公司");
//            return;
//        }
        if (province == 0 || city == 0) {
            ToastUtils.showShort("请选择项目地址");
            return;
        }
        if (phases == 0) {
            ToastUtils.showShort("请选择项目阶段 ");
            return;
        }
        if (types.size() == 0) {
            ToastUtils.showShort("请选择项目类别");
            return;
        }
        if (StringUtils.isTrimEmpty(start_time)) {
            ToastUtils.showShort("请选择项目起始时间");
            return;
        }
        if (StringUtils.isTrimEmpty(end_time)) {
            ToastUtils.showShort("请选择项目结束时间");
            return;
        }
//        if (StringUtils.isTrimEmpty(acreage)) {
//            ToastUtils.showShort("请输入项目面积");
//            return;
//        }
//        if (StringUtils.isTrimEmpty(valuation)) {
//            ToastUtils.showShort("请输入项目估价");
//            return;
//        }
        if (StringUtils.isTrimEmpty(intro)) {
            ToastUtils.showShort("请输入项目详情");
            return;
        }
//        if (StringUtils.isTrimEmpty(materials)) {
//            ToastUtils.showShort("请输入项目用材");
//            return;
//        }
        List<ContactBean> list = contactLiveData.getValue();
        if (list == null) return;
        for (int i = 0; i < list.size(); i++) {
            ContactBean bean = list.get(i);
//            if (StringUtils.isTrimEmpty(bean.getPhone_type())
//                    || bean.getProvince() == 0
//                    || bean.getCity() == 0
//                    || StringUtils.isTrimEmpty(bean.getName())
//                    || StringUtils.isTrimEmpty(bean.getTel())
//                    || StringUtils.isTrimEmpty(bean.getCompany())) {
//                ToastUtils.showShort("联系人信息不完整");
//                return;
//            }
            if (StringUtils.isTrimEmpty(bean.getName())) {
                ToastUtils.showShort("请输入联系人");
                return;
            }
            if (StringUtils.isTrimEmpty(bean.getTel())) {
                ToastUtils.showShort("请输入联系人手机号");
                return;
            }
        }
        ContactListBean contactListBean = new ContactListBean();
        contactListBean.setContact(list);
        contactListBean.setType(types);

        LogUtils.i(list);


        new ProjectRepository().addProject(releaseProjectLiveData, pageStateLiveData, userId, title, company, province, city, phases, start_time, end_time, acreage, valuation, intro, materials, longitude, latitude, contactListBean);
    }

    public void releaseLocus(String remarks, String name, String tel) {
        if (projectId == 0) {
            ToastUtils.showShort("请选择项目名称");
            return;
        }
        if (StringUtils.isTrimEmpty(name)) {
            ToastUtils.showShort("请输入拜访人");
            return;
        }
        if (StringUtils.isTrimEmpty(tel)) {
            ToastUtils.showShort("请输入电话");
            return;
        }
        if (StringUtils.isTrimEmpty(remarks)) {
            ToastUtils.showShort("请输入具体描述");
            return;
        }
        if (imageIds == null || imageIds.size() == 0) {
            ToastUtils.showShort("请上传图片");
            return;
        }
        @SuppressLint("SimpleDateFormat") String updateTime = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        new ProjectRepository().addTrack(releaseLocusLiveData, pageStateLiveData, userId, projectId, remarks, name, tel, updateTime, new AddTrackBean(imageIds));
    }
}
