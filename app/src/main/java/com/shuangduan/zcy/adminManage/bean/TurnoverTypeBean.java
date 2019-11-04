package com.shuangduan.zcy.adminManage.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.bean
 * @ClassName: TurnoverTypeBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/29 11:18
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/29 11:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverTypeBean {
    private List<IsShelfBean> is_shelf;
    private List<UseStatusBean> use_status;
    private List<UnitBean> unit;
    private List<MaterialStatusBean> material_status;
    private List<PlanBean> plan;

    public List<IsShelfBean> getIs_shelf() {
        return is_shelf;
    }

    public void setIs_shelf(List<IsShelfBean> is_shelf) {
        this.is_shelf = is_shelf;
    }

    public List<UseStatusBean> getUse_status() {
        return use_status;
    }

    public void setUse_status(List<UseStatusBean> use_status) {
        this.use_status = use_status;
    }

    public List<UnitBean> getUnit() {
        return unit;
    }

    public void setUnit(List<UnitBean> unit) {
        this.unit = unit;
    }

    public List<MaterialStatusBean> getMaterial_status() {
        return material_status;
    }

    public void setMaterial_status(List<MaterialStatusBean> material_status) {
        this.material_status = material_status;
    }

    public List<PlanBean> getPlan() {
        return plan;
    }

    public void setPlan(List<PlanBean> plan) {
        this.plan = plan;
    }

    public static class IsShelfBean {
        /**
         * id : 1
         * name : 公开上架
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UseStatusBean {
        /**
         * id : 1
         * name : 再用
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UnitBean {
        /**
         * id : 1
         * name : 台
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MaterialStatusBean {
        /**
         * id : 1
         * name : 完好
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PlanBean {
        /**
         * id : 1
         * name : 一直使用
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
