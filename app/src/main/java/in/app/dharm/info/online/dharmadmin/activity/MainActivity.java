package in.app.dharm.info.online.dharmadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import in.app.dharm.info.online.dharmadmin.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rlAllOrder, rlAllUsers, rlAllDeal, rlAllFeedback;

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

        onClickListener();
    }

    private void onClickListener() {
        rlAllOrder.setOnClickListener(this);
        rlAllFeedback.setOnClickListener(this);
        rlAllUsers.setOnClickListener(this);
        rlAllDeal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

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

            default:
                break;

        }
    }
}