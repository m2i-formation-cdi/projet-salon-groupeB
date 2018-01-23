package sp.fr.conference;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
public class PropConference extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;
    private DatabaseReference speakerReference;
    private DatabaseReference themeReference;
    private List<Conference> conferenceList = new ArrayList<>();
    private Spinner conferenceSpinner;
    EditText conferenceTitleEditText;
    EditText descriptionConferenceEditText;
    EditText speakerNameEditText;
    EditText speakerFirstNameEditText;
    String titleConference;
    String descriptionConference;
    String speakerName;
    String speakerFirstName;
    List<ThemesConference> themesList = new ArrayList<>();

    public PropConference() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        conferenceReference = firebaseDatabase.getReference().child("conference");
        speakerReference = firebaseDatabase.getReference().child("conference").child("speaker");
        themeReference = firebaseDatabase.getReference().child("theme");



        //Récupération des différents thèmes
        themeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot themeSnapshot : dataSnapshot.getChildren()) {
                    String themeKey = themeSnapshot.getKey();
                    ThemesConference theme = themeSnapshot.getValue(ThemesConference.class);
                    themesList.add(theme);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prop_conference, container, false);

        // récupération des valeurs du formulaire
        conferenceTitleEditText = view.findViewById(R.id.titleConferenceProposition);
        descriptionConferenceEditText = view.findViewById(R.id.descriptionConference);
        speakerNameEditText = view.findViewById(R.id.speakerName);
        speakerFirstNameEditText = view.findViewById(R.id.speakerFirstName);
        conferenceSpinner = view.findViewById(R.id.spinnerTheme);

        ThemeAdapter adapter = new ThemeAdapter(this.getActivity(), R.layout.theme_list_item, themesList);
        conferenceSpinner.setAdapter(adapter);

        //Gestion du clic sur le bouton valider
        Button btValid = view.findViewById(R.id.validateConference);
        btValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // récupération du titre de la conférence dans le formulaire
                titleConference = conferenceTitleEditText.getText().toString();
                descriptionConference = descriptionConferenceEditText.getText().toString();
                speakerName = speakerNameEditText.getText().toString();
                speakerFirstName = speakerFirstNameEditText.getText().toString();


                Conference conference = new Conference();
                User speaker = new User();
                // ajout des valeurs à la conférence
                conference.setDescription(descriptionConference);
                conference.setTitle(titleConference);
                speaker.setName(speakerName);
                speaker.setFirstName(speakerFirstName);
                conference.setUser(speaker);
                // push dans la database
                String conferenceId = conferenceReference.push().getKey();
                //String speakerId = speakerReference.push().getKey();
                conferenceReference.child(conferenceId).setValue(conference);
                //speakerReference.child(conferenceId).child(speakerId).setValue(speaker);
            }
        });

        return view;
    }

    private class ThemeAdapter extends ArrayAdapter<ThemesConference>{

        private Activity context;
        private int resource;
        private List<ThemesConference> data;

        public ThemeAdapter(@NonNull Activity context, int resource, @NonNull List<ThemesConference> objects) {
            super(context, resource, objects);

            this.context = context;
            this.resource = resource;
            this.data = objects;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = this.context.getLayoutInflater().inflate(this.resource, parent, false);

            ThemesConference item = this.data.get(position);
            TextView themeTextView = view.findViewById(R.id.themeSpinnerTextView);
            themeTextView.setText(item.getName());
            return view;
        }
    }


}
