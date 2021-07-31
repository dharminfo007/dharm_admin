package in.app.dharm.info.online.dharmadmin.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.app.dharm.info.online.dharmadmin.R;


public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ContactHolder> {

    // List to store all the contact details
    public ArrayList<Uri> imageList;
    private Context mContext;

    // Counstructor for the Class
    public ProductImageAdapter(ArrayList<Uri> imageList, Context context) {
        this.imageList = imageList;
        this.mContext = context;
    }

    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_product_image, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {

        Glide
                .with(mContext)
                .load(imageList.get(position).toString())
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .into(holder.imgProduct);

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });

    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct, imgRemove;


        public ContactHolder(View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgRemove = itemView.findViewById(R.id.imgRemove);

        }

    }

    public void removeAt(int position) {
        imageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imageList.size());
    }
}