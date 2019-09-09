package com.shuangduan.zcy.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class SharedUtils {

    private Map dataList = new HashMap<>();
    private static volatile SharedUtils instance;

    public static SharedUtils getInstance() {
        if(instance==null) {
            synchronized(SharedUtils.class) {
                if(instance==null) {
                    instance = new SharedUtils();
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
