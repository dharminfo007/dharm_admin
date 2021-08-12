package in.app.dharm.info.online.dharmadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.adapter.DealListAdapter;
import in.app.dharm.info.online.dharmadmin.adapter.UserListAdapter;
import in.app.dharm.info.online.dharmadmin.model.DealListPojo;
import in.app.dharm.info.online.dharmadmin.model.UserList;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class ShowAllRequestedDealsActivity extends AppCompatActivity {

    RecyclerView rvProducts;
    private DealListAdapter listAdapter;
    ArrayList<DealListPojo> productArrayList;
    FirebaseFirestore db;
    public String TAG = "ShowAllRequestedDealsActivity";
    ProgressDialog pd;
    MaterialTextView txtNoDataFound;
    ArrayList<String> images = new ArrayList<>();
    DataProcessor dataProcessor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_requested_deals);
        pd = new ProgressDialog(ShowAllRequestedDealsActivity.this);
        pd.setMessage("loading...");

        dataProcessor = new DataProcessor(this);

        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        images = new ArrayList<>();
        rvProducts = (RecyclerView) findViewById(R.id.rvDealList);
        txtNoDataFound = findViewById(R.id.txtNoData);
        rvProducts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvProducts.setLayoutManager(layoutManager);
        listAdapter = new DealListAdapter(productArrayList, this);
        rvProducts.setAdapter(listAdapter);

        initProductDataAvailability();
    }

    private void initProductDataAvailability() {
        pd.show();
        db.collection("deallist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            productArrayList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DealListPojo orderListPojo = new DealListPojo(
                                        document.getString("cartoon"),
                                        document.getString("deal_amount"), document.getString("deal_in_date"),
                                        document.getString("product_id"), document.getString("status"),
                                        document.getString("user"));

                                productArrayList.add(orderListPojo);
                            }
                            Log.d(TAG, " => " + productArrayList.size());
                            if (productArrayList.size() > 0) {
                                txtNoDataFound.setVisibility(View.GONE);
                                rvProducts.setVisibility(View.VISIBLE);
                                listAdapter = new DealListAdapter(productArrayList, ShowAllRequestedDealsActivity.this);
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
}