package com.example.admin.icareapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView test;
    private List<String> listOfDays;
    private List<String> listOfHours;
    private Map<String, List<String>> listOfHoursPerDay;
    private ExpandableListViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView expList = (ExpandableListView) findViewById(R.id.expListView);
        listOfDays = new ArrayList<String>();
        listOfHours = new ArrayList<String>();
        listOfHoursPerDay = new HashMap<String, List<String>>();

        myAdapter = new ExpandableListViewAdapter(this, listOfDays, listOfHoursPerDay);
        expList.setAdapter(myAdapter);
        //test = (TextView) findViewById(R.id.testText);
        //new JSONParse().execute("http://192.168.0.101/Select_Time.php");
    }

    //for testing
    public void init(){
        listOfDays.add("Monday");
        listOfDays.add("Tuesday");
        listOfDays.add("Wednesday");
        listOfDays.add("Thursday");
        listOfDays.add("Friday");
        listOfDays.add("Saturday");
        listOfDays.add("Sunday");

        for (String s : listOfDays){
            listOfHoursPerDay.put(s, listOfHours);
        }

    }

    public void getHours(JSONArray jsonArray){
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                listOfHours.add(jsonArray.get(i).toString());
            }
        }catch(org.json.JSONException e){
            e.printStackTrace();
        }
    }

    /*private class JSONParse extends AsyncTask<String, Void, JSONArray>{
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONParser jParser = new JSONParser(params[0]);

            return jParser.getJSONFromUrl();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            pDialog.dismiss();
            getHours(jsonArray);
            init();
            myAdapter.notifyDataSetChanged();
        }


    }*/
}
