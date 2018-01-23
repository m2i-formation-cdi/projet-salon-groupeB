package sp.fr.conference;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.model.Conference;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropConference extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferenceReference;
    private List<Conference> conferenceList = new ArrayList<>();
    private ListView conferenceListView;
    EditText conferenceTitleEditText;
    EditText descriptionConferenceEditText;
    EditText speakerNameEditText;
    String titleConference;
    String descriptionConference;
    String speakerName;

    public PropConference() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        conferenceReference = firebaseDatabase.getReference().child("conference");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_prop_conference, container, false);

        // récupération des valeurs du formulaire
        conferenceTitleEditText = view.findViewById(R.id.titleConferenceProposition);
        descriptionConferenceEditText = view.findViewById(R.id.descriptionConference);
        speakerNameEditText = view.findViewById(R.id.speakerName);

        //Gestion du clic sur le bouton valider
        Button btValid = view.findViewById(R.id.validateConference);
        btValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // récupération du titre de la conférence dans le formulaire
                titleConference = conferenceTitleEditText.getText().toString();
                descriptionConference = descriptionConferenceEditText.getText().toString();
                speakerName = speakerNameEditText.getText().toString();

                Conference conference = new Conference();
                // ajout des valeurs à la conférence
                conference.setDescription(descriptionConference);
                conference.setTitle(titleConference);
                //conference.setUser(User.setName(speakerName));
                // push dans la database
                String conferenceId = conferenceReference.push().getKey();
                conferenceReference.child(conferenceId).setValue(conference);

            }
        });

        return view;
    }

    public void addConference (){



        /*
        Person person = new Person();
        //Adding values
        person.setName(name);
        person.setAddress(address);
        Firebase newRef = ref.child("Person").push();
        newRef.setValue(person);

        Author hugo = new Author("Hugo", "Victor", "Français");
        Author auster = new Author("Auster", "Paul", "Américain");

        String bookId = bookReference.push().getKey();
        Book book = new Book("Ruy Blas", 12.0, hugo);
        bookReference.child(bookId).setValue(book);

        bookId = bookReference.push().getKey();
        book = new Book("Les misérables", 12.0, hugo);
        bookReference.child(bookId).setValue(book);

        bookId = bookReference.push().getKey();
        book = new Book("New York", 12.0, auster);
        bookReference.child(bookId).setValue(book);
        */
    }


}
