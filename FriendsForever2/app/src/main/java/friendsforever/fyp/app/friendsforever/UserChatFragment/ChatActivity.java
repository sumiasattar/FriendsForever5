package friendsforever.fyp.app.friendsforever.UserChatFragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import friendsforever.fyp.app.friendsforever.R;
import friendsforever.fyp.app.friendsforever.UserCallFragment.ViewPagerAdapter;

public class ChatActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tabLayout=(TabLayout) findViewById(R.id.tablayout_id);
        viewPager=(ViewPager)findViewById(R.id.viewpager_id);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentChat(),"online");
        adapter.AddFragment(new FragmentUser(),"offline");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.message);
        tabLayout.getTabAt(1).setIcon(R.drawable.user);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setElevation(0);
    }
}
