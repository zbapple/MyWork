package com.wujia.witstore.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wujia.witstore.R;
import com.wujia.witstore.utils.MyUtils;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.SystemTool;

/*********************************************************** 
 * @ClassName: MyActivity
 * @Description: 我的功能模块
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年6月29日 上午11:08:10
 ************************************************************/
public class MyActivity extends KJActivity {

	@BindView(id= R.id.Tv_myvaule1)
	TextView tv1;
//  @BindView(id=R.id.Tv_myvaule2)
//	TextView tv2;
	@BindView(id=R.id.Tv_myvaule3)
	TextView tv3;
	@BindView(id=R.id.Tv_myvaule4)
	TextView tv4;
	@BindView(id=R.id.Tv_myvaule5)
	TextView tv5;
	@BindView(id=R.id.Btnback,click=true)
	Button back;

	@Override
	public void setRootView() {
		setContentView(R.layout.my_show);
	}

	@Override
	// 初始化控件值
	public void initWidget(){
		tv1.setText(LoginActivity.appContext.getUserName());
		//	tv2.setText(MainActivity.appContext.getUserId());
		tv3.setText(MyUtils.getOsVersion());
		tv4.setText(SystemTool.getSDKVersion()+"");
		tv5.setText(android.os.Build.MODEL);
	}

	@Override
	// 点击事件监听
	public void widgetClick(View v) {
		finish();
	}

}
