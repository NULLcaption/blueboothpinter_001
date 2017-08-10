package com.cxg.blueboothpinter2.query;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.cxg.blueboothpinter2.pojo.Ztwm004;
import com.cxg.blueboothpinter2.utils.Helpers;
import com.cxg.blueboothpinter2.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description: webService公用类
 * author: xg.chen
 * date: 2017/6/27 9:00
 * version: 1.0
*/
public class WebService implements IDataProvider {

    private static WebService instance;

    private static final int TIME_OUT = 30000;

    private static final String TAG = "WebService";

    private WebService() {
        super();
    }

    public static IDataProvider getInstance() {
        if (instance == null) instance = new WebService();
        return instance;
    }


    @Override
    public void startDateUpdateTasks(Activity activity) {
        Context ctx = DataProviderFactory.getContext();
        if (ctx != null) {
            SharedPreferences sp = ctx.getSharedPreferences("XPPWebService", Context.MODE_PRIVATE);

            if (sp.contains("lastUpdate")) {
                Date now = new Date();
                String str1 = sp.getString("lastUpdate", "");
                String str2 = Helpers.getDateStrWithoutTime(now);
                if (str1.startsWith(str2)) {
                    Log.d(TAG, "No updates needed at this time.");
                    return;
                }
            }
            if (UpdateTask.getInstance().getStatus() != AsyncTask.Status.RUNNING) {
                sp.edit().putInt("lastUpdatedShopSequence", -1).commit();
                new UpdateTask(activity, false).execute();
            }
        }
    }

    @Override
    public Map<String, String> getMeins() {
        Map<String,String> map = new HashMap<>();
        try {
            map = WebServiceUtils.callWebServiceFor004(WebServiceUtils.URL_004, WebServiceUtils.METHOD_NAME_004);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Ztwm004> getEMaktx(String string) {
        //处理后的类型具体的返回值
        List<Ztwm004> ztwm004List = new ArrayList<>();
        try {
            HashMap<String,String> properties = new HashMap<>();
            properties.put("IMatnr",string);
            List<Object> list = WebServiceUtils.callWebServiceFor001(WebServiceUtils.URL_001, WebServiceUtils.METHOD_NAME_001, properties);
            if (list.size() != 0){
                Ztwm004 ztwm004 = new Ztwm004();
                String anyType = "anyType{}";
                if (!anyType.equals(list.get(0).toString())){
                    ztwm004.setEMaktx(list.get(0).toString());
                } else {
                    ztwm004.setEMaktx("");
                }
                if (!anyType.equals(list.get(1).toString())) {
                    ztwm004.setMeins(list.get(1).toString());
                } else {
                    ztwm004.setMeins("");
                }
                ztwm004List.add(ztwm004);
            }
            return ztwm004List;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ztwm004List;
    }

    @Override
    public List<Ztwm004> getZipcode(Ztwm004 ztwm004) {
        //处理后的类型具体的返回值
        List<Ztwm004> ztwm004List = new ArrayList<>();
        try {
            //请求的参数对象
            Ztwm004 properties = new Ztwm004();

            properties.setILgmng(ztwm004.getILgmng());
            properties.setMatnr(ztwm004.getMatnr());
            properties.setMeins(ztwm004.getMeins());//单位
            properties.setMenge(ztwm004.getMenge());//数量
            properties.setWerks(ztwm004.getWerks());
            properties.setZbc(ztwm004.getZbc());
            properties.setZgrdate(ztwm004.getZgrdate());
            properties.setZkurno(ztwm004.getZkurno());
            properties.setZlinecode(ztwm004.getZlinecode());
            properties.setIZlocco(ztwm004.getZcupno());//客流码
            properties.setZproddate(ztwm004.getZproddate());
            properties.setItZipcode(ztwm004.getItZipcode());

            //通过webservice获取到的返回值
            List<Object> list = WebServiceUtils.callWebServiceFor002(WebServiceUtils.URL_007, WebServiceUtils.METHOD_NAME_007, properties);
            Ztwm004 ztwm004_002 = new Ztwm004();
            List<Ztwm004> ztwm004List_001 = new ArrayList<>();
            if (list.size()!=0) {
                for (int i = 0; i < list.size(); i++) {
                    ztwm004List_001.add((Ztwm004)list.get(i));
                }
                ztwm004_002.setZtwm004s(ztwm004List_001);
            }
            ztwm004List.add(ztwm004_002);

            return ztwm004List;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ztwm004List;
    }

}
