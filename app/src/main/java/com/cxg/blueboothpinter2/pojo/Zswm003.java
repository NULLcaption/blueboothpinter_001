package com.cxg.blueboothpinter2.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * 加载单位返回值
 * Created by Administrator on 2017/6/21.
 */
@DatabaseTable(tableName = "Zswm003")
public class Zswm003 implements Serializable,KvmSerializable {

    @DatabaseField
    private String Msehi;

    @DatabaseField
    private String Mseh3;

    public Zswm003() {
        super();
    }

    public Zswm003(String msehi, String mseh3) {
        Msehi = msehi;
        Mseh3 = mseh3;
    }

    public String getMsehi() {
        return Msehi;
    }

    public void setMsehi(String msehi) {
        Msehi = msehi;
    }

    public String getMseh3() {
        return Mseh3;
    }

    public void setMseh3(String mseh3) {
        Mseh3 = mseh3;
    }

    @Override
    public String toString() {
        return "Zswm003{" + "Msehi='" + Msehi + '\'' + ", Mseh3='" + Mseh3 + '\'' + '}';
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
