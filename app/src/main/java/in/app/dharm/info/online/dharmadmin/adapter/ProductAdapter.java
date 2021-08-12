package in.app.dharm.info.online.dharmadmin.adapter;

import android.content.Context;
import android.content.Intent;
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
import in.app.dharm.info.online.dharmadmin.activity.ShowAllOrdersActivity;
import in.app.dharm.info.online.dharmadmin.model.OrderListPojo;
import in.app.dharm.info.online.dharmadmin.model.ProductListPojo;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ContactHolder> {

    // List to store all the contact details
    public ArrayList<OrderListPojo> productList;
    private Context mContext;
    DataProcessor dataProcessor;
    ProductListAdapter listAdapter;

    // Counstructor for the Class
    public ProductAdapter(ArrayList<OrderListPojo> contactsList, Context context) {
        this.productList = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_product_list, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final OrderListPojo product = productList.get(position);

        holder.tvUserName.setText(product.getUser());
        holder.tvOrderId.setText("Order id : "+product.getId());

        if(product.getOrder_status().equals("true")){
            holder.tvCancelOrder.setClickable(false);
            holder.tvCancelOrder.setEnabled(false);
            holder.tvAcceptOrder.setClickable(false);
            holder.tvAcceptOrder.setEnabled(false);
        }else {
            holder.tvCancelOrder.setClickable(true);
            holder.tvCancelOrder.setEnabled(true);
            holder.tvAcceptOrder.setClickable(true);
            holder.tvAcceptOrder.setEnabled(true);
        }
        holder.rvProductItems.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        holder.rvProductItems.setLayoutManager(layoutManager);
        listAdapter = new ProductListAdapter(product.getProductArrayList(), mContext);
        holder.rvProductItems.setAdapter(listAdapter);
        holder.tvAcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ShowAllOrdersActivity) {
                    ((ShowAllOrdersActivity) mContext).updateOrderStatus(position, product.getId());
                }
            }
        });
        holder.tvCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ShowAllOrdersActivity) {
                    ((ShowAllOrdersActivity) mContext).removeAt(position, product.getId());
                }
            }
        });

//        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentImage = new Intent(mContext, ImageDetailsActivity.class);
//                intentImage.putExtra("imageList", productList.get(position).getListProductImages());
//                mContext.startActivity(intentImage);
//            }
//        });

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

//        private TextView tvTitle, tvCartoon, tvStock, tvPrice, tvUserName, tvOrderId;
        private TextView tvUserName, tvOrderId, tvAcceptOrder, tvCancelOrder;
//        CardView cardProducts;
        ImageView imgProduct;
        RecyclerView rvProductItems;

        public ContactHolder(View itemView) {
            super(itemView);

//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvCartoon = itemView.findViewById(R.id.tvCartoon);
//            tvStock = itemView.findViewById(R.id.tvStock);
//            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            rvProductItems = itemView.findViewById(R.id.rvProductItems);
            tvAcceptOrder = itemView.findViewById(R.id.tvAcceptOrder);
            tvCancelOrder = itemView.findViewById(R.id.tvCancelOrder);

        }

//        public void setProductTitle(String title) {
//            tvTitle.setText(title);
//        }

//        public void setProductDesc(String desc) {
//            tvDesc.setText(desc);
//        }

//        public void setProductCartoon(String cartoon) {
//            tvCartoon.setText(cartoon);
//        }
//
//        public void setProductStock(String stock) {
//            tvStock.setText(stock);
//        }
//
//        public void setProductPrice(String price) {
//            tvPrice.setText(price);
//        }
//        public void setProductOfferDisc(String offerDisc) {
//            tvOfferDisc.setText(offerDisc);
//        }
    }

    public void updateList(ArrayList<OrderListPojo> list) {
        productList = list;
        notifyDataSetChanged();
    }

}