package friendsforever.fyp.app.friendsforever.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import friendsforever.fyp.app.friendsforever.R;

public class GoogleSignin extends AppCompatActivity {
    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mauth;
    Button btn_login, btn_logout;
    GoogleSignInClient mGoogleSignInClient;
    private Context context;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_signin);
        btn_login = findViewById(R.id.google);
        btn_logout = findViewById(R.id.logout);

        mauth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        btn_login.setOnClickListener(v -> SignInGoogle());
        btn_logout.setOnClickListener(v -> Logout());
        if (mauth.getCurrentUser()!=null){
            FirebaseUser user=mauth.getCurrentUser();
            updateUI(user);
        }
    }

    void SignInGoogle() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                if (account !=null)firebaseAuthWithGoogle(account);
            }catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG","firebaseAuthWithGoogle:"+account.getId());
        AuthCredential credential= GoogleAuthProvider
                .getCredential(account.getIdToken(),null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this,task ->{
                    if (task.isSuccessful()){
                        Log.d("TAG","signin success");
                        FirebaseUser user=mauth.getCurrentUser();

                        updateUI(user);

                    }else {
                        Log.w("TAG","sigin failure",task.getException());
                        Toast.makeText(this, "signin failed", Toast.LENGTH_SHORT).show();
                        updateUI(null );
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null){
            String name=user.getDisplayName();
            String email=user.getEmail();
            String photo=String.valueOf(user.getPhotoUrl());
//            text.append("info:\n");
//            text.append(name +"\n");
//            text.append(email);
//            Picasso.with(context).load(photo).into(image);
            btn_login.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
           startActivity(new Intent(GoogleSignin.this,BottomNavigationActivity.class));
        }else {
//            text.setText(getString(R.string.firebase_login));
//            Picasso.with(context).load(R.drawable.ic_firebase_logo).into(image);
            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
        }
    }
    void Logout(){
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,task -> updateUI(null));
    }
    }

