package sp.fr.conference.model;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sp.fr.conference.R;

/**
 * Created by Formation on 24/01/2018.
 */

public class ConferenceArrayAdapter extends ArrayAdapter<Conference> {

    private Activity context;
    int resource;
    List<Conference> data;

    public ConferenceArrayAdapter(Activity context, int resource, List<Conference> data) {
        super(context, resource, data);

        this.context = context;
        this.resource = resource;
        this.data = data;

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = context.getLayoutInflater().inflate(this.resource, parent, false);

        Conference currentConference = data.get(position);

        TextView textViewMesConf = view.findViewById(R.id.textViewMesConferences);

        /**TextView textView = view.findViewById(R.id.ListTextItemConferenceStatut);
        //Rcupération de l'image du statut
        ImageView imageViewStatutConference = view.findViewById(R.id.imageViewStatutConference);

        String etatConference = "";

        if(currentConference.getStatut() == null) {
            imageViewStatutConference.setImageResource(R.mipmap.wait);
        } else if(currentConference.getStatut().equals("0")){
            imageViewStatutConference.setImageResource(R.mipmap.delete);
        }else if(currentConference.getStatut().equals("1")){
            //etatConference = "Validé";
            imageViewStatutConference.setImageResource(R.mipmap.check);
        }

        textView.setText(
                currentConference.getTitle() + " " + etatConference
        );*/

        textViewMesConf.setText(
                currentConference.getTitle()
        );

        return view;
    }

}
