package com.shuangduan.zcy.model.bean;


import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: SupplierRoleBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 18:09
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 18:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SupplierRoleBean {
    /**
     * manage_status : 3
     * role : [{"menu":"construction-list","status":1},{"menu":"construction-detail","status":1},{"menu":"construction-add","status":1},{"menu":"construction-edit","status":1},{"menu":"construction-delete","status":1},{"menu":"equipment-list","status":1},{"menu":"equipment-detail","status":1},{"menu":"equipment-add","status":1},{"menu":"equipment-edit","status":1},{"menu":"equipment-delete","status":1},{"menu":"equipment-order-list","status":1},{"menu":"equipment-order-detail","status":1},{"menu":"equipment-order-edit","status":1},{"menu":"construction-order-list","status":1},{"menu":"construction-order-detail","status":1},{"menu":"construction-order-edit","status":1}]
     */

    private int manage_status;
    private List<RoleBean> role;

    public int getManage_status() {
        return manage_status;
    }

    public void setManage_status(int manage_status) {
        this.manage_status = manage_status;
    }

    public List<RoleBean> getRole() {
        return role;
    }

    public void setRole(List<RoleBean> role) {
        this.role = role;
    }

    public static class RoleBean {
        /**
         * menu : construction-list
         * status : 1
         */

        private String menu;
        private int status;

        public String getMenu() {
            return menu;
        }

        public void setMenu(String menu) {
            this.menu = menu;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
