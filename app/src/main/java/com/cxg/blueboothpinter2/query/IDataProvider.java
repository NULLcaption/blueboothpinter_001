package com.cxg.blueboothpinter2.query;

import android.app.Activity;

import com.cxg.blueboothpinter2.pojo.Ztwm004;

import java.util.List;
import java.util.Map;

/**
 * service interface
 * Created by Administrator on 2017/5/5.
 */

public interface IDataProvider {
    /**
     * init data
     *
     * @param activity ui
     */
    void startDateUpdateTasks(Activity activity);

    /**
     * 进入时加载单位
     *
     * @return map
     */
    Map<String, String> getMeins();

    /**
     * 获取物料名称
     *
     * @param string
     * @return
     */
    List<Ztwm004> getEMaktx(String string);

    /**
     * 生成托盘编码
     *
     * @param ztwm004
     * @return
     */
    List<Ztwm004> getZipcode(Ztwm004 ztwm004);

    /**
     * 获取客户客户名称
     *
     * @param string
     * @return
     */
    List<Ztwm004> getEName1(String string);
}
