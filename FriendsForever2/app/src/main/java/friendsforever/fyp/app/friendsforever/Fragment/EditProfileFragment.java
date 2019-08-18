package friendsforever.fyp.app.friendsforever.Fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import friendsforever.fyp.app.friendsforever.Model.Users;
import friendsforever.fyp.app.friendsforever.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
  ListView li;
  ArrayAdapter<String>adapter;


    CircleImageView mProfilePic;
     String[]default_item=new String[] {"Username",  "UserEmail","UserPwd", "UserPhone","UserAdress","UserImgUrl"};
    Button mEditProfile;
    EditText mnameET, mEmailET,mPasswordTV, mPhoneTV,mAdressTV;
    FirebaseUser user;
    UserInfo info;
    DatabaseReference databaseReference;
List<String>itemList;
String uid;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user_name=dataSnapshot.child(uid).child("name").getValue(String.class);
                String user_email=dataSnapshot.child(uid).child("email").getValue(String.class);
                String user_password=dataSnapshot.child(uid).child("password").getValue(String.class);
                String user_phone=dataSnapshot.child(uid).child("phone").getValue(String.class);
                String user_adress=dataSnapshot.child(uid).child("adress").getValue(String.class);
                String user_image=dataSnapshot.child(uid).child("ImageURL").getValue(String.class);
                mnameET.setText(user_name);
                mEmailET.setText(user_email);
                mPasswordTV.setText(user_password);
                mPhoneTV.setText(user_phone);
                mAdressTV.setText(user_adress);
                Glide.with(getContext())
                        .load( user_image)
                        .into(mProfilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "network error please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
