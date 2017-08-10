package com.cxg.blueboothpinter2.provider;


import com.cxg.blueboothpinter2.pojo.Ztwm004;

/**
 * Ztwm004实体类的增删改查操作（本地的缓存中）
 * Created by Administrator on 2017/6/15.
 */

public class Ztwm004Bean extends Ztwm004 {

    public Ztwm004Bean() {
        super();
    }

    public Ztwm004Bean(String mandt, String zipcode, String charg, String zcupno, String werks,
                       String zkurno, String zbc, String zlinecode, String matnr, String zproddate,
                       String zinstock, String zoutstock, String mblnr, String mjahr, String menge,
                       String meins, String tanum, String zptflg, String zgrdate, String zlichn,
                       String lifnr, String znum, String zqcnum, String EMaktx, String EName1,
                       String EName2, String ILgmng, String IZlocco, String itZipcode) {
        super(mandt, zipcode, charg, zcupno, werks, zkurno, zbc, zlinecode, matnr,
                zproddate, zinstock, zoutstock, mblnr, mjahr, menge, meins, tanum,
                zptflg, zgrdate, zlichn, lifnr, znum, zqcnum, EMaktx, EName1, EName2, ILgmng, IZlocco, itZipcode);
    }

    /**
     * save实体类
     *
     * @return
     */
    public boolean save() {
        try {
            OrmHelper.getInstance().getZtwm004Dao().createOrUpdate(this);
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * update实体类
     *
     * @return
     */
    public boolean update() {
        try {
            boolean b = OrmHelper.getInstance().getZtwm004Dao().update(this) > -1;
            return b;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
