package sp.fr.conference;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.jar.Attributes;

import sp.fr.conference.model.ThemesConference;


public class AdministratorFragment extends Fragment  implements View.OnClickListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference themeReference;
    private List<ThemesConference> ThemesList;
    private ListView themesListView ;
    private ArrayAdapter themeAdapter;
    private ThemeArrayAdapter adapter;
    private EditText editTextNewOrUpdate, editTextAddTheme;
    private Boolean NewOrUpdateStatue = false;
    private ImageView imageValide, imageViewAdd, imageViewAjout;


    private String key;

    public AdministratorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_administrator, container, false);

        //Préparation de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();
        themeReference = firebaseDatabase.getReference().child("theme");

        //Rcupération de la listView
        themesListView = view.findViewById(R.id.ListViewThemes);
        editTextNewOrUpdate = view.findViewById(R.id.EditTextEditTheme);
        editTextAddTheme = view.findViewById(R.id.EditTextAddTheme);
        imageViewAdd = view.findViewById(R.id.imageViewAdd);
        imageViewAjout = view.findViewById(R.id.imageViewAddTheme);

        imageValide = view.findViewById(R.id.imageViewEdit);
        imageValide.setOnClickListener(this);
        imageViewAdd.setOnClickListener(this);
        imageViewAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });

        themesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                  @Override
                                                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                      Log.i("Affichage Systeme", "Click sur " + position );
                                                      editTextNewOrUpdate.setVisibility(TextView.INVISIBLE);

                                                      if(NewOrUpdateStatue == false) {

                                                          editTextNewOrUpdate.setVisibility(TextView.VISIBLE);
                                                          imageValide.setVisibility(TextView.VISIBLE);
                                                          NewOrUpdateStatue = true;
                                                          ThemesConference item = (ThemesConference) parent.getItemAtPosition(position);
                                                          key = item.getId();
                                                          editTextNewOrUpdate.setText( item.getName() );

                                                      } else if(NewOrUpdateStatue == true) {

                                                          editTextNewOrUpdate.setVisibility(TextView.INVISIBLE);
                                                          imageValide.setVisibility(TextView.INVISIBLE);
                                                          NewOrUpdateStatue = false;
                                                      }


                                                  }
                                              }
        );

        ThemesList = new ArrayList<>();

        //addBooks();

        //Instanciation de la liste
        adapter = new ThemeArrayAdapter(this.getActivity(), R.layout.theme_list_items , ThemesList);
        themesListView.setAdapter(adapter);

        themeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Réinitialisation de la liste
                ThemesList.clear();

                //Boucle sur l'ensemble des noeuds
                for(DataSnapshot themeSnapshot : dataSnapshot.getChildren()) {

                    //Création d'une instance de theme et hydratation avec les données du snapshot
                    ThemesConference themeConf = (ThemesConference)themeSnapshot.getValue(ThemesConference.class);

                    String name = themeSnapshot.getChildren().toString();

                    //Ajout du theme à la liste
                    ThemesList.add(themeConf);


                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void addBooks(){

        //création d'un test
        String testId = themeReference.push().getKey();
        ThemesConference sport = new ThemesConference("Demo",  testId);
        themeReference.child(testId).setValue(sport);


    }

    @Override
    public void onClick(View v) {

        //String testId = themeReference.push().getKey();

        String name = editTextNewOrUpdate.getText().toString();
        ThemesConference sport = new ThemesConference(name,  key);
        themeReference.child(key).setValue(sport);

    }

    private class ThemeArrayAdapter extends ArrayAdapter<ThemesConference> {

        private Activity context;
        int resource;
        List<ThemesConference> data;

        public ThemeArrayAdapter(Activity context, int resource, List<ThemesConference> data) {
            super(context, resource, data);

            this.context = context;
            this.resource = resource;
            this.data = data;

        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View view = context.getLayoutInflater().inflate(this.resource, parent, false);

            ThemesConference currentTheme = ThemesList.get(position);

            TextView textView = view.findViewById(R.id.themeListTextItem);

            textView.setText(
                    currentTheme.getName()
            );

            return view;
        }


    }



}
