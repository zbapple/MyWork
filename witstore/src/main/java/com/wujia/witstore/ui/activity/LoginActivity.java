package com.wujia.witstore.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.wujia.witstore.R;
import com.wujia.witstore.connector.LoginConnetctor;
import com.wujia.witstore.connector.StoreConnetctor;
import com.wujia.witstore.connector.official.LoginConnetctorImpl;
import com.wujia.witstore.connector.official.StoreConnetctorImpl;
import com.wujia.witstore.data.DBManager;
import com.wujia.witstore.data.model.Area;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.data.model.Store;
import com.wujia.witstore.http.AppString;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.AppContext;
import com.wujia.witstore.utils.DataUtil;
import com.wujia.witstore.utils.MyUtils;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by ZOUBIN on 2015/8/24.
 * 9:50
 */
public class LoginActivity extends KJActivity {

    @BindView(id = R.id.img_login, click = true)
    ImageView iBtnLogin;
    @BindView(id=R.id.et_username)
    TextView etvUserName;
    @BindView(id=R.id.et_password)
    TextView etvPassWord;
    @BindView(id = R.id.checkBox)
    private CheckBox checkBox1;
    @BindView(id = R.id.tv_cancel, click = true)
    private TextView ibtnCancel;
    @BindView(id = R.id.img_sz, click = true)
    private ImageView imgsz;
    private LoginConnetctor lg;
    public static AppContext appContext;
    private View layout;
    private EditText ip;
    LoginActivity ary;
    Intent intent;
    int dataNub ;
    private String isMemory = "";// isMemory变量用来判断SharedPreferences有没有数据，包括上面的YES和NO
    private String FILE = "saveUserNamePwd";// 用于保存SharedPreferences的文件
    private SharedPreferences sp = null;// 声明一个SharedPreferences
    static String YES = "yes";
    static String NO = "no";
    private String isFristLogin ;// isFristLogin变量用来判断是否是第一次登录有没有数据，包括上面的YES和NO
    ShimmerTextView tv;
    Shimmer shimmer;

    @Override
    public void setRootView() {
        setContentView(R.layout.login);
        ary=this;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        appContext = (AppContext) this.getApplication();
        sp = getSharedPreferences(FILE, MODE_PRIVATE);
        isMemory = sp.getString("isMemory", NO);
        isFristLogin=sp.getString("isFristLogin", YES);
        if (isMemory.equals(YES)) {
            checkBox1.setChecked(true);
            String name = sp.getString("name", "");
            String password = sp.getString("password", "");
            this.etvUserName.setText(name);
            this.etvPassWord.setText(password);
        }
        initAm();
        tv = (ShimmerTextView) findViewById(R.id.textView2);
        toggleAnimation();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_cancel:
                this.etvPassWord.setText("");
                break;

            case R.id.img_login:
                Login();
                break;

            case R.id.img_sz:
                LayoutInflater inflater = getLayoutInflater();
                layout = inflater.inflate(R.layout.alert,
                        (ViewGroup) findViewById(R.id.alert));
                ip = (EditText) layout.findViewById(R.id.ip);
                ip.setText(PreferenceHelper.readString(ary,
                        AppConfig.APP_SET, "ip", AppConfig.PATH));
                new AlertDialog.Builder(this)
                        .setTitle("设置")
                        .setView(layout)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        PreferenceHelper.write(ary,
                                                AppConfig.APP_SET, "ip", ip
                                                        .getText().toString());
                                    }
                                }).setNegativeButton("取消", null).show();
                break;
        }
    }

    // remenber方法用于判断是否记住密码，checkBox1选中时，提取出EditText里面的内容，放到SharedPreferences里面的name和password中
    public void remenber() {
        if (checkBox1.isChecked()) {
            if (sp == null) {
                sp = getSharedPreferences(FILE, MODE_PRIVATE);
            }
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("name", this.etvUserName.getText().toString());
            edit.putString("password", this.etvPassWord.getText().toString());
            edit.putString("isMemory", YES);
            edit.commit();

        } else {
            if (sp == null) {
                sp = getSharedPreferences(FILE, MODE_PRIVATE);
            }
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("isMemory", NO);
            edit.commit();
        }
    }


    private void Login() {
        if(!MyUtils.isAppInstalled(this, "com.mcu.iVMS")){
            String fileName="iVMS-4500_V4.0.1_build20150409.apk";
            String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+fileName;
            MyUtils.copyApkFromAssets(this, fileName, path);
            File file=new File(path);
            SystemTool.installApk(this, file);
        }
        remenber();
        String passWord = this.etvPassWord.getText().toString();
        String userName = this.etvUserName.getText().toString();
        if(passWord.isEmpty()||userName.isEmpty()){
            intent = new Intent(ary, MainActivity.class);
            ary.startActivity(intent);
            ViewInject.toast("登录成功！");
        }else
        {
            lg = new LoginConnetctorImpl();
            lg.authentication(userName, passWord, callback);
        }
    }

    private void initMyData() {
       // initVms();
        if (isFristLogin.equals(NO)) {
            dataNub++;
            initStore();
        }else{
            toIndexActivity();
        }
    }

    private void initStore() {
        StoreConnetctor scon = new StoreConnetctorImpl();
        scon.storeQuery(LoginActivity.appContext.getUserId(), "", callback_Stroe);
    }

    @SuppressWarnings("unchecked")
    HttpCallBack callback_Stroe = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                Log.i("callback_Stroe", new String(t));

                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(
                        new String(t));
                if (resultInfo.getStatus().equals("0")) {
                    Map<String, List<Map<?, ?>>> resultMap = (Map<String, List<Map<?, ?>>>) resultInfo
                            .getResult();
                    List<Store> storeList = DataUtil.toListBean(
                            resultMap.get("storeList"), Store.class);
                    List<Area> areaList = DataUtil.toListBean(
                            resultMap.get("subareaList"), Area.class);
                    DBManager dbManager = DBManager.getInstance();
                    toArea(areaList); // 这里因为没有实现OneToOne到数据库的映射，手动设置主键ID
                    dbManager.save(Area.class, areaList);
                    dbManager.save(Store.class, storeList);
                    Log.i("初始化：", "库房数据成功");
                    toIndexActivity();

                } else {
                    ViewInject.toast(resultInfo.getMsg());
                }
            } catch (Exception e) {
                Log.e("callback_Stroe", e.getMessage());
                ViewInject.toast(AppString.ERR_TIMEOUT);
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            Log.e("callback_Stroe", strMsg);
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    HttpCallBack callback = new HttpCallBack() {
        @Override
        public void onPreStar() {
            super.onPreStar();
            KJLoger.debug("即将开始http请求");
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                Log.i("LoginActivity-login:", new String(t));
                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(new String(t));
                if (resultInfo.getStatus().equals("0")) {
                    String cookie = headers.get("Set-Cookie");
                    Map<String, String> map = (Map<String, String>) resultInfo.getResult();
                    appContext.setUserId(map.get("userId")); // 得到用户ID
                    appContext.setUserName(map.get("userName"));// 等到用户名称
                    appContext.setCookie(cookie.substring(0,cookie.indexOf(";")));
                    Log.i("LoginActivity-login:", "登录成功！");
                    initMyData();
                } else {
                    ViewInject.toast(resultInfo.getMsg());
                }
            } catch (Exception e) {
                ViewInject.toast("服务器连接异常！" + e.getMessage());
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    private void toIndexActivity() {
        dataNub--;
        if (dataNub <= 0) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("isFristLogin", NO);
            edit.commit();
            intent = new Intent(ary, MainActivity.class);
            ary.startActivity(intent);
            ViewInject.toast("登录成功！");
        }
    }

    /**
     * @Title: toListBean
     * @Description: 手动设置库房主键ID
     * @param @param areaList
     * @return void
     */
    private void toArea(List<Area> areaList) {
        for (Area a : areaList) {
            a.setStoreId(a.getStore().getId());
            a.setStore(null);
        }
    }

    public void initAm(){
        ImageView ly= (ImageView) this.findViewById(R.id.imageView4);
        //动画集
        AnimationSet set = new AnimationSet(true);
        Animation alphaAnimation;
        alphaAnimation = new AlphaAnimation(0.6f, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        set.addAnimation(alphaAnimation);
        ly.startAnimation(set);
        ly.setClickable(true);

    }

    public void toggleAnimation() {
        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
        } else {
            shimmer = new Shimmer();
            shimmer.start(tv);
        }
    }
}
