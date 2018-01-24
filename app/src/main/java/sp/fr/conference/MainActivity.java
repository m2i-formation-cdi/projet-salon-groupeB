package sp.fr.conference;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import sp.fr.conference.model.User;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final int LOGIN_REQUESTCODE = 1;
    private User user;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FirebaseUser fbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Réference aux TextView dans l'en tête de la navigation
        View headerView = ((NavigationView)navigationView.findViewById(R.id.nav_view))
                .getHeaderView(0);



        userNameTextView = headerView.findViewById(R.id.headerUserName);
        userEmailTextView = headerView.findViewById(R.id.headerUserEmail);

        //Instanciation de l'utilisateur
        this.user = new User();
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Toast.makeText(this,"id"+id,Toast.LENGTH_SHORT).show();

        if (id == R.id.login) {
            //
        } else if (id == R.id.logOut) {
            //
        } else if (id == R.id.listinfConference) {
            //
        } else if (id == R.id.myConference) {
            //
        } else if (id == R.id.themeDescription){
            navigateToFragment(new DescriptionConferenceFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToFragment(Fragment targetFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer,targetFragment)
                .commit();

    }


    /**
     * Méthode permettant d'accèder à l'utilisateur
     * @return
     */
    public User getUser(){
        return this.user;
    }



    //Lancement de la procedure d'authentification
    public void onLogin(MenuItem item) {
        //Définir des fournisseurs d'authentification
        List<AuthUI.IdpConfig> providers =  new ArrayList<>();
        providers.add(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER)
                        .build()
        );
        //Lancement de  l'activité d'authentification
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                LOGIN_REQUESTCODE
        );
    }

    /**
     * Résultat de
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_REQUESTCODE){
            //Récupération de la réponse
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                //Récupération de l'utilisateur connecté
                fbUser= FirebaseAuth.getInstance().getCurrentUser();

                //Affichage des infos utilisateur
                if(fbUser!= null) {
                    String userName = fbUser.getDisplayName();
                    String userEmail = fbUser.getEmail();

                    userNameTextView.setText(userName);
                    userEmailTextView.setText(userEmail);
                }
                //Masquage du lien login
                navigationView.getMenu().findItem(R.id.login).setVisible(false);
                //Affichage du lien LogOut
                navigationView.getMenu().findItem(R.id.logOut).setVisible(true);

            }else {
                if(response != null){
                    Log.d("Main","Erreur Fireauth code: "+ response.getErrorCode());
                }
                Toast.makeText(this,
                        "Impossible de vous authentifier",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onLogout(MenuItem item) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Afficher du lien login
                        navigationView.getMenu().findItem(R.id.login).setVisible(true);
                        //Masquage du lien LogOut
                        navigationView.getMenu().findItem(R.id.logOut).setVisible(false);

                        //Vider les infos utilisateurs dans l'en tête
                        userNameTextView.setText("");
                        userEmailTextView.setText("");

                        fbUser = null;


                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
        );

    }
}










