package com.wujia.witstore.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.VoiceContorlImp;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.wujia.witstore.R;
import com.wujia.witstore.connector.LoginConnetctor;
import com.wujia.witstore.connector.official.LoginConnetctorImpl;
import com.wujia.witstore.data.DBManager;
import com.wujia.witstore.data.model.Alarm;
import com.wujia.witstore.data.model.Area;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.data.model.Store;
import com.wujia.witstore.http.AppString;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.fragment.AcsFragment;
import com.wujia.witstore.ui.fragment.AlarmFragment;
import com.wujia.witstore.ui.fragment.CDFragment;
import com.wujia.witstore.ui.fragment.HcsFragment;
import com.wujia.witstore.ui.fragment.IlsFragment;
import com.wujia.witstore.ui.fragment.ImsFragment;
import com.wujia.witstore.ui.fragment.ManFragment;
import com.wujia.witstore.ui.fragment.MyKJFragment;
import com.wujia.witstore.ui.fragment.RfidFragment;
import com.wujia.witstore.ui.fragment.SerchFragment;
import com.wujia.witstore.ui.fragment.VmsFragment;
import com.wujia.witstore.ui.view.HelpDialog;
import com.wujia.witstore.utils.DataUtil;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends KJActivity {

    @BindView(id = R.id.img_back, click = true)
    private ImageView imgBack;
    @BindView(id = R.id.img_help, click = true)
    private ImageView imgHelp;
    @BindView(id = R.id.img_mue, click = true)
    private ImageView imgMue;
    @BindView(id = R.id.bottomRg)
    private RadioGroup bottomRg;
    @BindView(id = R.id.rbOne)
    private RadioButton homePage;
    @BindView(id = R.id.rbTwo)
    private RadioButton voiceControl;
    @BindView(id = R.id.rbThree)
    private RadioButton my;
    ManFragment manFragment;
    @BindView(id=R.id.tv_tile)
    private TextView tvTile;
    private long mExitTime;
    MyKJFragment kjFragment;
    private MueOnChang mueOnChang;
    private VoiceContorlImp voice; //声控控件
    private MainActivity myActiy;
    private ArrayList<String> titleList=new ArrayList<>();
    /***
     * 初始化布局
     */
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=55effd24");
        voice = new VoiceContorlImp();
        voice.setRootView(this);
        myActiy=this;

    }

    /***
     * 初始化控件
     */
    @Override
    public void initWidget() {
        manFragment = new ManFragment();
        manFragment.setIntActivityData(new ManFragment.IntActivityData() {
            @Override
            public void intData(View view) {
                imgBack.setVisibility(View.GONE);
                imgHelp.setVisibility(View.VISIBLE);
            }
        });
        manFragment.setManFrtClickListener(new ManFragment.ManFrtClickListener() {
            @Override
            public void onMyClick(View v) {
                switch (v.getId()) {
                    case R.id.img_bj:
                        KJFragment kj = new AlarmFragment();
                        myChangeFragment(R.id.fm_content, kj, true);
                        break;
                    case R.id.img_mjj:
                        kjFragment = new ImsFragment();
                        myChangeFragment(R.id.fm_content, kjFragment, true);
                        break;
                    case R.id.img_mj:
                        kjFragment = new AcsFragment();
                        myChangeFragment(R.id.fm_content, kjFragment, true);
                        break;
                    case R.id.img_gp:
                        KJFragment cdFragment = new CDFragment();
                        myChangeFragment(R.id.fm_content, cdFragment, true);
                        break;
                    case R.id.img_sp:
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setComponent(new ComponentName("com.mcu.iVMS",
                                "com.mcu.iVMS.ui.control.loading.LoadingActivity"));
                        startActivity(intent);
                        break;
                    case R.id.img_wsd:
                        kjFragment = new HcsFragment();
                        myChangeFragment(R.id.fm_content, kjFragment, true);
                        break;
                    case R.id.img_rfid:
                        KJFragment kjr = new RfidFragment();
                        myChangeFragment(R.id.fm_content, kjr, true);
                        break;
                    case R.id.img_dg:
                        kjFragment = new IlsFragment();
                        myChangeFragment(R.id.fm_content, kjFragment, true);
                        break;
                    case R.id.img_serch:
                        KJFragment serchFragment = new SerchFragment();
                        myChangeFragment(R.id.fm_content, serchFragment, true);
                        break;
                }
            }
        });
        myChangeFragment(R.id.fm_content, manFragment, true);
        bottomRg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.rbTwo:
                        // TODO: 2015/8/10 点击声控事件
                        voiceStart();
                        break;
                }
            }
        });

        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rbOne:
                        // TODO: 2015/8/10 点击首页事件
                        RadioButton tempButton1 = (RadioButton) findViewById(checkedId);
                        tempButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myChangeFragment(R.id.fm_content, manFragment, true);
                            }
                        });
                        break;

                    case R.id.rbTwo:
                        // TODO: 2015/8/10 点击声控事件
                        RadioButton tempButton2 = (RadioButton) findViewById(checkedId);
                        tempButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                voiceStart();
                            }
                        });
                        break;

                    case R.id.rbThree:
                        // TODO: 2015/8/10 点击我的事件
                        RadioButton tempButton3 = (RadioButton) findViewById(checkedId);
                        tempButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(myActiy, MyActivity.class));
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        });
    }



    /***
     * 初始化数据
     */
    @Override
    public void initData() {
         LoginConnetctor lg =new LoginConnetctorImpl();
        lg.mseQuery(LoginActivity.appContext.getUserId(), "", callback);
    }

    HttpCallBack callback = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                Log.i("callback_Stroe", new String(t));

                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(
                        new String(t));
                if (resultInfo.getStatus().equals("0")) {
                    ArrayList<Map<?, ?> > resultMap =  (ArrayList<Map<?, ?> >) resultInfo .getResult();
                    if(resultMap!=null) {
                        titleList.clear();
                        for (Map a : resultMap) {
                            titleList.add(a.get("title").toString().replace(" ", ""));
                        }
                    }
                } else {
                    ViewInject.toast(resultInfo.getMsg());
                }
            } catch (Exception e) {
                Log.e("广播查询失败", e.getMessage());
                ViewInject.toast(AppString.ERR_TIMEOUT);
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            Log.e("广播查询失败", strMsg);
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_mue:
                mueOnChang.onChangMue(v);
                break;

            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.img_help:
                HelpDialog helpDialog=new HelpDialog(this);
                helpDialog.createHelpDialog();
                break;
        }
    }

    public void myChangeFragment(int resView, KJFragment targetFragment, boolean toStack) {
        onChangNavImg(targetFragment);
        android.app.FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(resView, targetFragment, targetFragment.getClass().getName());
        if (toStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void onChangNavImg(KJFragment targetFragment) {
        if (manFragment == targetFragment) {
            imgBack.setVisibility(View.GONE);
            imgHelp.setVisibility(View.VISIBLE);
        } else {
            imgBack.setVisibility(View.VISIBLE);
            imgHelp.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {

        if (null != manFragment && manFragment.isVisible()) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                KJActivityStack.create().AppExit(aty);
            }
        } else {
            super.onBackPressed();
        }
    }

    public interface MueOnChang{
          void onChangMue(View v);
    }

    public void setMueOnChang(MueOnChang mueOnChang) {
        this.mueOnChang = mueOnChang;
    }

    private void voiceStart() {
        voice.startVoice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        voice.close();
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

}
