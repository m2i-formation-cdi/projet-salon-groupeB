package sp.fr.conference;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionConferenceFragment extends Fragment {
    private TextView userNameTextView;
    private Boolean isConnected;
    private LinearLayout mlayout;
    private Boolean isParticipeted;

    public DescriptionConferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description_conference, container, false);
        MainActivity activity = (MainActivity) getActivity();
        isConnected = activity.getConnected();
        mlayout = view.findViewById(R.id.descriptionAuthentifier);
        mlayout.setVisibility(LinearLayout.INVISIBLE);
        Toast.makeText(this.getActivity(), "Masquer", Toast.LENGTH_SHORT).show();
        if (isConnected) {
            mlayout = view.findViewById(R.id.descriptionAuthentifier);
            mlayout.setVisibility(LinearLayout.VISIBLE);
            Toast.makeText(this.getActivity(), "Afficher", Toast.LENGTH_SHORT).show();
        }


        return view;

    }
}
