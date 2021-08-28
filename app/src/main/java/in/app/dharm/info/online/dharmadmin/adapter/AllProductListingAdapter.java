package in.app.dharm.info.online.dharmadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.activity.ViewAllProductActivity;
import in.app.dharm.info.online.dharmadmin.model.AllProductListPojo;

public class AllProductListingAdapter extends RecyclerView.Adapter<AllProductListingAdapter.ContactHolder> {

    // List to store all the contact details
    public ArrayList<AllProductListPojo> productList;
    private Context mContext;

    // Constructor for the Class
    public AllProductListingAdapter(ArrayList<AllProductListPojo> contactsList,
                                    Context context) {
        this.productList = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_all_product, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final AllProductListPojo product = productList.get(position);

        // Set the data to the views here
        holder.setProductTitle(product.getName());
        holder.setProductCartoon(product.getTvPiecesPerCartoon() + " Pcs/Ctn");
        holder.setProductStock(product.getTvStock() + " in stocks");
        holder.setProductPrice("₹ " + product.getTvPrice());

        if (productList.get(position).getListProductImages().size() > 0) {
            Glide
                    .with(mContext)
                    .load(productList.get(position).getListProductImages().get(0))
                    .centerCrop()
                    .dontAnimate()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(holder.imgProduct);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position, product);
            }
        });
    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvCartoon, tvStock, tvPrice, btnEdit;
        ImageView imgProduct;

        public ContactHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCartoon = itemView.findViewById(R.id.tvCartoon);
            tvStock = itemView.findViewById(R.id.tvStock);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnEdit = itemView.findViewById(R.id.btnEdit);

        }

        public void setProductTitle(String title) {
            tvTitle.setText(title);
        }


        public void setProductCartoon(String cartoon) {
            tvCartoon.setText(cartoon);
        }

        public void setProductStock(String stock) {
            tvStock.setText(stock);
        }

        public void setProductPrice(String price) {
            tvPrice.setText(price);
        }

    }

    private void openDialog(int pos, AllProductListPojo product) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.dialog_bottom_sheet);

        EditText etCartoon = bottomSheetDialog.findViewById(R.id.etCartoon);
        EditText etPrice = bottomSheetDialog.findViewById(R.id.etPrice);
        EditText etStock = bottomSheetDialog.findViewById(R.id.etStock);
        TextView tvUpdate = bottomSheetDialog.findViewById(R.id.tvUpdate);
        ImageView imgClose = bottomSheetDialog.findViewById(R.id.imgClose);

        etCartoon.setText(product.getTvPiecesPerCartoon());
        etStock.setText(product.getTvStock());
        etPrice.setText(product.getTvPrice());

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ViewAllProductActivity) {
                    ((ViewAllProductActivity) mContext).updateProduct(pos, bottomSheetDialog,
                            product, etCartoon.getText().toString(),
                            etPrice.getText().toString(), etStock.getText().toString());
                }

            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();

    }
}