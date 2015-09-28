package com.wujia.witstore.ui.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.connector.HcsConnector;
import com.wujia.witstore.connector.official.HcsConnectorImpl;
import com.wujia.witstore.data.model.Hcs;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.ui.view.WheelDialog;
import com.wujia.witstore.utils.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZOUBIN on 2015/8/19.
 * 15:20
 */
public class HcsFragment extends MyKJFragment {

    ArrayList<Hcs> listResult;

    @Override
    public void setMyViewPagerView(View userLayout, Object object, int position) {
        if (position == 0) {
            listResult = (ArrayList<Hcs>) DataUtil.toListBean(
                    (List<Map<?, ?>>) dataList,
                    Hcs.class);
        }
        TextView tvwd = (TextView) userLayout.findViewById(R.id.tv_wd);
        TextView tvsd = (TextView) userLayout.findViewById(R.id.tv_sd);
        TextView tvsdsxx = (TextView) userLayout.findViewById(R.id.tv_sdsx);
        TextView tvwdsxx = (TextView) userLayout.findViewById(R.id.tv_wdsx);
        ImageView imgkt = (ImageView) userLayout.findViewById(R.id.img_kt);
        ImageView imgqs = (ImageView) userLayout.findViewById(R.id.img_qs);
        ImageView imgzs = (ImageView) userLayout.findViewById(R.id.img_zs);
        ImageView imgtf = (ImageView) userLayout.findViewById(R.id.img_tf);
        ImageView imgjh = (ImageView) userLayout.findViewById(R.id.img_jh);
        ImageView imgxd = (ImageView) userLayout.findViewById(R.id.img_xd);

        Hcs hcs = listResult.get(position);
        String temperature = hcs.getTemperature() == null ? "0" : hcs.getTemperature();
        String humidity = hcs.getHumidity() == null ? "0" : hcs.getHumidity();
        String humidityLowerLimit = hcs.getHumidityLowerLimit() == null ? "0" : hcs.getHumidityLowerLimit();
        String humidityUpperLimit = hcs.getHumidityUpperLimit() == null ? "0" : hcs.getHumidityUpperLimit();
        String tempLowerLimit = hcs.getTempLowerLimit() == null ? "0" : hcs.getTempLowerLimit();
        String tempUpperLimit = hcs.getTempUpperLimit() == null ? "0" : hcs.getTempUpperLimit();
        tvwd.setText(temperature + "℃");
        tvsd.setText(humidity + "%");
        tvsdsxx.setText(humidityLowerLimit + "%HR～" + humidityUpperLimit + "%HR");
        tvwdsxx.setText(tempLowerLimit + "℃～" + tempUpperLimit + "℃");
        if (position == 0) {
            if (hcs != null) {
                String dar = hcs.getMonitorAddr() == null ? "" : hcs.getMonitorAddr();
                String storeName = hcs.getStore() == null ? "" : hcs.getStore().getName();
                String areaName = hcs.getArea() == null ? "" : hcs.getArea().getName();
                getOneName().setText(storeName);
                getTwoName().setText(areaName);
                getThreeName().setText(dar);
            }
        }

        if (hcs.getKtzt() != null) {
            switch (hcs.getKtzt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgkt, R.drawable.wsd_c_zl_act_icon);
                    break;
                case "3":
                case "3.0":
                    setImgSrc(imgkt, R.drawable.temp_c_act_ty);
                    break;
                default:
                    setImgSrc(imgkt, R.drawable.wsd_c_zl_icon);
                    break;
            }
        } else {
            setImgSrc(imgkt, R.drawable.wsd_c_dy_icon);
        }

        if (hcs.getQszt() != null) {
            switch (hcs.getQszt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgqs, R.drawable.wsd_c_qs_act_icon);
                    break;
                default:
                    setImgSrc(imgqs, R.drawable.wsd_c_qs_icon);
                    break;
            }
        } else {
            setImgSrc(imgqs, R.drawable.wsd_c_qs_icon);
        }

        if (hcs.getZszt() != null) {
            switch (hcs.getZszt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgzs, R.drawable.wsd_c_zs_act_icon);
                    break;
                default:
                    setImgSrc(imgzs, R.drawable.wsd_c_zs_icon);
                    break;
            }
        } else {
            setImgSrc(imgzs, R.drawable.wsd_c_zs_icon);
        }

        if (hcs.getTfzt() != null) {
            switch (hcs.getTfzt()) {
                case "2":
                case "2.0":
                case "3":
                case "3.0":
                    setImgSrc(imgtf, R.drawable.wsd_c_tf_act_icon);
                    break;
                default:
                    setImgSrc(imgtf, R.drawable.wsd_c_tf_icon);
                    break;
            }
        } else {
            setImgSrc(imgtf, R.drawable.wsd_c_tf_icon);
        }

        if (hcs.getJhzt() != null && hcs.getJhzt().equals(AppConfig.HCS_OPEN)) {
            switch (hcs.getJhzt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgjh, R.drawable.wsd_c_jh_act_icon);
                    break;
                default:
                    setImgSrc(imgjh, R.drawable.wsd_c_jh_icon);
                    break;
            }
        } else {
            setImgSrc(imgjh, R.drawable.wsd_c_jh_icon);
        }

        if (hcs.getGjzt() != null) {
            switch (hcs.getGjzt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgxd, R.drawable.wsd_c_xd_act_icon);
                    break;
                default:
                    setImgSrc(imgxd, R.drawable.wsd_c_xd_icon);
                    break;
            }
        } else {
            setImgSrc(imgxd, R.drawable.wsd_c_xd_icon);
        }

    }

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

    @Override
    public void setMyListViewValue(int position, View view, Object item) {
        TextView tv_lie_name = (TextView) view.findViewById(R.id.tv_lie_name);
        TextView tvwd = (TextView) view.findViewById(R.id.tv_lie_wd);
        TextView tvsd = (TextView) view.findViewById(R.id.tv_lie_sd);
        ImageView imgkt = (ImageView) view.findViewById(R.id.img_kt);
        ImageView imgqs = (ImageView) view.findViewById(R.id.img_qs);
        ImageView imgzs = (ImageView) view.findViewById(R.id.img_zs);
        ImageView imgtf = (ImageView) view.findViewById(R.id.img_tf);
        ImageView imgjh = (ImageView) view.findViewById(R.id.img_jh);
        ImageView imgxd = (ImageView) view.findViewById(R.id.img_xd);

        Hcs hcs = listResult.get(position);
        String temperature = hcs.getTemperature() == null ? "0" : hcs.getTemperature();
        String humidity = hcs.getHumidity() == null ? "0" : hcs.getHumidity();
        String dar = hcs.getMonitorAddr() == null ? "" : hcs.getMonitorAddr();

        tv_lie_name.setText(dar);
        tvwd.setText(temperature + "℃");
        tvsd.setText(humidity + "%");
        if (position == 0) {
            if (hcs != null) {
                String storeName = hcs.getStore() == null ? "" : hcs.getStore().getName();
                String areaName = hcs.getArea() == null ? "" : hcs.getArea().getName();
                getOneName().setText(storeName);
                getTwoName().setText(areaName);
            }
        }

        if (hcs.getKtzt() != null) {
            switch (hcs.getKtzt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgkt, R.drawable.wsd_c_zl_act_icon);
                    break;
                case "3":
                case "3.0":
                    setImgSrc(imgkt, R.drawable.temp_c_act_ty);
                    break;
                default:
                    setImgSrc(imgkt, R.drawable.wsd_c_zl_icon);
                    break;
            }
        } else {
            setImgSrc(imgkt, R.drawable.wsd_c_dy_icon);
        }

        if (hcs.getQszt() != null) {
            switch (hcs.getQszt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgqs, R.drawable.wsd_c_qs_act_icon);
                    break;
                default:
                    setImgSrc(imgqs, R.drawable.wsd_c_qs_icon);
                    break;
            }
        } else {
            setImgSrc(imgqs, R.drawable.wsd_c_qs_icon);
        }

        if (hcs.getZszt() != null) {
            switch (hcs.getZszt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgzs, R.drawable.wsd_c_zs_act_icon);
                    break;
                default:
                    setImgSrc(imgzs, R.drawable.wsd_c_zs_icon);
                    break;
            }
        } else {
            setImgSrc(imgzs, R.drawable.wsd_c_zs_icon);
        }

        if (hcs.getTfzt() != null) {
            switch (hcs.getTfzt()) {
                case "2":
                case "2.0":
                case "3":
                case "3.0":
                    setImgSrc(imgtf, R.drawable.wsd_c_tf_act_icon);
                    break;
                default:
                    setImgSrc(imgtf, R.drawable.wsd_c_tf_icon);
                    break;
            }
        } else {
            setImgSrc(imgtf, R.drawable.wsd_c_tf_icon);
        }

        if (hcs.getJhzt() != null && hcs.getJhzt().equals(AppConfig.HCS_OPEN)) {
            switch (hcs.getJhzt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgjh, R.drawable.wsd_c_jh_act_icon);
                    break;
                default:
                    setImgSrc(imgjh, R.drawable.wsd_c_jh_icon);
                    break;
            }
        } else {
            setImgSrc(imgjh, R.drawable.wsd_c_jh_icon);
        }

        if (hcs.getGjzt() != null) {
            switch (hcs.getGjzt()) {
                case "2":
                case "2.0":
                    setImgSrc(imgxd, R.drawable.wsd_c_xd_act_icon);
                    break;
                default:
                    setImgSrc(imgxd, R.drawable.wsd_c_xd_icon);
                    break;
            }
        } else {
            setImgSrc(imgxd, R.drawable.wsd_c_xd_icon);
        }

    }

    @Override
    public KjDataInfo inItMyFragmentData() {
        return new KjDataInfo(R.layout.hcs, R.layout.hcs_list, R.drawable.wsd_c_wz_bg, R.string.index_wsd,KjDataInfo.ONE);
    }

    @Override
    protected void loadingData() {
        HcsConnector hcsConn = new HcsConnectorImpl();
        hcsConn.hcsQuery(DataUtil.getInt(wheelDialog.getOneKey(), 0), 0, LoginActivity.appContext.getUserId(), "1", callback_Query);

    }

    @Override
    protected void headClick(WheelDialog wheelDialog) {
        loadingData();
    }

    public void setImgSrc(ImageView img, int resource) {
        Drawable drawable = getResources().getDrawable(resource);
        if (null != drawable) {
            img.setImageDrawable(drawable);
        }
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        vp.setOnPageChangeListener(new MyOnPageChangeListener());
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Log.i("当前页:", arg0 + "");
            if (listResult != null) {
                Hcs hcs = listResult.get(arg0);

                if (hcs != null) {
                    String dar = hcs.getMonitorAddr() == null ? "" : hcs.getMonitorAddr();
                    String storeName = hcs.getStore() == null ? "" : hcs.getStore().getName();
                    String areaName = hcs.getArea() == null ? "" : hcs.getArea().getName();
                    getOneName().setText(storeName);
                    getTwoName().setText(areaName);
                    getThreeName().setText(dar);
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
}
