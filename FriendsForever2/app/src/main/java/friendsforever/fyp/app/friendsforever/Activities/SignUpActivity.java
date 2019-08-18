package friendsforever.fyp.app.friendsforever.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wang.avi.AVLoadingIndicatorView;

import java.sql.Ref;
import java.util.HashMap;

import friendsforever.fyp.app.friendsforever.Model.ImageUploadModel;
import friendsforever.fyp.app.friendsforever.Model.Users;
import friendsforever.fyp.app.friendsforever.R;

public class SignUpActivity extends AppCompatActivity {
    private AVLoadingIndicatorView loader;
    EditText userName, Password, PhoneNo, Address, Email;
    String userNameStr, PasswordStr, PhoneNoStr, AddressStr, EmailStr;
    Button btnSignUp,chooseImage;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    StorageReference mProfilePic;

    ImageView imgUserPhoto;
    private  static  final int RC_PHOTO_PICKER=1;
    StorageReference UserprofilePicRef;
    private Uri selectedProfileImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        UserprofilePicRef = FirebaseStorage.getInstance().getReference("UserProfilePics");
        reference=FirebaseDatabase.getInstance().getReference("User");
        loader = findViewById(R.id.userLoader);
        userName=findViewById(R.id.username);
        Email=findViewById(R.id.useremail);
        Password=findViewById(R.id.userpwd);
        PhoneNo=findViewById(R.id.userphone);
        Address=findViewById(R.id.useradress);
        imgUserPhoto=findViewById(R.id.Profileimgvw);





        chooseImage=findViewById(R.id.photo);
        btnSignUp=findViewById(R.id.btn_signup);


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"complete action"),2);
            }
        });



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userNameStr=userName.getText().toString();
                EmailStr=Email.getText().toString();
                PasswordStr=Password.getText().toString();
                PhoneNoStr=PhoneNo.getText().toString();
                AddressStr=Address.getText().toString();

                if (userNameStr.isEmpty()){
                    userName.setError("please enter the place name");
                }else if (EmailStr.isEmpty()){
                    Email.setError("please enter Email");
                }else if (PasswordStr.isEmpty()){
                    Password.setError("please enter Password");
                }else if (PhoneNoStr.isEmpty()){
                    PhoneNo.setError("Please rnter phoneNo");

                }else if (AddressStr.isEmpty()) {
                    Address.setError("please enter address");
                }else {


                    addFileAndDataToDatabase();


                }

            }
        });


    }



    private void addFileAndDataToDatabase() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference mPicRef=UserprofilePicRef.child(selectedProfileImageUri.getLastPathSegment());

        mPicRef.putFile(selectedProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String id = reference.push().getKey();
                        String name = userName.getText().toString();
                        String placUri = uri.toString();
                        String pass=Password.getText().toString();
                        String phone=PhoneNo.getText().toString();
                        String address=Address.getText().toString();
                        String email=Email.getText().toString();
                        Users user = new Users(id,name,placUri,pass,phone,address,email);

                        reference.child(id).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUpActivity.this, "data stored successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null)
            selectedProfileImageUri = data.getData();

        imgUserPhoto.setImageURI(selectedProfileImageUri);
    }
}

