package com.shuangduan.zcy.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.model.bean$
 * @class AuthGroupBean$
 * @class describe
 * @time 2019/10/23 16:28
 * @change
 * @class 基建物资列表 授权组ID数组
 */
public class AuthGroupBean {
    public List<String> auth_group = new ArrayList<>();

    @Override
    public String toString() {
        return "AuthGroupBean{" +
                "auth_group=" + auth_group +
                '}';
    }
}
