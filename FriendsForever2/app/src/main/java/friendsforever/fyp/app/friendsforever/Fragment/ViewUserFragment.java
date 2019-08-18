package friendsforever.fyp.app.friendsforever.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import friendsforever.fyp.app.friendsforever.Model.Users;
import friendsforever.fyp.app.friendsforever.R;



public class ViewUserFragment extends Fragment {
    private ViewFlipper simpleViewFlipper;
    private ArrayList<String> mCategories = new ArrayList<>();

    int countInt, incrementalCount;
    DatabaseReference CustomerReference;
    //    CustomerProfileAdapter mProductAdapter;
    RecyclerView mCustomerRecycVw;
    String count;

    public ViewUserFragment (){
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_products, container, false);

        getActivity().setTitle("Users");
        mCustomerRecycVw = view.findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCustomerRecycVw.setLayoutManager(mLayoutManager);

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        CustomerReference = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(CustomerReference, Users.class)
                .build();

        final FirebaseRecyclerAdapter<Users, CustomersViewHolder> adapter = new FirebaseRecyclerAdapter<Users, ViewUserFragment.CustomersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CustomersViewHolder holder, int position, @NonNull Users model) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.mName.setText(model.getUsername());
                holder.mphone.setText(model.getPhone());
                holder.madress.setText(model.getAdress());
            }



            @NonNull
            @Override
            public ViewUserFragment.CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_dummy_item_layout, viewGroup, false);
                ViewUserFragment.CustomersViewHolder customersViewHolder = new CustomersViewHolder(view);
                return customersViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class CustomersViewHolder extends RecyclerView.ViewHolder {


        ImageView postImage;
        TextView mName, mphone, madress;


        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.UserNameTV);
            mphone = itemView.findViewById(R.id.UserPhoneTV);
            madress = itemView.findViewById(R.id.adress);

        }
    }
}
