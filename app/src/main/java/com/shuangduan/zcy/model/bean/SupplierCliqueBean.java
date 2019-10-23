package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/15 9:34
 * @change
 * @chang time
 * @class describe
 */
public class SupplierCliqueBean {
    /**
     * supplier_status : 3
     * supplier_id : [{"id":91},{"id":94}]
     */

    private int supplier_status;
    private List<SupplierIdBean> supplier_id;

    public int getSupplier_status() {
        return supplier_status;
    }

    public void setSupplier_status(int supplier_status) {
        this.supplier_status = supplier_status;
    }

    public List<SupplierIdBean> getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(List<SupplierIdBean> supplier_id) {
        this.supplier_id = supplier_id;
    }

    public static class SupplierIdBean {
        /**
         * id : 91
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
