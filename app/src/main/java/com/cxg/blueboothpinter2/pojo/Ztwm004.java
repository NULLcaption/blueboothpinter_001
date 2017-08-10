package com.cxg.blueboothpinter2.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**a
 * 返回值实体类
 * Created dministrator on 2017/6/14.
 */
@DatabaseTable(tableName = "Ztwm004")
public class Ztwm004 implements Serializable {

    @DatabaseField
    private String Mandt;
    @DatabaseField
    private String Zipcode;
    @DatabaseField
    private String Charg;//ERP编码
    @DatabaseField
    private String Zcupno;//杯上批次编码
    @DatabaseField
    private String Werks;//工厂
    @DatabaseField
    private String Zkurno;//客流码
    @DatabaseField
    private String Zbc;//班别
    @DatabaseField
    private String Zlinecode;//线别
    @DatabaseField
    private String Matnr;//物料Id
    @DatabaseField
    private String Zproddate;//入库时间
    @DatabaseField
    private String Zinstock;
    @DatabaseField
    private String Zoutstock;
    @DatabaseField
    private String Mblnr;
    @DatabaseField
    private String Mjahr;
    @DatabaseField
    private String Menge;//数量
    @DatabaseField
    private String Meins;//单位
    @DatabaseField
    private String Tanum;
    @DatabaseField
    private String Zptflg;
    @DatabaseField
    private String Zgrdate;//生产时间
    @DatabaseField
    private String Zlichn;//版本
    @DatabaseField
    private String Lifnr;//供应商
    @DatabaseField
    private String Znum;//单据
    @DatabaseField
    private String Zqcnum;//车牌号
    @DatabaseField
    private String EMaktx;//物料名称
    @DatabaseField
    private String EName1;//客流码名称
    @DatabaseField
    private String EName2;//供应商名称
    @DatabaseField
    private String ILgmng;
    @DatabaseField
    private String IZlocco;
    @DatabaseField
    private String ItZipcode;

    private List<String> charglist;
    private List<String> zipcodelist;
    private List<Ztwm004> ztwm004s;

    public Ztwm004() {
        super();
    }

    public Ztwm004(String mandt, String zipcode, String charg, String zcupno, String werks, String zkurno, String zbc, String zlinecode, String matnr, String zproddate, String zinstock, String zoutstock, String mblnr, String mjahr, String menge, String meins, String tanum, String zptflg, String zgrdate, String zlichn, String lifnr, String znum, String zqcnum, String EMaktx, String EName1, String EName2, String ILgmng, String IZlocco, String itZipcode) {
        Mandt = mandt;
        Zipcode = zipcode;
        Charg = charg;
        Zcupno = zcupno;
        Werks = werks;
        Zkurno = zkurno;
        Zbc = zbc;
        Zlinecode = zlinecode;
        Matnr = matnr;
        Zproddate = zproddate;
        Zinstock = zinstock;
        Zoutstock = zoutstock;
        Mblnr = mblnr;
        Mjahr = mjahr;
        Menge = menge;
        Meins = meins;
        Tanum = tanum;
        Zptflg = zptflg;
        Zgrdate = zgrdate;
        Zlichn = zlichn;
        Lifnr = lifnr;
        Znum = znum;
        Zqcnum = zqcnum;
        this.EMaktx = EMaktx;
        this.EName1 = EName1;
        this.EName2 = EName2;
        this.ILgmng = ILgmng;
        this.IZlocco = IZlocco;
        ItZipcode = itZipcode;
    }

    public List<String> getCharglist() {
        return charglist;
    }

    public void setCharglist(List<String> charglist) {
        this.charglist = charglist;
    }

    public List<String> getZipcodelist() {
        return zipcodelist;
    }

    public void setZipcodelist(List<String> zipcodelist) {
        this.zipcodelist = zipcodelist;
    }

    public List<Ztwm004> getZtwm004s() {
        return ztwm004s;
    }

    public void setZtwm004s(List<Ztwm004> ztwm004s) {
        this.ztwm004s = ztwm004s;
    }

    public String getILgmng() {
        return ILgmng;
    }

    public void setILgmng(String ILgmng) {
        this.ILgmng = ILgmng;
    }

    public String getIZlocco() {
        return IZlocco;
    }

    public void setIZlocco(String IZlocco) {
        this.IZlocco = IZlocco;
    }

    public String getItZipcode() {
        return ItZipcode;
    }

    public void setItZipcode(String itZipcode) {
        ItZipcode = itZipcode;
    }

    public String getEMaktx() {
        return EMaktx;
    }

    public void setEMaktx(String EMaktx) {
        this.EMaktx = EMaktx;
    }

    public String getEName1() {
        return EName1;
    }

    public void setEName1(String EName1) {
        this.EName1 = EName1;
    }

    public String getEName2() {
        return EName2;
    }

    public void setEName2(String EName2) {
        this.EName2 = EName2;
    }

    public String getMandt() {
        return Mandt;
    }

    public void setMandt(String mandt) {
        Mandt = mandt;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getCharg() {
        return Charg;
    }

    public void setCharg(String charg) {
        Charg = charg;
    }

    public String getZcupno() {
        return Zcupno;
    }

    public void setZcupno(String zcupno) {
        Zcupno = zcupno;
    }

    public String getWerks() {
        return Werks;
    }

    public void setWerks(String werks) {
        Werks = werks;
    }

    public String getZkurno() {
        return Zkurno;
    }

    public void setZkurno(String zkurno) {
        Zkurno = zkurno;
    }

    public String getZbc() {
        return Zbc;
    }

    public void setZbc(String zbc) {
        Zbc = zbc;
    }

    public String getZlinecode() {
        return Zlinecode;
    }

    public void setZlinecode(String zlinecode) {
        Zlinecode = zlinecode;
    }

    public String getMatnr() {
        return Matnr;
    }

    public void setMatnr(String matnr) {
        Matnr = matnr;
    }


    public String getZproddate() {
        return Zproddate;
    }

    public void setZproddate(String zproddate) {
        Zproddate = zproddate;
    }

    public String getZinstock() {
        return Zinstock;
    }

    public void setZinstock(String zinstock) {
        Zinstock = zinstock;
    }

    public String getZoutstock() {
        return Zoutstock;
    }

    public void setZoutstock(String zoutstock) {
        Zoutstock = zoutstock;
    }

    public String getMblnr() {
        return Mblnr;
    }

    public void setMblnr(String mblnr) {
        Mblnr = mblnr;
    }

    public String getMjahr() {
        return Mjahr;
    }

    public void setMjahr(String mjahr) {
        Mjahr = mjahr;
    }

    public String getMenge() {
        return Menge;
    }

    public void setMenge(String menge) {
        Menge = menge;
    }

    public String getMeins() {
        return Meins;
    }

    public void setMeins(String meins) {
        Meins = meins;
    }

    public String getTanum() {
        return Tanum;
    }

    public void setTanum(String tanum) {
        Tanum = tanum;
    }

    public String getZptflg() {
        return Zptflg;
    }

    public void setZptflg(String zptflg) {
        Zptflg = zptflg;
    }

    public String getZgrdate() {
        return Zgrdate;
    }

    public void setZgrdate(String zgrdate) {
        Zgrdate = zgrdate;
    }

    public String getZlichn() {
        return Zlichn;
    }

    public void setZlichn(String zlichn) {
        Zlichn = zlichn;
    }

    public String getLifnr() {
        return Lifnr;
    }

    public void setLifnr(String lifnr) {
        Lifnr = lifnr;
    }

    public String getZnum() {
        return Znum;
    }

    public void setZnum(String znum) {
        Znum = znum;
    }

    public String getZqcnum() {
        return Zqcnum;
    }

    public void setZqcnum(String zqcnum) {
        Zqcnum = zqcnum;
    }

    @Override
    public String toString() {
        return "Ztwm004{" + "Mandt='" + Mandt + '\'' + ", Zipcode='" + Zipcode + '\'' + ", Charg='" + Charg + '\'' + ", Zcupno='" + Zcupno + '\'' + ", Werks='" + Werks + '\'' + ", Zkurno='" + Zkurno + '\'' + ", Zbc='" + Zbc + '\'' + ", Zlinecode='" + Zlinecode + '\'' + ", Matnr='" + Matnr + '\'' + ", Zproddate='" + Zproddate + '\'' + ", Zinstock='" + Zinstock + '\'' + ", Zoutstock='" + Zoutstock + '\'' + ", Mblnr='" + Mblnr + '\'' + ", Mjahr='" + Mjahr + '\'' + ", Menge='" + Menge + '\'' + ", Meins='" + Meins + '\'' + ", Tanum='" + Tanum + '\'' + ", Zptflg='" + Zptflg + '\'' + ", Zgrdate='" + Zgrdate + '\'' + ", Zlichn='" + Zlichn + '\'' + ", Lifnr='" + Lifnr + '\'' + ", Znum='" + Znum + '\'' + ", Zqcnum='" + Zqcnum + '\'' + ", EMaktx='" + EMaktx + '\'' + ", EName1='" + EName1 + '\'' + ", EName2='" + EName2 + '\'' + '}';
    }
}
