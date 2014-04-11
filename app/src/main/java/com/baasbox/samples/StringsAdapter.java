package com.baasbox.samples;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by eto on 11/04/14.
 */
public class StringsAdapter extends BaseAdapter {
    private final List<String> mBoundedData;
    private LayoutInflater mInfalter;

    StringsAdapter(List<String> data,Context context){
        mBoundedData = data;
        mInfalter = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mBoundedData==null?0:mBoundedData.size();
    }

    @Override
    public String getItem(int position) {
        if (mBoundedData!=null){
            return mBoundedData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class Holder {
        TextView tv;
        // ImageView v;
        // ...
    }

    @Override
    public View getView(int position, View recycledView, ViewGroup parent) {
        Holder h;
        if(recycledView == null){
            recycledView = mInfalter.inflate(R.layout.row_layout,parent,false);
            h=new Holder();
            Log.d("ADAPTER","Creating new view");
            h.tv = (TextView)recycledView.findViewById(R.id.tv_row);
            recycledView.setTag(h);
        } else {
            h = (Holder)recycledView.getTag();
        }
        h.tv.setText(getItem(position));
        return recycledView;
    }
}
