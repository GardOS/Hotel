package com.westerdals.gard.hotel;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class RootActivity extends AppCompatActivity {
    private TextView headerText;
    private Button exploreButton;
    private Button weatherTodayButton;
    private Button recommendationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_root);

        initWidgets();
        initListeners();
    }

    private void initWidgets(){
        headerText = (TextView)findViewById(R.id.headerText);
        exploreButton = (Button)findViewById(R.id.exploreButton);
        weatherTodayButton  = (Button)findViewById(R.id.weatherTodayButton);
        recommendationsButton  = (Button)findViewById(R.id.recommendationsButton);
    }

    private void initListeners() {
        headerText.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                Fragment frontPageFragment = new FrontPageFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_fragment, frontPageFragment);
                fragmentTransaction.commit();
            }
        });

        exploreButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                MapsFragment mapsFragment = new MapsFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_fragment, mapsFragment);
                fragmentTransaction.commit();
            }
        });

        weatherTodayButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Fragment weatherFragment = new WeatherFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_fragment, weatherFragment);
                fragmentTransaction.commit();
            }
        });

        recommendationsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Fragment recommendationFragment = new RecommendationFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_fragment, recommendationFragment);
                fragmentTransaction.commit();
            }
        });
    }
}