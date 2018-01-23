package sp.fr.conference;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.telecom.Conference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MesConferencesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;
    private List<Conference> userConferenceList = new ArrayList<>();
    private ListView userConferenceListView;
    private ArrayAdapter UserConferenceArrayAdapter;
    private UserConferenceArrayAdapter adapter;


    public MesConferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_mes_conferences, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        conferenceReference = firebaseDatabase.getReference().child("conference");

        userConferenceListView = view.findViewById(R.id.userConferenceListViewItem);

        adapter = new UserConferenceArrayAdapter(this.getActivity(),R.layout.mes_conferences_list_view);
        userConferenceListView.setAdapter(adapter);


        conferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userConferenceList.clear();

                for (DataSnapshot userConferenceSnapshot : dataSnapshot.getChildren()){

                    Conference userConference = userConferenceSnapshot.getValue(Conference.class);
                    userConferenceList.add(userConference);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

    }

    private class UserConferenceArrayAdapter extends ArrayAdapter<Conference>{

        private Fragment context;
        int resource;
        List<Conference> data;

        public UserConferenceArrayAdapter(@NonNull Context context, int resource) {
            super(getActivity(),R.layout.mes_conferences_list_view,userConferenceList);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Conference selectedConference = userConferenceList.get(position);

        View view = getActivity().getLayoutInflater().inflate(R.layout.mes_conferences_list_view, parent, false);

        TextView textView = view.findViewById(R.id.textViewMesConferences);
        textView.setText(selectedConference.getTitle());

        ImageView changeButton = view.findViewById(R.id.changeButton);

        ImageView deleteButton = view.findViewById(R.id.deleteButton);

        changeButton.setTag(position);
        deleteButton.setTag(position);

        return view;

    }
}
