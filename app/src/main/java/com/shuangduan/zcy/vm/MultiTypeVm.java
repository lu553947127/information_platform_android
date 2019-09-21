package com.shuangduan.zcy.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.api.repository.ProjectRepository;
import com.shuangduan.zcy.model.bean.CityBean;
import com.shuangduan.zcy.model.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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

            List<TypeBean> childList = new ArrayList<>();
            TypeBean childBean = new TypeBean();
            childBean.setIsSelect(1);
            childBean.setId(0);
            childBean.setCatname("全部");
            childList.add(childBean);
            typeBean.setChildList(childList);
            list.add(0, typeBean);

            typeFirstLiveData.postValue(list);
            typeSecondLiveData.postValue(list.get(0).getChildList());
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
    public void clickFirst(int position) {
        List<TypeBean> firstList = typeFirstLiveData.getValue();
        if (firstList == null) return;
        if (firstList.get(position).isCheck == 1) return;
        //当前点击的必选中
        firstList.get(position).setIsSelect(1);
        //上一个默认未选中
        firstList.get(prePosition).setIsSelect(0);
        //改变上一个选中效果，字体颜色
        List<TypeBean> childList = firstList.get(prePosition).getChildList();
        if (childList == null) return;
        if (childList.get(0).getIsSelect() != 1) {
            //非全选,遍历子类，判断是否修改字体效果
            for (TypeBean bean : childList) {
                if (bean.getIsSelect() == 1) {
                    //有一个选中就改变选中效果
                    firstList.get(prePosition).setIsSelect(1);
                    break;
                }
            }
        } else {
            //全选，直接改变选中效果
            firstList.get(prePosition).setIsSelect(1);
        }
        //点击状态：未点击0，点击1
        firstList.get(prePosition).setIsCheck(0);
        firstList.get(position).setIsCheck(1);
        typeFirstLiveData.postValue(firstList);
        //更新选中id, 更新二级列表
        id = firstList.get(position).getId();
        if (firstList.get(position).getChildList() == null) {
            //如果不存在存储的子类
            getTypesSecond();
        } else {
            //存在子类
            typeSecondLiveData.postValue(firstList.get(position).getChildList());
        }
        prePosition = position;
    }

    /**
     * 点击二级分类
     */
    public void clickSecond(int i) {
        List<TypeBean> data = typeSecondLiveData.getValue();
        if (i == 0) {
            LogUtils.i("***********", prePosition, data.get(0).getIsSelect());
            if (prePosition == 0) {//一级全部的全部
                List<TypeBean> firstList = typeFirstLiveData.getValue();
                if (data.get(0).getIsSelect() == 1) {//更改为所有全部非选中
                    selectAll = false;
                    if (firstList != null) {
                        for (int j = 0; j < firstList.size(); j++) {
                            TypeBean firstData = firstList.get(j);
                            firstData.setIsSelect(j == 0 ? 1 : 0);
                            List<TypeBean> childList = firstData.getChildList();
                            if (childList != null) {
                                for (int k = 0; k < childList.size(); k++) {
                                    childList.get(k).setIsSelect(0);
                                }
                            }
                        }
                    }
                } else {//更改为所有全部选中
                    selectAll = true;
                    if (firstList != null) {
                        for (int j = 0; j < firstList.size(); j++) {
                            TypeBean firstData = firstList.get(j);
                            List<TypeBean> childList = firstData.getChildList();
                            firstData.setIsSelect(1);
                            if (childList != null) {
                                for (int k = 0; k < childList.size(); k++) {
                                    childList.get(k).setIsSelect(1);
                                }
                            }
                        }
                    }
                }
                typeSecondLiveData.postValue(firstList.get(0).getChildList());
                typeFirstLiveData.postValue(firstList);
            } else {//一级非全部的全部
                //选全部
                if (data.get(0).getIsSelect() == 1) {
                    //全部选中，修改全部为非选中状态
                    for (int j = 0; j < data.size(); j++) {
                        data.get(j).setIsSelect(0);
                    }
                } else {
                    //非全部选中，修改全部为选中状态
                    for (int j = 0; j < data.size(); j++) {
                        data.get(j).setIsSelect(1);
                    }
                }
                typeSecondLiveData.postValue(data);
                changeFirstState();
            }
        } else {
            //单一选项
            data.get(i).setIsSelect(data.get(i).getIsSelect() == 1 ? 0 : 1);
            if (data.get(i).getIsSelect() != 1) {
                //未选中状态，之前就是选中状态，需判断是否为全部选中，是则修改全部为非选中状态
                if (data.get(0).getIsSelect() == 1) {
                    //只有“全部”选项是选中状态才更新状态
                    data.get(0).setIsSelect(0);
                }
            } else {
                //选中状态，之前就是未选中状态，需判断是否为全部选中，是则修改全部为选中状态
                //初始把“全部”更新为选中
                data.get(0).setIsSelect(1);
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).getIsSelect() != 1) {
                        //循环查询，出现未选中就更新第一个选项“全部”的状态
                        data.get(0).setIsSelect(0);
                        break;
                    }
                }
            }
            typeSecondLiveData.postValue(data);
            changeFirstState();
        }
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

    private StringBuilder stringBuilder;

    public List<Integer> getFirstSecondIds() {
        List<TypeBean> firstList = typeFirstLiveData.getValue();
        stringBuilder = new StringBuilder();
        List<Integer> result = new ArrayList<>();
        if (firstList != null) {
            for (int i = 0; i < firstList.size(); i++) {
                if (i == 0 && firstList.get(i).getChildList().get(0).getIsSelect() == 1) {
                    //全选，返回null
                    return null;
                } else if (firstList.get(i).isSelect == 1) {
                    result.add(firstList.get(i).getId());
                    if (stringBuilder.length() == 0) {
                        stringBuilder.append(firstList.get(i).getCatname());
                    } else {
                        stringBuilder.append(",").append(firstList.get(i).getCatname());
                    }
                    List<TypeBean> secondList = firstList.get(i).getChildList();
                    if (secondList != null) {
                        for (int j = 0; j < secondList.size(); j++) {
                            if (secondList.get(j).isSelect == 1) {
                                result.add(secondList.get(j).getId());
                                stringBuilder.append(",").append(secondList.get(j).getCatname());
                            }
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }

    public List<Integer> getSecondIds() {
        List<TypeBean> firstList = typeFirstLiveData.getValue();
        stringBuilder = new StringBuilder();
        List<Integer> result = new ArrayList<>();

        if (prePosition == 0) { //默认选中全部类型
            //全选，返回null
            return null;
        }

        //获取当前一级类型下所有子类型
        List<TypeBean> subTypeList = firstList.get(prePosition).getChildList();
        if (subTypeList != null) {
            for (TypeBean item : subTypeList) {
                if (item.isSelect == 1) {
                    //去掉id为0的类型
                    if (item.getId() == 0) continue;
                    result.add(item.getId());
                }
            }
        }
        return result;

//        if (firstList != null){
//            for (int i = 0; i < firstList.size(); i++) {
//                if (i == 0 && firstList.get(i).getChildList().get(0).getIsSelect() == 1){
//                    //全选，返回null
//                    return null;
//                }else if (firstList.get(i).isSelect == 1){
//                    List<TypeBean> secondList = firstList.get(i).getChildList();
//                    if (secondList != null){
//                        for (int j = 0; j < secondList.size(); j++) {
//                            if (secondList.get(j).isSelect == 1){
//                                result.add(secondList.get(j).getId());
//                                if (stringBuilder.length() == 0){
//                                    stringBuilder.append(secondList.get(j).getCatname());
//                                }else {
//                                    stringBuilder.append(",").append(secondList.get(j).getCatname());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            return result;
//        }
//        return null;
    }

    public String getStringResult() {
        return stringBuilder != null ? stringBuilder.toString() : "";
    }
}
