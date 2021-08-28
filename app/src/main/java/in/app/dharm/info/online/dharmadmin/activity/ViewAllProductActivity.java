package in.app.dharm.info.online.dharmadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.adapter.AllProductListingAdapter;
import in.app.dharm.info.online.dharmadmin.model.AllProductListPojo;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class ViewAllProductActivity extends AppCompatActivity {

    RecyclerView rvViewAllProduct;
    private AllProductListingAdapter listAdapter;
    ArrayList<AllProductListPojo> productArrayList;
    FirebaseFirestore db;
    public String TAG = "ViewAllProductActivity";
    ProgressDialog pd;
    MaterialTextView txtNoDataFound;
    EditText etSearch;
    ArrayList<String> images = new ArrayList<>();
    DataProcessor dataProcessor;
    AllProductListPojo allProductListPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_product);

        pd = new ProgressDialog(ViewAllProductActivity.this);
        pd.setMessage("loading...");

        dataProcessor = new DataProcessor(this);

        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        images = new ArrayList<>();
        rvViewAllProduct = (RecyclerView) findViewById(R.id.rvViewAllProduct);
        etSearch = findViewById(R.id.etSearch);
        txtNoDataFound = findViewById(R.id.txtNoProduct);
        rvViewAllProduct.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvViewAllProduct.setLayoutManager(layoutManager);
        listAdapter = new AllProductListingAdapter(productArrayList, this);
        rvViewAllProduct.setAdapter(listAdapter);

    }


    private void initProductDataAvailability() {
        pd.show();
        db.collection("productlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            productArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                allProductListPojo = new AllProductListPojo(document.getString("name"),
                                        document.getString("description"), document.getString("pieces per cartoon"),
                                        document.getString("stock"),
                                        document.getString("price"), document.getString("in_date"),
                                        document.getString("type"), document.getString("id"), (ArrayList<String>) document.get("images"));

                                productArrayList.add(allProductListPojo);
                            }
                            Log.d(TAG, " => " + productArrayList.size());
                            if (productArrayList.size() > 0) {
                                txtNoDataFound.setVisibility(View.GONE);
                                rvViewAllProduct.setVisibility(View.VISIBLE);
                                listAdapter = new AllProductListingAdapter(productArrayList, ViewAllProductActivity.this);
                                rvViewAllProduct.setAdapter(listAdapter);
                                listAdapter.notifyDataSetChanged();
                            } else {
                                txtNoDataFound.setVisibility(View.VISIBLE);
                                rvViewAllProduct.setVisibility(View.GONE);
                                listAdapter.notifyDataSetChanged();
                            }

                        } else {
                            pd.dismiss();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initProductDataAvailability();
    }

    public void updateProduct(int position, BottomSheetDialog bottomSheetDialog, AllProductListPojo allProductListPojo,
                              String cartoonUpdate,
                              String priceUpdate,
                              String stockUpdate) {
        pd.show();

        db.collection("productlist").document(allProductListPojo.getId())
                .update("price", priceUpdate,
                        "pieces per cartoon", cartoonUpdate,
                        "stock", stockUpdate);
        Toast.makeText(ViewAllProductActivity.this, "product updated successfully", Toast.LENGTH_LONG).show();
        listAdapter.notifyItemChanged(position);
        pd.dismiss();
        bottomSheetDialog.dismiss();
        finish();
    }
}