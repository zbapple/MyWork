package com.wujia.witstore.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.data.model.Alarm;
import com.wujia.witstore.ui.view.WheelDialog;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.ArrayList;

/**
 * Created by ZOUBIN on 2015/8/19.
 * 15:20
 */
public class CDFragment extends AlarmFragment {

    @Override
    public void initAlarm(ArrayList<Alarm> listResult) {
        if (null != listResult) {
            ArrayList<Alarm>  scsList = new ArrayList<>();
            for (Alarm alarm : listResult) {
                switch (alarm.getDeviceType()) {
                    case "DMS":
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
        tileName.setText(R.string.index_xf);
    }
}
