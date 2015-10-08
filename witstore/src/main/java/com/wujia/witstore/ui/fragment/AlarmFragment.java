package com.wujia.witstore.ui.fragment;

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
import com.wujia.witstore.connector.ImsConnector;
import com.wujia.witstore.connector.StoreConnetctor;
import com.wujia.witstore.connector.official.ImsConnectorImpl;
import com.wujia.witstore.connector.official.StoreConnetctorImpl;
import com.wujia.witstore.data.model.Alarm;
import com.wujia.witstore.data.model.Hcs;
import com.wujia.witstore.data.model.Ils;
import com.wujia.witstore.data.model.Ims;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppString;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.ui.activity.MainActivity;
import com.wujia.witstore.ui.myadapter.MyArrayAdapter;
import com.wujia.witstore.ui.view.CircleFlowIndicator;
import com.wujia.witstore.ui.view.ViewFlow;
import com.wujia.witstore.utils.DataUtil;
import com.wujia.witstore.utils.MyUtils;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZOUBIN on 2015/8/19.
 * 15:20
 */
public class AlarmFragment extends KJFragment implements
        MyArrayAdapter.InitArrayValue {

    protected ListView listView;
    protected ArrayList<Alarm> dataList = new ArrayList<>(); // List数据集
    protected MyArrayAdapter<Alarm> myAdapter;
    private int tag = 0;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.rfid, viewGroup, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView img_mue = (ImageView) this.getActivity().findViewById(R.id.img_mue);
        img_mue.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        ImageView img_mue = (ImageView) this.getActivity().findViewById(R.id.img_mue);
        img_mue.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        myAdapter = new MyArrayAdapter<>(this.getActivity(), R.layout.rfid_list, dataList, this); // 设置listView控件适配器的值
        intiVlaue();
    }

    // 加载
    public void intiVlaue() {
        Bundle bundle = this.getActivity().getIntent().getExtras();
        if (bundle != null) { // 判断是否有传数据过来有则显示传过来的数据
            if (bundle.containsKey("data")) {
                dataList.clear();
                ArrayList<Alarm> list = (ArrayList<Alarm>) bundle.getSerializable("data");
                if (null != list)
                    dataList.addAll((ArrayList<Alarm>) bundle.getSerializable("data"));
                myAdapter.notifyDataSetChanged();
//				}
                initAlarm(dataList);
                this.getActivity().getIntent().getExtras().clear();
            }
        }
        if(dataList==null||dataList.size()<=0){
            loadingData();
        }
    }

    @Override
    protected void initWidget(View parentView) {
        listView = (ListView) parentView.findViewById(R.id.listView2);
        listView.setAdapter(myAdapter);
        TextView tileName = (TextView) this.getActivity().findViewById(R.id.tv_tile);
        setHeadName(tileName);
    }

    @Override
    public void setViewValue(int position, View view, Object item) {

        RelativeLayout rlto = (RelativeLayout) view.findViewById(R.id.Rlto);

        TextView tvv1 = (TextView) view.findViewById(R.id.Tvv1);
        TextView tvv2 = (TextView) view.findViewById(R.id.Tvv2);
        TextView tvv3 = (TextView) view.findViewById(R.id.Tvv3);
        TextView tvv4 = (TextView) view.findViewById(R.id.Tvv4);
        TextView tvv42 = (TextView) view.findViewById(R.id.Tvv42);
        TextView tvv5 = (TextView) view.findViewById(R.id.Tvv5);
        TextView tv_no=(TextView) view.findViewById(R.id.tv_no);

        Alarm alarm = (Alarm) item;
        tvv1.setText(alarm.getDeviceType());
        tvv2.setText(alarm.getAlarmLevel() + "-" + alarm.getAlarmType());
        tvv3.setText(alarm.getAlarmReason());
        tvv4.setText(alarm.getDeviceName());
        tvv42.setText(alarm.getIsHandle().equals("0") ? "未处理" : "已处理");
        tvv5.setText(alarm.getAlarmDate());
        tv_no.setText(position+1+"");
        rlto.setTag(position);
        rlto.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Rlto:
                if (dataList != null && dataList.size() > 0) {
                    Object obj = v.getTag();
                    if (obj != null) {
                        int position = (Integer) obj;
                        Alarm alarm = dataList.get(position);
                        tag = position;
                        handles(alarm.getId());
                    }
                }
                break;
        }
    }

    /**
     * @return void
     * @Title: handle
     * @Description:操作方法
     */
    public void handles(int id) {

        StoreConnetctor storeConn = new StoreConnetctorImpl();
        storeConn.storeAlarmHandle(LoginActivity.appContext.getUserId(), id,
                MyUtils.getDeviceNumb(), MyUtils.getOsVersion(), "1",
                callback_handles);

    }

    public HttpCallBack callback_handles = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                String result = new String(t);
                Log.e("callback_handles", result);
                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(
                        result);
                if (resultInfo.getStatus().equals("0")) {
                    dataList.remove(tag);
                    myAdapter.notifyDataSetChanged();
                    ViewInject.toast(resultInfo.getMsg());
                } else {
                    ViewInject.toast(resultInfo.getMsg());
                }
            } catch (Exception e) {
                ViewInject.toast(AppString.ERR_TIMEOUT);
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    protected void loadingData() {
        StoreConnetctor alarmConn = new StoreConnetctorImpl();
        alarmConn.storeAlarm(LoginActivity.appContext.getUserId(), "1", callback_Query);

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
                    listResult = (ArrayList<Alarm>) DataUtil.toListBean( (List<Map<?, ?>>) listResult,Alarm.class);
                    dataList.clear();
                    if (null != listResult) {
                        initAlarm(listResult);
                    }
                    refreshData();
                } else {

                    ViewInject.toast(resultInfo.getMsg());
                }
            } catch (Exception e) {

                e.printStackTrace();
                ViewInject.toast(AppString.ERR_TIMEOUT);
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            ViewInject.toast(AppString.ERR_NKFT + strMsg);
        }
    };

    public void refreshData() {
        if(myAdapter!=null)
            myAdapter.notifyDataSetChanged();
    }


    public void initAlarm(ArrayList<Alarm> listResult) {
        dataList.addAll(listResult);
    }

    public void setHeadName(TextView tileName){

        tileName.setText(R.string.index_bj);
    }

}
