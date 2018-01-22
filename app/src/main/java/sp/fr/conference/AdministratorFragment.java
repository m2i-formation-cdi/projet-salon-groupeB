package sp.fr.conference;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdministratorFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference themeReference;
    private List<String> themesList;
    private ListView themesListView;

    public AdministratorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_administrator, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        themeReference = firebaseDatabase.getReference().child("theme");

        themesList = new ArrayList<>();

        //themesListView = findViewById(R.id.ListViewThemes);

        return view;
    }



}
