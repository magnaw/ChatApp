package chatapp.magnus.chatapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import chatapp.magnus.chatapp.posts.ContentItem;
import chatapp.magnus.chatapp.posts.UserDTO;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListFragment.OnListFragmentInteractionListener {

    private FirebaseAuth mAuth;
    private ImageView navAvatar;
    private TextView navName;
    private TextView navEmail;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        navAvatar = (ImageView) headerView.findViewById(R.id.navAvatar);
        navName = (TextView) headerView.findViewById(R.id.navName);
        navEmail = (TextView) headerView.findViewById(R.id.navEmail);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.child("users").child(SingletonApplications.fbUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SingletonApplications.localUser = dataSnapshot.getValue(UserDTO.class);
                Picasso.with(context)
                        .load(SingletonApplications.localUser.getAvatar())
                        .placeholder(R.drawable.ic_menu_gallery)
                        .into(navAvatar);
                navName.setText(SingletonApplications.localUser.getName());
                navEmail.setText(SingletonApplications.localUser.getMail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().add(R.id.maincontent, new ListFragment()).commit();
//            getFragmentManager().beginTransaction().add(R.id.fraglist, new android.app.ListFragment()).commit();
        }




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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

//        } else if (id == R.id.nav_share) {

//        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                }
            }, 200);

        }


        return true;
    }

    @Override
    public void onListFragmentInteraction(ContentItem item) {

    }
}
