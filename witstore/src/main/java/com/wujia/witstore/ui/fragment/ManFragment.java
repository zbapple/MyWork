package com.wujia.witstore.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.wujia.witstore.R;
import com.wujia.witstore.connector.LoginConnetctor;
import com.wujia.witstore.connector.official.LoginConnetctorImpl;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.AppString;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.ui.activity.MainActivity;
import com.wujia.witstore.ui.myadapter.ImagePagerAdapter;
import com.wujia.witstore.ui.view.CircleFlowIndicator;
import com.wujia.witstore.ui.view.ViewFlow;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ManFragment extends KJFragment {

    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<Integer> imageUrlList;
    private ArrayList<String> linkUrlArray;
    private ArrayList<String> titleList;
    private ViewFlipper notice_vf;
    private int mCurrPos;
    private MainActivity myActivity;
    private Timer timer = null;
    private ManFrtClickListener manFrtClickListener;
    private ImageView img_serch, img_mue;
    private IntActivityData intActivityData;
    TextView msgTag;

    public interface ManFrtClickListener {
        void onMyClick(View v);
    }

    public void setManFrtClickListener(ManFrtClickListener manFrtClickListener) {
        this.manFrtClickListener = manFrtClickListener;
    }

    public void setIntActivityData(IntActivityData intActivityData) {
        this.intActivityData = intActivityData;
    }

    public interface IntActivityData {
        void intData(View view);
    }

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_man, viewGroup, false);
    }

    @Override
    protected void initWidget(View parentView) {

        mViewFlow = (ViewFlow) parentView.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) parentView.findViewById(R.id.viewflowindic);
        initBanner(imageUrlList);
        initRollNotice(parentView);
        initView(parentView);
        if (intActivityData != null)
            intActivityData.intData(parentView);

    }

    private void initView(View v) {
        ImageView img_wsd = (ImageView) v.findViewById(R.id.img_wsd);
        ImageView img_rfid = (ImageView) v.findViewById(R.id.img_rfid);
        ImageView img_dg = (ImageView) v.findViewById(R.id.img_dg);
        ImageView img_mj = (ImageView) v.findViewById(R.id.img_mj);
        ImageView img_mjj = (ImageView) v.findViewById(R.id.img_mjj);
        ImageView img_bj = (ImageView) v.findViewById(R.id.img_bj);
        ImageView img_gp = (ImageView) v.findViewById(R.id.img_gp);
        ImageView img_sp = (ImageView) v.findViewById(R.id.img_sp);
        img_mue = (ImageView) this.getActivity().findViewById(R.id.img_mue);
        img_serch = (ImageView) this.getActivity().findViewById(R.id.img_serch);
        img_wsd.setOnClickListener(this);
        img_rfid.setOnClickListener(this);
        img_dg.setOnClickListener(this);
        img_mj.setOnClickListener(this);
        img_mjj.setOnClickListener(this);
        img_bj.setOnClickListener(this);
        img_gp.setOnClickListener(this);
        img_sp.setOnClickListener(this);
        img_serch.setOnClickListener(this);
        TextView tileName = (TextView) this.getActivity().findViewById(R.id.tv_tile);
        if (tileName != null) {
            tileName.setText(R.string.index_home_page);
        }
    }

    @Override
    protected void initData() {
        myActivity =  (MainActivity) this.getActivity();
        imageUrlList = new ArrayList<>();

        linkUrlArray = new ArrayList<>();
        imageUrlList.add(R.drawable.index_ban);
        imageUrlList.add(R.drawable.index_ban2);
        imageUrlList.add(R.drawable.index_ban3);
        imageUrlList.add(R.drawable.index_ban4);

        titleList=myActivity.getTitleList();
        if(titleList==null||titleList.size()==0){
            titleList.add("祝您天天好心情，越活越年轻！");
        }
    }




    Shimmer shimmer;

    public void toggleAnimation(ShimmerTextView tv) {
        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
            shimmer = new Shimmer();
            shimmer.start(tv);
        } else {
            shimmer = new Shimmer();
            shimmer.start(tv);
        }
    }

    private void initRollNotice(View parentView) {
        FrameLayout main_notice = (FrameLayout) parentView.findViewById(R.id.main_notice);
        LinearLayout notice_parent_ll = (LinearLayout) myActivity.getLayoutInflater().inflate(R.layout.layout_notice, null);
        notice_vf = ((ViewFlipper) notice_parent_ll.findViewById(R.id.homepage_notice_vf));
        msgTag = (TextView) notice_parent_ll.findViewById(R.id.tv_msg_tag);
        if (titleList.size() > 0) {
            msgTag.setText(titleList.size() + "");
            msgTag.setVisibility(View.VISIBLE);
        } else {
            msgTag.setVisibility(View.GONE);
        }
        main_notice.addView(notice_parent_ll);
        if (null == timer) {
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            moveNext();
                            Log.d("Task", "下一个");
                        }
                    });
                }
            };
            timer.schedule(task, 0, 4000);
        }
    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        timer = null;
        super.onDestroyView();
    }

    private void moveNext() {
        setView(this.mCurrPos, this.mCurrPos + 1);
        this.notice_vf.setInAnimation(myActivity, R.anim.in_bottomtop);
        this.notice_vf.setOutAnimation(myActivity, R.anim.out_bottomtop);
        this.notice_vf.showNext();
    }

    /***/
    private void setView(int curr, int next) {
        View noticeView = this.getActivity().getLayoutInflater().inflate(R.layout.notice_item, null);
        ShimmerTextView notice_tv = (ShimmerTextView) noticeView.findViewById(R.id.notice_tv);
        toggleAnimation(notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }

        if (titleList.size() > 0) {
            msgTag.setText(titleList.size() + "");
            msgTag.setVisibility(View.VISIBLE);

            notice_tv.setText(titleList.get(next));
            notice_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    TextView tv = (TextView) arg0;
                    ViewInject.toast(tv.getText() + "");
                }
            });
        } else {
            msgTag.setVisibility(View.GONE);
        }

        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;
    }

    private void initBanner(ArrayList<?> imageUrlList) {
        mViewFlow.setAdapter(new ImagePagerAdapter(this.getActivity(), imageUrlList, linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    @Override
    public void onClick(View v) {
        if (null != manFrtClickListener) {
            manFrtClickListener.onMyClick(v);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        img_mue.setVisibility(View.GONE);
        img_serch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        img_mue.setVisibility(View.VISIBLE);
        img_serch.setVisibility(View.GONE);
    }

}
