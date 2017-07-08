package com.westerdals.gard.hotel;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private ArrayList<Marker> eatMarkers = new ArrayList<>();
    private ArrayList<Marker> activitiesMarkers = new ArrayList<>();
    private ArrayList<Marker> sightseeingMarkers = new ArrayList<>();
    private Button eatButton;
    private Button activitiesButton;
    private Button sightseeingButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        initWidgets(view);
        initListeners();

        mapView = (MapView)view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.onResume();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);

        initMarkers();

        map.addMarker(new MarkerOptions().position(new LatLng(59.909417982179306, 10.753124738158238)).title("You are here"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.909417982179306, 10.753124738158238), 15));
    }

    private void initMarkers(){
        eatMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.91037, 10.76030)).title("Maaemo").snippet("Three Michelin star restaurant").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).visible(true)));
        eatMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.90829, 10.72358)).title("Døgnvill").snippet("Hamburger restaurant").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).visible(true)));
        eatMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.91834, 10.76021)).title("Schouskjelleren mikrobryggeri").snippet("Microbrewery/pub").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).visible(true)));
        eatMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.90841, 10.75674)).title("Vaaghals").snippet("Norwegian-themed Restaurant").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).visible(true)));

        activitiesMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.91207, 10.74301)).title("Steen & Strøm").snippet("Fancy shopping mall").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).visible(true)));
        activitiesMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.90691, 10.72193)).title("Astrup Fearnley Museet").snippet("Contemporary art museum").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).visible(true)));
        activitiesMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.91402, 10.71886)).title("Solli bowling").snippet("Bowling and pizza from Peppes").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).visible(true)));
        activitiesMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.91159, 10.73020)).title("Nobels Fredsenter").snippet("Nobels peace center").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).visible(true)));

        sightseeingMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.90740, 10.75312)).title("Operahuset i Oslo").snippet("Opera house").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).visible(true)));
        sightseeingMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.91712, 10.72755)).title("Det kongelige slott").snippet("The royal castle").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).visible(true)));
        sightseeingMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.91200, 10.73366)).title("Rådhuset").snippet("The city hall").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).visible(true)));
        sightseeingMarkers.add(map.addMarker(new MarkerOptions().position(new LatLng(59.90796, 10.73665)).title("Akershus Festning").snippet("Old fortress used in several wars").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).visible(true)));
    }

    private void initWidgets(View view){
        eatButton = (Button)view.findViewById(R.id.eatButton);
        activitiesButton = (Button)view.findViewById(R.id.activitiesButton);
        sightseeingButton = (Button)view.findViewById(R.id.sightseeingButton);
    }

    private void initListeners(){
        eatButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleMarkerVisibility(eatMarkers);
            }
        });

        activitiesButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleMarkerVisibility(activitiesMarkers);
            }
        });

        sightseeingButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleMarkerVisibility(sightseeingMarkers);
            }
        });
    }

    private void toggleMarkerVisibility(ArrayList<Marker> markers){
        if (markers.get(0).isVisible()){
            for (Marker marker : markers) {
                marker.setVisible(false);
            }
        }else {
            for (Marker marker : markers) {
                marker.setVisible(true);
            }
        }
    }
}
