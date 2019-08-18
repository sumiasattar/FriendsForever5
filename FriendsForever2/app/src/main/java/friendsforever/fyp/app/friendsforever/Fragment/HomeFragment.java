package friendsforever.fyp.app.friendsforever.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import friendsforever.fyp.app.friendsforever.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ViewFlipper viewFlipper;
View view;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        int images[]={R.drawable.friendfoue,R.drawable.friendsone,
                R.drawable.friendtwo,R.drawable.friendthree,R.drawable.friendfive};
// View Flliper
        viewFlipper=view.findViewById (R.id.view_flipper);
        for (int image:images){
            flliperimages(image);
        }
        return view;
    }
    public void flliperimages(int image){
        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        //animation
        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);



    }

    }


