package com.wujia.witstore.ui.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wujia.witstore.R;

/**
 * Created by ZOUBIN on 2015/9/16.
 * 17:58
 */
public class HelpDialog {
    Context context;
    private Dialog loadingDialog;
    private int a []={R.drawable.index_tip1,R.drawable.index_tip2,R.drawable.index_tip3};
    private int imgnum=0;
    public HelpDialog(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    public Dialog createHelpDialog() {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.help, null);//得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.ll_help);// 加载布局
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBlackImg(v, a[getImgnum()]);
            }
        });
        loadingDialog = new Dialog(context, R.style.MyWheelDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(false); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(true); //点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();
        return loadingDialog;
    }

    public void setBlackImg(View view, int img) {
        if (null != view) {
            Drawable drawable = context.getResources().getDrawable(img);
            if (null != drawable) {

                view.setBackgroundDrawable(drawable);
            }
        }
    }
    private int getImgnum(){
       int b=0;
        b=b+imgnum;
        if(b<a.length-1) {
            imgnum++;
        }
        else {
            imgnum = 0;
        }
        Log.e("asdasd",b+"");
        return b;
    }
}
