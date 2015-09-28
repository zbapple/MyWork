package com.wujia.witstore.ui.myadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/***********************************************************
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @ClassName: MyArrayAdapter
 ************************************************************/
public class MyArrayAdapter<T> extends ArrayAdapter<T> {

    private Context context;
    private int mResource;
    private InitArrayValue initV;

    public MyArrayAdapter(Context context, int resource, List<T> objects, InitArrayValue initV) {
        super(context, resource, objects);

        this.mResource = resource;
        this.context = context;
        this.initV = initV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        T item = getItem(position);

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(mResource, parent, false);

        } else {
            view = convertView;
        }

        this.initV.setViewValue(position, view, item);

        return view;
    }

    public interface InitArrayValue {
         void setViewValue(int position, View view, Object item);
    }


}
