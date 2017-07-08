package com.westerdals.gard.hotel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherFragment extends Fragment {

    private TextView[] textViewHolder;
    private ImageView[] imageViewHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        String url = "http://api.openweathermap.org/data/2.5/forecast?id=3143244&units=metric&appid=06dfc6fc98ae6066c0e8b59e582b65cd";

        initWidgets(view);

        new WeatherManager(textViewHolder, imageViewHolder, getContext()).execute(url);

        return view;
    }

    private void initWidgets(View view){
        TextView currentDegreesText = (TextView)view.findViewById(R.id.currentDegreesText);
        TextView currentDescriptionText = (TextView)view.findViewById(R.id.currentDescriptionText);
        TextView currentRainText = (TextView)view.findViewById(R.id.currentRainText);
        ImageView currentWeatherImage = (ImageView)view.findViewById(R.id.currentWeatherImage);

        TextView threeHoursDegreesText = (TextView)view.findViewById(R.id.threeHoursDegreesText);
        TextView threeHoursDescriptionText = (TextView)view.findViewById(R.id.threeHoursDescriptionText);
        TextView threeHoursRainText = (TextView)view.findViewById(R.id.threeHoursRainText);
        ImageView threeHoursWeatherImage = (ImageView)view.findViewById(R.id.threeHoursWeatherImage);

        TextView sixHoursDegreesText = (TextView)view.findViewById(R.id.sixHoursDegreesText);
        TextView sixHoursDescriptionText = (TextView)view.findViewById(R.id.sixHoursDescriptionText);
        TextView sixHoursRainText = (TextView)view.findViewById(R.id.sixHoursRainText);
        ImageView sixHoursWeatherImage = (ImageView)view.findViewById(R.id.sixHoursWeatherImage);

        textViewHolder = new TextView[]{
                currentDegreesText, currentDescriptionText, currentRainText,
                threeHoursDegreesText, threeHoursDescriptionText, threeHoursRainText,
                sixHoursDegreesText, sixHoursDescriptionText, sixHoursRainText};
        imageViewHolder = new ImageView[]{currentWeatherImage, threeHoursWeatherImage, sixHoursWeatherImage};
    }
}
