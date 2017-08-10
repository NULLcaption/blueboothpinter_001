package com.cxg.blueboothpinter2.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cxg.blueboothpinter2.R;
import com.cxg.blueboothpinter2.pojo.Ztwm004;
import com.cxg.blueboothpinter2.utils.Bluetooth;
import com.cxg.blueboothpinter2.utils.ExitApplication;
import com.cxg.blueboothpinter2.utils.MessageBox;
import com.cxg.blueboothpinter2.utils.StatusBox;
import com.cxg.blueboothpinter2.utils.lable_sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 蓝牙打印首页
 */
public class BoothActivity extends AppCompatActivity {

    /*蓝牙适配器*/
    public static BluetoothAdapter myBluetoothAdapter;
    /*远程连接地址*/
    public String SelectedBDAddress;
    /*打印机盒子状态*/
    public StatusBox statusBox;
    /*盒子信息*/
    public MessageBox megBox;

    private Ztwm004 ztwm004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booth);

        Button Button1 = (Button) findViewById(R.id.button1);
        statusBox = new StatusBox(this, Button1);
        megBox = new MessageBox(this);
        SelectedBDAddress = "";

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        ztwm004 = dataFmort(bundle);

        /*判断设备是否支持蓝牙设备*/
        boolean bluetoothDevice = ListBluetoothDevice();
        if (!bluetoothDevice) {
            String mags = "与蓝牙设备匹配有问题，请检查后重试!";
            showMessage(mags);
            finish();//用于结束一个Activity的生命周期
        }

        /*循环多张打印*/
        Button1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                Print1(SelectedBDAddress, ztwm004);
            }
        });


        /*返回*/
        Button Button3 = (Button) findViewById(R.id.button3);
        Button3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(BoothActivity.this, BlueBoothPinterActivity.class);
                //用Bundle携带数据
                Bundle ztwm004_001 = new Bundle();

                ztwm004_001.putString("IZipcode", ztwm004.getZipcodelist().get(0) + ", ...");
                ztwm004_001.putString("Charg", ztwm004.getCharg());
                ztwm004_001.putString("Zproddate", ztwm004.getZproddate());
                ztwm004_001.putString("Werks", ztwm004.getWerks());
                ztwm004_001.putString("Zkurno", ztwm004.getZkurno());
                ztwm004_001.putString("Zbc", ztwm004.getZbc());
                ztwm004_001.putString("Zlinecode", ztwm004.getZlinecode());
                ztwm004_001.putString("Matnr", ztwm004.getMatnr());
                ztwm004_001.putString("Menge", ztwm004.getMenge());
                ztwm004_001.putString("Meins", ztwm004.getMeins());
                ztwm004_001.putString("EMaktx", ztwm004.getEMaktx());
                ztwm004_001.putString("EName1", ztwm004.getEName1());

                intent.putExtras(ztwm004_001);

                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                Toast.makeText(getApplicationContext(), "返回至打印数据预览", Toast.LENGTH_SHORT).show();
            }
        });

        ExitApplication.getInstance().addActivity(this);
    }

    /**
     * 将传输过来的数据封装成对象
     *
     * @param bundle 数据
     * @return ztwm004
     */
    private Ztwm004 dataFmort(Bundle bundle) {
        List<String> localList1 = extractMessageByRegular(bundle.getString("zipcodeList"));
        List<String> localArrayList1 = new ArrayList<>();
        String[] arrayOfString1 = new String[0];
        for (int i = 0; ; i++) {
            int j = localList1.size();
            if (i >= j) {
                break;
            }
            arrayOfString1 = (localList1.get(i)).split(", ");
        }
        int k = arrayOfString1.length;
        for (int m = 0; m < k; m++) {
            localArrayList1.add(arrayOfString1[m]);
        }

        List<String> localList2 = extractMessageByRegular(bundle.getString("chargList"));
        List<String> localArrayList2 = new ArrayList<>();
        String[] arrayOfString2 = new String[0];
        for (int n = 0; ; n++) {
            int i1 = localList2.size();
            if (n >= i1) {
                break;
            }
            arrayOfString2 = (localList2.get(n)).split(", ");
        }
        int i2 = arrayOfString2.length;
        for (int i3 = 0; i3 < i2; i3++) {
            localArrayList2.add(arrayOfString2[i3]);
        }
        String str1 = "";
        for (int i4 = 0; ; i4++) {
            int i5 = localArrayList2.size();
            if (i4 >= i5) {
                break;
            }
            str1 = localArrayList2.get(0);
        }
        String Lgmng = bundle.getString("Lgmng");
        String Werks = bundle.getString("Werks");
        String Zkurno = bundle.getString("Zkurno");
        String Zbc = bundle.getString("Zbc");
        String Zlinecode = bundle.getString("Zlinecode");
        String Matnr = bundle.getString("Matnr");
        String Menge = bundle.getString("Menge");
        String Meins = bundle.getString("Meins");
        String Zproddate = bundle.getString("Zproddate");//生产日期
        String Zgrdate = bundle.getString("Zgrdate");//入库时间
        String EMaktx = bundle.getString("EMaktx");
        String EName1 = bundle.getString("EName1");

        ztwm004 = new Ztwm004();
        ztwm004.setZipcodelist(localArrayList1);
        ztwm004.setWerks(Werks);
        ztwm004.setZkurno(Zkurno);
        ztwm004.setZbc(Zbc);
        ztwm004.setZlinecode(Zlinecode);
        ztwm004.setMatnr(Matnr);
        ztwm004.setILgmng(Lgmng);
        ztwm004.setMenge(Menge);
        ztwm004.setMeins(Meins);
        ztwm004.setCharg(str1);
        ztwm004.setZproddate(Zproddate);
        ztwm004.setZgrdate(Zgrdate);
        ztwm004.setEMaktx(EMaktx);
        ztwm004.setEName1(EName1);

        return ztwm004;
    }

    /**
     * description:获取上一个Activity的list绑定值
     * author: xg.chen
     * date: 2017/8/3 13:21
     * version: 1.0
     */
    public static List<String> extractMessageByRegular(String paramString) {
        ArrayList localArrayList = new ArrayList();
        Matcher localMatcher = Pattern.compile("(\\[[^\\]]*\\])").matcher(paramString);
        while (localMatcher.find()) {
            localArrayList.add(localMatcher.group().substring(1, -1 + localMatcher.group().length()));
        }
        return localArrayList;
    }


    /**
     * 循环多张打印
     *
     * @param BDAddress
     */
    private void Print1(String BDAddress, Ztwm004 ztwm004) {
        statusBox.Show("正在打印...");
        if (!Bluetooth.OpenPrinter(BDAddress)) {
            showMessage(Bluetooth.ErrorMessage);
            Bluetooth.close();
            statusBox.Close();
            return;
        }
        // create page
        List localList = ztwm004.getZipcodelist();
        lable_sdk.SelectPage(0);
        lable_sdk.ClearPage();
        lable_sdk.SelectPage(1);
        lable_sdk.ClearPage();
        lable_sdk.SetPageSize(83 * 8, 72 * 8);
        lable_sdk.ErrorConfig(true);
        for (int i = 0; i < localList.size(); i++) {
            String str = (String) localList.get(i);
            DrawContent(ztwm004, str);// content
            lable_sdk.PrintPage(0x04, 10, false);
            lable_sdk.ClearPage();
        }
        Bluetooth.close();
        statusBox.Close();
    }// print1


    /**
     * 打印数据输出时间设置
     *
     * @param timeout
     * @return
     */
    public static int zp_realtime_status(int timeout) {
        byte[] status = new byte[8];
        byte[] buf = new byte[11];
        buf[0] = 0x1f;
        buf[1] = 0x00;
        buf[2] = 0x06;
        buf[3] = 0x00;
        buf[4] = 0x07;
        buf[5] = 0x14;
        buf[6] = 0x18;
        buf[7] = 0x23;
        buf[8] = 0x25;
        buf[9] = 0x32;
        buf[10] = 0x00;
        Bluetooth.SPPWrite(buf, 10);
        if (Bluetooth.SPPReadTimeout(status, 1, timeout) == false) {
            return -1;
        }
        return status[0];
    }

    /**
     * 页面布局
     */
    private void DrawContent(Ztwm004 paramZtwm004, String paramString) {
        try {
            lable_sdk.DrawText(40, 64, "客户:" + paramZtwm004.getZkurno(), 2, 2);
            zp_realtime_status(1000);
            lable_sdk.DrawText(40, 128, "班别:" + paramZtwm004.getZbc(), 0, 0);
            zp_realtime_status(1000);
            lable_sdk.DrawText(40, 160, "物料编号:" + paramZtwm004.getMatnr(), 2, 1);
            zp_realtime_status(1000);
            lable_sdk.DrawText(40, 192, paramZtwm004.getEMaktx(), 2, 1);
            zp_realtime_status(1000);
            lable_sdk.DrawText(40, 224, "生产日期:" + paramZtwm004.getZgrdate(), 0, 0);
            zp_realtime_status(1000);
            lable_sdk.DrawText(40, 256, "入库日期:" + paramZtwm004.getZproddate(), 0, 0);
            zp_realtime_status(1000);
            lable_sdk.DrawText(40, 288, "ERP批次号:" + paramZtwm004.getCharg(), 0, 0);
            zp_realtime_status(1000);
            lable_sdk.DrawText(40, 320, "数量:" + paramZtwm004.getILgmng() + " " + paramZtwm004.getMeins(), 0, 0);
            zp_realtime_status(1000);
            lable_sdk.DrawCode1D(96, 352, paramString, 1, 3, 80);
            zp_realtime_status(1000);
            lable_sdk.DrawText(160, 440, paramString, 0, 0);
            zp_realtime_status(1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }// DrawContent

    /**
     * 远程连接设备的蓝牙列表
     *
     * @return
     */
    public boolean ListBluetoothDevice() {
        final List<Map<String, String>> list = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.listView1);
        SimpleAdapter m_adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[]{"DeviceName", "BDAddress"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(m_adapter);

        if ((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) == null) {
            Toast.makeText(this, "没有找到蓝牙适配器", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!myBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 2);
        }

        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() <= 0) {
            return false;
        }
        for (BluetoothDevice device : pairedDevices) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("DeviceName", device.getName());
            map.put("BDAddress", device.getAddress());
            list.add(map);
        }
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedBDAddress = list.get(position).get("BDAddress");
                if (((ListView) parent).getTag() != null) {
                    ((View) ((ListView) parent).getTag()).setBackgroundDrawable(null);
                }
                ((ListView) parent).setTag(view);
                view.setBackgroundColor(Color.YELLOW);
            }
        });
        return true;
    }// ListBluetoothDevice

    /**
     * 输出信息
     *
     * @param str
     */
    public void showMessage(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
