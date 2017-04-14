package com.lanthanh.admin.icareapp.presentation.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.userdetailpage.UserDetailsActivity;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class UserTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int TYPE_HEADER = 0;
    private final int TYPE_BODY = 1;
    private List<String> list;
    private Activity ctx;
    private Typeface fontName;
    private Typeface fontNorm;
    private MainActivityPresenter mainActivityPresenter;

    public UserTabAdapter(Activity context, List<String> list, MainActivityPresenter mainActivityPresenter){
        ctx = context;
        this.list = list;
        this.fontName = Typeface.createFromAsset(ctx.getAssets(), GraphicUtils.FONT_BOLD);//Custom font
        this.fontNorm = Typeface.createFromAsset(ctx.getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        this.mainActivityPresenter = mainActivityPresenter;
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
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ctx.getLayoutInflater();

        if (viewType == TYPE_HEADER){
            View headerView = inflater.inflate(R.layout.fragment_usertab_header, null);
            return new HeaderViewHolder(headerView);
        }else {
            View bodyView = inflater.inflate(R.layout.fragment_usertab_body, null);
            return new BodyViewHolder(bodyView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int type = getItemViewType(position);

        if (type == TYPE_HEADER) {
            ((HeaderViewHolder) holder).setName(list.get(position));
            ((HeaderViewHolder) holder).getTxtDetail().setOnClickListener(view -> mainActivityPresenter.navigateActivity(UserDetailsActivity.class));
        } else {
            ((BodyViewHolder) holder).setOption(list.get(position));
            if (position == 1){
                ((BodyViewHolder) holder).setOptionImage(R.drawable.ic_logout);
                ((BodyViewHolder) holder).getView().setOnClickListener(view -> mainActivityPresenter.logout());
            }
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDetail;
//        private ImageView img;

        HeaderViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.user_name);
            txtName.setTypeface(fontName);
            txtDetail = (TextView) itemView.findViewById(R.id.user_detail);
            txtDetail.setTypeface(fontNorm);
//            img = (ImageView) itemView.findViewById(R.id.user_img);
        }

        void setName(String s){
            txtName.setText(s);
        }

        TextView getTxtDetail(){
            return txtDetail;
        }
    }

    private class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtOpt;
        private ImageView img;
        private View itemView;

        public BodyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            txtOpt = (TextView) itemView.findViewById(R.id.user_option);
            txtOpt.setTypeface(fontNorm);
            img = (ImageView) itemView.findViewById(R.id.user_img);
        }

        void setOption(String s){
            txtOpt.setText(s);
        }

        void setOptionImage(int resId){
            img.setImageResource(resId);
        }

        View getView(){
            return itemView;
        }
    }
}
