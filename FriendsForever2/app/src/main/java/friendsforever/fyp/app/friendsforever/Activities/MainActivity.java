package friendsforever.fyp.app.friendsforever.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

import friendsforever.fyp.app.friendsforever.R;

public class
MainActivity extends AppCompatActivity {
    private AVLoadingIndicatorView loader;
    Button mLoginBtn;
    String EmailStr,PasswordStr;
    TextView mSignUptxt;
    FirebaseAuth auth;
    private ProgressBar progressBar;
    EditText mEmailEditText,mPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        loader=findViewById(R.id.loader);
        mSignUptxt=findViewById(R.id.signuphere);
        mLoginBtn = findViewById(R.id.Login);
        mEmailEditText = findViewById(R.id.Eail_edittext);
        mPasswordEditText = findViewById(R.id.Passwordedittext);

        mSignUptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EmailStr = mEmailEditText.getText().toString();
                PasswordStr = mPasswordEditText.getText().toString();
                if (TextUtils.isEmpty(EmailStr) || TextUtils.isEmpty(PasswordStr)){
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    auth.signInWithEmailAndPassword(EmailStr,PasswordStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    loader.setVisibility(View.GONE);
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(MainActivity.this,BottomNavigationActivity.class);
                                       startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
