package sp.fr.conference;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.model.Conference;
import sp.fr.conference.model.ThemesConference;
import sp.fr.conference.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConferenceInformation extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;
    private DatabaseReference speakerReference;
    private DatabaseReference userReference;
    private DatabaseReference themeReference;
    private TextView titleConference;
    private TextView themeConference;
    private TextView speakerName;
    private TextView speakerFirstName;
    private TextView dateConference;
    private TextView startHour;
    private TextView endHour;
    private TextView adress;
    private TextView description;
    private List<User> userList = new ArrayList<>();
    private ListView userListView;
    private UserArrayAdapter adapter;
    private TextView themeTextView;


    public ConferenceInformation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("CREATION  DU FRAGMENT", "----------------------- coucou ----------------------");

        String key = "0";

        // Instanciation de la base et des référencces aux conférences
        firebaseDatabase = FirebaseDatabase.getInstance();
        conferenceReference = firebaseDatabase.getReference().child("conference").child(key);

        //key = -L3cEldAnCKVTvP8SSUt

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conference, container, false);

        titleConference = view.findViewById(R.id.titleConferenceTextView);
        themeConference = view.findViewById(R.id.themeConferenceTextView);
        speakerName = view.findViewById(R.id.nameTextView);
        speakerFirstName = view.findViewById(R.id.firstNameTextView);
        dateConference = view.findViewById(R.id.dateTextView);
        startHour = view.findViewById(R.id.startTextView);
        endHour = view.findViewById(R.id.endTextView);
        adress = view.findViewById(R.id.adressTextView);
        description = view.findViewById(R.id.descriptionTextView);


        //Récupération valeurs
        conferenceReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String confKey = dataSnapshot.getKey();
                Log.i("KEY", dataSnapshot.getKey());

                Conference conference = dataSnapshot.getValue(Conference.class);
                titleConference.setText(conference.getTitle());
                themeConference.setText(conference.getTheme().getName());
                speakerName.setText(conference.getSpeaker().getName());
                speakerFirstName.setText(conference.getSpeaker().getFirstName());
                dateConference.setText(conference.getDay());
                startHour.setText(conference.getStartHour());
                endHour.setText(conference.getEndHour());
                adress.setText(conference.getLocation());
                description.setText(conference.getDescription());

                Log.i("TITLE", conference.getTitle());
                // Log.i("THEME", conference.getTheme().getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userReference = firebaseDatabase.getReference().child("conference").child(key).child("attendants");
        userListView = view.findViewById(R.id.userListView);

        // Récupération valeurs
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                 String confKey = userSnapshot.getKey();
                 Log.i("KEY ATTENDANT", userSnapshot.getKey());
                 User attendant = userSnapshot.getValue(User.class);
                 Log.i("NAME ATTENDANT", attendant.getName());
                 userList.add(attendant);
            }

            adapter.notifyDataSetChanged();
        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Création de l'Adapter pour récupérer les données vers l'Adapter
        adapter = new UserArrayAdapter(this.getActivity(), R.layout.user_list_item, userList);
        userListView.setAdapter(adapter);

        return view;
    }


    // Envoi des données de l'Adapter vers la vue
    private class UserArrayAdapter extends ArrayAdapter<User> {

        private Activity context;
        private int resource;
        private List<User> data;

        public UserArrayAdapter(@NonNull Activity context, int resource, @NonNull List<User> objects) {
            super(context, resource, objects);
/*
            Log.i("ARRAY", "----------------------t'es dans l'array---------de mon cul-------------");

            this.context = context;
            this.resource = resource;
            this.data = objects;*/
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.user_list_item, parent, false);

            User item = userList.get(position);
            TextView userTextView = view.findViewById(R.id.userListItem);
            userTextView.setText(item.getName());
            Log.i("USER NAME", item.getName());
            return view;
        }
    }
}
