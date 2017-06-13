package com.baibao.cloud.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.baibao.cloud.SynergyOrderMerItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SYNERGY_ORDER_MER_ITEM".
*/
public class SynergyOrderMerItemDao extends AbstractDao<SynergyOrderMerItem, Long> {

    public static final String TABLENAME = "SYNERGY_ORDER_MER_ITEM";

    /**
     * Properties of entity SynergyOrderMerItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Sku = new Property(1, String.class, "sku", false, "SKU");
        public final static Property Qr_code = new Property(2, String.class, "qr_code", false, "QR_CODE");
        public final static Property Wpmc = new Property(3, String.class, "wpmc", false, "WPMC");
        public final static Property Item_code = new Property(4, String.class, "item_code", false, "ITEM_CODE");
        public final static Property Code = new Property(5, String.class, "code", false, "CODE");
        public final static Property Wpdj = new Property(6, String.class, "wpdj", false, "WPDJ");
        public final static Property Wpsl = new Property(7, String.class, "wpsl", false, "WPSL");
        public final static Property Dw = new Property(8, String.class, "dw", false, "DW");
        public final static Property Address = new Property(9, String.class, "address", false, "ADDRESS");
        public final static Property Status = new Property(10, Boolean.class, "status", false, "STATUS");
    };


    public SynergyOrderMerItemDao(DaoConfig config) {
        super(config);
    }
    
    public SynergyOrderMerItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SYNERGY_ORDER_MER_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SKU\" TEXT," + // 1: sku
                "\"QR_CODE\" TEXT," + // 2: qr_code
                "\"WPMC\" TEXT," + // 3: wpmc
                "\"ITEM_CODE\" TEXT," + // 4: item_code
                "\"CODE\" TEXT," + // 5: code
                "\"WPDJ\" TEXT," + // 6: wpdj
                "\"WPSL\" TEXT," + // 7: wpsl
                "\"DW\" TEXT," + // 8: dw
                "\"ADDRESS\" TEXT," + // 9: address
                "\"STATUS\" INTEGER);"); // 10: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SYNERGY_ORDER_MER_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SynergyOrderMerItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sku = entity.getSku();
        if (sku != null) {
            stmt.bindString(2, sku);
        }
 
        String qr_code = entity.getQr_code();
        if (qr_code != null) {
            stmt.bindString(3, qr_code);
        }
 
        String wpmc = entity.getWpmc();
        if (wpmc != null) {
            stmt.bindString(4, wpmc);
        }
 
        String item_code = entity.getItem_code();
        if (item_code != null) {
            stmt.bindString(5, item_code);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(6, code);
        }
 
        String wpdj = entity.getWpdj();
        if (wpdj != null) {
            stmt.bindString(7, wpdj);
        }
 
        String wpsl = entity.getWpsl();
        if (wpsl != null) {
            stmt.bindString(8, wpsl);
        }
 
        String dw = entity.getDw();
        if (dw != null) {
            stmt.bindString(9, dw);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(10, address);
        }
 
        Boolean status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(11, status ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SynergyOrderMerItem readEntity(Cursor cursor, int offset) {
        SynergyOrderMerItem entity = new SynergyOrderMerItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sku
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // qr_code
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // wpmc
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // item_code
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // code
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // wpdj
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // wpsl
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // dw
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // address
            cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0 // status
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SynergyOrderMerItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSku(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setQr_code(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setWpmc(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setItem_code(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setWpdj(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWpsl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDw(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAddress(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStatus(cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SynergyOrderMerItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SynergyOrderMerItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}