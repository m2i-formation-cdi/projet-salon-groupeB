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
import java.util.Map;

import sp.fr.conference.model.Conference;
import sp.fr.conference.model.ConferenceArrayAdapter;
import sp.fr.conference.model.ConferenceGestion;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdministratorGestionConferenceFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;

    private TextView affichageNomConference;
    private TextView affichageDescriptionConference;
    private TextView joursAfficherDebut, MoisAfficherDebut, AnneeAfficherDebut;
    private TextView JoursAfficherFin, MoisAfficherFin, AnneeAfficherFin, lieuxConference;

    private Button buttonRefuser, buttonValider;

    private String dateDebut, dateFin, lieux;

    private String name;
    private String description;
    private List<Conference> ConferenceList;
    private Conference obj;
    private ConferenceArrayAdapter adapter;
    private boolean isValide = false;

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
        obj = activity.getObj();

        //Conference conf = new Conference(key);

        //Préparation de la base de données
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        conferenceReference = firebaseDatabase.getReference().child("conference");


        //récupération des composants
        affichageNomConference = view.findViewById(R.id.textViexName);
        affichageDescriptionConference = view.findViewById(R.id.textViexDescription);
        lieuxConference = view.findViewById(R.id.lieuxConference);

        //récupération des composants de date
        joursAfficherDebut = view.findViewById(R.id.JoursAfficherDebut);
        MoisAfficherDebut = view.findViewById(R.id.MoisAfficherDebut);
        AnneeAfficherDebut = view.findViewById(R.id.AnneeAfficherDebut);

        JoursAfficherFin = view.findViewById(R.id.JoursAfficherFin);
        MoisAfficherFin = view.findViewById(R.id.MoisAfficherFin);
        AnneeAfficherFin = view.findViewById(R.id.AnneeAfficherFin);

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

                if(!joursAfficherDebut.equals("") || joursAfficherDebut == null) {
                    if(!MoisAfficherDebut.equals("") || MoisAfficherDebut == null) {
                        if(!AnneeAfficherDebut.equals("") || AnneeAfficherDebut == null) {

                            if(!JoursAfficherFin.equals("") || JoursAfficherFin == null) {
                                if(!MoisAfficherFin.equals("") || MoisAfficherFin == null) {
                                    if(!AnneeAfficherFin.equals("") || AnneeAfficherFin == null) {
                                        isValide = true;
                                    }
                                }
                            }

                        }
                    }
                }

                if(isValide) { //si true

                    //enregistrement
                        //concaténation des chaines
                        dateDebut = JoursAfficherFin.getText().toString() + "/" + MoisAfficherDebut.getText().toString() + "/" + AnneeAfficherDebut.getText().toString();
                        dateFin = JoursAfficherFin.getText().toString() + "/" + MoisAfficherFin.getText().toString() + "/" + AnneeAfficherFin.getText().toString();
                        lieux = lieuxConference.getText().toString();
                        String date = "22/05/2018";

                    //ConferenceGestion confObj = new ConferenceGestion(dateDebut, dateFin, lieux, date);

                    //conferenceReference.setValue(confObj);

                    //affichage du message de réussite
                    Toast.makeText(getActivity(), "ACCEPTER", Toast.LENGTH_SHORT).show();
                    //redirection vers la liste



                } else { //sinon

                    Toast.makeText(getActivity(), "Date invalide", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //Mise à jour des informations
        affichageNomConference.setText("Titre : " + name);
        affichageDescriptionConference.setText("Description : " + description);

        ConferenceList = new ArrayList<>();

        //Instanciation de la liste
        adapter = new ConferenceArrayAdapter(this.getActivity(), R.layout.theme_list_items_conference_statut, ConferenceList);




        return view;
    }

}
