package com.lanthanh.admin.icareapp.presentation.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.List;

/**
 * Created by ADMIN on 16-Oct-16.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter{
    private List<String> listOfDays, listOfHours;
    private Typeface fontDay, fontTime;
    private Activity context;
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;

    public ExpandableListViewAdapter(Activity context, List<String> listOfDays, List<String> listOfHours) {
        this.listOfDays = listOfDays;
        this.listOfHours = listOfHours;
        this.context = context;
        this.fontDay = Typeface.createFromAsset(context.getAssets(), GraphicUtils.FONT_REGULAR);//Custom font
        this.fontTime = Typeface.createFromAsset(context.getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
    }

    //Get one header (a day of week) of the group
    @Override
    public String getGroup(int groupPosition) {
        return listOfDays.get(groupPosition);
    }

    //Get an hour in the list of hours per day
    @Override
    public String getChild(int groupPosition, int childPosition) {
        //return listOfHoursPerDay.get(listOfDays.get(groupPosition)).get(childPosition);
        return listOfHours.get(childPosition);
    }

    //Get the ID of an hour in the list of hours per day
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //Get the ID of a day of the group
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //Get the total days in the group
    @Override
    public int getGroupCount() {
        return listOfDays.size();
    }

    //Get the total hours in a day
    @Override
    public int getChildrenCount(int groupPosition) {
        //return listOfHoursPerDay.get(listOfDays.get(groupPosition)).size();
        return listOfHours.size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // Return a day's view (display)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.booking_day_of_week, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        }else
            groupViewHolder = (GroupViewHolder) convertView.getTag();

        groupViewHolder.setImage(isExpanded);
        TextView dayDisplay = groupViewHolder.getTextDisplay();
        dayDisplay.setText(getGroup(groupPosition));
        dayDisplay.setTypeface(fontDay);

        return convertView;
    }


    //Return a hour's view (display)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.booking_time_of_day, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else
            childViewHolder = (ChildViewHolder) convertView.getTag();


        TextView timeDisplay = childViewHolder.getTextDisplay();
        timeDisplay.setText(getChild(groupPosition, childPosition));
        timeDisplay.setTypeface(fontTime);

        return convertView;
    }

    //Class that hold the view once it has been found by findViewById method
    private class ChildViewHolder{
        private TextView text;

        ChildViewHolder(View base){
            text = (TextView) base.findViewById(R.id.time_of_day);
        }

        TextView getTextDisplay(){
            return text;
        }
    }

    private class GroupViewHolder{
        private TextView text;
        private ImageView image;
        GroupViewHolder(View base){
            text = (TextView) base.findViewById(R.id.day_of_week);
            image = (ImageView) base.findViewById(R.id.image);
        }

        TextView getTextDisplay(){
            return text;
        }

        void setImage(boolean isExpanded){
            if (isExpanded)
                image.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);
            else
                image.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        }
    }

    public void updateGroupList(List<String> l){
        listOfDays = l;
        this.notifyDataSetChanged();
    }

    public void updateChildList(List<String> l){
        listOfHours = l;
        this.notifyDataSetChanged();
    }
}
