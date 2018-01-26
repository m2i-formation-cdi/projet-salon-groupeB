package sp.fr.conference;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private EditText conferenceTitleEditText;
    private EditText descriptionConferenceEditText;
    private EditText speakerNameEditText;
    private EditText speakerFirstNameEditText;
    private String titleConference;
    private String descriptionConference;
    private String speakerName;
    private String speakerFirstName;
    private String themeReferenceText;
    private String themeId;
    private List<ThemesConference> themesList = new ArrayList<>();
    private ThemeAdapter adapter;
    private TextView themeTextView;

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

                // se lance une fois que le createView a fini
                adapter.notifyDataSetChanged();
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


        // Création de l'Adapter pour récupérer les données de la liste (themesList) vers l'Adapter
        adapter = new ThemeAdapter(this.getActivity(), R.layout.theme_list_item, themesList);
        adapter.setDropDownViewResource(R.layout.theme_list_item);
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
                themeReferenceText = ((ThemesConference) conferenceSpinner.getSelectedItem()).getName();

                Conference conference = new Conference();
                User speaker = new User();
                ThemesConference themeConf = new ThemesConference();

                // ajout des valeurs à la conférence
                conference.setDescription(descriptionConference);
                conference.setTitle(titleConference);
                speaker.setName(speakerName);
                speaker.setFirstName(speakerFirstName);
                conference.setSpeaker(speaker);
                themeConf.setName(themeReferenceText);
                //themeConf.setName((String) themeTextView.getText());
                //themeConf.setId();
                conference.setTheme(themeConf);
                // push dans la database
                String conferenceId = conferenceReference.push().getKey();
                //String speakerId = speakerReference.push().getKey();
                conferenceReference.child(conferenceId).setValue(conference);
                //speakerReference.child(conferenceId).child(speakerId).setValue(speaker);

                cleanForm();

                navigateToFragment(new ConferenceInformation());
            }
        });




        return view;
    }

    // Envoi des données de l'Adapter vers la vue
    private class ThemeAdapter extends ArrayAdapter<ThemesConference>{

        private Activity context;
        private int resource;
        private List<ThemesConference> data;

        public ThemeAdapter(@NonNull Activity context, int resource,@NonNull List<ThemesConference> objects) {
            super(context, resource, R.id.themeSpinnerTextView, objects);

            this.context = context;
           this.resource = resource;
            this.data = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = this.context.getLayoutInflater().inflate(R.layout.theme_list_item, parent, false);

            ThemesConference item = this.data.get(position);
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
    }

    public void cleanForm(){
        conferenceTitleEditText.setText("");
        descriptionConferenceEditText.setText("");
        speakerNameEditText.setText("");
        speakerFirstNameEditText.setText("");
    }

    private void navigateToFragment(Fragment targetFragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }

}
