package in.app.dharm.info.online.dharmadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.model.OrderListPojo;
import in.app.dharm.info.online.dharmadmin.model.Product;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ContactHolder> {

    // List to store all the contact details
    public ArrayList<Product> productList;
    private Context mContext;
    DataProcessor dataProcessor;

    // Counstructor for the Class
    public ProductListAdapter(ArrayList<Product> contactsList, Context context) {
        this.productList = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ProductListAdapter.ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_order_list, parent, false);
        return new ProductListAdapter.ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ContactHolder holder, final int position) {
//        final Product product = productList.get(position);

        HashMap<String, String> map = new HashMap<String, String>();

        //Getting Collection of values from HashMap

        Collection<String> values = map.values();

        //Creating an ArrayList of values

        ArrayList<String> listOfValues = new ArrayList<String>(values);
        // Set the data to the views here
//        holder.setProductId(product.getId());
//        holder.setProductQty(product.getQty());
//        holder.setProductPrice("₹ " + product.getPrice());
//        holder.setProductUnit( product.getUnit());
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

        public void setProductId(String title) {
            tvProId.setText(title);
        }

        public void setProductQty(String cartoon) {
            tvQty.setText(cartoon);
        }

        public void setProductUnit(String unit) {
            tvUnit.setText(unit);
        }

        public void setProductPrice(String price) {
            tvPrice.setText(price);
        }

    }


}