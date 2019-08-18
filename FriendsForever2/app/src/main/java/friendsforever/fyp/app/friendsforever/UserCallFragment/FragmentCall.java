package friendsforever.fyp.app.friendsforever.UserCallFragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import friendsforever.fyp.app.friendsforever.R;

public class FragmentCall extends Fragment {
    private static final int REQUEST_CALL=1;
    private EditText mEditTextNumber;
    View v;

    public FragmentCall() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.call_fragment,container,false);
        mEditTextNumber=v.findViewById(R.id.edit_text_number);
        ImageView imagecall=v.findViewById(R.id.calling);

        imagecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhonecall();

            }
        });
        return v;
    }
    private void makePhonecall(){
        String number=mEditTextNumber.getText().toString();
        if (number.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }else {
                String dial= "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }else{
            Toast.makeText(getContext(), "enter phone num", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults [0]== PackageManager.PERMISSION_GRANTED) {
                makePhonecall();

            }else {
                Toast.makeText(getContext(), "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
