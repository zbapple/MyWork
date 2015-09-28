package com.wujia.witstore.ui.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.connector.ImsConnector;
import com.wujia.witstore.connector.official.ImsConnectorImpl;
import com.wujia.witstore.data.model.Ims;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.AppString;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.ui.view.WheelDialog;
import com.wujia.witstore.utils.DataUtil;
import com.wujia.witstore.utils.MyUtils;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZOUBIN on 2015/8/19.
 * 15:20
 */
public class ImsFragment extends MyKJFragment {

    ArrayList<Ims> listResult;
    RelativeLayout rlLie;
    TextView tv_lie1,tv_lie2;
    ImageView imgbt_hl,imgbt_gb,imgbt_tf;

    @Override
    public void showListView(){
     super.showListView();
        rlLie.setVisibility(View.VISIBLE);
        tv_lie1.setText(getOneName().getText());
        tv_lie2.setText(getTwoName().getText());
        getThreeName().setVisibility(View.GONE);
    }

    @Override
    public  void showViewPag(){
        super.showViewPag();
        rlLie.setVisibility(View.GONE);
        getThreeName().setVisibility(View.VISIBLE);
    }

    @Override
    protected void myInitWidget(View parentView) {
        super.myInitWidget(parentView);
        rlLie= (RelativeLayout) parentView.findViewById(R.id.rl_lie);
        tv_lie1=(TextView)parentView.findViewById(R.id.tv_lie1);
        tv_lie2=(TextView)parentView.findViewById(R.id.tv_lie2);
        imgbt_hl=(ImageView)parentView.findViewById(R.id.imgbt_hl);
        imgbt_gb=(ImageView)parentView.findViewById(R.id.imgbt_gb);
        imgbt_tf=(ImageView)parentView.findViewById(R.id.imgbt_tf);
        imgbt_hl.setOnClickListener(myLieOnClick);
        imgbt_gb.setOnClickListener(myLieOnClick);
        imgbt_tf.setOnClickListener(myLieOnClick);
    }

    @Override
    public void setMyViewPagerView(View userLayout, Object object, int position) {
        if(position==0) {
            listResult = (ArrayList<Ims>) DataUtil.toListBean(
                    (List<Map<?, ?>>) dataList,
                    Ims.class);
            if (listResult != null) {
                Ims ims = listResult.get(position);
                if (ims != null) {
                    getOneName().setText(ims.getStore().getName());
                    getTwoName().setText(ims.getArea().getName());
                    getThreeName().setText(ims.getColumnNo() + "例");
                }
            }
        }
        ImageView imgZk = (ImageView) userLayout.findViewById(R.id.img_zk); //左开
        ImageView imgYk = (ImageView) userLayout.findViewById(R.id.img_yk);// 右开
        ImageView imgTz = (ImageView) userLayout.findViewById(R.id.img_tz);// 停止
        ImageView imgTf = (ImageView) userLayout.findViewById(R.id.img_tf);// 通风
        ImageView imgGb = (ImageView) userLayout.findViewById(R.id.img_gb);// 关闭
        ImageView imgHl = (ImageView) userLayout.findViewById(R.id.img_hl);// 合拢
        imgZk.setOnClickListener(this);// 设置点击监听事件
        imgYk.setOnClickListener(this);// 设置点击监听事件
        imgTz.setOnClickListener(this);// 设置点击监听事件
        imgTf.setOnClickListener(this);// 设置点击监听事件
        imgGb.setOnClickListener(this);// 设置点击监听事件
        imgHl.setOnClickListener(this);// 设置点击监听事件

    }

    @Override
    public void setMyListViewValue(int position, View view, Object item) {

        if (listResult != null) {
            Ims ims = listResult.get(position);
            if (ims != null) {
                TextView tv_lieName=(TextView)view.findViewById(R.id.tv_lieName);
                tv_lieName.setText("第"+ims.getColumnNo()+"例");
                ImageView imgZk = (ImageView) view.findViewById(R.id.imgbt_zk); //左开
                ImageView imgYk = (ImageView) view.findViewById(R.id.imgbt_yk);// 右开
                ImageView imgTz = (ImageView) view.findViewById(R.id.imgbt_tz);// 停止
                imgZk.setOnClickListener(this.myLieItmeOnClick);
                imgYk.setOnClickListener(this.myLieItmeOnClick);
                imgTz.setOnClickListener(this.myLieItmeOnClick);
                imgZk.setTag(ims.getId());
                imgYk.setTag(ims.getId());
                imgTz.setTag(ims.getId());
            }
        }


    }

    @Override
    public KjDataInfo inItMyFragmentData() {
        return new KjDataInfo(R.layout.ims,R.layout.ims_list,R.drawable.mjj_c_wz_bg,R.string.index_mjj,KjDataInfo.TWO);
    }

    @Override
    protected void loadingData() {
        ImsConnector imsConn = new ImsConnectorImpl();
        imsConn.imsQuery(DataUtil.getInt(wheelDialog.getOneKey(), 0), DataUtil.getInt(wheelDialog.getTwoKey(), 0), LoginActivity.appContext.getUserId(), "1", callback_Query);

    }

    OnClickListener myLieOnClick =new OnClickListener() {
        @Override
        public void onClick(View v) {
            Ims ims=null;

            if (listResult != null && listResult.size() > 0) {
                ims =  listResult.get(0);
            }
            switch (v.getId()) {

                case R.id.imgbt_gb:
                    if (ims != null) {
                        imshandle("5", ims.getId());
                    }
                    break;

                case R.id.imgbt_hl:
                    if (ims != null) {
                        imshandle("4", ims.getId());
                    }
                    break;

                case R.id.imgbt_tf:
                    if (ims != null) {
                        imshandle("3", ims.getId());
                    }
                    break;
            }
        }
    };


    OnClickListener myLieItmeOnClick =new OnClickListener(){

        @Override
        public void onClick(View v) {
            int id= (int) v.getTag();
            switch (v.getId()) {
                case R.id.imgbt_yk:
                        imshandle("2", id);
                    break;

                case R.id.imgbt_zk:
                        imshandle("1", id);
                    break;

                case R.id.imgbt_tz:
                        imshandle("0", id);
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        super.onClick(v);

        Ims ims=null;

        if (listResult != null && listResult.size() > 0) {
            ims =  listResult.get(vp .getCurrentItem());
        }
        switch (v.getId()) {

            case R.id.img_gb:
                if (ims != null) {
                    imshandle("5", ims.getId());
                }
                break;

            case R.id.img_hl:
                if (ims != null) {
                    imshandle("4", ims.getId());
                }
                break;

            case R.id.img_tf:
                if (ims != null) {
                    imshandle("3", ims.getId());
                }
                break;

            case R.id.img_yk:
                if (ims != null) {
                    imshandle("2", ims.getId());
                }
                break;

            case R.id.img_zk:
                if (ims != null) {
                    imshandle("1", ims.getId());
                }
                break;

            case R.id.img_tz:
                if (ims != null) {
                    imshandle("0", ims.getId());
                }
                break;
        }
    }

    @Override
    protected void headClick(WheelDialog wheelDialog) {
        loadingData();
        tv_lie1.setText(wheelDialog.getOneName());
        tv_lie2.setText(wheelDialog.getTwoName());
    }

    /**
     * @Title: imshandle
     * @Description: 密集架操作方法
     * @param mack
     *            操作标识
     * @param id
     *            密集架ID
     * @return void
     */
    public void imshandle(String mack, int id) {
       if(start) {
           ImsConnector imsConn = new ImsConnectorImpl();
           imsConn.imsHandle(mack, LoginActivity.appContext.getUserId(), id,
                   MyUtils.getDeviceNumb(), MyUtils.getOsVersion(), "1",
                   imsCallback_handles);
       }
        super.start=false;
    }

    public HttpCallBack imsCallback_handles = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                String result = new String(t);
                Log.e("callback_handles", result);
                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(result);
                if (resultInfo.getStatus().equals("0")) {

                    if(resultInfo.getFlag().equals("IMS_RIGHTOPEN")){
                        setBlackImg(R.id.Rlbl, R.drawable.mjj_yk_icon);
                    }
                    if(resultInfo.getFlag().equals("IMS_LEFTOPEN")){
                        setBlackImg(R.id.Rlbl, R.drawable.mjj_zk_icon);
                    }
                    if(resultInfo.getFlag().equals("IMS_WIND")){
                        setBlackImg(R.id.Rlbl, R.drawable.mjj_tf_icon);
                    }
                    if(resultInfo.getFlag().equals("IMS_CLOSE")){
                        setBlackImg(R.id.Rlbl, R.drawable.mjj_hl_icon);
                    }
                    closeLoading(dialog);
                    ViewInject.toast(resultInfo.getMsg());
                } else {
                    closeLoading(dialog);
                    ViewInject.toast(resultInfo.getMsg());
                }
            } catch (Exception e) {
                closeLoading(dialog);
                ViewInject.toast(e.getMessage());
            }
            finally{
                start=true;
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            closeLoading(dialog);
            start=true;
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        vp.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Log.i("当前页:", arg0+"");
            if(listResult!=null){
                Ims ims =  listResult.get(arg0);
                if(ims!=null){
                    getOneName().setText(ims.getStore().getName());
                    getTwoName().setText(ims.getArea().getName());
                    getThreeName().setText(ims.getColumnNo()+"例");
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    }

    public void setBlackImg(int a, int img) {
        View view = myPageVl.get(vp.getCurrentItem());
        RelativeLayout rl = (RelativeLayout) view.findViewById(a);
        if (null != rl) {
            Drawable drawable = getResources().getDrawable(img);
            if (null != drawable) {
                rl.setBackgroundDrawable(drawable);
            }
        }
    }

}
