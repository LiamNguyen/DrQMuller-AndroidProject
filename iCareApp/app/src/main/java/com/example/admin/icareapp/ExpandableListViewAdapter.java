package com.example.admin.icareapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

/**
 * Created by ADMIN on 16-Oct-16.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter{
    private List<String> listOfDays;
    private Map<String,List<String>> listOfHoursPerDay;
    private Activity context;
    private ViewHolder holder;

    public ExpandableListViewAdapter(Activity context, List<String> listOfDays, Map<String,List<String>> listOfHoursPerDay){
        this.listOfDays = listOfDays;
        this.listOfHoursPerDay = listOfHoursPerDay;
        this.context = context;
    }

    //Get one header (a day of week) of the group
    @Override
    public Object getGroup(int groupPosition) {
        return listOfDays.get(groupPosition);
    }

    //Get an hour in the list of hours per day
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listOfHoursPerDay.get(listOfDays.get(groupPosition)).get(childPosition);
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
        return listOfHoursPerDay.get(listOfDays.get(groupPosition)).size();
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
            convertView = inflater.inflate(R.layout.day_of_week, null);
            holder = new ViewHolder(convertView, R.id.day_of_week);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        TextView dayDisplay = holder.getTextDisplay();
        dayDisplay.setText((String)getGroup(groupPosition));
        dayDisplay.setTypeface(null, Typeface.BOLD);

        return convertView;
    }


    //Return a hour's view (display)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.time_of_day, null);
            holder = new ViewHolder(convertView, R.id.time_of_day);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        TextView timeDisplay = holder.getTextDisplay();
        timeDisplay.setText((String)getChild(groupPosition, childPosition));

        return convertView;
    }

    //Class that hold the view once it has been found by findViewById method
    private class ViewHolder{
        private View base;
        private int viewID;
        private TextView text;

        public ViewHolder(View base, int id){
            this.base = base;
            this.viewID = id;
        }

        public TextView getTextDisplay(){
            if (text == null)
                text = (TextView) base.findViewById(viewID);

            return text;
        }
    }

}
