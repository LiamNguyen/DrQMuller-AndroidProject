package DrQMuller.DrQMuller.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import DrQMuller.R;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class BookingDetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        List<BookingItem> list = new ArrayList<>();
        listInit(list);
        RecyclerView rv = (RecyclerView)findViewById(R.id.bookingdet_recycler_view);

        BookingCVAdapter adapter = new BookingCVAdapter(this, list);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//    }

    public void listInit(List<BookingItem> list){
        Gson gson = new Gson();
        JSONArray bookingCode;
        SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
        bookingCode = gson.fromJson(sharedPref.getString("bookingCode", ""), JSONArray.class);
        System.out.println(bookingCode);
        if (bookingCode != null && bookingCode.length() != 0){
            for (int i = 0; i < bookingCode.length(); i ++) {
                try {
                    JSONArray data = gson.fromJson(sharedPref.getString(bookingCode.getString(i), ""), JSONArray.class);
                    list.add(new BookingItem(bookingCode.toString(i), data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getString(6)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
