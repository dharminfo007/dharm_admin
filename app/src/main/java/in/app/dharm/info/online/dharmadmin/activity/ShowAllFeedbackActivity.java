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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.adapter.FeedbackAdapter;
import in.app.dharm.info.online.dharmadmin.model.FeedBackListPojo;
import in.app.dharm.info.online.dharmadmin.util.DataProcessor;

public class ShowAllFeedbackActivity extends AppCompatActivity {

    RecyclerView rvFeedBack;
    private FeedbackAdapter feedbackAdapter;
    ArrayList<FeedBackListPojo> productArrayList;
    FirebaseFirestore db;
    public String TAG = "ShowAllFeedbackActivity";
    ProgressDialog pd;
    MaterialTextView txtNoFeedBackFound;
    ArrayList<String> images = new ArrayList<>();
    DataProcessor dataProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_feedback);
        pd = new ProgressDialog(ShowAllFeedbackActivity.this);
        pd.setMessage("loading...");

        dataProcessor = new DataProcessor(this);

        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        images = new ArrayList<>();
        rvFeedBack = (RecyclerView) findViewById(R.id.rvFeedback);
        txtNoFeedBackFound = findViewById(R.id.txtNoFeedback);
        rvFeedBack.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvFeedBack.setLayoutManager(layoutManager);
        feedbackAdapter = new FeedbackAdapter(productArrayList, this);
        rvFeedBack.setAdapter(feedbackAdapter);

        
        initProductDataAvailability();
    }

    private void initProductDataAvailability() {
        pd.show();
        db.collection("feedbacklist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            productArrayList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                FeedBackListPojo orderListPojo = new FeedBackListPojo(
                                        document.getString("feedback"),
                                    document.getString("feedback_add_date"), 
                                        document.getString("user"));

                                productArrayList.add(orderListPojo);
                            }
                            Log.d(TAG, " => " + productArrayList.size());
                            if (productArrayList.size() > 0) {
                                txtNoFeedBackFound.setVisibility(View.GONE);
                                rvFeedBack.setVisibility(View.VISIBLE);
                                feedbackAdapter = new FeedbackAdapter(productArrayList, ShowAllFeedbackActivity.this);
                                rvFeedBack.setAdapter(feedbackAdapter);
                                feedbackAdapter.notifyDataSetChanged();
                            } else {
                                txtNoFeedBackFound.setVisibility(View.VISIBLE);
                                rvFeedBack.setVisibility(View.GONE);
                                feedbackAdapter.notifyDataSetChanged();
                            }

                        } else {
                            pd.dismiss();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void removeAt(int position, String document) {
        db.collection("deallist").document(document)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                productArrayList.remove(position);
                feedbackAdapter.notifyItemRemoved(position);
                feedbackAdapter.notifyItemRangeChanged(position, productArrayList.size());
                checkDealList();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void updateDealStatus(int position, String document){

        db.collection("deallist").document(document).update("status", "accepted")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        initProductDataAvailability();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void checkDealList() {
        if (productArrayList.size() > 0) {
            txtNoFeedBackFound.setVisibility(View.GONE);
            rvFeedBack.setVisibility(View.VISIBLE);
            feedbackAdapter = new FeedbackAdapter(productArrayList, ShowAllFeedbackActivity.this);
            rvFeedBack.setAdapter(feedbackAdapter);
            feedbackAdapter.notifyDataSetChanged();
        } else {
            txtNoFeedBackFound.setVisibility(View.VISIBLE);
            rvFeedBack.setVisibility(View.GONE);
            feedbackAdapter.notifyDataSetChanged();
        }
    }
}