package com.lanthanh.admin.icareapp.presentation.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.utils.ConverterUtils;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab.AppointmentDialogFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 21-Nov-16.
 */

public class AppointmentCVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final static String APPOINTMENT_ID = "appointmentId";
    public final static String APPOINTMENT_TITLE = "title";
    public final static String APPOINTMENT_CUSTOMER_NAME = "name";
    public final static String APPOINTMENT_ADDRESS = "address";
    public final static String APPOINTMENT_VOUCHER = "voucher";
    public final static String APPOINTMENT_TYPE = "type";
    public final static String APPOINTMENT_START_DATE = "start_date";
    public final static String APPOINTMENT_EXPIRE_DATE = "expire_date";
    public final static String APPOINTMENT_SCHEDULES = "schedules";

    private final int TYPE_HEADER = 0;
    private final int TYPE_BODY = 2;
    private List<DTOAppointment> list;
    private Activity ctx;
    private Typeface font;
    private FragmentManager fm;

    public AppointmentCVAdapter(Activity context, List<DTOAppointment> list){
        ctx = context;
        this.list = list;
        font = Typeface.createFromAsset(ctx.getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        fm = ((AppCompatActivity) context).getSupportFragmentManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_BODY;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ctx.getLayoutInflater();
        if (viewType == TYPE_HEADER){
            View view = inflater.inflate(R.layout.fragment_appointment_header, null);
            return new HeaderViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.fragment_appointment_cardview, null);
            return new BodyViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER){
            ((HeaderViewHolder) holder).setHeaderTextView(ctx.getString(R.string.appointment));
        }else {
            ((BodyViewHolder) holder).setName(list.get(position - 1).getUser().getName());
            ((BodyViewHolder) holder).setVoucher(list.get(position - 1).getVoucher().getVoucherName());
            ((BodyViewHolder) holder).setStartDate(ConverterUtils.date.convertDateForDisplay(list.get(position - 1).getStartDate()));
            ((BodyViewHolder) holder).setEndDate(ConverterUtils.date.convertDateForDisplay(list.get(position - 1).getExpireDate()));
            ((BodyViewHolder) holder).getDetailTextView().setOnClickListener(
                (View view) -> {
                    AppointmentDialogFragment frag = new AppointmentDialogFragment();
                    //TODO put Serialized Variable instead of field by field
                    //Set information into bundle so that fragment can display
                    Bundle args = new Bundle();

                    String appointmentId = list.get(position - 1).getAppointmentId();
                    String title =  ctx.getString(R.string.bill);
                    String name = list.get(position - 1).getUser().getName();
                    String address = formFullAddress(position - 1);
                    String voucher = list.get(position - 1).getVoucher().getVoucherName();
                    String type = list.get(position - 1).getType().getTypeName();
                    String startDate = ConverterUtils.date.convertDateForDisplay(list.get(position - 1).getStartDate());
                    String expireDate = ConverterUtils.date.convertDateForDisplay(list.get(position - 1).getExpireDate());

                    args.putString(APPOINTMENT_ID, appointmentId);
                    args.putString(APPOINTMENT_TITLE, title);
                    args.putString(APPOINTMENT_CUSTOMER_NAME, name);
                    args.putString(APPOINTMENT_ADDRESS, address);
                    args.putString(APPOINTMENT_VOUCHER, voucher);
                    args.putString(APPOINTMENT_TYPE, type);
                    args.putString(APPOINTMENT_START_DATE, startDate);
                    args.putString(APPOINTMENT_EXPIRE_DATE, expireDate);

                    ArrayList<String> appointmentScheduleString = new ArrayList<>();
                    for (DTOAppointmentSchedule dtoAppointmentSchedule : list.get(position - 1).getAppointmentScheduleList()) {
                        appointmentScheduleString.add(dtoAppointmentSchedule.toString());
                    }
                    args.putStringArrayList(APPOINTMENT_SCHEDULES, appointmentScheduleString);

                    //Attach bundle to fragment
                    frag.setArguments(args);

                    //Show fragment
                    frag.show(fm, frag.getClass().getName());
                }
            );
            ((BodyViewHolder) holder).getCancelAppointmentTextView().setOnClickListener(
                (View v) ->
                new AlertDialog.Builder(ctx)
                        .setMessage(ctx.getString(R.string.cancel_confirm))
                        .setPositiveButton(
                            ctx.getString(R.string.agree_button),
                            (DialogInterface dialog, int which) -> {
                                dialog.dismiss();
                                ((MainActivity) ctx).getMainPresenter().cancelAppointment(list.get(position - 1).getAppointmentId());
                            }
                        )
                        .setNegativeButton(
                            ctx.getString(R.string.abort_button),
                            (DialogInterface dialog, int which) -> dialog.dismiss()
                        )
                        .setCancelable(true).show()
            );
        }
    }

    private String formFullAddress(int position) {
        return String.format(
                "%s, %s, %s, %s",
                list.get(position).getLocation().getAddress(),
                list.get(position).getDistrict().getDistrictName(),
                list.get(position).getCity().getCityName(),
                list.get(position).getCountry().getCountryName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, voucher, startDate, expireDate, startDateTitle, expireDateTitle, detail, cancel;

        BodyViewHolder(View itemView) {
            super(itemView);

            //Name
            name = (TextView) itemView.findViewById(R.id.name);
            name.setTypeface(font);
            //Voucher
            voucher = (TextView) itemView.findViewById(R.id.voucher);
            voucher.setTypeface(font);
            //Start date
            startDateTitle = (TextView) itemView.findViewById(R.id.startdatetitle);
            startDate = (TextView) itemView.findViewById(R.id.startdate);
            startDate.setTypeface(font);
            //Expire date
            expireDateTitle = (TextView) itemView.findViewById(R.id.expiredatetitle);
            expireDate = (TextView) itemView.findViewById(R.id.expiredate);
            expireDate.setTypeface(font);
            //Detail
            detail = (TextView) itemView.findViewById(R.id.detail_appointment);
            detail.setTypeface(font);
            //Cancel
            cancel = (TextView) itemView.findViewById(R.id.cancel_appointment);
            cancel.setTypeface(font);
        }

        public void setName(String s) {
            name.setText(s);
        }

        public void setVoucher(String s) {
            voucher.setText(s);
        }

        public void setStartDate(String s) {
            if (s != null) {
                if (!s.equals("11/11/1111"))
                    startDate.setText(s);
                else{
                    startDateTitle.setVisibility(View.GONE);
                    startDate.setVisibility(View.GONE);
                }
            }
        }

        public void setEndDate(String s) {
            if (s != null) {
                expireDate.setText(s);
                if (startDate.getVisibility() == View.GONE){
                    //If start date = null => one day booking => while hiding start date, move expire date to the right
                    //rename to Ngay thuc hien
                    expireDateTitle.setText(ctx.getString(R.string.booking_do_date));

                    //move to right
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expireDate.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_START);
                    RelativeLayout.LayoutParams params_title = (RelativeLayout.LayoutParams) expireDateTitle.getLayoutParams();
                    params_title.addRule(RelativeLayout.ALIGN_PARENT_START);
                    expireDate.setLayoutParams(params);
                    expireDateTitle.setLayoutParams(params_title);
                }
            }
            else {
                Log.e(AppointmentCVAdapter.class.getSimpleName(), "Severe: No expire date from AppointmentCVAdapter class");
            }
        }

        public TextView getDetailTextView(){
            return detail;
        }

        public TextView getCancelAppointmentTextView(){
            return cancel;
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView header;

        HeaderViewHolder (View base){
            super(base);

            header = (TextView) base.findViewById(R.id.headerTv);
            header.setTypeface(font);
        }

        void setHeaderTextView(String header){
            this.header.setText(header);
        }
    }

    public void updateList(List<DTOAppointment> list){
        this.list = list;
        this.notifyDataSetChanged();
    }
}
