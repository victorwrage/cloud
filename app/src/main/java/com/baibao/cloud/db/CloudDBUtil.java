package com.baibao.cloud.db;

import android.content.Context;

import com.baibao.cloud.SynergyMerItem;
import com.baibao.cloud.SynergyOrderItem;
import com.baibao.cloud.WDTMerItem;
import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderItemInfo;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.socks.library.KLog;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/8 10:34
 */

public class CloudDBUtil {

    //TAG
    private static final String TAG = CloudDBUtil.class.getSimpleName();

    private DAOManager daoManager;

    //构造方法
    public CloudDBUtil(Context context) {
        daoManager = DAOManager.getInstance();
        daoManager.initManager(context);
    }

    /**
     * 对数据库中student表的插入操作
     *
     * @param item
     * @return
     */
    public boolean insertSynergyOrder(SynergyOrderItem item) {
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(item) != -1 ? true : false;
        return flag;
    }
    public boolean insertWDTMer(WDTMerItem item) {
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(item) != -1 ? true : false;
        return flag;
    }
    public boolean insertOrReplaceWDTMer(WandiantongItemInfo.OrderInfo item) {
        WDTMerItem wdtMerItem = new WDTMerItem((long)item.getCode().hashCode(),item.getCode(),item.getBarcode(),item.getType_code(),item.getName(),item.getUnit(),
        item.getStock(),item.getPrice(),item.getPurchase(),item.getAddress(),item.getCreatetime(),item.getMem_price(),item.getCompany_id(),item.getTotal_num(),
        item.getMinstock(),item.getRemark());

        boolean flag = false;
        try {
            flag = daoManager.getDaoSession().insertOrReplace(wdtMerItem) != -1 ? true : false;

        }catch(Exception e){
            e.fillInStackTrace();
        }
        return flag;
    }
    public boolean insertOrReplaceSynergyOrder(SynergyOrderInfo item) {
        SynergyOrderItem synergyMerItem = new SynergyOrderItem((long)item.getDdh().hashCode(),item.getDdh(),item.getQy(),item.getDdje(),item.getJhzt());
        boolean flag = false;
        try {
            flag = daoManager.getDaoSession().insertOrReplace(synergyMerItem) != -1 ? true : false;

        }catch(Exception e){
            e.fillInStackTrace();
        }
        return flag;
    }

    public boolean insertOrReplaceSynergyMer(SynergyOrderItemInfo item) {
        String id_str  = item.getDdh()+item.getSku();
        int ids = id_str.hashCode();
        KLog.v(ids+"");
        SynergyMerItem synergyMerItem = new SynergyMerItem((long)ids,item.getSku(),item.getDdh(),item.getCwpsl(),item.getAddress(),
        item.getCode(),item.getDw(),item.getWpdj(),item.getWpmc(),item.getWpsl());
        boolean flag = false;
        try {
            flag = daoManager.getDaoSession().insertOrReplace(synergyMerItem) != -1 ? true : false;

        }catch(Exception e){
            e.fillInStackTrace();
        }
        return flag;
    }



    /**
     * 修改
     *
     * @param
     * @return
     */
    public boolean updateWDTMerItem(WandiantongItemInfo.OrderInfo item) {
        WDTMerItem wdtMerItem = new WDTMerItem((long)item.getCode().hashCode(),item.getCode(),item.getBarcode(),item.getType_code(),item.getName(),item.getUnit(),
                item.getStock(),item.getPrice(),item.getPurchase(),item.getAddress(),item.getCreatetime(),item.getMem_price(),item.getCompany_id(),item.getTotal_num(),
                item.getMinstock(),item.getRemark());
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(wdtMerItem);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean deleteSynergyOrder(SynergyOrderInfo item) {
        SynergyOrderItem synergyOrderItem = new SynergyOrderItem((long)item.getDdh().hashCode());
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(synergyOrderItem);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }
    public boolean deleteSynergyMer(SynergyMerItem item) {

        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(item);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }


    /**
     * 查询单条
     *
     * @param key
     * @return
     */
    public SynergyOrderItem listOneSynergyOrderItem(long key) {
        return daoManager.getDaoSession().load(SynergyOrderItem.class, key);
    }

    public WDTMerItem listOneWDTMerItem(long key) {
        return daoManager.getDaoSession().load(WDTMerItem.class, key);
    }

    /**
     * 全部查询
     *
     * @return
     */
    public List<SynergyOrderItem> listAllSynergyOrder() {
        return daoManager.getDaoSession().loadAll(SynergyOrderItem.class);
    }
    public List<SynergyMerItem> listAllSynergyMer() {
        return daoManager.getDaoSession().loadAll(SynergyMerItem.class);
    }
    public List<WDTMerItem> listAllWDTMer() {
        return daoManager.getDaoSession().loadAll(WDTMerItem.class);
    }

    /**
     * 原生查询
     */
    public void queryNative() {
        //查询条件
        String where = "where name like ? and _id > ?";
        //使用sql进行查询
        List<SynergyOrderItem> list = daoManager.getDaoSession().queryRaw(SynergyOrderItem.class, where,
                new String[]{"%l%", "6"});
        KLog.i(TAG, list + "");
    }

    /**
     * QueryBuilder查询大于
     */
    public void queryBuilder() {
        //查询构建器
        QueryBuilder<SynergyOrderItem> queryBuilder = daoManager.getDaoSession().queryBuilder(SynergyOrderItem.class);
        //查询年龄大于19的北京
      //  List<SynergyOrderItem> list = queryBuilder.where(SynergyOrderItemDao.Properties.Age.ge(19)).where(SynergyOrderItemDao.Properties.Address.like("北京")).list();
    //    KLog.i(TAG, list + "");
    }
}
