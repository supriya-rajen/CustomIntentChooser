package com.example.test.customintentchooser;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NewAdapter extends BaseAdapter {
    ArrayList<Appdata> name;
    Context context;
    private int width, gridImageWidth;

    public NewAdapter(MainActivity mainActivity, ArrayList<Appdata> name) {
        this.name = name;
        this.context = mainActivity;
        gridImageWidth = (int) (width - (getPixelFromDp(context,100)));
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        CustomViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row,null);
            holder = new CustomViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder =(CustomViewHolder) convertView.getTag();
        }
        //holder.viewImage.setLayoutParams(new FrameLayout.LayoutParams(gridImageWidth / 3, gridImageWidth / 3));

        holder.label.setText(name.get(position).appName);
        holder.icon.setImageDrawable(name.get(position).appIcon);
        return convertView;
    }

    private class CustomViewHolder {
        public TextView label;
        public ImageView icon;
        public RelativeLayout viewImage;
        public CustomViewHolder(View holder) {
            Log.e("ss__","10");
            label=(TextView)holder.findViewById(R.id.label);
            icon=(ImageView)holder.findViewById(R.id.icon);
            viewImage = (RelativeLayout) holder.findViewById(R.id.frame);
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    public static int getPixelFromDp(Context context, int dpUnits) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpUnits, getDisplayMetrics(context));
        return (int) px;
    }
}

