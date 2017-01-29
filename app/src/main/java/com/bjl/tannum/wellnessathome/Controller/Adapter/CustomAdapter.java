package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.R;

/**
 * Created by tannum on 1/29/2017 AD.
 */

public class CustomAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    Context mContext;
    String[] strName;
    int[] resId;

    public CustomAdapter(Context mContext ,String[] strName, int[] resId) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.resId = resId;
        this.strName = strName;
    }

    @Override
    public int getCount() {
        return strName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.listview_row,parent,false);

        TextView textView = (TextView)convertView.findViewById(R.id.textView1);
        textView.setText(strName[position]);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView1);
        imageView.setBackgroundResource(resId[position]);

        return convertView;
    }
}
