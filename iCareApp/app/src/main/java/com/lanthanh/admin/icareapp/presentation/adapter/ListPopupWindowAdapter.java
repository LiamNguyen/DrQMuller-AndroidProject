package com.lanthanh.admin.icareapp.presentation.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.List;

/**
 * Created by ADMIN on 09-Jan-17.
 */

public class ListPopupWindowAdapter extends ArrayAdapter<String> {
    private Typeface font;
    private List<String> list;
    private Activity mContext;

    public ListPopupWindowAdapter(Activity context, int textViewResourceId, List<String> l) {
        super(context, textViewResourceId, l);
        mContext = context;
        list = l;
        font = Typeface.createFromAsset(context.getAssets(), GraphicUtils.FONT_SEMIBOLD);//Custom font
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return list.get(position-1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            row = inflater.inflate(R.layout.activity_popup_item, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }else
            holder = (ViewHolder) row.getTag();

        TextView label = holder.getTextView();
        if (list.size() <= 0) {
            label.setText(mContext.getString(R.string.empty_cart));
            label.setTypeface(font);
        }else{
            if (position == 0) {
                label.setText(mContext.getString(R.string.list_cart));
                label.setTypeface(font);
            } else {
                label.setText(getItem(position));
                label.setTypeface(font);
                ImageView icon = holder.getImageView();
                icon.setImageResource(R.drawable.ic_delete_item);
            }
        }

        return row;
    }

    public class ViewHolder{
        private View v;
        private TextView tv;
        private ImageView iv;

        ViewHolder(View base){
            v = base;
        }

        public TextView getTextView(){
            if (tv == null)
                tv = (TextView) v.findViewById(R.id.selected_item);
            return tv;
        }

        public ImageView getImageView(){
            if (iv == null)
                iv = (ImageView) v.findViewById(R.id.delete_item);
            return iv;
        }
    }
}
