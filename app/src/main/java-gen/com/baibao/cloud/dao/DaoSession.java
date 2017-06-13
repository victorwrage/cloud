package com.baibao.cloud.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.baibao.cloud.SynergyOrderItem;
import com.baibao.cloud.SynergyMerItem;
import com.baibao.cloud.WDTMerItem;

import com.baibao.cloud.dao.SynergyOrderItemDao;
import com.baibao.cloud.dao.SynergyMerItemDao;
import com.baibao.cloud.dao.WDTMerItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig synergyOrderItemDaoConfig;
    private final DaoConfig synergyMerItemDaoConfig;
    private final DaoConfig wDTMerItemDaoConfig;

    private final SynergyOrderItemDao synergyOrderItemDao;
    private final SynergyMerItemDao synergyMerItemDao;
    private final WDTMerItemDao wDTMerItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        synergyOrderItemDaoConfig = daoConfigMap.get(SynergyOrderItemDao.class).clone();
        synergyOrderItemDaoConfig.initIdentityScope(type);

        synergyMerItemDaoConfig = daoConfigMap.get(SynergyMerItemDao.class).clone();
        synergyMerItemDaoConfig.initIdentityScope(type);

        wDTMerItemDaoConfig = daoConfigMap.get(WDTMerItemDao.class).clone();
        wDTMerItemDaoConfig.initIdentityScope(type);

        synergyOrderItemDao = new SynergyOrderItemDao(synergyOrderItemDaoConfig, this);
        synergyMerItemDao = new SynergyMerItemDao(synergyMerItemDaoConfig, this);
        wDTMerItemDao = new WDTMerItemDao(wDTMerItemDaoConfig, this);

        registerDao(SynergyOrderItem.class, synergyOrderItemDao);
        registerDao(SynergyMerItem.class, synergyMerItemDao);
        registerDao(WDTMerItem.class, wDTMerItemDao);
    }
    
    public void clear() {
        synergyOrderItemDaoConfig.getIdentityScope().clear();
        synergyMerItemDaoConfig.getIdentityScope().clear();
        wDTMerItemDaoConfig.getIdentityScope().clear();
    }

    public SynergyOrderItemDao getSynergyOrderItemDao() {
        return synergyOrderItemDao;
    }

    public SynergyMerItemDao getSynergyMerItemDao() {
        return synergyMerItemDao;
    }

    public WDTMerItemDao getWDTMerItemDao() {
        return wDTMerItemDao;
    }

}