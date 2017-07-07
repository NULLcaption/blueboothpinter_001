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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cxg.blueboothpinter2.R;
import com.cxg.blueboothpinter2.application.XPPApplication;
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
    /*编辑张数*/
    public EditText tv1;

    private Ztwm004 ztwm004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booth);

        Button Button1 = (Button) findViewById(R.id.button1);
        statusBox = new StatusBox(this, Button1);
        megBox = new MessageBox(this);
        tv1 = (EditText) findViewById(R.id.editText);
        tv1.setText("1");
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

        /*单张打印*/
        Button Button2 = (Button) findViewById(R.id.button2);
        Button2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                Print2(SelectedBDAddress, ztwm004);
            }
        });

        /*返回*/
        Button Button3 = (Button) findViewById(R.id.button3);
        Button3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(BoothActivity.this, BlueBoothPinterActivity.class);
                //用Bundle携带数据
                Bundle ztwm004_001 = new Bundle();

                ztwm004_001.putString("IZipcode", ztwm004.getZipcode());
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
        XPPApplication.closeKeyboard(tv1,this);
    }

    /**
     * 将传输过来的数据封装成对象
     *
     * @param bundle 数据
     * @return ztwm004
     */
    private Ztwm004 dataFmort(Bundle bundle) {
        String IZipcode = bundle.getString("IZipcode");
        String Werks = bundle.getString("Werks");
        String Zkurno = bundle.getString("Zkurno");
        String Zbc = bundle.getString("Zbc");
        String Zlinecode = bundle.getString("Zlinecode");
        String Matnr = bundle.getString("Matnr");
        String Menge = bundle.getString("Menge");
        String Meins = bundle.getString("Meins");
        String Charg = bundle.getString("Charg");
        String Zproddate = bundle.getString("Zproddate");//生产日期
        String Zgrdate = bundle.getString("Zgrdate");//入库时间
        String EMaktx = bundle.getString("EMaktx");
        String EName1 = bundle.getString("EName1");

        ztwm004 = new Ztwm004();
        ztwm004.setZipcode(IZipcode);
        ztwm004.setWerks(Werks);
        ztwm004.setZkurno(Zkurno);
        ztwm004.setZbc(Zbc);
        ztwm004.setZlinecode(Zlinecode);
        ztwm004.setMatnr(Matnr);
        ztwm004.setMenge(Menge);
        ztwm004.setMeins(Meins);
        ztwm004.setCharg(Charg);
        ztwm004.setZproddate(Zproddate);
        ztwm004.setZgrdate(Zgrdate);
        ztwm004.setEMaktx(EMaktx);
        ztwm004.setEName1(EName1);

        return ztwm004;
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
        String name = tv1.getText().toString();
        int num = Integer.parseInt(name);
        lable_sdk.SelectPage(0);
        lable_sdk.ClearPage();
        lable_sdk.SelectPage(1);
        lable_sdk.ClearPage();
        lable_sdk.SetPageSize(83 * 8, 72 * 8);
        lable_sdk.ErrorConfig(true);
        for (int i = 1; i <= num; i++) {
            DrawContent(i, ztwm004);// content
            lable_sdk.PrintPage(0x04, 150, false);
            lable_sdk.ClearPage();
        }
        Bluetooth.close();
        statusBox.Close();
    }// print1

    /**
     * 单张打印
     *
     * @param BDAddress 蓝牙打印地址
     * @param ztwm004   打印数据
     */
    private void Print2(String BDAddress, Ztwm004 ztwm004) {
        statusBox.Show("正在打印...");
        if (!Bluetooth.OpenPrinter(BDAddress)) {
            showMessage(Bluetooth.ErrorMessage);
            Bluetooth.close();
            statusBox.Close();
            return;
        }
        // create page
        lable_sdk.SelectPage(0);
        lable_sdk.ClearPage();
        lable_sdk.SelectPage(1);
        lable_sdk.ClearPage();
        lable_sdk.SetPageSize(83 * 8, 72 * 8);
        lable_sdk.ErrorConfig(true);
        System.out.println("=====>" + ztwm004);
        DrawContent(1, ztwm004);// content
        lable_sdk.PrintPage(0x04, 50, false);
        lable_sdk.SelectPage(0);
        lable_sdk.ClearPage();
        lable_sdk.SelectPage(1);
        lable_sdk.ClearPage();
        if (zp_realtime_status(10000) != 0) {
            showMessage(Bluetooth.ErrorMessage);
        }
        Bluetooth.close();
        statusBox.Close();
    }

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
     *
     * @param num     页数
     * @param ztwm004 打印对象
     */
    private void DrawContent(int num, Ztwm004 ztwm004) {
        lable_sdk.DrawText(7 * 8, 8 * 8, "客户:" + ztwm004.getZkurno(), 0x00, 0x0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 12 * 8, "客户名称:", 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 16 * 8, "线别:" + ztwm004.getZlinecode(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 20 * 8, "班别:" + ztwm004.getZbc(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 24 * 8, "物料编号:" + ztwm004.getMatnr(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 28 * 8, ztwm004.getEMaktx(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 31 * 8, "生产日期:" + ztwm004.getZgrdate(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 35 * 8, "入库日期:" + ztwm004.getZproddate(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 39 * 8, "ERP批次号:" + ztwm004.getCharg(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawText(7 * 8, 43 * 8, "数量:" + ztwm004.getMenge(), 0, 0);
        zp_realtime_status(1000);
        lable_sdk.DrawCode1D(7 * 8, 47 * 8, ztwm004.getZipcode(), 0x1, 0x03, (16 * 8));
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
