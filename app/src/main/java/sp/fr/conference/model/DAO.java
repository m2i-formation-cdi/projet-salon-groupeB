package sp.fr.conference.model;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.MainActivity;

/**
 * Created by Formation on 26/01/2018.
 */

public class DAO extends Activity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference themeReference;
    private List<Conference> ConferenceList;
    private List<ThemesConference> ThemesList;
    MainActivity activity;
    MainActivity obj;
    private ThemesConference themeConf;
    private ThemeArrayAdapter themeAdapter;
    private ConferenceArrayAdapter adapter; //Pas forcement utile
    private boolean statut = false;

    public DAO() {

        //Préparation de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    public List<ThemesConference> getThemesList() {
        return ThemesList;
    }

    public DAO setThemesList(List<ThemesConference> themesList) {
        ThemesList = themesList;
        return this;
    }

    public void synchoMain() {

        //activity = (MainActivity) getActivity(); //Pas sur de la syntaxe
        //MainActivity obj =  activity.setKey( item );

    }

    public void ajoutMainTheme( ThemesConference themesList ) {

        MainActivity obj =  activity.setThemesList( themesList );

    }

    public void connectConference() {

        //Création de l'array
        ConferenceList = new ArrayList<>();

        //Déplacement dans la base de donnée sur thème
        themeReference.child("conference");

        themeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Réinitialisation de la liste
                ConferenceList.clear();

                //Boucle sur l'ensemble des noeuds
                for(DataSnapshot conferenceSnapshot : dataSnapshot.getChildren()) {

                    //Création d'une instance de theme et hydratation avec les données du snapshot
                    Conference Conf = (Conference)conferenceSnapshot.getValue(Conference.class);

                    String name = conferenceSnapshot.getChildren().toString();

                    //Ajout du theme à la liste
                    ConferenceList.add(Conf);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<ThemesConference> connectTheme() {

        //Création de l'array
        ThemesList = new ArrayList<>();

        //Synchro avec le main
        if(ThemesList.size() != 0) {
            synchoMain();
        }

        //Déplacement dans la base de donnée sur thème
        themeReference = firebaseDatabase.getReference().child("themes");

        themeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Réinitialisation de la liste
                ThemesList.clear();

                //Boucle sur l'ensemble des noeuds
                for(DataSnapshot themeSnapshot : dataSnapshot.getChildren()) {

                    //Création d'une instance de theme et hydratation avec les données du snapshot
                    themeConf = (ThemesConference)themeSnapshot.getValue(ThemesConference.class);

                    String name = themeSnapshot.getChildren().toString();

                    //Ajout du theme à la liste
                    ThemesList.add(themeConf);

                    Log.i("INFO", "-----------------------  DAO -------------------------");

                }


                //themeAdapter.notifyDataSetChanged();
                statut = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return ThemesList;
    }

    public boolean isStatut() {
        return statut;
    }

    public DAO setStatut(boolean statut) {
        this.statut = statut;
        return this;
    }


}
