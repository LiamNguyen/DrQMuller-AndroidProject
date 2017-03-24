package com.lanthanh.admin.icareapp.presentation.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class CustomSpinnerAdapter<T> extends ArrayAdapter {
    private Context context;
    private Typeface font;
    private String defaultText;

    public CustomSpinnerAdapter(Context context, int layout, List<T> list, String defaulText){
        super(context, layout, list);
        this.defaultText = defaulText;
        this.context = context;
        font = Typeface.createFromAsset(this.context.getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
    }

    @Override
    public boolean isEnabled(int position){
        if(position == 0)
        {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;

        if(position == 0)
            tv.setTextColor(context.getResources().getColor(R.color.colorLightGray));// Set the hint text color gray
        else
            tv.setTextColor(context.getResources().getColor(R.color.colorLightBlack));
        tv.setTypeface(font);
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        ((TextView) view).setTypeface(font);
        if (position == 0) {
            ((TextView) view).setText(defaultText);
        } else {
            ((TextView) view).setText(getItem(position - 1).toString());
        }

        return view;
    }
}
