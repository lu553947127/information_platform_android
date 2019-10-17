package com.shuangduan.zcy.weight;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.weight
 * @ClassName: DataHolder
 * @Description: 大文件数据传输工具
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 20:43
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 20:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DataHolder {

    private Map dataList = new HashMap<>();
    private static volatile DataHolder instance;

    public static DataHolder getInstance() {
        if(instance==null) {
            synchronized(DataHolder.class) {
                if(instance==null) {
                    instance = new DataHolder();
                }
            }
        }
        return instance;
    }

    public void setData(String key, Object o) {
        WeakReference value =new WeakReference<>(o);
        dataList.put(key, value);
    }

    public Object getData(String key) {
        WeakReference reference = (WeakReference) dataList.get(key);
        if(reference !=null) {
            Object o = reference.get();
            return o;
        }
        return null;
    }
}
