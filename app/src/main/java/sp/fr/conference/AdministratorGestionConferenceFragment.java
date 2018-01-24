package sp.fr.conference;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdministratorGestionConferenceFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference themeReference;
    private TextView affichageNomConference;
    private TextView affichageDescriptionConference;

    private String key;
    private String name;
    private String description;

    public AdministratorGestionConferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_administrator_gestion_conference, container, false);

        //récupération de la key
        MainActivity activity = (MainActivity) getActivity();
        key = activity.getKey();
        name = activity.getName();
        description = activity.getDescription();

        //Toast.makeText(getActivity(), key, Toast.LENGTH_LONG).show();

        //Préparation de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();
        themeReference = firebaseDatabase.getReference().child("conference");

        //récupération des composants
        affichageNomConference = view.findViewById(R.id.textViexName);
        affichageDescriptionConference = view.findViewById(R.id.textViexDescription);


        affichageNomConference.setText("Titre : " + name);
        affichageDescriptionConference.setText("Description : " + description);



        return view;
    }

}