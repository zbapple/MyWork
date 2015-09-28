package com.wujia.witstore.ui.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wujia.witstore.R;
import com.wujia.witstore.data.DBManager;
import com.wujia.witstore.data.model.Area;
import com.wujia.witstore.data.model.Store;
import com.wujia.witstore.ui.AppContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by ZOUBIN on 2015/9/1.
 * 9:53
 */
public class WheelDialog implements OnWheelChangedListener {

    public  int SHOWNUM;

    private Context context;
    /**
     * 库区的WheelView控件
     */
    private WheelView one;
    /**
     * 分区WheelView控件
     */
    private WheelView two;
    /**
     * 密集架例WheelView控件
     */
    private WheelView three;
    /**确定按钮*/
    private Button btConfirm;
    /**
     * 一级轮子
     */
    private String[] oneDatas;
    /**
     * key - 一级 value - 二级
     */
    private Map<String, String[]> twoDatas ;
    /**
     * key 二级 values - 三级
     */
    private Map<String, String[]> threeDatas;
    /**
     * 一级轮子
     */
    private String[] oneDatasKey;
    /**
     * key - 一级 value - 二级
     */
    private Map<String, String[]> twoDatasKey;
    /**
     * key 二级 values - 三级
     */
    private Map<String, String[]> threeDatasKey  ;
    /**
     * 当前（一级“省”）的名称
     */
    private String oneName;
    /**
     * 当前（二级“市”）的名称
     */
    private String twoName;
    /**
     * 当前（三级“区”）的名称
     */
    private String threeName ="";
    /**
     * 当前（一级“省”）的名称
     */
    private String oneKey;
    /**
     * 当前（二级“市”）的名称
     */
    private String twoKey;
    /**
     * 当前（三级“区”）的名称
     */
    private String threeKey ="";
    private int showWheelNum;
    private   Dialog loadingDialog;

    private ConfirmBtClickListener confirmBtClickListener;

    public ConfirmBtClickListener getConfirmBtClickListener() {
        return confirmBtClickListener;
    }

    public void setConfirmBtClickListener(ConfirmBtClickListener confirmBtClickListener) {
        this.confirmBtClickListener = confirmBtClickListener;
    }

    public WheelDialog(Context context,int showWheelNum){
        this.context=context;
        this.showWheelNum=showWheelNum;
    }

    @SuppressLint("InflateParams")
    public  Dialog createWheelDialog(String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.store, null);//得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.ly_wheel);// 加载布局
        initWheel(v, "请选择库房");
        loadingDialog = new Dialog(context, R.style.MyWheelDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(false); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(true); //点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();
        return loadingDialog;
    }

    private  void initWheel( View v,String msg){
        initData();
        one = (WheelView) v.findViewById(R.id.id_province);
        one.setViewAdapter(new ArrayWheelAdapter<String>(context, oneDatas));
        one.addChangingListener(this);    // 添加change事件
        one.setVisibleItems(5);

        two = (WheelView) v.findViewById(R.id.id_city);
        two.addChangingListener(this); // 添加change事件
        two.setVisibleItems(5);

        three = (WheelView) v.findViewById(R.id.id_area);
        three.addChangingListener(this); // 添加change事件
        three.setVisibleItems(5);
        switch (showWheelNum){
            case 0:
                 two.setVisibility(View.GONE);
                 three.setVisibility(View.GONE);
                break;
            case 1:
                three.setVisibility(View.GONE);
                break;
        }


        btConfirm=(Button)v.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getConfirmBtClickListener() != null) {
                    getConfirmBtClickListener().onConfirmClick(v);
                }
                loadingDialog.dismiss();
            }
        });

        TextView tipTextView = (TextView) v.findViewById(R.id.tv_tile);// 提示文字
        tipTextView.setText(msg);

         updateCities();
         updateAreas();
         updateLi();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas()
    {
        int pCurrent = two.getCurrentItem();
        String[] areas=null;
        if(null!=twoDatas&&twoDatas.containsKey(oneName)) {
            twoName = twoDatas.get(oneName)[pCurrent];
            twoKey  =twoDatasKey.get(oneName)[pCurrent];
            areas = threeDatas.get(oneKey+twoKey);
        }
        if (areas == null)
        {
            areas = new String[] { "" };
        }
        three.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
        three.setCurrentItem(0);
        updateLi();
    }

    private void updateLi(){
        int pCurrent = three.getCurrentItem();
        String[] li=null;
        if(null!=threeDatas&&threeDatas.containsKey(oneKey+twoKey)) {
            threeName = threeDatas.get(oneKey+twoKey)[pCurrent];
            threeKey  =threeDatasKey.get(oneKey+twoKey)[pCurrent];
            li = threeDatas.get(oneKey+twoKey);
        }
        if (li == null)
        {
            threeName="";
            threeKey="";
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities()
    {
        int pCurrent = one.getCurrentItem();
        oneName = oneDatas[pCurrent];
        oneKey  = oneDatasKey[pCurrent];
        String[] cities ;
        if(null!=twoDatas&&twoDatas.containsKey(oneName)) {
            cities = twoDatas.get(oneName);
        }else{
            cities = new String[] { "" };
        }
        two.setViewAdapter(new ArrayWheelAdapter(context, cities));
        two.setCurrentItem(0);
        updateAreas();

    }

    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue)
    {
        if (wheel == one)
        {
            updateCities();
        } else if (wheel == two)
        {
            updateAreas();

        } else if (wheel == three)
        {
            updateLi();
        }
    }

    public void showChoose(View view)
    {
        Toast.makeText(context, oneKey + twoKey + threeKey, Toast.LENGTH_LONG).show();
    }

    public interface ConfirmBtClickListener {
        void onConfirmClick(View v);
    }

    private void initData(){
        this.oneDatas=WheelData.getInstance().getOneDatas();
        this.oneDatasKey=WheelData.getInstance().getOneDatasKey();
        this.twoDatas=WheelData.getInstance().getTwoDatas();
        this.twoDatasKey=WheelData.getInstance().getTwoDatasKey();
        this.threeDatas=WheelData.getInstance().getThreeDatas();
        this.threeDatasKey=WheelData.getInstance().getThreeDatasKey();
    }

    public WheelView getOne() {
        return one;
    }

    public WheelView getTwo() {
        return two;
    }

    public WheelView getThree() {
        return three;
    }

    public String getOneKey() {
        return oneKey;
    }

    public String getTwoKey() {
        return twoKey;
    }

    public String getThreeKey() {
        return threeKey;
    }

    public String getOneName() {
        return oneName;
    }

    public String getTwoName() {
        return twoName;
    }

    public String getThreeName() {
        return threeName;
    }
}
