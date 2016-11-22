package DrQMuller.DrQMuller.UserTab;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import DrQMuller.DrQMuller.MainActivity;

import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class UserTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int TYPE_HEADER = 0;
    private final int TYPE_BODY = 1;
    private List<String> list;
    private Activity ctx;

    public UserTabAdapter(Activity context, List<String> list){
        ctx = context;
        this.list = list;
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
            View headerView = inflater.inflate(DrQMuller.R.layout.user_header, null);
            return new HeaderViewHolder(headerView);
        }else {
            View bodyView = inflater.inflate(DrQMuller.R.layout.user_body, null);
            return new BodyViewHolder(bodyView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int i = getItemViewType(position);

        if(i == TYPE_HEADER){
            ((HeaderViewHolder) holder).setName(list.get(position));
        }else{
            ((BodyViewHolder) holder).setOption(list.get(position));
            ((BodyViewHolder) holder).getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) ctx).navigateToBookingDetails();
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView txtName;
        private TextView txtDetail;
        private ImageView img;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public HeaderViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txtName = (TextView) itemView.findViewById(DrQMuller.R.id.user_name);
            txtDetail = (TextView) itemView.findViewById(DrQMuller.R.id.user_detail);
            img = (ImageView) itemView.findViewById(DrQMuller.R.id.user_img);
        }

        public void setName(String s){
            txtName.setText(s);
        }
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView txtOpt;
        private ImageView img;
        private View itemView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public BodyViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.itemView = itemView;
            txtOpt = (TextView) itemView.findViewById(DrQMuller.R.id.user_option);
            img = (ImageView) itemView.findViewById(DrQMuller.R.id.user_img);
        }

        public void setOption(String s){
            txtOpt.setText(s);
        }

        public View getView(){
            return itemView;
        }
    }
}
