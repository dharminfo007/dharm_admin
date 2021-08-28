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
import in.app.dharm.info.online.dharmadmin.model.DealListPojo;

public class DealListAdapter extends RecyclerView.Adapter<DealListAdapter.ContactHolder> {

    // List to store all the contact details
    public ArrayList<DealListPojo> userLists;
    private Context mContext;

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
        final DealListPojo deal = userLists.get(position);

        holder.tvProId.setText(deal.getpName());
        holder.tvQty.setText(deal.getCartoon()+" Cartoon");
        holder.tvPrice.setText("₹ " +deal.getDeal_amt());
        holder.tvUnit.setText(deal.getUser());
        holder.tvDealStatus.setText(deal.getStatus());

        if(deal.getStatus().equals("accepted")){
            holder.tvCancelDeal.setVisibility(View.GONE);
        }else {
            holder.tvCancelDeal.setVisibility(View.VISIBLE);        }

        holder.tvAcceptDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deal.getStatus().equals("accepted")){
                    ((ShowAllRequestedDealsActivity) mContext).updateDealStatus(position, "DOD_"+deal.getUser()+"_"+deal.getpId());
                }else {
                    Toast.makeText(mContext, "Deal already accepted!", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.tvCancelDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ShowAllRequestedDealsActivity) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to cancel deal?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ((ShowAllRequestedDealsActivity) mContext).removeAt(position,
                                            "DOD_"+deal.getUser()+"_"+deal.getpId());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView tvProId, tvQty, tvUnit, tvPrice, tvDealStatus, tvCancelDeal, tvAcceptDeal;

        public ContactHolder(View itemView) {
            super(itemView);

            tvProId = itemView.findViewById(R.id.tvProId);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDealStatus = itemView.findViewById(R.id.tvDealStatus);
            tvCancelDeal = itemView.findViewById(R.id.tvCancelDeal);
            tvAcceptDeal = itemView.findViewById(R.id.tvAcceptDeal);

        }

    }


}
