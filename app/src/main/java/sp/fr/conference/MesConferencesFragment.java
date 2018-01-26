package sp.fr.conference;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import sp.fr.conference.model.ConferenceArrayAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MesConferencesFragment extends Fragment implements View.OnClickListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;
    private List<Conference> ConferenceList;
    private ListView conferenceListView;
    private ConferenceArrayAdapter adapter;
    private ImageView changeButton, deleteButton, addNewConfButton;


    public MesConferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.mes_conferences_list_view, container, false);

        //Préparation de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();
        conferenceReference = firebaseDatabase.getReference().child("conference");

        //Récupération de la listView et des images
        conferenceListView = view.findViewById(R.id.userConferenceListViewItem);
        ConferenceList = new ArrayList<>();
        changeButton = view.findViewById(R.id.changeButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        addNewConfButton = view.findViewById(R.id.addNewConfButton);

        /**Gestion des suppression, modification ou ajout au clic d'un bouton
        changeButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        addNewConfButton.setOnClickListener(this);*/

        conferenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Conference item = (Conference) parent.getItemAtPosition(position);

            }
        });


        //Instanciation de la liste
        adapter = new ConferenceArrayAdapter(this.getActivity(),R.layout.fragment_mes_conferences, ConferenceList);
        conferenceListView.setAdapter(adapter);


        conferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Réinitialisation de la liste
                ConferenceList.clear();

                //Boucle sur l'ensemble des noeuds
                for (DataSnapshot conferenceSnapshot : dataSnapshot.getChildren()){

                    //Création d'une instance de conference et hydratation avec les données du snapshot
                    Conference userConf = (Conference)conferenceSnapshot.getValue(Conference.class);

                    String title = conferenceSnapshot.getChildren().toString();

                    //Ajout de la conference à la liste
                    ConferenceList.add(userConf);
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
    public void onClick(View view) {

       /** if(view.getId() == R.id.deleteButton) {
           //A MODIFIER CF BDD SI VALIDER=1 SINON=0
            Toast.makeText(getActivity(), "Conference supprimer de ma liste", Toast.LENGTH_SHORT).show();
        }
        else if(view.getId() == R.id.changeButton){
            navigateToFragment(new PropConference());
            Toast.makeText(getActivity(), "Redirection sur ConferenceInformation", Toast.LENGTH_SHORT).show();
        }
        else if(view.getId() == R.id.addNewConfButton){
            navigateToFragment(new PropConference());
            Toast.makeText(getActivity(), "Redirection sur PropConference", Toast.LENGTH_SHORT).show();
        }*/

    }

    public void navigateToFragment(Fragment targetFragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,targetFragment).commit();
    }
}
