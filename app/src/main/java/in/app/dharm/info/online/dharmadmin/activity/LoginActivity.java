package in.app.dharm.info.online.dharmadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import in.app.dharm.info.online.dharmadmin.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSkip;
    MaterialTextView tvLogin;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin:
//                dataProcessor.setBool("isSkip", true);

                if (currentUser == null) {                                       //check if the user is new then signIn anonymously
                    mAuth.signInAnonymously().                                 //.signInAnonymously is a method provided by Firebase
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {        //insert a Listener that listen
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)          // insert a method that will executes when the process is completed
                        {
                            if (task.isSuccessful())                    // check the required task is completed successfully
                            {
                                startActivity(new Intent(LoginActivity.this, AddNewProductActivity.class));
                                finishAffinity();      //inVisible the progressBar
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {         //if the signin failed
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TAG", e.getMessage());            //return error in logs
                                }
                            });
                } else                                            //check if the user is not new
                {
                    startActivity(new Intent(LoginActivity.this, AddNewProductActivity.class));
                    finishAffinity();
                    Toast.makeText(LoginActivity.this, "you are already login anonymously!!!", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }
}