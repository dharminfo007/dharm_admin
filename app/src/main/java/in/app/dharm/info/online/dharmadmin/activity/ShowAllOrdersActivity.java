package in.app.dharm.info.online.dharmadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.adapter.ProductAdapter;
import in.app.dharm.info.online.dharmadmin.model.OrderListPojo;
import in.app.dharm.info.online.dharmadmin.model.Product;
import in.app.dharm.info.online.dharmadmin.model.ProductListPojo;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class ShowAllOrdersActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvProducts, rvProductsFilter;
    private ProductAdapter listAdapter;
    ArrayList<OrderListPojo> productArrayList;
    ArrayList<String> filterListPojoArrayList;
    //    ImageView imgBack;
    FirebaseFirestore db;
    public String TAG = "ShowAllOrdersActivity";
    ProgressDialog pd;
    MaterialTextView txtNoDataFound;
    EditText etSearch;
    ArrayList<String> images = new ArrayList<>();
    ImageView imgCart;
    TextView tvAllProductsTitle;
    DataProcessor dataProcessor;
    ProductListPojo productListPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_orders);

        pd = new ProgressDialog(ShowAllOrdersActivity.this);
        pd.setMessage("loading...");

        dataProcessor = new DataProcessor(this);

        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        images = new ArrayList<>();
        filterListPojoArrayList = new ArrayList<>();
        rvProducts = (RecyclerView) findViewById(R.id.rvProducts);
        tvAllProductsTitle = findViewById(R.id.tvAllProductsTitle);
        etSearch = findViewById(R.id.etSearch);
        rvProductsFilter = (RecyclerView) findViewById(R.id.rvProductsFilter);
        txtNoDataFound = findViewById(R.id.txtNoDataMatch);
        tvAllProductsTitle.setText("ALL PRODUCTS");
        rvProducts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvProducts.setLayoutManager(layoutManager);
        listAdapter = new ProductAdapter(productArrayList, this);
        rvProducts.setAdapter(listAdapter);

        onClickListenersInit();
        initProductDataAvailability();
        findProducts();
    }

    private void findProducts() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                if (s.length() > 0) {
//                    filter(s.toString());
                }

                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });
    }


    private void initProductDataAvailability() {
        pd.show();
        db.collection("orderlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            productArrayList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                OrderListPojo orderListPojo = new OrderListPojo(document.getId(), document.getString("order_total"),
                                        document.getString("user"),document.getString("order_accepted"),
                                        (ArrayList<HashMap<String, String>>) document.get("products"));
                                productArrayList.add(orderListPojo);
                            }
                            Log.d(TAG, " => " + productArrayList.size());
                            if (productArrayList.size() > 0) {
                                txtNoDataFound.setVisibility(View.GONE);
                                rvProducts.setVisibility(View.VISIBLE);
                                listAdapter = new ProductAdapter(productArrayList, ShowAllOrdersActivity.this);
                                rvProducts.setAdapter(listAdapter);
                                listAdapter.notifyDataSetChanged();
                            } else {
                                txtNoDataFound.setVisibility(View.VISIBLE);
                                rvProducts.setVisibility(View.GONE);
                                listAdapter.notifyDataSetChanged();
                            }

                        } else {
                            pd.dismiss();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void onClickListenersInit() {
//        imgBack.setOnClickListener(this);
        tvAllProductsTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    public void removeAt(int position, String document) {
        db.collection("orderlist").document(document)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                productArrayList.remove(position);
                listAdapter.notifyItemRemoved(position);
                listAdapter.notifyItemRangeChanged(position, productArrayList.size());
                checkOrderList();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void updateOrderStatus(int position, String document){

        db.collection("orderlist").document(document).update("order_accepted", "true")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void checkOrderList() {
        if (productArrayList.size() > 0) {
            txtNoDataFound.setVisibility(View.GONE);
            rvProducts.setVisibility(View.VISIBLE);
        } else {
            txtNoDataFound.setText("No orders found");
            txtNoDataFound.setVisibility(View.VISIBLE);
            rvProducts.setVisibility(View.GONE);
        }
    }
}