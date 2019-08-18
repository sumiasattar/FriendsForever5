package friendsforever.fyp.app.friendsforever.UserCallFragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import friendsforever.fyp.app.friendsforever.R;

public class FragmentContact extends Fragment {
    Button button;
    View v;
    int[] to;
    String[] from;
    ListView L1;
    public FragmentContact() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.contact_fragment,container,false);
        button=v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get(v);
            }
        });
        L1=v.findViewById(R.id.list);

        return v;
    }
  public void get(View v){

      Cursor cursor=getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
      getActivity().startManagingCursor(cursor);

       from= new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};

      to= new int[]{android.R.id.text1, android.R.id.text2};
      SimpleCursorAdapter simpleCursorAdapter= new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_2,cursor,from,to);
      L1.setAdapter(simpleCursorAdapter);
      L1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
      L1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Toast.makeText(getContext(), from[i], Toast.LENGTH_SHORT).show();
          }
      });
  }

}
