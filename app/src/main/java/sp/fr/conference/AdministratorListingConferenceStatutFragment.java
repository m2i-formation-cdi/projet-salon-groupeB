package sp.fr.conference;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.model.Conference;
import sp.fr.conference.model.ConferenceArrayAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdministratorListingConferenceStatutFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference themeReference;
    private ListView conferenceListView ;
    private List<Conference> ConferenceList;
    private ConferenceArrayAdapter adapter;
    private int filtre = 0;

    public AdministratorListingConferenceStatutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_administrator_listin_conference_statutg, container, false);

        //Préparation de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();
        themeReference = firebaseDatabase.getReference().child("conference");

        //Rcupération de la listView
        conferenceListView = view.findViewById(R.id.ListViewThemesWaiting);

        ConferenceList = new ArrayList<>();

        conferenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                Conference item = (Conference) parent.getItemAtPosition(position);


                MainActivity activity = (MainActivity) getActivity();
                MainActivity obj =  activity.setObj( item );


                navigateToFragment( new AdministratorGestionConferenceFragment() );

            }
        });

        Log.i("INFO", "----------------------- start array adapter -------------------------");
        //Instanciation de la liste
        adapter = new ConferenceArrayAdapter(this.getActivity(), R.layout.theme_list_items_conference_statut, ConferenceList);
        conferenceListView.setAdapter(adapter);


        themeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Réinitialisation de la liste
                ConferenceList.clear();

                //Boucle sur l'ensemble des noeuds
                for(DataSnapshot themeSnapshot : dataSnapshot.getChildren()) {

                    //Création d'une instance de theme et hydratation avec les données du snapshot
                    Conference themeConf = (Conference)themeSnapshot.getValue(Conference.class);

                    String name = themeSnapshot.getChildren().toString();

                    //Ajout du theme à la liste

                    if(filtre == 0) { //Affciher l'ensemble des conferences
                        ConferenceList.add(themeConf);
                    } else if(themeConf.getStatut() == null) {

                        if (filtre == 1) { //Affiche les conferences en attente
                            ConferenceList.add(themeConf);
                        }

                    } else {

                        if (filtre == 2 && themeConf.getStatut().equals("0")) { //Affiche les conferences refuse
                            ConferenceList.add(themeConf);
                        } else if (filtre == 3 && themeConf.getStatut().equals("1")) { //Affiche les conferences valide
                            ConferenceList.add(themeConf);
                        }
                    }

                    /**
                     *
                     * ConferenceList.clear(); //clean de la liste
                     * adapter.notifyDataSetChanged(); //recharge de la liste
                     */

                }
                Log.i("INFO", "----------------------- étape changed -------------------------");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.i("INFO", "----------------------- return view -------------------------");
        return view;
    }

    private void navigateToFragment(Fragment targetFragment) {

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, targetFragment).commit();

    }

}