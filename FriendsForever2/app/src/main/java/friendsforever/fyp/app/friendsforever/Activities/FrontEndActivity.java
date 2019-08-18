package friendsforever.fyp.app.friendsforever.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import friendsforever.fyp.app.friendsforever.R;

public class FrontEndActivity extends AppCompatActivity {
    Button mLogin_go, mSignUp_go;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private static final String TAG = "FACELOG";
private ImageView imageView ,imageGoogle;
//Google sigin
static final int GOOGLE_SIGN = 123;
    FirebaseAuth mauth;
    Button  btn_logout;
    GoogleSignInClient mGoogleSignInClient;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_end);
imageGoogle=findViewById(R.id.google);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        imageGoogle.setOnClickListener(v -> SignInGoogle());
      //  btn_logout.setOnClickListener(v -> Logout());
//        if (mauth.getCurrentUser()!=null){
//            FirebaseUser user=mauth.getCurrentUser();
//            updateUI(user);
//        }

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        imageView  = findViewById(R.id.login_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setEnabled(false);
                LoginManager.getInstance().logInWithReadPermissions(FrontEndActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
            }
        });
        mSignUp_go = findViewById(R.id.signup_go);
        mSignUp_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontEndActivity.this, SignUpActivity.class));
                Toast.makeText(FrontEndActivity.this, "SignUp pressed", Toast.LENGTH_SHORT).show();

            }
        });
        mLogin_go = findViewById(R.id.log_go);
        mLogin_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontEndActivity.this, MainActivity.class));
                Toast.makeText(FrontEndActivity.this, "Login Clicked", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,task -> updateUI(null));
    }

    private void SignInGoogle() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
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
            imageGoogle.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
            startActivity(new Intent(FrontEndActivity.this,BottomNavigationActivity.class));
        }else {
//            text.setText(getString(R.string.firebase_login));
//            Picasso.with(context).load(R.drawable.ic_firebase_logo).into(image);
            imageGoogle.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }

    }

    private void updateUI() {
        Toast.makeText(FrontEndActivity.this, "you are log in", Toast.LENGTH_SHORT).show();
        Intent accountIntent = new Intent(FrontEndActivity.this, BottomNavigationActivity.class);
        startActivity(accountIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// gogle login
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
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            imageView.setEnabled(true);
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FrontEndActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            imageView.setEnabled(true);
                            updateUI();
                        }

                        // ...
                    }
                });
    }
}
