package com.szxb.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.szxb.entity.HomeInfoEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HOME_INFO_ENTITY".
*/
public class HomeInfoEntityDao extends AbstractDao<HomeInfoEntity, Long> {

    public static final String TABLENAME = "HOME_INFO_ENTITY";

    /**
     * Properties of entity HomeInfoEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property NoId = new Property(1, int.class, "noId", false, "NO_ID");
        public final static Property GasNo = new Property(2, String.class, "gasNo", false, "GAS_NO");
        public final static Property GasUnitPrice = new Property(3, String.class, "gasUnitPrice", false, "GAS_UNIT_PRICE");
        public final static Property GasMemberUnitPrice = new Property(4, String.class, "gasMemberUnitPrice", false, "GAS_MEMBER_UNIT_PRICE");
        public final static Property GasCapacity = new Property(5, String.class, "gasCapacity", false, "GAS_CAPACITY");
        public final static Property GasMoney = new Property(6, String.class, "gasMoney", false, "GAS_MONEY");
        public final static Property GasMemberMoney = new Property(7, String.class, "gasMemberMoney", false, "GAS_MEMBER_MONEY");
        public final static Property GasPayStatus = new Property(8, String.class, "gasPayStatus", false, "GAS_PAY_STATUS");
        public final static Property GasOrderNo = new Property(9, String.class, "gasOrderNo", false, "GAS_ORDER_NO");
        public final static Property XbOrderNo = new Property(10, String.class, "xbOrderNo", false, "XB_ORDER_NO");
        public final static Property GasDate = new Property(11, String.class, "gasDate", false, "GAS_DATE");
        public final static Property GasTime = new Property(12, String.class, "gasTime", false, "GAS_TIME");
    }


    public HomeInfoEntityDao(DaoConfig config) {
        super(config);
    }
    
    public HomeInfoEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HOME_INFO_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NO_ID\" INTEGER NOT NULL ," + // 1: noId
                "\"GAS_NO\" TEXT," + // 2: gasNo
                "\"GAS_UNIT_PRICE\" TEXT," + // 3: gasUnitPrice
                "\"GAS_MEMBER_UNIT_PRICE\" TEXT," + // 4: gasMemberUnitPrice
                "\"GAS_CAPACITY\" TEXT," + // 5: gasCapacity
                "\"GAS_MONEY\" TEXT," + // 6: gasMoney
                "\"GAS_MEMBER_MONEY\" TEXT," + // 7: gasMemberMoney
                "\"GAS_PAY_STATUS\" TEXT," + // 8: gasPayStatus
                "\"GAS_ORDER_NO\" TEXT UNIQUE ," + // 9: gasOrderNo
                "\"XB_ORDER_NO\" TEXT," + // 10: xbOrderNo
                "\"GAS_DATE\" TEXT," + // 11: gasDate
                "\"GAS_TIME\" TEXT);"); // 12: gasTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HOME_INFO_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HomeInfoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getNoId());
 
        String gasNo = entity.getGasNo();
        if (gasNo != null) {
            stmt.bindString(3, gasNo);
        }
 
        String gasUnitPrice = entity.getGasUnitPrice();
        if (gasUnitPrice != null) {
            stmt.bindString(4, gasUnitPrice);
        }
 
        String gasMemberUnitPrice = entity.getGasMemberUnitPrice();
        if (gasMemberUnitPrice != null) {
            stmt.bindString(5, gasMemberUnitPrice);
        }
 
        String gasCapacity = entity.getGasCapacity();
        if (gasCapacity != null) {
            stmt.bindString(6, gasCapacity);
        }
 
        String gasMoney = entity.getGasMoney();
        if (gasMoney != null) {
            stmt.bindString(7, gasMoney);
        }
 
        String gasMemberMoney = entity.getGasMemberMoney();
        if (gasMemberMoney != null) {
            stmt.bindString(8, gasMemberMoney);
        }
 
        String gasPayStatus = entity.getGasPayStatus();
        if (gasPayStatus != null) {
            stmt.bindString(9, gasPayStatus);
        }
 
        String gasOrderNo = entity.getGasOrderNo();
        if (gasOrderNo != null) {
            stmt.bindString(10, gasOrderNo);
        }
 
        String xbOrderNo = entity.getXbOrderNo();
        if (xbOrderNo != null) {
            stmt.bindString(11, xbOrderNo);
        }
 
        String gasDate = entity.getGasDate();
        if (gasDate != null) {
            stmt.bindString(12, gasDate);
        }
 
        String gasTime = entity.getGasTime();
        if (gasTime != null) {
            stmt.bindString(13, gasTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HomeInfoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getNoId());
 
        String gasNo = entity.getGasNo();
        if (gasNo != null) {
            stmt.bindString(3, gasNo);
        }
 
        String gasUnitPrice = entity.getGasUnitPrice();
        if (gasUnitPrice != null) {
            stmt.bindString(4, gasUnitPrice);
        }
 
        String gasMemberUnitPrice = entity.getGasMemberUnitPrice();
        if (gasMemberUnitPrice != null) {
            stmt.bindString(5, gasMemberUnitPrice);
        }
 
        String gasCapacity = entity.getGasCapacity();
        if (gasCapacity != null) {
            stmt.bindString(6, gasCapacity);
        }
 
        String gasMoney = entity.getGasMoney();
        if (gasMoney != null) {
            stmt.bindString(7, gasMoney);
        }
 
        String gasMemberMoney = entity.getGasMemberMoney();
        if (gasMemberMoney != null) {
            stmt.bindString(8, gasMemberMoney);
        }
 
        String gasPayStatus = entity.getGasPayStatus();
        if (gasPayStatus != null) {
            stmt.bindString(9, gasPayStatus);
        }
 
        String gasOrderNo = entity.getGasOrderNo();
        if (gasOrderNo != null) {
            stmt.bindString(10, gasOrderNo);
        }
 
        String xbOrderNo = entity.getXbOrderNo();
        if (xbOrderNo != null) {
            stmt.bindString(11, xbOrderNo);
        }
 
        String gasDate = entity.getGasDate();
        if (gasDate != null) {
            stmt.bindString(12, gasDate);
        }
 
        String gasTime = entity.getGasTime();
        if (gasTime != null) {
            stmt.bindString(13, gasTime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public HomeInfoEntity readEntity(Cursor cursor, int offset) {
        HomeInfoEntity entity = new HomeInfoEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // noId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // gasNo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // gasUnitPrice
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // gasMemberUnitPrice
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // gasCapacity
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // gasMoney
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // gasMemberMoney
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // gasPayStatus
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // gasOrderNo
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // xbOrderNo
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // gasDate
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // gasTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HomeInfoEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNoId(cursor.getInt(offset + 1));
        entity.setGasNo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGasUnitPrice(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGasMemberUnitPrice(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGasCapacity(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGasMoney(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGasMemberMoney(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setGasPayStatus(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setGasOrderNo(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setXbOrderNo(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setGasDate(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setGasTime(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HomeInfoEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HomeInfoEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HomeInfoEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
