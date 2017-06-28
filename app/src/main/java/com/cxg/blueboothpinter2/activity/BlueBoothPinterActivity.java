package com.cxg.blueboothpinter2.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxg.blueboothpinter2.R;
import com.cxg.blueboothpinter2.application.XPPApplication;
import com.cxg.blueboothpinter2.pojo.Ztwm004;
import com.cxg.blueboothpinter2.query.DataProviderFactory;
import com.cxg.blueboothpinter2.utils.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: 蓝牙打印半成品首页
 * author: xg.chen
 * date: 2017/6/23 15:00
 * version: 1.0
 */
public class BlueBoothPinterActivity extends AppCompatActivity {

    private TextView IZipcode, EMaktx, Charg;
    private Button btnpost, btnpost1, printer, exit;
    private EditText Werks, Zkurno, Zbc, Matnr, Zproddate, Menge, Meins, Zgrdate, Zlinecode;
    private List<Ztwm004> ztwm004list;
    private Dialog waitingDialog;
    private Dialog overdialog;
    private Map<String, String> map = new HashMap<>();
    private DatePicker zproddateDatePicker, zgrdateDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_booth_pinter);
        //视图
        initView();
        //时间选择控件
        selectDatePicker();
        //数据
        initData();
    }

    /**
     * description: init view
     * author: xg.chen
     * date: 2017/6/23 15:47
     * version: 1.0
     */
    public void initView() {
        //工厂
        Werks = (EditText) findViewById(R.id.werks);
        Werks.setOnClickListener(BtnClicked);

        Zkurno = (EditText) findViewById(R.id.Zkurno);
        //班别
        Zbc = (EditText) findViewById(R.id.Zbc);
        Zbc.setOnClickListener(BtnClicked);
        //线别
        Zlinecode = (EditText) findViewById(R.id.Zlinecode);
        Zlinecode.setOnClickListener(BtnClicked);
        //托盘编码
        IZipcode = (TextView) findViewById(R.id.IZipcode);
        //物料编码
        Matnr = (EditText) findViewById(R.id.matnr);
        //库存日期
        Zproddate = (EditText) findViewById(R.id.Zproddate);
        Zproddate.setOnClickListener(BtnClicked);

        Menge = (EditText) findViewById(R.id.Menge);
        //单位
        Meins = (EditText) findViewById(R.id.Meins);
        Meins.setOnClickListener(BtnClicked);
        //生产日期
        Zgrdate = (EditText) findViewById(R.id.Zgrdate);
        Zgrdate.setOnClickListener(BtnClicked);
        //物料名称
        EMaktx = (TextView) findViewById(R.id.EMaktx);
        //ERP编码
        Charg = (TextView) findViewById(R.id.Charg);

        findViewById(R.id.clean).setOnClickListener(BtnClicked);//清空按钮
        findViewById(R.id.printer).setOnClickListener(BtnClicked);//打印按钮
        findViewById(R.id.exit).setOnClickListener(BtnClicked);//退出按钮
        findViewById(R.id.btnpost1).setOnClickListener(BtnClicked);//搜索物料名称
        findViewById(R.id.btnpost).setOnClickListener(BtnClicked);//生成托盘编码

    }

    /**
     * description: init data
     * author: xg.chen
     * date: 2017/6/23 15:47
     * version: 1.0
     */
    public void initData() {
        //页面UI更新
        ztwm004list = new ArrayList<>();
        if (ztwm004list.size() != 0) {
            for (Ztwm004 ztwm004 : ztwm004list) {
                Werks.setText(ztwm004.getWerks());
                Zkurno.setText(ztwm004.getZkurno());
                Zbc.setText(ztwm004.getZbc());
                Zlinecode.setText(ztwm004.getZlinecode());
                IZipcode.setText(ztwm004.getZipcode());
                Matnr.setText(ztwm004.getMatnr());
                Zproddate.setText(ztwm004.getZproddate());
                Menge.setText(ztwm004.getMenge());
                Meins.setText(ztwm004.getMeins());
                Zgrdate.setText(ztwm004.getZgrdate());
                EMaktx.setText(ztwm004.getEMaktx());
                Charg.setText(ztwm004.getCharg());
            }
        }
        //加载数据获取单位
        if(map.size() == 0){
            new getMeinsTask().execute();
        }
    }

    /**
     * 进入时加载单位
     */
    private class getMeinsTask extends AsyncTask<String, Integer, Map<String, String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showWaitingDialog();
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            return DataProviderFactory.getProvider().getMeins();
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            dismissWaitingDialog();
            if (result.size() != 0) {
                map = result;
            } else {
                Toast.makeText(getApplicationContext(), "连接超时...退出稍后重试...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * description: 按钮事件监听类
     * author: xg.chen
     * date: 2017/6/23 15:30
     * version: 1.0
     */
    private View.OnClickListener BtnClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.werks:
                    overdialog = null;
                    View overdiaView1 = View.inflate(BlueBoothPinterActivity.this,
                            R.layout.dialog_search_msg, null);
                    overdialog = new Dialog(BlueBoothPinterActivity.this,
                            R.style.dialog_xw);
                    ListView werksList = (ListView) overdiaView1
                            .findViewById(R.id.werksList);
                    List<String> list = new ArrayList<>();
                    list.add("湖州工厂");
                    list.add("成都工厂");
                    list.add("天津工厂");
                    SettingAdapter settingAdapter = new SettingAdapter(
                            getApplicationContext(), list);
                    werksList.setAdapter(settingAdapter);
                    overdialog.setContentView(overdiaView1);
                    overdialog.setCanceledOnTouchOutside(true);
                    Button overcancel = (Button) overdiaView1
                            .findViewById(R.id.dialog_cancel_btn);
                    overcancel.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            overdialog.cancel();
                        }
                    });
                    overdialog.show();
                    break;
                case R.id.Zlinecode:
                    overdialog = null;
                    View overdiaView = View.inflate(BlueBoothPinterActivity.this,
                            R.layout.dialog_search_msg, null);
                    overdialog = new Dialog(BlueBoothPinterActivity.this,
                            R.style.dialog_xw);
                    ListView ZlinecodeList = (ListView) overdiaView
                            .findViewById(R.id.werksList);
                    TextView tv_title1 = (TextView) overdiaView
                            .findViewById(R.id.Title);
                    tv_title1.setText("请选择线别:");
                    List<String> list1 = new ArrayList<>();
                    for (int i = 0; i <= 13; i++) {
                        if (i <= 9) {
                            list1.add("0" + i);
                        } else {
                            list1.add("" + i);
                        }
                    }
                    SettingAdapter1 settingAdapter_Zlinecode = new SettingAdapter1(
                            getApplicationContext(), list1);
                    ZlinecodeList.setAdapter(settingAdapter_Zlinecode);
                    overdialog.setContentView(overdiaView);
                    overdialog.setCanceledOnTouchOutside(true);
                    Button overcancel1 = (Button) overdiaView
                            .findViewById(R.id.dialog_cancel_btn);
                    overcancel1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            overdialog.cancel();
                        }
                    });
                    overdialog.show();
                    break;
                case R.id.Zbc:
                    overdialog = null;
                    View overdiaView_Zbc = View.inflate(BlueBoothPinterActivity.this,
                            R.layout.dialog_search_msg, null);
                    overdialog = new Dialog(BlueBoothPinterActivity.this,
                            R.style.dialog_xw);
                    ListView ZbcList = (ListView) overdiaView_Zbc
                            .findViewById(R.id.werksList);
                    TextView tv_titleZbc = (TextView) overdiaView_Zbc
                            .findViewById(R.id.Title);
                    tv_titleZbc.setText("请选择班别:");
                    List<String> listZbc = new ArrayList<>();
                    listZbc.add("白班");
                    listZbc.add("夜班");
                    SettingAdapterZbc settingAdapterZbc = new SettingAdapterZbc(
                            getApplicationContext(), listZbc);
                    ZbcList.setAdapter(settingAdapterZbc);
                    overdialog.setContentView(overdiaView_Zbc);
                    overdialog.setCanceledOnTouchOutside(true);
                    Button overcancelZbc = (Button) overdiaView_Zbc
                            .findViewById(R.id.dialog_cancel_btn);
                    overcancelZbc.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            overdialog.cancel();
                        }
                    });
                    overdialog.show();
                    break;
                case R.id.Meins:
                    overdialog = null;
                    View overdiaView_Meins = View.inflate(BlueBoothPinterActivity.this,
                            R.layout.dialog_search_msg, null);
                    overdialog = new Dialog(BlueBoothPinterActivity.this,
                            R.style.dialog_xw);
                    ListView MeinsList = (ListView) overdiaView_Meins
                            .findViewById(R.id.werksList);
                    TextView tv_titleMeins = (TextView) overdiaView_Meins
                            .findViewById(R.id.Title);
                    tv_titleMeins.setText("请选择单位:");
                    List<String> listMeins = new ArrayList<>();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        listMeins.add(entry.getValue());//获取单位
                    }
                    SettingAdapterMeins settingAdapterMeins = new SettingAdapterMeins(
                            getApplicationContext(), listMeins);
                    MeinsList.setAdapter(settingAdapterMeins);
                    overdialog.setContentView(overdiaView_Meins);
                    overdialog.setCanceledOnTouchOutside(true);
                    Button overcancelMeins = (Button) overdiaView_Meins
                            .findViewById(R.id.dialog_cancel_btn);
                    overcancelMeins.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            overdialog.cancel();
                        }
                    });
                    overdialog.show();
                    break;
                //生产日期
                case R.id.Zgrdate:
                    // 日期格式为yyyy-MM-dd
                    zgrdateDatePicker.show(Zgrdate.getText().toString());
                    break;
                //库存日期
                case R.id.Zproddate:
                    // 日期格式为yyyy-MM-dd
                    zproddateDatePicker.show(Zgrdate.getText().toString());
                    break;
                case R.id.btnpost1:
                    if (!"".equals(Matnr.getText().toString().trim().toString())) {
                        // 正则判断下是否输入值为数字
                        Pattern p2 = Pattern.compile("\\d");
                        String Matnr1 = Matnr.getText().toString().trim();
                        Matcher matcher = p2.matcher(Matnr1);
                        if (matcher.matches()) {
                            Toast.makeText(getApplicationContext(), "请填写准确的物料码...", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        new getEMaktxTask().execute(Matnr.getText().toString().trim());
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入物料码,然后查询即可!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                //生成托盘编码
                case R.id.btnpost:
                    Ztwm004 ztwm004_1 = new Ztwm004();
                    if (!"".equals(Werks.getText().toString().trim())) {
                        String string = "湖州工厂";
                        String string1 = "成都工厂";
                        String string2 = "天津工厂";
                        if (string.equals(Werks.getText().toString().trim())) {
                            ztwm004_1.setWerks("1000");
                        } else if (string1.equals(Werks.getText().toString().trim())) {
                            ztwm004_1.setWerks("2000");
                        } else if (string2.equals(Werks.getText().toString().trim())) {
                            ztwm004_1.setWerks("3000");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择工厂!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!"".equals(Matnr.getText().toString().trim())) {
                        // 正则判断下是否输入值为数字
                        Pattern p2 = Pattern.compile("\\d");
                        String Matnr1 = Matnr.getText().toString().trim();
                        Matcher matcher = p2.matcher(Matnr1);
                        if (matcher.matches()) {
                            Toast.makeText(getApplicationContext(), "请填写准确的物料码...", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        ztwm004_1.setMatnr(Matnr.getText().toString().trim());
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入物料码!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!"".equals(Zgrdate.getText().toString().trim())) {
                        ztwm004_1.setZgrdate(Zgrdate.getText().toString().trim());
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择生产日期!", Toast.LENGTH_SHORT).show();
                    }
                    if (!"".equals(Zproddate.getText().toString().trim())) {
                        ztwm004_1.setZproddate(Zproddate.getText().toString().trim());
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择入库日期!", Toast.LENGTH_SHORT).show();

                    }
                    if (!"".equals(Zlinecode.getText().toString().trim())) {
                        ztwm004_1.setZlinecode(Zlinecode.getText().toString().trim());
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择线别!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!"".equals(Zkurno.getText().toString().trim())) {
                        ztwm004_1.setZkurno("000000");
                    } else {
                        Toast.makeText(getApplicationContext(), "请选填写客流码!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //班别
                    if (!"".equals(Zbc.getText().toString().trim())) {
                        String string = "白班";
                        String string1 = "夜班";
                        if (string.equals(Zbc.getText().toString().trim())) {
                            ztwm004_1.setZbc("1");
                        } else if (string1.equals(Zbc.getText().toString().trim())) {
                            ztwm004_1.setZbc("2");
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "请选择班别!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //数量
                    if (!"".equals(Menge.getText().toString().trim())) {
                        // 正则判断下是否输入值为数字
                        Pattern p2 = Pattern.compile("([1-9]+[0-9]*|0)(\\\\.[\\\\d]+)?");
                        String Matnr1 = Menge.getText().toString().trim();
                        Matcher matcher = p2.matcher(Matnr1);
                        if (matcher.matches()) {
                            Toast.makeText(getApplicationContext(), "请填写准确的数量...", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        ztwm004_1.setMenge(Menge.getText().toString().trim());
                    } else {
                        Toast.makeText(getApplicationContext(), "请填写准确的数量!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //单位
                    if (!"".equals(Meins.getText().toString().trim())) {
                        if ("包".equals(Meins.getText().toString().trim())) {
                            ztwm004_1.setMeins("BAO");
                        } else if ("套".equals(Meins.getText().toString().trim())) {
                            ztwm004_1.setMeins("TAO");
                        } else if ("杯".equals(Meins.getText().toString().trim())) {
                            ztwm004_1.setMeins("BEI");
                        } else if ("箱".equals(Meins.getText().toString().trim())) {
                            ztwm004_1.setMeins("BOX");
                        } else if ("公斤".equals(Meins.getText().toString().trim())) {
                            ztwm004_1.setMeins("KG");
                        } else {
                            ztwm004_1.setMeins("BOX");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择单位!", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    //生成托盘编码
                    new getZipcodeTask().execute(ztwm004_1);

                    break;
                case R.id.exit :
                    XPPApplication.exit(BlueBoothPinterActivity.this);
                    Toast.makeText(getApplicationContext(), "退出应用", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.clean :
                    Werks.setText(null);
                    Zkurno.setText(null);
                    Zbc.setText(null);
                    Zlinecode.setText(null);
                    IZipcode.setText(null);
                    Matnr.setText(null);
                    Menge.setText(null);
                    Meins.setText(null);
                    EMaktx.setText(null);
                    break;
                case R.id.printer :
                    if ("".equals(IZipcode.getText().toString().trim().toString())) {
                        Toast.makeText(getApplicationContext(), "无打印数据，请重新操作！", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
                    Intent intent = new Intent(BlueBoothPinterActivity.this, BoothActivity.class);

                    //用Bundle携带数据
                    Bundle ztwm004 = new Bundle();

                    //托盘编码
                    ztwm004.putString("IZipcode", IZipcode.getText().toString().trim());
                    //ERP编号
                    ztwm004.putString("Charg", Charg.getText().toString().trim());
                    //入库时间
                    ztwm004.putString("Zproddate", Zproddate.getText().toString().trim());
                    //工厂
                    ztwm004.putString("Werks", Werks.getText().toString().trim());
                    //客流码
                    ztwm004.putString("Zkurno", Zkurno.getText().toString().trim());
                    //班别
                    ztwm004.putString("Zbc", Zbc.getText().toString().trim());
                    //线别
                    ztwm004.putString("Zlinecode", Zlinecode.getText().toString().trim());
                    //物料码
                    ztwm004.putString("Matnr", Matnr.getText().toString().trim());
                    //数量
                    ztwm004.putString("Menge", Menge.getText().toString().trim());
                    //单位转换成汉字
                    ztwm004.putString("Meins", Meins.getText().toString().trim());
                    //生产日期
                    ztwm004.putString("Zgrdate", Zgrdate.getText().toString().trim());
                    //物料名称
                    ztwm004.putString("EMaktx", EMaktx.getText().toString().trim());

                    intent.putExtras(ztwm004);

                    //进入到下一个Activity
                    startActivity(intent);

                    //Activity过场动画
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

                    Toast.makeText(getApplicationContext(), "进入到打印页面", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * description: 生成托盘编码
     * author: xg.chen
     * date: 2017/6/26 17:10
     * version: 1.0
     */
    private class getZipcodeTask extends AsyncTask<Object, Integer, List<Ztwm004>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showWaitingDialog();
        }

        @Override
        protected List<Ztwm004> doInBackground(Object... params) {
            Object ztwm004_1 = params[0];
            return DataProviderFactory.getProvider().getZipcode((Ztwm004) ztwm004_1);
        }

        @Override
        protected void onPostExecute(List<Ztwm004> result) {
            dismissWaitingDialog();
            if (result.size() != 0) {
                Charg.setText(result.get(0).getCharg());
                IZipcode.setText(result.get(0).getZipcode());
            } else {
                Toast.makeText(getApplicationContext(), "连接超时...退出稍后重试...", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * description: 获取物料名称
     * author: xg.chen
     * date: 2017/6/26 15:00
     * version: 1.0
     */
    private class getEMaktxTask extends AsyncTask<String, Integer, List<Ztwm004>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showWaitingDialog();
        }

        @Override
        protected List<Ztwm004> doInBackground(String... params) {
            String string = params[0];
            return DataProviderFactory.getProvider().getEMaktx(string);
        }

        @Override
        protected void onPostExecute(List<Ztwm004> result) {
            dismissWaitingDialog();
            if (result.size() != 0) {
                EMaktx.setText(result.get(0).getEMaktx());
            } else {
                Toast.makeText(getApplicationContext(), "连接超时...退出稍后重试...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * description: 选择时间控件
     * author: xg.chen
     * date: 2017/6/26 11:15
     * version: 1.0
     */
    private void selectDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        Zproddate.setText(now.split(" ")[0]);
        Zgrdate.setText(now.split(" ")[0]);

        zproddateDatePicker = new DatePicker(this, new DatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                Zproddate.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        zproddateDatePicker.showSpecificTime(false); // 不显示时和分false
        zproddateDatePicker.setIsLoop(false); // 不允许循环滚动*/

        zgrdateDatePicker = new DatePicker(this, new DatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                Zgrdate.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        zgrdateDatePicker.showSpecificTime(false); // 显示时和分true
        zgrdateDatePicker.setIsLoop(true); // 允许循环滚动
    }

    /**
     * description: 选择工厂之适配器
     * author: xg.chen
     * date: 2017/6/23 16:11
     * version: 1.0
     */
    protected class ViewHodler {
        TextView stringList = null;
    }

    protected void resetViewHolder(ViewHodler pViewHolder) {
        pViewHolder.stringList.setText(null);
    }

    public class SettingAdapter extends BaseAdapter {
        private List<String> data = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public SettingAdapter(Context context, List<String> data) {
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return data.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler hodler;
            if (convertView == null) {
                // 获取组件布局
                hodler = new ViewHodler();
                convertView = layoutInflater.inflate(
                        R.layout.dialog_search_list_child, null);
                hodler.stringList = (TextView) convertView
                        .findViewById(R.id.werksName);
                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodler) convertView.getTag();
                resetViewHolder(hodler);
            }

            hodler.stringList.setText(data.get(position));
            // 绑定数据、以及事件触发
            final int n = position;
            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // channelId = data.get(n);
                    Werks.setText(data.get(n));
                    overdialog.cancel();
                }
            });
            return convertView;
        }
    }

    /**
     * description: 线别适配器
     * author: xg.chen
     * date: 2017/6/26 9:00
     * version: 1.0
     */
    protected class ViewHodler1 {
        TextView stringList = null;
    }

    protected void resetViewHolder(ViewHodler1 pViewHolder) {
        pViewHolder.stringList.setText(null);
    }

    public class SettingAdapter1 extends BaseAdapter {
        private List<String> data = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public SettingAdapter1(Context context, List<String> data) {
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return data.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler1 hodler;
            if (convertView == null) {
                // 获取组件布局
                hodler = new ViewHodler1();
                convertView = layoutInflater.inflate(
                        R.layout.dialog_search_list_child, null);
                hodler.stringList = (TextView) convertView
                        .findViewById(R.id.werksName);
                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodler1) convertView.getTag();
                resetViewHolder(hodler);
            }

            hodler.stringList.setText(data.get(position));
            // 绑定数据、以及事件触发
            final int n = position;
            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // channelId = data.get(n);
                    Zlinecode.setText(data.get(n));
                    overdialog.cancel();
                }
            });
            return convertView;
        }
    }

    /**
     * description: 班别适配器
     * author: xg.chen
     * date: 2017/6/26 9:00
     * version: 1.0
     */
    protected class ViewHodlerZbc {
        TextView stringList = null;
    }

    protected void resetViewHolder(ViewHodlerZbc pViewHolder) {
        pViewHolder.stringList.setText(null);
    }

    public class SettingAdapterZbc extends BaseAdapter {
        private List<String> data = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public SettingAdapterZbc(Context context, List<String> data) {
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return data.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodlerZbc hodler;
            if (convertView == null) {
                // 获取组件布局
                hodler = new ViewHodlerZbc();
                convertView = layoutInflater.inflate(
                        R.layout.dialog_search_list_child, null);
                hodler.stringList = (TextView) convertView
                        .findViewById(R.id.werksName);
                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodlerZbc) convertView.getTag();
                resetViewHolder(hodler);
            }

            hodler.stringList.setText(data.get(position));
            // 绑定数据、以及事件触发
            final int n = position;
            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // channelId = data.get(n);
                    Zbc.setText(data.get(n));
                    overdialog.cancel();
                }
            });
            return convertView;
        }
    }

    /**
     * description: 选择单位
     * author: xg.chen
     * date: 2017/6/26 13:12
     * version: 1.0
     */
    protected class ViewHodlerMeins {
        TextView stringList = null;
    }

    protected void resetViewHolder(ViewHodlerMeins pViewHolder) {
        pViewHolder.stringList.setText(null);
    }

    public class SettingAdapterMeins extends BaseAdapter {
        private List<String> data = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public SettingAdapterMeins(Context context, List<String> data) {
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return data.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodlerMeins hodler;
            if (convertView == null) {
                // 获取组件布局
                hodler = new ViewHodlerMeins();
                convertView = layoutInflater.inflate(
                        R.layout.dialog_search_list_child, null);
                hodler.stringList = (TextView) convertView
                        .findViewById(R.id.werksName);
                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodlerMeins) convertView.getTag();
                resetViewHolder(hodler);
            }

            hodler.stringList.setText(data.get(position));
            // 绑定数据、以及事件触发
            final int n = position;
            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Meins.setText(data.get(n));
                    overdialog.cancel();
                }
            });
            return convertView;
        }
    }

    /**
     * description: 加载图片开始
     * author: xg.chen
     * date: 2017/6/26 11:56
     * version: 1.0
     */
    private void showWaitingDialog() {
        if (waitingDialog == null) {

            waitingDialog = new Dialog(this, R.style.TransparentDialog);
            waitingDialog.setContentView(R.layout.login_waiting_dialog);
            DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    ImageView img = (ImageView) waitingDialog.findViewById(R.id.loading);
                    ((AnimationDrawable) img.getDrawable()).start();
                }
            };
            DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // updateButtonLook(false);
                }
            };
            waitingDialog.setOnShowListener(showListener);
            waitingDialog.setCanceledOnTouchOutside(false);
            waitingDialog.setOnCancelListener(cancelListener);
            waitingDialog.show();
        }
    }

    /**
     * description: 加载结束
     * author: xg.chen
     * date: 2017/6/26 11:56
     * version: 1.0
     */
    private void dismissWaitingDialog() {
        if (waitingDialog != null) {
            ImageView img = (ImageView) waitingDialog.findViewById(R.id.loading);
            ((AnimationDrawable) img.getDrawable()).stop();

            waitingDialog.dismiss();
            waitingDialog = null;
        }
    }
}
