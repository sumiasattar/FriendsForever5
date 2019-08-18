package friendsforever.fyp.app.friendsforever.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import friendsforever.fyp.app.friendsforever.R;

public class SplashScreenActivity extends AppCompatActivity {
    Thread splashTread;
    ImageView imageView;
    TextView mFriendforevertxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mFriendforevertxt=findViewById(R.id.txt);
        // Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        // mFriendforevertxt.setAnimation(animation);
      /*   Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
startActivity(new Intent(MainActivity.this,LogInActivity.class));
finish();
            }
        } ,5000);*/



        imageView = (ImageView)findViewById(R.id.imageView);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.animation);
        imageView.setAnimation(anim);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int[] ids = new int[]{R.drawable.lgo};
        //,R.drawable.
        // , R.drawable.s_image_black2};
        Random randomGenerator = new Random();
        int r= randomGenerator.nextInt(ids.length);
        this.imageView.setImageDrawable(getResources().getDrawable(ids[r]));

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreenActivity.this, FrontEndActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                   SplashScreenActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                  SplashScreenActivity.this.finish();
                }

            }
        };
        splashTread.start();
    }
}
