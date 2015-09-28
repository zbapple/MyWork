package com.wujia.witstore.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.data.model.Alarm;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppString;
import com.wujia.witstore.ui.myadapter.MyArrayAdapter;
import com.wujia.witstore.utils.DataUtil;

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
public class RfidFragment extends AlarmFragment {

    @Override
    public void initAlarm(ArrayList<Alarm> listResult) {
        if (null != listResult) {
            ArrayList<Alarm>  scsList = new ArrayList<>();
            for (Alarm alarm : listResult) {
                switch (alarm.getDeviceType()) {
                    case "RFID":
                        scsList.add(alarm);
                        break;
                }
            }
            dataList.clear();
            dataList.addAll(scsList);
        }

    }
    @Override
    public void setHeadName(TextView tileName){

        tileName.setText(R.string.index_stda);
    }

}

