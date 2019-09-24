package com.shuangduan.zcy.model.event;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.model.event$
 * @class MaterialEvent$
 * @class 基建物资商品名称
 * @time 2019/9/24 16:33
 * @change
 * @class describe
 */
public class SupplierEvent {
    public int id;
    public String supplier;

    public SupplierEvent(int id, String supplier) {
        this.id = id;
        this.supplier = supplier;
    }
}
