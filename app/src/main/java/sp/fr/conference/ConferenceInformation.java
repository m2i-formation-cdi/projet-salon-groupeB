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


/**
 * A simple {@link Fragment} subclass.
 */
public class ConferenceInformation extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;
    private DatabaseReference speakerReference;
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
    private List<Conference> conferencesList = new ArrayList<>();
  //  private ConferenceAdapter adapter;
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
                //for (DataSnapshot conferenceSnapshot : dataSnapshot.getChildren()) {

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

            //}


                // se lance une fois que le createView a fini
               // adapter.notifyDataSetChanged();

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Création de l'Adapter pour récupérer les données vers l'Adapter
        //adapter = new ConferenceAdapter(this.getActivity(), R.layout.fragment_conference, conferencesList);
        //titleConference.setAdapter(adapter);

        return view;
    }


/*
    // Envoi des données de l'Adapter vers la vue
    private class ConferenceAdapter extends ArrayAdapter<Conference> {

        private Activity context;
        private int resource;
        private List<Conference> data;

        public ConferenceAdapter(@NonNull Activity context, int resource, @NonNull List<Conference> objects) {
            super(context, resource, objects);

            this.context = context;
            this.resource = resource;
            this.data = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = this.context.getLayoutInflater().inflate(R.layout.theme_list_item, parent, false);

            Conference item = this.data.get(position);
            TextView themeTextView = view.findViewById(R.id.themeSpinnerTextView);
            themeTextView.setText(item.getName());
            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = this.context.getLayoutInflater().inflate(R.layout.theme_list_item, parent, false);

            ThemesConference item = this.data.get(position);
            themeTextView = view.findViewById(R.id.themeSpinnerTextView);
            themeTextView.setText(item.getName());
            return view;
        }
*/



}
