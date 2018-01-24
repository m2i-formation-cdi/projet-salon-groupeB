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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.model.Conference;
/**
 * A simple {@link Fragment} subclass.
 */
public class MesConferencesFragment extends Fragment implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;
    private List<Conference> conferenceList;
    private ListView conferenceListView;
    private ConferenceArrayAdapter adapter;
    private ImageView changeButton, deleteButton;


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
        conferenceList = new ArrayList<>();
        changeButton = view.findViewById(R.id.changeButton);
        deleteButton = view.findViewById(R.id.deleteButton);

        changeButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        conferenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Conference item = (Conference) parent.getItemAtPosition(position);
            }
        });

        //Instanciation de la liste
        adapter = new ConferenceArrayAdapter(this.getActivity(),R.layout.fragment_mes_conferences, conferenceList);
        conferenceListView.setAdapter(adapter);


        conferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Réinitialisation de la liste
                conferenceList.clear();

                //Boucle sur l'ensemble des noeuds
                for (DataSnapshot conferenceSnapshot : dataSnapshot.getChildren()){

                    //Création d'une instance de thème et hydratation avec les données du snapshot
                    Conference userConf = (Conference)conferenceSnapshot.getValue(Conference.class);

                    String title = conferenceSnapshot.getChildren().toString();

                    //Ajout de la conference à la liste
                    conferenceList.add(userConf);
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

    }


    private class ConferenceArrayAdapter extends ArrayAdapter<Conference>{

        private Activity context;
        int resource;
        List<Conference> data;

        public ConferenceArrayAdapter(Activity context, int resource, List<Conference> data) {
            super(context, resource, data);

            this.context = context;
            this.resource = resource;
            this.data = data;
        }

    public View getView(int position, View convertView, ViewGroup parent){

        View view = context.getLayoutInflater().inflate(this.resource, parent, false);

        Conference selectedConference = conferenceList.get(position);

        TextView textView = view.findViewById(R.id.textViewMesConferences);

        return view;

        }
    }
    public void onDelete(View view){
        //Récupération de la position taguée
        int position = (int) view.getTag();
        Conference conf = this.conferenceList.get(position);

    }

    public void onChange(View view){
        //Récupération de la position taguée
        int position = (int) view.getTag();
        Conference conf = this.conferenceList.get(position);

    }
}