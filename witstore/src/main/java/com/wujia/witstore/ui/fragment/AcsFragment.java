package com.wujia.witstore.ui.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.connector.AcsConnector;
import com.wujia.witstore.connector.official.AcsConnectorImpl;
import com.wujia.witstore.data.model.Acs;
import com.wujia.witstore.data.model.HandlLog;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppString;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.ui.activity.MainActivity;
import com.wujia.witstore.ui.myadapter.MyArrayAdapter;
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
public class AcsFragment extends MyKJFragment {

    ArrayList<Acs> listResult;
    ArrayList<HandlLog> myList = new ArrayList<>();
    MyArrayAdapter<HandlLog> myAcsArrayAdapter;

    @Override
    public void setMyViewPagerView(View userLayout, Object object, int position) {
        if (position == 0) {
            listResult = (ArrayList<Acs>) DataUtil.toListBean(
                    (List<Map<?, ?>>) dataList,
                    Acs.class);
        }
        final Acs acs = listResult.get(position);
        ListView myLv = (ListView) userLayout.findViewById(R.id.listView);
        myLv.setAdapter(myAcsArrayAdapter);
        if (position == 0) {
            queryHandlLog(acs.getId());
        }
        if (listResult != null && position == 0) {
            if (acs != null) {
                getOneName().setText(acs.getStore().getName());
                getTwoName().setText(acs.getArea() != null ? acs.getArea().getName() : "");
                getThreeName().setText(acs.getName());
            }
        }
        ImageView img_open = (ImageView) userLayout.findViewById(R.id.img_open);
        img_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acssHandle(acs.getId(), 1);
            }
        });

    }

    private void queryHandlLog(int id) {
        AcsConnector acssConn = new AcsConnectorImpl();
        acssConn.acsHandleLog(id, LoginActivity.appContext.getUserId(), "1", myAllback_Query);
    }

    protected HttpCallBack myAllback_Query = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                Log.i("callback_Query", new String(t));
                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(new String(t));
                if (resultInfo.getStatus().equals("0")) {
                    ArrayList listResult = (ArrayList) resultInfo.getResult();
                    if (null != listResult) {
                        listResult = (ArrayList<HandlLog>) DataUtil.toListBean(
                                (List<Map<?, ?>>) listResult,
                                HandlLog.class);
                        myList.clear();
                        myList.addAll(listResult);
                        myAcsArrayAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
        }
    };

    @Override
    public void setMyListViewValue(int position, View view, Object item) {
        final Acs acs = listResult.get(position);
        if (acs != null) {
            ImageView img_open = (ImageView) view.findViewById(R.id.img_open);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            tvName.setText(acs.getName());
            img_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acssHandle(acs.getId(), 1);
                }
            });
        }
    }

    @Override
    public KjDataInfo inItMyFragmentData() {
        return new KjDataInfo(R.layout.acs, R.layout.acs_list, R.drawable.door_c_wz_bg, R.string.index_mj, KjDataInfo.ONE);
    }

    @Override
    protected void loadingData() {
        AcsConnector acssConn = new AcsConnectorImpl();
        acssConn.acsQuery(DataUtil.getInt(wheelDialog.getOneKey(), 0), DataUtil.getInt(wheelDialog.getTwoKey(), 0), LoginActivity.appContext.getUserId(), "1", callback_Query);
    }

    @Override
    protected void headClick(WheelDialog wheelDialog) {
        loadingData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        vp.setOnPageChangeListener(new MyOnPageChangeListener());
        myAcsArrayAdapter = new MyArrayAdapter<>(this.getActivity(), R.layout.acs_list_sub, myList, new MyArrayAdapter.InitArrayValue() {
            @Override
            public void setViewValue(int position, View view, Object item) {
                if (item != null) {
                    HandlLog handlLog = (HandlLog) item;
                    TextView tv_time = (TextView) view.findViewById(R.id.Tvv5);
                    TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                    TextView tv_status = (TextView) view.findViewById(R.id.tv_status);
                    TextView tv_describe = (TextView) view.findViewById(R.id.tv_describe);
                    tv_time.setText(handlLog.getOperatorDate());
                    tv_name.setText(handlLog.getOperatorName());
                    tv_status.setText(handlLog.getOperateClentZh());
                    tv_describe.setText(handlLog.getOperateTypeZh());
                }

            }
        });
    }

    @Override
    public void showListView() {
        super.showListView();
        getThreeName().setVisibility(View.GONE);
    }

    @Override
    public void showViewPag() {
        super.showViewPag();
        getThreeName().setVisibility(View.VISIBLE);
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Log.i("当前页:", arg0 + "");
            if (listResult != null) {
                Acs acs = listResult.get(arg0);
                if (acs != null) {
                    getOneName().setText(acs.getStore().getName());
                    getTwoName().setText(acs.getArea() != null ? acs.getArea().getName() : "");
                    getThreeName().setText(acs.getName());
                    queryHandlLog(acs.getId());
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

    public void acssHandle(int id, int mack) {
        if (start) {
            Log.e("asdfasdfasdfa:", id + "");
            setBlackImg(R.id.img_start, R.drawable.door_c_act_mj);
            AcsConnector acssConn = new AcsConnectorImpl();
            acssConn.acsHandle(LoginActivity.appContext.getUserId(), id,
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
                    setBlackImg(R.id.img_start, R.drawable.door_c_mj);
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
}
