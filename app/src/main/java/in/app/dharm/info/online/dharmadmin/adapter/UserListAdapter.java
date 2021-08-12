package in.app.dharm.info.online.dharmadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.model.UserList;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ContactHolder> {

    // List to store all the contact details
    public ArrayList<UserList> userLists;
    private Context mContext;
    DataProcessor dataProcessor;
    ProductListAdapter listAdapter;

    // Counstructor for the Class
    public UserListAdapter(ArrayList<UserList> userLists, Context context) {
        this.userLists = userLists;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public UserListAdapter.ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_user_list, parent, false);
        return new UserListAdapter.ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ContactHolder holder, final int position) {
        final UserList user = userLists.get(position);

        holder.tvUserName.setText(user.getPhone());
        holder.tvUserAddress.setText(user.getAddress());

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName, tvUserAddress;

        public ContactHolder(View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserAddress = itemView.findViewById(R.id.tvUserAddress);

        }

    }


}