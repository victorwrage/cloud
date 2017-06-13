package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/5/8 10:11
 */

public class DAOMaker {

    public static void main(String[] args) {
        //生成数据库的实体类,还有版本号
        Schema schema = new Schema(1, "com.baibao.cloud");
        addOrderItem(schema);
        addSynergyPostItem(schema);
        addWDTMerItem(schema);
        //指定dao
        schema.setDefaultJavaPackageDao("com.baibao.cloud.dao");
        try {
            //指定路径
            new DaoGenerator().generateAll(schema, "D:\\work\\cloud\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addOrderItem(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("SynergyOrderItem");
        //主键 是int类型
        entity.addIdProperty();
        //名称
        entity.addStringProperty("ddh");
        entity.addStringProperty("qy");
        entity.addStringProperty("ddje");
        entity.addIntProperty("jhzt");
    }
    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addSynergyPostItem(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("SynergyMerItem");
        //主键 是int类型
        entity.addIdProperty();
        //名称
        entity.addStringProperty("sku");
        entity.addStringProperty("ddh");
        entity.addStringProperty("wpsl");
        entity.addStringProperty("address");
        entity.addStringProperty("pcode");
        entity.addStringProperty("unit");
        entity.addStringProperty("price");
        entity.addStringProperty("name");
        entity.addStringProperty("number");
    }




    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addWDTMerItem(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("WDTMerItem");
        //主键 是int类型
        entity.addIdProperty();
        //名称
        entity.addStringProperty("code");
        entity.addStringProperty("barcode");
        entity.addStringProperty("type_code");
        entity.addStringProperty("name");
        entity.addStringProperty("unit");
        entity.addStringProperty("stock");
        entity.addStringProperty("price");
        entity.addStringProperty("purchase");
        entity.addStringProperty("address");
        entity.addStringProperty("createtime");
        entity.addStringProperty("mem_price");
        entity.addStringProperty("company_id");
        entity.addStringProperty("total_num");
        entity.addStringProperty("minstockm");
        entity.addStringProperty("remark");
    }

}
