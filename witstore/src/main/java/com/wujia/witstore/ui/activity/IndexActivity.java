package com.wujia.witstore.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;

import com.wujia.witstore.R;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class IndexActivity extends KJActivity {
    //Alpha动画 - 渐变透明度
    private Animation alphaAnimation = null;

    //Sacle动画 - 渐变尺寸缩放
    private Animation scaleAnimation = null;

    //Translate动画 - 位置移动
    private Animation translateAnimation = null;

    //Rotate动画 - 画面旋转
    private Animation rotateAnimation = null;

    @BindView(id = R.id.loginRoot)
    LinearLayout ly;

    @Override
    public void setRootView() {
        setContentView(R.layout.nvg_animation);
        initAm();
        toLogin();
    }

    private void toLogin(){
//         闪屏核心代码
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(IndexActivity.this,
                        LoginActivity.class);
                // 从启动动画切换到主界面
                startActivity(intent);
                IndexActivity.this.finish();// 结束动画
            }
        }, 4000);
    }

    public void initAm(){
        LinearLayout ly= (LinearLayout) this.findViewById(R.id.loginRoot);
        //动画集
        AnimationSet set = new AnimationSet(true);
        Animation alphaAnimation=null;
//        rotateAnimation = new RotateAnimation(0f, 360f);
//        rotateAnimation.setDuration(1000);
//        set.addAnimation(rotateAnimation);

//        translateAnimation = new TranslateAnimation(0,50, 0, 50);
//        translateAnimation.setDuration(1000);
//        translateAnimation.setRepeatCount(Animation.INFINITE);
//        translateAnimation.setRepeatMode(Animation.REVERSE);
//        set.addAnimation(translateAnimation);

        alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        set.addAnimation(alphaAnimation);



//        scaleAnimation = new ScaleAnimation(0.1f, 1.0f,0.1f,1.0f);
//        scaleAnimation.setDuration(5000);
//        set.addAnimation(scaleAnimation);

        ly.startAnimation(set);
        ly.setClickable(true);
//        ly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toLogin();
//            }
//        });
    }
}
