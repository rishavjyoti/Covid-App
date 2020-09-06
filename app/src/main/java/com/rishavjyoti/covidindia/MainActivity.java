package com.rishavjyoti.covidindia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
implements HomeFragment.OnFragmentInteractionListener,
 SecondFragment.OnFragmentInteractionListener,
SearchFragment.OnFragmentInteractionListener,
NewsFragment.OnFragmentInteractionListener{
    public void onFragmentInteraction(Uri uri){
        ///////////////
    }
    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
    private SecondFragment secondFragment;
    private NewsFragment newsFragment;
    private TextView home,news;
    private FloatingActionButton search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
        homeFragment = new HomeFragment();
        secondFragment = new SecondFragment();
        newsFragment = new NewsFragment();
        setupUIViews();
        setFragment(homeFragment);
        //On click listeners
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(homeFragment);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(newsFragment);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(secondFragment);
            }
        });
    }
    //Setting ui items with their id
    private void setupUIViews(){
        frameLayout = findViewById(R.id.frameMain);
        home = findViewById(R.id.ivHome);
        news = findViewById(R.id.ivNews);
        search = findViewById(R.id.fabSearch);
    }
    /////////////////////////////////////////////////////
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameMain, fragment);
        fragmentTransaction.commit();
    }
    /////////////////////////////////////////////////////
}
