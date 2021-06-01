package com.dicoding.picodiploma.gastiadiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dicoding.picodiploma.gastiadiapp.ui.chat.ChatFragment;
import com.dicoding.picodiploma.gastiadiapp.ui.dashboard.DashboardFragment;
import com.dicoding.picodiploma.gastiadiapp.ui.profile.Login;
import com.dicoding.picodiploma.gastiadiapp.ui.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new DashboardFragment())
                .commit();

        SpaceNavigationView navView = (SpaceNavigationView) findViewById(R.id.nav_view);
        navView.initWithSaveInstanceState(savedInstanceState);
        navView.addSpaceItem(new SpaceItem("", R.drawable.ic_home));
        navView.addSpaceItem(new SpaceItem("", R.drawable.ic_profile));

        navView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                navView.setCentreButtonSelectable(true);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new ChatFragment())
                        .commit();
            }

            public void logout (View view){
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new DashboardFragment())
                                .commit();
                        break;

                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new ProfileFragment())
                                .commit();
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) { }
        });
    }
}