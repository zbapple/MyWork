package com.wujia.witstore.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.AppString;
import com.wujia.witstore.ui.activity.MainActivity;
import com.wujia.witstore.ui.myadapter.MyArrayAdapter;
import com.wujia.witstore.ui.myadapter.MyPagerAdapter;
import com.wujia.witstore.ui.view.MyViewPager;
import com.wujia.witstore.ui.view.WheelDialog;
import com.wujia.witstore.utils.ClassUtil;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZOUBIN on 2015/8/25.
 * 11:15
 */
public abstract class MyKJFragment extends KJFragment implements MyPagerAdapter.InitViewValue, MyArrayAdapter.InitArrayValue {

    private MyPagerAdapter myPagerAdapter;
    private MyArrayAdapter myArrayAdapter;
    protected ArrayList<?> dataList; //数据
    protected MyViewPager vp; // 横向滑动控件
    protected ListView lv;//纵向显示控件
    protected Map<Integer, View> myPageVl = new HashMap<>();
    protected int showStatus;
    protected static final int SHOW_VIEWPAGER = 0;
    protected static final int SHOW_LISTVIEW = 1;
    protected Dialog dialog; // Loading控件
    protected int intoPostion = 0; // 当前横向滑动显示的页面序号
    protected RelativeLayout rlHead;
    protected TextView oneName;
    protected TextView twoName;
    protected TextView threeName;
    private KjDataInfo kjDataInfo;
    public WheelDialog wheelDialog = null;
    public boolean start = true;
    protected MainActivity mainActivity;

    MyKJFragment() {
    }

    /**
     * 设置myPagerAdapter适配器值
     */
    public abstract void setMyViewPagerView(View userLayout, Object object, int position);

    /**
     * 设置listView适配器值
     */
    public abstract void setMyListViewValue(int position, View view, Object item);

    public abstract KjDataInfo inItMyFragmentData();

    protected abstract void loadingData();

    protected abstract void headClick(WheelDialog wheelDialog); //库房分区点击事件

    class KjDataInfo {
        private int viewPagerLayout;
        private int listViewLayout;
        private int headBackGround;
        private int tileName;
        private int showWheel;
        public final static int ONE = 0;
        public final static int TWO = 1;

        KjDataInfo(int viewPagerLayout, int listViewLayout, int headBackGround, int tileName, int showWheel) {
            this.viewPagerLayout = viewPagerLayout;
            this.listViewLayout = listViewLayout;
            this.headBackGround = headBackGround;
            this.tileName = tileName;
            this.showWheel = showWheel;
        }

    }

    protected HttpCallBack callback_Query = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                Log.i("callback_Query", new String(t));
                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(new String(t));
                if (resultInfo.getStatus().equals("0")) {
                    ArrayList listResult = (ArrayList) resultInfo.getResult();
                    dataList.clear();
                    if (null != listResult) {
                       int id= mainActivity.getIntent().getIntExtra("id", AppConfig.SERCH_DIF);
                        if(id==AppConfig.SERCH_DIF) {
                            dataList.addAll(listResult);
                        }else{
                            dataList.addAll(ClassUtil.getInstance().serchListValue(listResult,"id",id+""));
                            mainActivity.getIntent().putExtra("id", AppConfig.SERCH_DIF);
                        }
                    }
                    refreshData();
                    closeLoading(dialog); // 取消Loading
                } else {
                    closeLoading(dialog);
                    ViewInject.toast(resultInfo.getMsg());
                }
            } catch (Exception e) {
                closeLoading(dialog);
                e.printStackTrace();
                ViewInject.toast(AppString.ERR_TIMEOUT);
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            closeLoading(dialog);
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        return layoutInflater.inflate(R.layout.plan_man, viewGroup, false);
    }

    protected void myInitWidget(View parentView) {
    }

    protected void myInitData() {
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        kjDataInfo = inItMyFragmentData();
        if (null == kjDataInfo)
            kjDataInfo = new KjDataInfo(0, 0, 0, R.string.not_data, 0);
        wheelDialog = new WheelDialog(this.getActivity(), kjDataInfo.showWheel);
        wheelDialog.setConfirmBtClickListener(new WheelDialog.ConfirmBtClickListener() {
            @Override
            public void onConfirmClick(View v) {
                headClick(wheelDialog);
                oneName.setText(wheelDialog.getOneName());
                twoName.setText(wheelDialog.getTwoName());
                threeName.setText(wheelDialog.getThreeName());
            }
        });
        dataList = new ArrayList();
        if (kjDataInfo != null) {
            myPagerAdapter = new MyPagerAdapter(this.getActivity(), dataList, kjDataInfo.viewPagerLayout, this); // 初始化myPagerAdapter控件适配器的值
            myArrayAdapter = new MyArrayAdapter(this.getActivity(), kjDataInfo.listViewLayout, dataList, this); // 初始化listView控件适配器的值
        }
        myInitData();

    }

    /**
     * 初始化控件
     */
    @Override
    protected void initWidget(View parentView) {
        mainActivity = (MainActivity) getActivity();
        if (kjDataInfo != null) {
            vp = (MyViewPager) parentView.findViewById(R.id.kj_vp);
            lv = (ListView) parentView.findViewById(R.id.listView);
            rlHead = (RelativeLayout) parentView.findViewById(R.id.rl_head);
            oneName = (TextView) parentView.findViewById(R.id.tv_one);
            twoName = (TextView) parentView.findViewById(R.id.tv_two);
            threeName = (TextView) parentView.findViewById(R.id.tv_three);
            mainActivity.setMueOnChang(new MainActivity.MueOnChang() {
                @Override
                public void onChangMue(View v) {
                    onChangShowView();
                }
            });
            if (kjDataInfo.headBackGround != 0) {
                setBlackImg(rlHead, kjDataInfo.headBackGround);
            }
            TextView tileName = (TextView) this.getActivity().findViewById(R.id.tv_tile);
            tileName.setText(kjDataInfo.tileName);
            rlHead.setOnClickListener(this);
            vp.setAdapter(myPagerAdapter);
            vp.setCurrentItem(intoPostion);
            lv.setAdapter(myArrayAdapter);
        }
        myInitWidget(parentView);
    }

    @Override
    protected void threadDataInited() {
        loadingData();
    }

    /**
     * ViewPager中的控件赋值 vp.setObjectForPosition(userLayout, position);必须加上 动画处理
     */
    @Override
    public void setPagViewValue(View userLayout, Object object, int position) {
        setMyViewPagerView(userLayout, object, position);
        vp.setObjectForPosition(userLayout, position); // 设置动画参
        myPageVl.put(position, userLayout);
    }

    @Override
    public void setViewValue(int position, View view, Object item) {
        setMyListViewValue(position, view, item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_head:
                wheelDialog.createWheelDialog("请选择城市");
                break;
        }
    }

    public void onChangShowView() {
        switch (showStatus) {
            case SHOW_VIEWPAGER:
                showListView();
                break;
            case SHOW_LISTVIEW:
                showViewPag();
                break;
        }
    }

    public void showViewPag() {
        setShowStatus(SHOW_VIEWPAGER);
        lv.setVisibility(View.GONE);
        vp.setVisibility(View.VISIBLE);
    }

    public void showListView() {
        setShowStatus(SHOW_LISTVIEW);
        vp.setVisibility(View.GONE);
        lv.setVisibility(View.VISIBLE);
    }

    public void refreshData() {
        if(myPagerAdapter!=null)
        myPagerAdapter.notifyDataSetChanged();
        if(myArrayAdapter!=null)
        myArrayAdapter.notifyDataSetChanged();
    }

    /**
     * 设置切换状态
     */
    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public void closeLoading(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @SuppressWarnings("deprecation")
         public void setBlackImg(int a, int img) {
        View view = myPageVl.get(vp.getCurrentItem());
        ImageView rl = (ImageView) view.findViewById(a);
        if (null != rl) {
            Drawable drawable = getResources().getDrawable(img);
            if (null != drawable) {
                rl.setBackgroundDrawable(drawable);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void setSrcImg(int a, int img) {
        View view = myPageVl.get(vp.getCurrentItem());
        if(view!=null) {
            ImageView rl = (ImageView) view.findViewById(a);
            if (null != rl) {
                Drawable drawable = getResources().getDrawable(img);
                if (null != drawable) {
                    rl.setImageDrawable(drawable);
                }
            }
        }
    }

    public void setBlackImg(View view, int img) {
        if (null != view) {
            Drawable drawable = getResources().getDrawable(img);
            if (null != drawable) {

                view.setBackgroundDrawable(drawable);
            }
        }
    }

    public TextView getOneName() {
        return oneName;
    }

    public TextView getTwoName() {
        return twoName;
    }

    public TextView getThreeName() {
        return threeName;
    }

}
