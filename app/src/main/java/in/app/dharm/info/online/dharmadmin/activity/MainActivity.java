package in.app.dharm.info.online.dharmadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import in.app.dharm.info.online.dharmadmin.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rlAllOrder, rlAllUsers, rlAllDeal, rlAllFeedback, rlAllSaleProduct,
            rlAddNewProduct, rlAddVideo, rlAddBanners, rlAllProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initi();
    }

    private void initi() {
        rlAllDeal = findViewById(R.id.rlAllDeal);
        rlAllUsers = findViewById(R.id.rlAllUser);
        rlAllFeedback = findViewById(R.id.rlAllFeedback);
        rlAllOrder = findViewById(R.id.rlAllOrder);
        rlAllSaleProduct = findViewById(R.id.rlAllSaleProduct);
        rlAddNewProduct = findViewById(R.id.rlAddNewProduct);
        rlAddVideo = findViewById(R.id.rlAddVideo);
        rlAddBanners = findViewById(R.id.rlAddBanners);
        rlAllProducts = findViewById(R.id.rlAllProducts);

        onClickListener();
    }

    private void onClickListener() {
        rlAllOrder.setOnClickListener(this);
        rlAllFeedback.setOnClickListener(this);
        rlAllUsers.setOnClickListener(this);
        rlAllDeal.setOnClickListener(this);
        rlAllSaleProduct.setOnClickListener(this);
        rlAddNewProduct.setOnClickListener(this);
        rlAddVideo.setOnClickListener(this);
        rlAddBanners.setOnClickListener(this);
        rlAllProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlAllOrder:
                startActivity(new Intent(MainActivity.this, ShowAllOrdersActivity.class));
                break;

            case R.id.rlAllUser:
                startActivity(new Intent(MainActivity.this, ShowAllUsersActivity.class));
                break;

            case R.id.rlAllDeal:
                startActivity(new Intent(MainActivity.this, ShowAllRequestedDealsActivity.class));
                break;

            case R.id.rlAllFeedback:
                startActivity(new Intent(MainActivity.this, ShowAllFeedbackActivity.class));
                break;

            case R.id.rlAllSaleProduct:
                startActivity(new Intent(MainActivity.this, AddNewSaleProductActivity.class));
                break;

            case R.id.rlAddNewProduct:
                startActivity(new Intent(MainActivity.this, AddNewProductActivity.class));
                break;

            case R.id.rlAddVideo:
                startActivity(new Intent(MainActivity.this, AddNewVideoActivity.class));
                break;

            case R.id.rlAddBanners:
                startActivity(new Intent(MainActivity.this, AddNewBannerActivity.class));
                break;

            case R.id.rlAllProducts:
                startActivity(new Intent(MainActivity.this, ViewAllProductActivity.class));
                break;

            default:
                break;

        }
    }
}