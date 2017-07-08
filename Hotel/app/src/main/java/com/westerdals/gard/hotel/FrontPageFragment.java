package com.westerdals.gard.hotel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FrontPageFragment extends Fragment {
    private LinearLayout exploreLayout;
    private LinearLayout weatherLayout;
    private LinearLayout recommendationsLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_front_page, container, false);

        initWidgets(view);
        initListeners();

        return view;
    }

    private void initWidgets(View view){
        exploreLayout = (LinearLayout)view.findViewById(R.id.exploreLayout);
        weatherLayout = (LinearLayout)view.findViewById(R.id.weatherLayout);
        recommendationsLayout = (LinearLayout)view.findViewById(R.id.recommendationsLayout);
    }

    private void initListeners(){
        exploreLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                Fragment mapsFragment = new MapsFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_fragment, mapsFragment);
                fragmentTransaction.commit();
            }
        });

        weatherLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                Fragment weatherFragment = new WeatherFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_fragment, weatherFragment);
                fragmentTransaction.commit();
            }
        });

        recommendationsLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                RecommendationFragment recommendationsFragment = new RecommendationFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_fragment, recommendationsFragment);
                fragmentTransaction.commit();
            }
        });
    }
}
