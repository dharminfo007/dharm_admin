package in.app.dharm.info.online.dharmadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.app.dharm.info.online.dharmadmin.R;
import in.app.dharm.info.online.dharmadmin.adapter.ProductImageAdapter;

public class AddNewSaleProductActivity extends AppCompatActivity implements View.OnClickListener {


    MaterialTextView imgToAdd, btnAddProductToDatabase;
    private static final int PERMISSION_TO_SELECT_IMAGE_FROM_GALLERY = 100;
    private static final int PICK_IMAGE_MULTIPLE = 200;
    String imageEncoded =  "";
    List<String> imagesEncodedList;
    RecyclerView rvProducts;
    ArrayList<Uri> mArrayUri;
    ArrayList<String> uploadImageUrls;
    private ProductImageAdapter listAdapter;
    FirebaseDatabase firebaseDatabase;

    // instance for firebase storage and StorageReference
    FirebaseFirestore db;
    public static String TAG = "AddNewSaleProductActivity";
    FirebaseStorage storage;
    private Uri filePath;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_sale_product);
        init();
    }


    public void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        imgToAdd = findViewById(R.id.imgToAdd);

        pd = new ProgressDialog(AddNewSaleProductActivity.this);
        pd.setMessage("loading...");

        btnAddProductToDatabase = findViewById(R.id.btnAddProductToDatabase);
        imgToAdd.setOnClickListener(this);
        btnAddProductToDatabase.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgToAdd:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_TO_SELECT_IMAGE_FROM_GALLERY);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                }
                break;


            case R.id.btnAddProductToDatabase:
//                 Add document data with auto-generated id.
                // Defining the child of storageReference

                    pd.show();
                    for (int i = 0; i < mArrayUri.size(); i++) {

                        StorageReference stRef = storage
                                .getReference(
                                        "image_sale_p"+"_"+i);

                        stRef.putFile(mArrayUri.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                stRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        uploadImageUrls.add(uri.toString());
                                        if (mArrayUri.size() == uploadImageUrls.size()) {
                                            Calendar c = Calendar.getInstance();
                                            System.out.println("Current time => " + c.getTime());

                                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                            String currentDate = df.format(c.getTime());
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("sale_img", uploadImageUrls);
                                            data.put("date", currentDate);

                                            db.collection("onsaleproduct").document( "1")
                                                    .set(data)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void documentReference) {
                                                            pd.dismiss();
                                                            Toast.makeText(AddNewSaleProductActivity.this, "Sale product added successfully...", Toast.LENGTH_LONG).show();
                                                            uploadImageUrls = new ArrayList<>();
                                                            mArrayUri = new ArrayList<>();
                                                            listAdapter = new ProductImageAdapter(mArrayUri, AddNewSaleProductActivity.this);
                                                            rvProducts.setAdapter(listAdapter);

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error adding sale product", e);
                                                        }
                                                    });
                                        }

                                    }
                                });
                            }

                        }).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Log.w("Cancel", "Error adding image");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NotNull Exception e) {
                                Log.w(TAG, "Error adding image" + e.getMessage() + "");
                            }
                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                Log.w("OnComplete", "adding image");
//                            uploadImageUrls.add(task.getResult().getStorage().getPath());
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
//                    progressDialog.dismiss();
                            }
                        });

                    }
                    Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();



                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();

                if (data.getData() != null) {         //on Single image selected

                    Uri mImageUri = data.getData();
                    filePath = mImageUri;
                    mArrayUri.add(filePath);
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    imagesEncodedList.add(imageEncoded);
                    cursor.close();

                } else {                              //on multiple image selected
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
//                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            filePath = item.getUri();
                            mArrayUri.add(filePath);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(filePath, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();


                        }
                        Log.v("MainActivity", "Selected Images" + mArrayUri.size());
                        listAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong"+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}