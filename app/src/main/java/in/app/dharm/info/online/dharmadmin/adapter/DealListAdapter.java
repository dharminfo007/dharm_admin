package in.app.dharm.info.online.dharmadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.model.DealListPojo;
import in.app.dharm.info.online.dharmadmin.model.UserList;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class DealListAdapter extends RecyclerView.Adapter<DealListAdapter.ContactHolder> {

    // List to store all the contact details
    public ArrayList<DealListPojo> userLists;
    private Context mContext;
    DataProcessor dataProcessor;
    ProductListAdapter listAdapter;

    // Counstructor for the Class
    public DealListAdapter(ArrayList<DealListPojo> userLists, Context context) {
        this.userLists = userLists;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public DealListAdapter.ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_deal_list, parent, false);
        return new DealListAdapter.ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull DealListAdapter.ContactHolder holder, final int position) {
        final DealListPojo user = userLists.get(position);

        holder.tvProId.setText(user.getpId());
        holder.tvQty.setText(user.getCartoon()+" Cartoon");
        holder.tvPrice.setText("₹ " +user.getDeal_amt());
        holder.tvUnit.setText(user.getUser());

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView tvProId, tvQty, tvUnit, tvPrice;

        public ContactHolder(View itemView) {
            super(itemView);

            tvProId = itemView.findViewById(R.id.tvProId);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }

    }


}
