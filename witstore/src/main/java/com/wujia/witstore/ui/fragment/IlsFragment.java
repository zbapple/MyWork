package com.wujia.witstore.ui.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.connector.IlsConnector;
import com.wujia.witstore.connector.official.IlsConnectorImpl;
import com.wujia.witstore.data.model.Ils;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.AppString;
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
public class IlsFragment extends MyKJFragment {

    ArrayList<Ils> listResult;

    @Override
    public void setMyViewPagerView(View userLayout, Object object, int position) {
        Ils ils ;
        TextView tvIlsState = (TextView) userLayout.findViewById(R.id.tv_ils_state);
        ImageView imageView =(ImageView)userLayout.findViewById(R.id.img_ils);
        if (position == 0) {
            listResult = (ArrayList<Ils>) DataUtil.toListBean(
                    (List<Map<?, ?>>) dataList,
                    Ils.class);
        }

        ils = listResult.get(position);

        if (listResult != null&&position == 0) {
            if (ils != null) {
                getOneName().setText(ils.getStore().getName());
                getTwoName().setText(ils.getArea() != null ? ils.getArea().getName() : "");
                getThreeName().setText(ils.getName());
            }
        }
        if (ils!=null&&ils.getStatus() != null && ils.getStatus().equals(AppConfig.ILS_OPEN)) {
            tvIlsState.setText(R.string.open);
            setBlackImg(imageView, R.drawable.light_c_act_dg);
        } else {
            tvIlsState.setText(R.string.close);
            setBlackImg(imageView, R.drawable.light_c_dg);
        }
        CheckBox cbox_onoff = (CheckBox) userLayout.findViewById(R.id.cbox_onoff);
        cbox_onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int currentItem = vp.getCurrentItem();
                Ils ils = listResult.get(currentItem);
                if (isChecked) {
                    ilsHandle(ils.getDeviceId(), 1);//开
                } else {
                    ilsHandle(ils.getDeviceId(), 0);//关
                }
            }

        });
    }

    @Override
    public void setMyListViewValue(int position, View view, Object item) {
        Ils ils ;
        Switch tvIlsState = (Switch) view.findViewById(R.id.sw_onoff);
        TextView tvName= (TextView) view.findViewById(R.id.tv_name);
        ils = listResult.get(position);
        tvName.setText(ils.getName());
        if (listResult != null&&position == 0) {
            if (ils != null) {
                getOneName().setText(ils.getStore().getName());
                getTwoName().setText(ils.getArea() != null ? ils.getArea().getName() : "");
            }
        }
        if (ils!=null&&ils.getStatus() != null && ils.getStatus().equals(AppConfig.ILS_OPEN)) {
            tvIlsState.setChecked(true);
        } else {
            tvIlsState.setChecked(false);
        }
        tvIlsState.setTag(position);
        tvIlsState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position= (int) buttonView.getTag();
                Ils ils = listResult.get(position);
                if (isChecked) {
                    ilsHandle(ils.getDeviceId(), 1);//开
                } else {
                    ilsHandle(ils.getDeviceId(), 0);//关
                }
            }
        });
    }

    @Override
    public KjDataInfo inItMyFragmentData() {
        return new KjDataInfo(R.layout.ils, R.layout.ils_list, R.drawable.light_c_wz_bg, R.string.index_dg, KjDataInfo.ONE);
    }

    @Override
    protected void loadingData() {
        IlsConnector ilsConn = new IlsConnectorImpl();
        ilsConn.ilsQuery(DataUtil.getInt(wheelDialog.getOneKey(), 0), DataUtil.getInt(wheelDialog.getTwoKey(), 0), LoginActivity.appContext.getUserId(), "1", callback_Query);
    }

    @Override
    protected void headClick(WheelDialog wheelDialog) {
        loadingData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        vp.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);


    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Log.i("当前页:", arg0 + "");
            if (listResult != null) {
                Ils ils = listResult.get(arg0);
                if (ils != null) {
                    getOneName().setText(ils.getStore().getName());
                    getTwoName().setText(ils.getArea()!=null?ils.getArea().getName():"");
                    getThreeName().setText(ils.getName());
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

    public void ilsHandle(int id, int mack) {
        if (start) {
            IlsConnector ilsConn = new IlsConnectorImpl();
            ilsConn.ilsHandle(LoginActivity.appContext.getUserId(), id,
                    MyUtils.getDeviceNumb(), MyUtils.getOsVersion(), mack, "token",
                    callback_handles);
        }
        super.start = false;
    }

    public HttpCallBack callback_handles = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                String result = new String(t);
                Log.e("callback_handles", result);
                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(result);
                if (resultInfo.getStatus().equals("0")) {
                    closeLoading(dialog);
                    View view = myPageVl.get(vp.getCurrentItem());
                    TextView tvIlsState = (TextView) view.findViewById(R.id.tv_ils_state);
                    if (resultInfo.getFlag().equals("ILS_OPEN")) {
                        tvIlsState.setText(R.string.open);
                        setSrcImg(R.id.img_ils, R.drawable.light_c_act_dg);
                    }
                    if (resultInfo.getFlag().equals("ILS_CLOSE")) {
                        tvIlsState.setText(R.string.close);
                        setSrcImg(R.id.img_ils, R.drawable.light_c_dg);
                    }

                }
            } catch (Exception e) {
                closeLoading(dialog);
                ViewInject.toast(AppString.ERR_TIMEOUT);
            } finally {
                start = true;
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            closeLoading(dialog);
            start = true;
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    @Override
    public void showListView(){
        super.showListView();

        getThreeName().setVisibility(View.GONE);
    }

    @Override
    public  void showViewPag(){
        super.showViewPag();
        getThreeName().setVisibility(View.VISIBLE);
    }

}
