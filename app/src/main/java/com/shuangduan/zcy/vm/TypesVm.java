package com.shuangduan.zcy.vm;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

import androidx.collection.SparseArrayCompat;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.MultiSelectBean;
import com.shuangduan.zcy.model.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe  工程信息列表类型分类
 * @time 2019/7/22 14:32
 * @change
 * @chang time
 * @class describe
 */
public class TypesVm extends BaseViewModel {
    public MutableLiveData<List<TypeBean>> typeFirstLiveData;
    public MutableLiveData<List<TypeBean>> typeSecondLiveData;
    public MutableLiveData<String> pageStateLiveData;
    private int userId;
    public int id;//分类id  0为一级
    public int positionFirstNow = 1;
    private String secondResult = "";
    private boolean firstInited = false;

    /**
     * 初始化
     */
    public void init(){
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        typeFirstLiveData = new MutableLiveData<>();
        typeSecondLiveData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        id = 0;
        getStageFirst();
    }

    public void getStageFirst(){
        new ProjectRepository().projectTypes(typeFirstLiveData, pageStateLiveData, userId, id);
    }

    /**
     * 设置省份默认数据
     */
    public void setTypeFirstInit(){
        if (!firstInited){
            List<TypeBean> list = typeFirstLiveData.getValue();
            //初始化省份数据，默认选中第一个省份
            if (list != null && list.size() > 0){
                //没有添加全部选项的添加
                if (!list.get(0).getCatname().equals("全部")){
                    TypeBean typeBean = new TypeBean();
                    typeBean.setCatname("全部");
                    typeBean.setId(0);
                    typeBean.setIsSelect(0);
                    list.add(0, typeBean);
                }

                //初始化选中一级第一个
                list.get(positionFirstNow).setIsSelect(1);
                typeFirstLiveData.postValue(list);

                firstInited = true;
            }
        }else {
            //省份被选定后才开始请求城市数据
            getSecond(positionFirstNow);
        }
    }

    public void getSecond(int position){
        id = typeFirstLiveData.getValue().get(position).getId();
        new ProjectRepository().projectTypes(typeSecondLiveData, pageStateLiveData, userId, id);
    }

    /**
     * 设置默认的城市状态
     */
    public void setTypeSecondInit(){
        List<TypeBean> cityList = typeSecondLiveData.getValue();
        if (cityList != null && cityList.size() > 0){
            //没有添加全部选项的添加
            if (!cityList.get(0).getCatname().equals("全部")){
                TypeBean typeBean = new TypeBean();
                typeBean.setCatname("全部");
                typeBean.setId(0);
                typeBean.setIsSelect(0);
                cityList.add(0, typeBean);
                typeSecondLiveData.postValue(cityList);
            }
        }
    }

    /**
     * 点击一级分类
     */
    public void clickFirst(int position){
//        if (positionFirstNow == position) return;
        List<TypeBean> provinceList = typeFirstLiveData.getValue();
        //选中状态：未选中0，选中1
//        provinceList.get(positionFirstNow).setIsSelect(0);
        provinceList.get(position).setIsSelect(1);
        typeFirstLiveData.postValue(provinceList);
//        positionFirstNow = position;
    }

    /**
     * 点击二级分类
     */
    public void clickSecond(int i){
        List<TypeBean> data = typeSecondLiveData.getValue();
        /*//点击城市后，如果存在上一个选择的省份，则将上一个省份的选中城市清空
            if (positionProvincePrevious >= 0){
                setCitySelectState(positionProvincePrevious);
            }*/

        if (i == 0) {
            //选全部
            if (data.get(0).getIsSelect() == 1){
                //全部选中，修改全部为非选中状态
                setSelectState(data, 0);
            }else {
                //非全部选中，修改全部为选中状态
                setSelectState(data, 1);
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
        typeSecondLiveData.postValue(data);
    }

    /**
     * 单选的二级列表点击
     */
    public void clickSecondSingle(int i){
        List<TypeBean> data = typeSecondLiveData.getValue();
        //单一选项
        if (data.get(i).getIsSelect() == 1) return;
        //选中状态，之前就是未选中状态
        setSelectState(data, 0);
        data.get(i).setIsSelect(1);
        typeSecondLiveData.postValue(data);
    }

    /**
     * 修改二级单一所有数据选中状态
     * @param i
     */
    private void setSelectState(List<TypeBean> data, int i){
        for (int j = 0; j < data.size(); j++) {
            data.get(j).setIsSelect(i);
        }
    }

    /**
     * 获取城市选择结果,amount
     */
    public int[] getResult(){
        List<TypeBean> dataCity = typeSecondLiveData.getValue();
        StringBuilder builder = new StringBuilder();
        int[] result = null;
        for (int i = 1; i < dataCity.size(); i++) {
            if (dataCity.get(i).getIsSelect() == 1) {
                builder.append(dataCity.get(i).getCatname());
                if (result == null){
                    result = new int[dataCity.size()];
                }
                result[i] = dataCity.get(i).getId();
            }
        }
        secondResult = builder.toString();
        return result;
    }

    /**
     * 获取城市选择结果,字符串
     */
    public String getStringResult(){
        if (!StringUtils.isTrimEmpty(secondResult))
            return secondResult;
        List<TypeBean> dataCity = typeSecondLiveData.getValue();
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < dataCity.size(); i++) {
            if (dataCity.get(i).getIsSelect() == 1) {
                builder.append(dataCity.get(i).getCatname());
            }
        }
        return builder.toString();
    }

    private String getKey(int i, int j){
        return i + "-" + j;
    }

    /**
     * 设置默认数据(不带全部)
     */
    public void setTypeFirstSingleInit(){
        if (!firstInited){
            List<TypeBean> list = typeFirstLiveData.getValue();
            //初始化省份数据，默认选中第一个省份
            if (list != null && list.size() > 0){
                //初始化选中一级第一个
                list.get(positionFirstNow).setIsSelect(1);
                typeFirstLiveData.postValue(list);

                firstInited = true;
            }
        }else {
            //省份被选定后才开始请求城市数据
            getSecond(positionFirstNow);
        }
    }

}
