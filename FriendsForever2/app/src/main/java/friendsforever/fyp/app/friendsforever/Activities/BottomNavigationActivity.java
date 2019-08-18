package friendsforever.fyp.app.friendsforever.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import friendsforever.fyp.app.friendsforever.Fragment.EditProfileFragment;
import friendsforever.fyp.app.friendsforever.Fragment.HomeFragment;
import friendsforever.fyp.app.friendsforever.R;
import friendsforever.fyp.app.friendsforever.UserCallFragment.CallActivity;
import friendsforever.fyp.app.friendsforever.UserChatFragment.ChatActivity;

public class BottomNavigationActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   startActivity(new Intent(BottomNavigationActivity.this,MapActivity.class));
                    return true;
                case R.id.navigation_dashboard:
                   startActivity(new Intent(BottomNavigationActivity.this, CallActivity.class));
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(BottomNavigationActivity.this, ChatActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.chat_lgo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_bottom_navigation);


        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Map");
        HomeFragment fragment4 = new HomeFragment();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.fram, fragment4, "HomeFragment");
        fragmentTransaction1.commit();

    }

    //shut down
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_home, menu);
        getMenuInflater().inflate(R.menu.actionbaricon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            startActivity(new Intent(BottomNavigationActivity.this, MainActivity.class));


        }else if (id == R.id.action_profile) {
            setTitle("Profile");
            EditProfileFragment profileFragment=new EditProfileFragment();
            FragmentTransaction fragmentProfile = getSupportFragmentManager().beginTransaction();
            fragmentProfile.replace(R.id.fram, profileFragment,"Profile");
            fragmentProfile.commit();

        }else if (id == R.id.action_home) {
            setTitle("HoePage");
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransactionhome = getSupportFragmentManager().beginTransaction();
            fragmentTransactionhome.replace(R.id.fram, homeFragment, "HomeFragent");
            fragmentTransactionhome.commit();
            // Intent intent = new Intent(this, MainActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close FriendForever App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
