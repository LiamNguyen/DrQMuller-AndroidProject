package com.lanthanh.admin.icareapp.Service;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Confirm.ConfirmBookingActivity;
import com.lanthanh.admin.icareapp.R;

import java.util.List;

/**
 * Created by ADMIN on 21-Nov-16.
 */

public class BookingCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<BookingItem> list;
    private Activity ctx;
    private Typeface font;
    private FragmentManager fm;

    public BookingCVAdapter(Activity context, List<BookingItem> list){
        ctx = context;
        this.list = list;
        font = Typeface.createFromAsset(ctx.getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        fm = ((AppCompatActivity) context).getSupportFragmentManager();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ctx.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_booking_details_cardview, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).setName(list.get(position).getName());
        ((ViewHolder) holder).setVoucher(list.get(position).getVoucher());
        ((ViewHolder) holder).setStartDate(list.get(position).getStartDate());
        ((ViewHolder) holder).setEndDate(list.get(position).getEndDate());
        ((ViewHolder) holder).setStatus(list.get(position).getStatus());
        ((ViewHolder) holder).getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) view.findViewById(R.id.status);
                if (txt.getText().toString().equals(ctx.getString(R.string.not_confirm_yet))){
                    Intent toConfirm = new Intent(ctx, ConfirmBookingActivity.class);
                    ctx.startActivity(toConfirm);
                    ctx.finish();
                }else if (txt.getText().toString().equals(ctx.getString(R.string.confirmed))){
                    DetailsFragment frag = DetailsFragment.newInstance(ctx.getString(R.string.bill), list.get(position).getName(), list.get(position).getVoucher(),
                                                                       list.get(position).getLocation(), list.get(position).getStartDate(),
                                                                       list.get(position).getEndDate(), list.get(position).getType(),
                                                                       list.get(position).getCode(), list.get(position).getApp1(),
                                                                       list.get(position).getApp2(), list.get(position).getApp3());
                    frag.show(fm, frag.getClass().getName());
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView txt1, txt2, txt3, txt4, txt5;
        private View view;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            view = itemView;
            txt1 = (TextView) itemView.findViewById(R.id.name);
            txt1.setTypeface(font);
            txt2 = (TextView) itemView.findViewById(R.id.voucher);
            txt2.setTypeface(font);
            txt3 = (TextView) itemView.findViewById(R.id.startdate);
            txt3.setTypeface(font);
            txt4 = (TextView) itemView.findViewById(R.id.enddate);
            txt4.setTypeface(font);
            txt5 = (TextView) itemView.findViewById(R.id.status);
            txt5.setTypeface(font);
        }

        public void setName(String s) {
            txt1.setText(s);
        }

        public void setVoucher(String s) {
            txt2.setText(s);
        }

        public void setStartDate(String s) {
            if (!s.isEmpty())
                txt3.setText(s);
            else
                txt3.setText("Không Có");
        }

        public void setEndDate(String s) {
            if (!s.isEmpty())
                txt4.setText(s);
            else
                txt4.setText("Không Có");
        }

        public void setStatus(String s) {
            txt5.setText(s);
        }

        public View getView(){
            return view;
        }
    }
}
