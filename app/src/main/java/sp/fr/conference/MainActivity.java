package sp.fr.conference;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.model.Comments;
import sp.fr.conference.model.Conference;
import sp.fr.conference.model.DAO;
import sp.fr.conference.model.ThemesConference;
import sp.fr.conference.model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Conference obj;
    private List<Conference> ConferenceList;
    private ThemesConference ThemesList;
    private List<ThemesConference> ThemesListArray;
/*
    private String title;
    private ThemesConference theme;
    private String description;
    private User attendents;
    private String id;
    private String statut;
    private String day;
    private String startHour;
    private String endHour;
    private String location;
    private String latitude;
    private String longitude;
    private User speaker;
    private Comments comments;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.login) {
            // Handle the camera action
        } else if (id == R.id.logOut) {

        } else if (id == R.id.listinfConference) {

        } else if (id == R.id.myConference) {
            navigateToFragment( new AdministratorThemeFragment() );

        } else if (id == R.id.administrator) {
            navigateToFragment( new AdministratorListingConferenceStatutFragment() );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToFragment(Fragment targetFragment) {

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, targetFragment).commit();

    }

    /**
     * Zone d'obsolescence
     * @return
     */

    public Conference getObj() {
        return obj;
    }

    public MainActivity setObj(Conference key) {
        this.obj = key;
        return this;
    }

    /**
     * Fin Zone d'obsolescence
     * @return
     */

    public List<Conference> getConferenceList() {
        return ConferenceList;
    }

    public MainActivity setConferenceList(List<Conference> conferenceList) {
        ConferenceList = conferenceList;
        return this;
    }

    public ThemesConference getThemesList() {
        return ThemesList;
    }

    public MainActivity setThemesList(ThemesConference themesList) {
        ThemesList = themesList;

        ThemesListArray.add(ThemesList);

        return this;
    }

    public List<ThemesConference> getThemesListArray() {
        return ThemesListArray;
    }

    public MainActivity setThemesListArray(List<ThemesConference> themesListArray) {
        ThemesListArray = themesListArray;
        return this;
    }
}
