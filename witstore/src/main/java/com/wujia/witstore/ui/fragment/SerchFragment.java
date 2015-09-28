package com.wujia.witstore.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wujia.witstore.R;
import com.wujia.witstore.connector.LoginConnetctor;
import com.wujia.witstore.connector.StoreConnetctor;
import com.wujia.witstore.connector.official.LoginConnetctorImpl;
import com.wujia.witstore.connector.official.StoreConnetctorImpl;
import com.wujia.witstore.data.model.Alarm;
import com.wujia.witstore.data.model.Ils;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.data.model.Serch;
import com.wujia.witstore.data.model.SerchModel;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppString;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.ui.activity.MainActivity;
import com.wujia.witstore.ui.myadapter.MyArrayAdapter;
import com.wujia.witstore.utils.DataUtil;
import com.wujia.witstore.utils.MyUtils;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.ViewInject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZOUBIN on 2015/9/17.
 * 10:39
 */
public class SerchFragment extends KJFragment implements
        MyArrayAdapter.InitArrayValue, SearchView.OnQueryTextListener {
    protected ListView listView;
    protected ArrayList<Serch> dataList = new ArrayList<>(); // List数据集
    protected MyArrayAdapter<Serch> myAdapter;
    private int tag = 0;
    private String queryStr;
    private MainActivity act;
    SearchView searchView;
    ImageView img_mue;
    TextView tileName;
    Object[] names = {"", "", ""};
    int i = 0;
    InputMethodManager inputMethodManager;

    private void hideSoftInput() {
        if (inputMethodManager != null) {
            View v = act.getCurrentFocus();
            if (v == null) {
                return;
            }

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            searchView.clearFocus();
        }
    }

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.serch, viewGroup, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        tileName.setVisibility(View.GONE);
        img_mue.setVisibility(View.GONE);
        searchView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        img_mue.setVisibility(View.VISIBLE);
        tileName.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);

    }

    @Override
    public void initData() {
        myAdapter = new MyArrayAdapter<>(this.getActivity(), R.layout.serch_list, dataList, this); // 设置listView控件适配器的值
        intiVlaue();
        act = (MainActivity) this.getActivity();

        inputMethodManager = (InputMethodManager) act.getSystemService(act.INPUT_METHOD_SERVICE);
    }

    // 单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            queryStr = URLEncoder.encode(query, "UTF-8");
        } catch (Exception e) {
        }
        loadingData();
        hideSoftInput();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }


    // 加载
    public void intiVlaue() {

        loadingData();

    }


    @Override
    protected void initWidget(View parentView) {
        img_mue = (ImageView) this.getActivity().findViewById(R.id.img_mue);
        searchView = (SearchView) this.getActivity().findViewById(R.id.searchView);
        tileName = (TextView) this.getActivity().findViewById(R.id.tv_tile);
        listView = (ListView) parentView.findViewById(R.id.ltv_1);
        listView.setAdapter(myAdapter);
        searchView.setOnQueryTextListener(this);
        searchView.onActionViewExpanded();
    }


    @Override
    public void setViewValue(int position, View view, Object item) {

        RelativeLayout rlto = (RelativeLayout) view.findViewById(R.id.Rlto);

        TextView tvv1 = (TextView) view.findViewById(R.id.Tvv1);
        TextView tvv2 = (TextView) view.findViewById(R.id.Tvv2);
        TextView tv_no = (TextView) view.findViewById(R.id.tv_no);
        TextView tvv4 = (TextView) view.findViewById(R.id.Tvv4);

        Serch alarm = (Serch) item;
        tvv1.setText(DataUtil.getType(alarm.getType()));
        tvv2.setText(alarm.getWname());
        tvv4.setText(alarm.getName());
        tv_no.setText(position + "");
        rlto.setTag(alarm);
        rlto.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Rlto:
                Serch alarm = (Serch) v.getTag();
                int id = alarm.getId();
                String type = alarm.getType();
                act.getIntent().putExtra("id", id);
                toFragment(type);
                break;
        }
    }


    protected void loadingData() {
        LoginConnetctor alarmConn = new LoginConnetctorImpl();
        alarmConn.serchQuery(queryStr, callback_Query);

    }

    protected HttpCallBack callback_Query = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                Log.i("callback_Query", new String(t));
                Gson son = new Gson();
                SerchModel serchModel = son.fromJson(new String(t), SerchModel.class);
                String a = serchModel.getResponseHeader().get("status") + "";
                if (a.equals("0.0")) {
                    ArrayList listResult = (ArrayList) serchModel.getResponse().get("docs");
                    listResult = (ArrayList<Serch>) DataUtil.toListBean((List<Map<?, ?>>) listResult, Serch.class);
                    dataList.clear();
                    if (null != listResult) {
                        initAlarm(listResult);
                    }
                    refreshData();
                } else {
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
        if (myAdapter != null)
            myAdapter.notifyDataSetChanged();
    }

    public void initAlarm(ArrayList<Serch> listResult) {
        dataList.addAll(listResult);
    }


    public void toFragment(String a) {
        switch (a) {
            case "ils":
                IlsFragment ilsFragment = new IlsFragment();
                act.myChangeFragment(R.id.fm_content, ilsFragment, true);
                return;
            case "acs":
                AcsFragment acsFragment = new AcsFragment();
                act.myChangeFragment(R.id.fm_content, acsFragment, true);
                return;
            case "dms":
                CDFragment cdFragment = new CDFragment();
                act.myChangeFragment(R.id.fm_content, cdFragment, true);
                return;
            case "ims":
                ImsFragment imsFragment = new ImsFragment();
                act.myChangeFragment(R.id.fm_content, imsFragment, true);
                return;
            case "hcs":
                HcsFragment hcsFragment = new HcsFragment();
                act.myChangeFragment(R.id.fm_content, hcsFragment, true);
                return;
            case "rfid":
                RfidFragment rfidFragment = new RfidFragment();
                act.myChangeFragment(R.id.fm_content, rfidFragment, true);
                return;

        }
    }
}
