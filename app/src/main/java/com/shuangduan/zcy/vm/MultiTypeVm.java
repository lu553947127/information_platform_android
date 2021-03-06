package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.StageBean;
import com.shuangduan.zcy.model.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/8 15:57
 * @change
 * @chang time
 * @class describe
 */
public class MultiTypeVm extends BaseViewModel {
    public MutableLiveData<List<TypeBean>> typeFirstLiveData;
    public MutableLiveData<List<TypeBean>> typeSecondLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int id;//分类id  0为一级
    private String secondResult = "";
    public boolean firstInited = false;
    public int prePosition = 0;//上一个选中的位置
    private boolean selectAll = true;

    public MultiTypeVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        typeFirstLiveData = new MutableLiveData<>();
        typeSecondLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        id = 0;
    }

    public void getTypesFirst() {
        new ProjectRepository().projectTypes(typeFirstLiveData, pageStateLiveData, userId, id);
    }

    public void getTypesSecond() {
        new ProjectRepository().projectTypes(typeSecondLiveData, pageStateLiveData, userId, id);
    }

    /**
     * 设置一级默认数据
     */
    public void setTypeFirstInit() {

        if (!firstInited) {
            firstInited = true;
            List<TypeBean> list = typeFirstLiveData.getValue();
            if (list == null) return;
            for (TypeBean bean : list) {
                //默认全选
                bean.setIsSelect(1);
            }
            TypeBean typeBean = new TypeBean();
            typeBean.setCatname("全部");
            typeBean.setId(0);
            typeBean.setIsSelect(1);
            typeBean.setIsCheck(1);

            list.add(0, typeBean);
            typeFirstLiveData.postValue(list);
        }
    }

    /**
     * 设置默认的城市状态
     */
    public void setTypeSecondInit() {
        List<TypeBean> secondList = typeSecondLiveData.getValue();
        if (secondList != null && secondList.size() > 0) {
            //没有添加全部选项的添加
            if (!secondList.get(0).getCatname().equals("全部")) {
                for (TypeBean bean : secondList) {
                    //默认全选
                    bean.setIsSelect(selectAll ? 1 : 0);
                }

                TypeBean typeBean = new TypeBean();
                typeBean.setCatname("全部");
                typeBean.setId(0);
                typeBean.setIsSelect(selectAll ? 1 : 0);
                secondList.add(0, typeBean);
                typeSecondLiveData.postValue(secondList);
            }
            //二级数据存储
            typeFirstLiveData.getValue().get(prePosition).setChildList(secondList);
        }
    }

    /**
     * 点击一级分类
     */
    public void clickFirst(int i) {
        List<TypeBean> data = typeFirstLiveData.getValue();
        if (i == 0) {
            //选全部
            if (data.get(0).getIsSelect() == 1){
                //全部选中，修改全部为非选中状态
                for (int j = 0; j < data.size(); j++) {
                    data.get(j).setIsSelect(0);
                }
            }else {
                //非全部选中，修改全部为选中状态
                for (int j = 0; j < data.size(); j++) {
                    data.get(j).setIsSelect(1);
                }
            }
        }else {
            //单一选项
            data.get(i).setIsSelect(data.get(i).getIsSelect() == 1?0:1);
            if (data.get(i).getIsSelect() != 1){
                //未选中状态，之前就是选中状态，需判断是否为全部选中，是则修改全部为非选中状态
                if (data.get(0).getIsSelect() == 1){
                    //只有“全部”选项是选中状态才更新状态
                    data.get(0).setIsSelect(0);
                }
            }else {
                //选中状态，之前就是未选中状态，需判断是否为全部选中，是则修改全部为选中状态
                //初始把“全部”更新为选中
                data.get(0).setIsSelect(1);
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).getIsSelect() != 1){
                        //循环查询，出现未选中就更新第一个选项“全部”的状态
                        data.get(0).setIsSelect(0);
                        break;
                    }
                }
            }
        }
        typeFirstLiveData.postValue(data);
    }


    private void changeFirstState() {
        List<TypeBean> firstData = typeFirstLiveData.getValue();

        //默认全选
        firstData.get(0).setIsSelect(1);
        firstData.get(0).getChildList().get(0).setIsSelect(1);
        for (int i = 0; i < firstData.size(); i++) {
            //如果有省份没有被选中，则为非全选
            if (firstData.get(i).isSelect != 1) {
                firstData.get(0).setIsSelect(0);
                firstData.get(0).getChildList().get(0).setIsSelect(0);
                break;
            }
            //如果省份中的城市有没有被选中，则为非全选
            List<TypeBean> childList = firstData.get(i).getChildList();
            if (childList != null) {
                if (childList.get(0).getIsSelect() != 1) {
                    firstData.get(0).setIsSelect(0);
                    firstData.get(0).getChildList().get(0).setIsSelect(0);
                    break;
                }
            }
        }

        if (firstData.get(0).getChildList().get(0).getIsSelect() == 1) {
            selectAll = true;//只在全部选中的时候改变为true
        } else {
            selectAll = false;
        }

        typeFirstLiveData.postValue(firstData);
    }


    public List<Integer> getSecondIds() {

        List<TypeBean> value = typeFirstLiveData.getValue();
        if (value == null || value.size() == 0 || value.get(0).getIsSelect() == 1){
            return null;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            if (value.get(i).getIsSelect() == 1){
                result.add(value.get(i).getId());
            }
        }
        return result.size() != 0? result : null;

    }

}
