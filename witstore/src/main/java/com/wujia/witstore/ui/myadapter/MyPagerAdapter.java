package com.wujia.witstore.ui.myadapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***********************************************************
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @ClassName: MyPagerAdapter
 ************************************************************/
public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<?> list;
    private LayoutInflater inflater;
    private int layout;
    private InitViewValue initV;


    public MyPagerAdapter(Context context, ArrayList<?> list, int layout,
                          InitViewValue initV) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.layout = layout;
        this.initV = initV;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
    }


    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        Object objeValue =  list.get(position);

        View userLayout = inflater.inflate(layout, view, false);
        if (initV != null) {
            initV.setPagViewValue(userLayout, objeValue, position);
        }
        userLayout.setTag(position);
        ( view).addView(userLayout, 0);

        return userLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


    public interface InitViewValue {
         void setPagViewValue(View userLayout, Object obje, int position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
