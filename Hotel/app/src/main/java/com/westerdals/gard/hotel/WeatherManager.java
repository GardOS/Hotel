package com.westerdals.gard.hotel;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Gard on 25.05.2017.
 */

public class WeatherManager extends AsyncTask<String, Void, Weather[]>{
    private Context context;
    private TextView[] textViews;
    private ImageView[] imageViews;
    private ProgressDialog progressDialog;

    public WeatherManager(TextView[] textViews, ImageView[] imageViews, Context context) {
        this.textViews = textViews;
        this.imageViews = imageViews;
        this.context = context;
    }

    @Override
    public void onPreExecute(){
        progressDialog = ProgressDialog.show(context,"","Fetching weather",false);
    }

    @Override
    protected Weather[] doInBackground(String... params) {
        Weather[] weatherArray = new Weather[]{new Weather(), new Weather(), new Weather()};

        try{
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null){
                builder.append(inputString);
            }

            JSONObject root = new JSONObject(builder.toString());
            JSONArray list = root.getJSONArray("list");

            // list contains all the forecasts with 3h between each index. 0 == now, 1 == in 3h, 2 == in 6h and so on
            for (int i = 0; i < weatherArray.length; i++){
                weatherArray[i] = getWeatherDataJSON(weatherArray[i], list.getJSONObject(i));
            }

            //When the data is fetched it might go too fast for the progressbar to be readable.
            //Removing the progressbar is not viable in the cases where fetching data will take time.
            //The result is a pause small enough not to phase the user while at the same time being readable.
            //This might be a bad idea, and I would try it with users to see the response.
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){}

            urlConnection.disconnect();

        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return weatherArray;
    }

    @Override
    protected void onPostExecute(Weather[] weatherArray) {
        for (int i = 0; i < weatherArray.length; i++){
            populateViews(weatherArray[i], i * 3);
        }
        progressDialog.dismiss();
    }

    private void populateViews(Weather weather, int index){
        // Each row has 3 textViews and one imageView. onPostExecutes passes the product of i * 3
        textViews[index].setText(String.valueOf(weather.getTemperature() + "°C"));
        textViews[index + 1].setText(weather.getDescription());
        textViews[index + 2].setText(String.valueOf(weather.getRain() + "mm"));
        imageViews[index / 3].setImageBitmap(weather.getImage());
    }

    private Weather getWeatherDataJSON(Weather weatherObject, JSONObject timePoint) throws JSONException{
        JSONObject main = timePoint.getJSONObject("main");
        JSONObject weatherData = timePoint.getJSONArray("weather").getJSONObject(0);

        weatherObject.setTemperature(main.getDouble("temp"));
        weatherObject.setDescription(weatherData.getString("main"));
        weatherObject.setImage(convertReferenceToBitmap(weatherData.getString("icon")));

        // Object only exist if there are any rain. Throws exception if it does not exist.
        try{
            JSONObject rain = timePoint.getJSONObject("rain");
            weatherObject.setRain(rain.getDouble("3h"));
        }catch (JSONException e){
            weatherObject.setRain(0.0);
        }

        return weatherObject;
    }

    private Bitmap convertReferenceToBitmap(String iconCode){
        Bitmap bitmap = null;
        try {
            URL imageUrl = new URL("http://openweathermap.org/img/w/" + iconCode + ".png");
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);

        } catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
