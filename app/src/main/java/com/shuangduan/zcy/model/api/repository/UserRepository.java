package com.shuangduan.zcy.model.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.model.bean.AuthenBean;
import com.shuangduan.zcy.model.bean.BankCardBean;
import com.shuangduan.zcy.model.bean.BankCardDisBean;
import com.shuangduan.zcy.model.bean.BusinessAreaBean;
import com.shuangduan.zcy.model.bean.MaterialCollectBean;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.model.bean.ProjectCollectBean;
import com.shuangduan.zcy.model.bean.PwdPayStateBean;
import com.shuangduan.zcy.model.bean.ReadHistoryBean;
import com.shuangduan.zcy.model.bean.ReceivingAddressBean;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.model.bean.SubBean;
import com.shuangduan.zcy.model.bean.TransRecordBean;
import com.shuangduan.zcy.model.bean.TransRecordDetailBean;
import com.shuangduan.zcy.model.bean.TransRecordFilterBean;
import com.shuangduan.zcy.model.bean.TransactionFlowTypeBean;
import com.shuangduan.zcy.model.bean.UserInfoBean;
import com.shuangduan.zcy.model.bean.WithdrawBean;
import com.shuangduan.zcy.model.bean.WithdrawRecordBean;
import com.shuangduan.zcy.model.event.CityEvent;
import com.shuangduan.zcy.model.event.MultiAreaEvent;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/18 16:25
 * @change
 * @chang time
 * @class describe
 */
public class UserRepository extends BaseRepository {
    /**
     * 录入信息
     */
    public void setInfo(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String username, int sex, String company, String position, BusinessAreaBean business_city, int experience, String managing_products) {
        request(apiService.setInfo(user_id, username, sex, company, position, business_city, experience, managing_products)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 我的界面
     */
    public void userInfo(MutableLiveData<UserInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.userInfo(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 修改登录密码
     */
    public void resetPassword(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String oldPwd, String newPwd) {
        request(apiService.resetPassword(user_id, oldPwd, newPwd)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 个人信息
     */
    public void information(MutableLiveData<UserInfoBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int uid) {
        request(apiService.information(user_id, uid)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新用户名
     */
    public void updateUserName(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String username) {
        request(apiService.updateUserName(user_id, username)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新头像
     */
    public void updateAvatar(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String avatar) {
        request(apiService.updateAvatar(user_id, avatar)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新性别
     */
    public void updateSex(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int sex) {
        request(apiService.updateSex(user_id, sex)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新公司
     */
    public void updateCompany(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String company) {
        request(apiService.updateCompany(user_id, company)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新职位
     */
    public void updatePosition(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String position) {
        request(apiService.updatePosition(user_id, position)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新城市
     */
    public void updateBusinessCity(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, BusinessAreaBean businessCity) {
        request(apiService.updateBusinessCity(user_id, businessCity)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新经验
     */
    public void updateExperience(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int experience) {
        request(apiService.updateExperience(user_id, experience)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 更新产品
     */
    public void updateProduct(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String product) {
        request(apiService.updateProduct(user_id, product)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 旧手机验证
     */
    public void checkTel(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String code) {
        request(apiService.telCheck(user_id, code)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 旧手机验证
     */
    public void updateTel(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String tel, String code, String oldTel, String oldCode) {
        request(apiService.telUpdate(user_id, tel, code, oldTel, oldCode)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 工程推送阶段选项
     */
    public void myPhases(MutableLiveData<List<MyPhasesBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.myPhases(user_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 工程推送阶段提交
     */
    public void setPhases(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, SubBean id) {
        request(apiService.setPhases(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 浏览历史-工程信息
     */
    public void historyProject(MutableLiveData<List<ReadHistoryBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.historyProject(user_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 浏览历史-招采信息
     */
    public void historyRecruit(MutableLiveData<List<ReadHistoryBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.historyTenderer(user_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 意见反馈
     */
    public void feedback(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String content) {
        request(apiService.feedback(user_id, content)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 我的收藏-工程信息
     */
    public void projectCollection(MutableLiveData<ProjectCollectBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page) {
        request(apiService.projectCollection(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 我的收藏-工程信息
     */
    public void recruitCollection(MutableLiveData<RecruitBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page) {
        request(apiService.recruitCollection(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }


    /**
     * 我的收藏-物资信息周转材料
     */
    public void materialCollection(MutableLiveData<MaterialCollectBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page) {
        request(apiService.materialCollection(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 我的收藏-设备物资
     */
    public void mineEquipmentCollection(MutableLiveData<MaterialCollectBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page) {
        request(apiService.mineEquipmentCollection(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 实名认证检测
     */
    public void authentication(MutableLiveData<AuthenBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.authentication(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 身份认证
     */
    public void idcard(MutableLiveData<AuthenBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String image_front, String image_reverse_site) {
        request(apiService.idcard(user_id, image_front, image_reverse_site)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 提现信息
     */
    public void withdrawMsg(MutableLiveData<WithdrawBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.withdrawMsg(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 银行卡列表
     */
    public void bankcardList(MutableLiveData<List<BankCardBean>> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.bankcardList(user_id)).setDataList(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 银行卡识别
     */
    public void bankcardDis(MutableLiveData<BankCardDisBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, String image) {
        request(apiService.bankcardDis(user_id, image)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 银行卡添加
     */
    public void bankcardAdd(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String bankcard_num, String opening_bank) {
        request(apiService.bankcardAdd(user_id, bankcard_num, opening_bank)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 银行卡解绑
     */
    public void unbindBankcard(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int bankcard_id) {
        request(apiService.unbindBankcard(user_id, bankcard_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 提现记录
     */
    public void withdrawRecord(MutableLiveData<WithdrawRecordBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page) {
        request(apiService.withdrawRecord(user_id, page)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 提现详情
     */
    public void withdrawRecordDetail(MutableLiveData<WithdrawRecordBean.ListBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id) {
        request(apiService.withdrawRecordDetail(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    /**
     * 提现
     */
    public void withdraw(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, int bankcard_id, String price) {
        request(apiService.withdraw(user_id, bankcard_id, price)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void setPwdPay(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String password, String tel, String code) {
        request(apiService.setPwdPay(user_id, password, tel, code)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void updatePwdPay(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String old_password, String password) {
        request(apiService.updatePwdPay(user_id, old_password, password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void forgetPwdPay(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int user_id, String tel, String code, String password) {
        request(apiService.forgetPwdPay(user_id, tel, code, password)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void pwdPayState(MutableLiveData<PwdPayStateBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.pwdPayState(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void transRecord(MutableLiveData<TransRecordBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int page, int type, TransactionFlowTypeBean flow_ype) {
        request(apiService.transactionRecord(user_id, page, type, flow_ype)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void transRecordDetail(MutableLiveData<TransRecordDetailBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id, int id) {
        request(apiService.transRecordDetail(user_id, id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    public void transRecordFilter(MutableLiveData<TransRecordFilterBean> liveData, MutableLiveData<String> pageStateLiveData, int user_id) {
        request(apiService.transRecordFilter(user_id)).setData(liveData).setPageState(pageStateLiveData).send();
    }


    //收货地址
    public void receivingAddress(MutableLiveData<ReceivingAddressBean> liveData, MutableLiveData<String> pageStateLiveData, int userId) {
        request(apiService.getReceivingAddress(userId)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //设置地址的默认状态
    public void setDefaultState(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int id,int state) {
        request(apiService.setDefaultState(id,state)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //删除地址
    public void deleteAddress(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int id) {
        request(apiService.deleteAddress(id)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //新增地址
    public void newAddress(MutableLiveData liveData, MutableLiveData<String> pageStateLiveData, int userId,String name,String phone,String company,String address,int state) {
        request(apiService.newAddress(userId,name,phone,company,address,state)).setData(liveData).setPageState(pageStateLiveData).send();
    }

    //修改地址
    public void editAddress(MutableLiveData liveData,MutableLiveData<String> pageStateLiveData,int userId,int id,String name,String phone,String company,String address,int state){
        request(apiService.editAddress(userId,id,name,phone,company,address,state)).setData(liveData).setPageState(pageStateLiveData).send();
    }

}
