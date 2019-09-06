package com.shuangduan.zcy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {

    private Context ctx;//上下文，获取去shared

    public SharedUtils(Context ctx){
        this.ctx = ctx;
    }

    //本地存储方法
    public void addShared(String key, String value , String name){
        //SharedPreferences的获取
        SharedPreferences shared = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        //editor->添加并提交内容以及clear
        SharedPreferences.Editor editor = shared.edit();
        //添加要保存内容
        editor.putString(key, value);
        //提交要保存内容
        editor.commit();
    }

    //本地获取方法
    public String getShared(String key, String name){

        //SharedPreferences的获取
        SharedPreferences shared = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        //按照传入key获取value如果key不存在返回参数二添加的默认内容
        String result = shared.getString(key, "-1");
        return result;
    }

    //本地清空方法
    public void clearShared(String name){
        SharedPreferences shared = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
    }
}
