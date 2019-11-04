package com.shuangduan.zcy.adminManage.vm;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.adminManage.repository.TurnoverRepository;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.utils.DataUtils;

import java.util.Calendar;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.vm
 * @ClassName: TurnoverAddVm
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/4 10:54
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/4 10:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverAddVm extends BaseViewModel {

    private int userId;
    public int category;
    public int material_id;
    public String categoryName;
    public String materialName;
    public int unit;
    public int use_status;
    public int material_status;
    public int is_shelf;
    public int province;
    public int city;
    public String address;
    public double longitude;
    public double latitude;
    public String todayTime;
    public String shelf_start_time;
    public String shelf_end_time;
    public String images;

    public int unit_id;
    public MutableLiveData<String> turnoverAddData;
    public MutableLiveData<String> pageStateLiveData;

    public TurnoverAddVm() {
        userId = SPUtils.getInstance().getInt(SpConfig.USER_ID);
        turnoverAddData = new MutableLiveData<>();
        pageStateLiveData = new MutableLiveData<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        todayTime = DataUtils.formatTime(year, month, day);
    }

    //后台管理 --- 周转材料添加
    public void constructionAdd(String stock,String unit_price,String spec,String person_liable,String tel,int shelf_type,int method,String guidance_price
            ,int plan,String use_count,int start_date,int entry_time,String accumulated_amortization,String original_price,String net_worth, String remark){
        new TurnoverRepository().constructionAdd(turnoverAddData,userId,category,material_id,stock,unit_price,unit,spec,use_status,material_status
                ,province,city,address,longitude,latitude,person_liable,tel,is_shelf,shelf_start_time,shelf_end_time,shelf_type,method,guidance_price,images
                ,unit_id,plan,use_count,start_date,entry_time,accumulated_amortization,original_price,net_worth,remark);
    }
}
