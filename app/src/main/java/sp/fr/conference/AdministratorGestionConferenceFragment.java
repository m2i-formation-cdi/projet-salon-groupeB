package sp.fr.conference;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.model.Conference;
import sp.fr.conference.model.ConferenceArrayAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdministratorGestionConferenceFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference themeReference;
    private TextView affichageNomConference;
    private TextView affichageDescriptionConference;

    private Button buttonRefuser, buttonValider;

    private String key;
    private String name;
    private String description;
    private List<Conference> ConferenceList;
    private ConferenceArrayAdapter adapter;

    public AdministratorGestionConferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_administrator_gestion_conference, container, false);

        //Préparation de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();
        themeReference = firebaseDatabase.getReference().child("conference");

        //récupération de la key
        MainActivity activity = (MainActivity) getActivity();
        key = activity.getKey();
        name = activity.getName();
        description = activity.getDescription();

        //Préparation de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();
        themeReference = firebaseDatabase.getReference().child("conference");

        //récupération des composants
        affichageNomConference = view.findViewById(R.id.textViexName);
        affichageDescriptionConference = view.findViewById(R.id.textViexDescription);

        //récupération des boutons
        buttonRefuser = view.findViewById(R.id.buttonRefuser);
        buttonValider = view.findViewById(R.id.buttonValider);

        //Mise en place des écouteurs
        buttonRefuser.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "REFUSER", Toast.LENGTH_SHORT).show();
            }
        });

        buttonValider.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "ACCEPTER", Toast.LENGTH_SHORT).show();
            }
        });

        //Mise à jour des informations
        affichageNomConference.setText("Titre : " + name);
        affichageDescriptionConference.setText("Description : " + description);

        ConferenceList = new ArrayList<>();

        //Instanciation de la liste
        //adapter = new AdministratorListingConferenceStatutFragment.ConferenceArrayAdapter(this.getActivity(), R.layout.theme_list_items_conference_statut, ConferenceList);


        return view;
    }

}
