package in.app.dharm.info.online.dharmadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.adapter.ProductAdapter;
import in.app.dharm.info.online.dharmadmin.adapter.UserListAdapter;
import in.app.dharm.info.online.dharmadmin.model.OrderListPojo;
import in.app.dharm.info.online.dharmadmin.model.ProductListPojo;
import in.app.dharm.info.online.dharmadmin.model.UserList;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class ShowAllUsersActivity extends AppCompatActivity  {

    RecyclerView rvProducts;
    private UserListAdapter listAdapter;
    ArrayList<UserList> productArrayList;
    FirebaseFirestore db;
    public String TAG = "ShowAllUsersActivity";
    ProgressDialog pd;
    MaterialTextView txtNoDataFound;
    ArrayList<String> images = new ArrayList<>();
    DataProcessor dataProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);

        pd = new ProgressDialog(ShowAllUsersActivity.this);
        pd.setMessage("loading...");

        dataProcessor = new DataProcessor(this);

        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        images = new ArrayList<>();
        rvProducts = (RecyclerView) findViewById(R.id.rvUsers);
        txtNoDataFound = findViewById(R.id.txtNoData);
        rvProducts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvProducts.setLayoutManager(layoutManager);
        listAdapter = new UserListAdapter(productArrayList, this);
        rvProducts.setAdapter(listAdapter);

        initProductDataAvailability();
    }

    private void initProductDataAvailability() {
        pd.show();
        db.collection("userlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            productArrayList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                UserList orderListPojo = new UserList(
                                        document.getString("address"),
                                        document.getString("in_date"), document.getString("phone"));
                                productArrayList.add(orderListPojo);
                            }
                            Log.d(TAG, " => " + productArrayList.size());
                            if (productArrayList.size() > 0) {
                                txtNoDataFound.setVisibility(View.GONE);
                                rvProducts.setVisibility(View.VISIBLE);
                                listAdapter = new UserListAdapter(productArrayList, ShowAllUsersActivity.this);
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