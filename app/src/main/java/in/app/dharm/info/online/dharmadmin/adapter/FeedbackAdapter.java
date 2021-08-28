package in.app.dharm.info.online.dharmadmin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.activity.ShowAllRequestedDealsActivity;
import in.app.dharm.info.online.dharmadmin.model.FeedBackListPojo;
import in.app.dharm.info.online.dharmadmin.model.FeedBackListPojo;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedHolder> {

    // List to store all the contact details
    public ArrayList<FeedBackListPojo> userLists;
    private Context mContext;

    // Counstructor for the Class
    public FeedbackAdapter(ArrayList<FeedBackListPojo> userLists, Context context) {
        this.userLists = userLists;
        this.mContext = context;
    }

    @Override
    public FeedbackAdapter.FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_feedback_list, parent, false);
        return new FeedbackAdapter.FeedHolder(view);
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.FeedHolder holder, final int position) {
        final FeedBackListPojo deal = userLists.get(position);

        holder.tvFeedback.setText(deal.getFeedBack());
        holder.tvUserName.setText(deal.getGetFeedBackUser());

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class FeedHolder extends RecyclerView.ViewHolder {

        private TextView tvFeedback, tvUserName, tvUnit, tvPrice, tvDealStatus, tvCancelDeal, tvAcceptDeal;

        public FeedHolder(View itemView) {
            super(itemView);

            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            tvUserName = itemView.findViewById(R.id.tvUserName);
//            tvQty = itemView.findViewById(R.id.tvQty);
//            tvUnit = itemView.findViewById(R.id.tvUnit);
//            tvPrice = itemView.findViewById(R.id.tvPrice);
//            tvDealStatus = itemView.findViewById(R.id.tvDealStatus);
//            tvCancelDeal = itemView.findViewById(R.id.tvCancelDeal);
//            tvAcceptDeal = itemView.findViewById(R.id.tvAcceptDeal);

        }

    }


}
