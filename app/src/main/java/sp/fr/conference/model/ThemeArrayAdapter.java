package sp.fr.conference.model;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sp.fr.conference.R;

/**
 * Created by Formation on 26/01/2018.
 */

public class ThemeArrayAdapter extends ArrayAdapter<ThemesConference> {

        private Activity context;
        int resource;
        List<ThemesConference> data;

        public ThemeArrayAdapter(Activity context, int resource, List<ThemesConference> data) {
            super(context, resource, data);
            Log.i("INFO", "-----------------------  ArrayAdapter -------------------------");
            this.context = context;
            this.resource = resource;
            this.data = data;

        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Log.i("INFO", "-----------------------  get view -------------------------");

            View view = context.getLayoutInflater().inflate(this.resource, parent, false);

            ThemesConference currentTheme = data.get(position);

            TextView textView = view.findViewById(R.id.themeListTextItem);

            textView.setText(
                    currentTheme.getName()
            );

            return view;
        }


    }
