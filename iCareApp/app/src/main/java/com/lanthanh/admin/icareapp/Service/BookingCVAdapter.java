package com.lanthanh.admin.icareapp.Service;

import android.app.Activity;
import android.content.Intent;
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

public class BookingCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

        private List<BookingItem> list;
        private Activity ctx;

        public BookingCVAdapter(Activity context, List<BookingItem> list){
            ctx = context;
            this.list = list;
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = ctx.getLayoutInflater();

            View view = inflater.inflate(R.layout.activity_booking_details_cardview, null);
            view.setOnClickListener(this);
            return new ViewHolder(view);

        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).setName(list.get(position).getName());
            ((ViewHolder) holder).setVoucher(list.get(position).getVoucher());
            ((ViewHolder) holder).setStartDate(list.get(position).getStartDate());
            ((ViewHolder) holder).setEndDate(list.get(position).getEndDate());
            ((ViewHolder) holder).setStatus(list.get(position).getStatus());
        }

    @Override
    public void onClick(View view) {
        TextView txt = (TextView) view.findViewById(R.id.status);
        if (txt.getText().toString().equals(ctx.getString(R.string.not_confirm_yet))){
            Intent toConfirm = new Intent(ctx, ConfirmBookingActivity.class);
            ctx.startActivity(toConfirm);
            ctx.finish();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            private TextView txt1, txt2, txt3, txt4, txt5;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                txt1 = (TextView) itemView.findViewById(R.id.name);
                txt2 = (TextView) itemView.findViewById(R.id.voucher);
                txt3 = (TextView) itemView.findViewById(R.id.startdate);
                txt4 = (TextView) itemView.findViewById(R.id.enddate);
                txt5 = (TextView) itemView.findViewById(R.id.status);
            }

            public void setName(String s) {
                txt1.setText(s);
            }

            public void setVoucher(String s) {
                txt2.setText(s);
            }

            public void setStartDate(String s) {
                txt3.setText(s);
            }

            public void setEndDate(String s) {
                txt4.setText(s);
            }

            public void setStatus(String s) {
                txt5.setText(s);
            }

        }
}
