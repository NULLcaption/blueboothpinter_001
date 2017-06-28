package com.cxg.blueboothpinter2.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

/**
 * description: 接口对象
 * author: xg.chen
 * date: 2017/6/27 14:05
 * version: 1.0
*/
@DatabaseTable(tableName = "ZwmRfcIts002")
public class ZwmRfcIts002  implements Serializable,KvmSerializable {

    @DatabaseField
    private String IMatnr;
    @DatabaseField
    private Double IMeins;
    @DatabaseField
    private String IMenge;
    @DatabaseField
    private String IWerks;
    @DatabaseField
    private String IZbc;
    @DatabaseField
    private Date IZgrdate;
    @DatabaseField
    private String IZkurno;
    @DatabaseField
    private String IZlinecode;
    @DatabaseField
    private Date IZproddate;

    public ZwmRfcIts002() {
    }

    public ZwmRfcIts002(String IMatnr, Double IMeins, String IMenge, String IWerks, String IZbc, Date IZgrdate, String IZkurno, String IZlinecode, Date IZproddate) {
        this.IMatnr = IMatnr;
        this.IMeins = IMeins;
        this.IMenge = IMenge;
        this.IWerks = IWerks;
        this.IZbc = IZbc;
        this.IZgrdate = IZgrdate;
        this.IZkurno = IZkurno;
        this.IZlinecode = IZlinecode;
        this.IZproddate = IZproddate;
    }

    public String getIMatnr() {
        return IMatnr;
    }

    public void setIMatnr(String IMatnr) {
        this.IMatnr = IMatnr;
    }

    public Double getIMeins() {
        return IMeins;
    }

    public void setIMeins(Double IMeins) {
        this.IMeins = IMeins;
    }

    public String getIMenge() {
        return IMenge;
    }

    public void setIMenge(String IMenge) {
        this.IMenge = IMenge;
    }

    public String getIWerks() {
        return IWerks;
    }

    public void setIWerks(String IWerks) {
        this.IWerks = IWerks;
    }

    public String getIZbc() {
        return IZbc;
    }

    public void setIZbc(String IZbc) {
        this.IZbc = IZbc;
    }

    public Date getIZgrdate() {
        return IZgrdate;
    }

    public void setIZgrdate(Date IZgrdate) {
        this.IZgrdate = IZgrdate;
    }

    public String getIZkurno() {
        return IZkurno;
    }

    public void setIZkurno(String IZkurno) {
        this.IZkurno = IZkurno;
    }

    public String getIZlinecode() {
        return IZlinecode;
    }

    public void setIZlinecode(String IZlinecode) {
        this.IZlinecode = IZlinecode;
    }

    public Date getIZproddate() {
        return IZproddate;
    }

    public void setIZproddate(Date IZproddate) {
        this.IZproddate = IZproddate;
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
