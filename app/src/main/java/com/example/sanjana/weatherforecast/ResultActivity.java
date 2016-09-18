package com.example.sanjana.weatherforecast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sanjana on 12/8/2015.
 */
public class ResultActivity extends Activity {

    private String summText;
    private String currentTemp;
    private String lowHighTemp;
    private ImageView summaryIcon;
    private TextView summary;
    private TextView currTemp;
    private TextView minMaxTemp;
    private TextView precipitation;
    private TextView chanceOfRain;

    private TextView windSpeed;
    private TextView dewPoint;
    private TextView visibility;
    private TextView humidity;
    private TextView sunrise;
    private TextView sunset;
    private JSONObject json;
    private String data;
    private String city;
    private String degree;
    private String stateName;
    private String lat;
    private String lon;
    private String icon;
    private String summaryFB;
    private String tempFB;
    private String unitsFB;
    private String iconFB;
    private String cityFB;
    private String stateFB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        data=bundle.getString("Data");
        city=bundle.getString("city");
        degree=bundle.getString("degree");
        stateName=bundle.getString("state");

        try{
            json = new JSONObject(data);
            summary = (TextView) findViewById(R.id.tvWeatherDetails);
            currTemp = (TextView) findViewById(R.id.tempDetailstxtView);
            minMaxTemp = (TextView) findViewById(R.id.minMaxTempDetailstxtView);
            precipitation = (TextView) findViewById(R.id.tvPrecipValue);
            chanceOfRain = (TextView) findViewById(R.id.tvChanceOfRainValue);
            windSpeed = (TextView) findViewById(R.id.tvWindSpeedValue);
            dewPoint = (TextView) findViewById(R.id.tvDewPointValue);
            visibility = (TextView) findViewById(R.id.tvVisibilityValue);
            humidity = (TextView) findViewById(R.id.tvHumidityValue);
            sunrise = (TextView) findViewById(R.id.tvSunriseValue);
            sunset = (TextView) findViewById(R.id.tvSunsetValue);
            summaryIcon = (ImageView)findViewById(R.id.ivWeatherImage);

            summText = json.getString("summary");
            summaryFB = summText;
            icon = json.getString("icon");
            unitsFB = (degree.equals("si")) ? "\u2103" : "\u2109";
            summText += " in " + getIntent().getStringExtra("city") + "," + getIntent().getStringExtra("state");
            summary.setText(summText);
            summaryIcon.setImageResource(DetailsActivity.getImage(json.getString("summary")));
            currentTemp = json.getString("temperature");
            cityFB = city;
            stateFB = stateName;
            if (getIntent().getStringExtra("degree").equals("si")) {
                currTemp.setText((currentTemp) + " \u2103");
            } else {
                currTemp.setText((currentTemp) + " \u2109");
            }

            tempFB = currentTemp;
            lowHighTemp = "L:" + json.getString("tempMin") + "\u00B0" + " | H:" + json.getString("tempMax") + "\u00B0";
            minMaxTemp.setText(lowHighTemp);
            precipitation.setText(json.getString("precipitation"));
            chanceOfRain.setText(json.getString("chanceOfRain"));
            windSpeed.setText(json.getString("windSpeed"));
            dewPoint.setText(json.getString("dewPoint"));
            visibility.setText(json.getString("visibility"));
            humidity.setText(json.getString("humidity"));
            sunrise.setText(json.getString("sunrise"));
            sunset.setText(json.getString("sunset"));
            lat = json.getString("lat");
            lon = json.getString("lon");

            if(icon.equals("clear-day")){
                iconFB ="clear";
            }
            else if(icon.equals("clear-night")){
                iconFB ="clear_night";
            }
            else if(icon.equals("rain")){
                iconFB ="rain";
            }
            else if(icon.equals("snow")){
                iconFB ="snow";
            }
            else if(icon.equals("sleet")){
                iconFB ="sleet";
            }
            else if(icon.equals("wind")){
                iconFB ="wind";
            }
            else if(icon.equals("fog")){
                iconFB ="fog";
            }
            else if(icon.equals("partly-cloudy-day")){
                iconFB ="cloud_day";
            }
            else if(icon.equals("partly-cloudy-night")){
                iconFB ="cloud_night";
            }
            else if(icon.equals("cloudy")) {
                iconFB ="cloudy";
            }
            else{
                iconFB ="cloudy";
            }


        }catch(JSONException e){
            Toast.makeText(ResultActivity.this, "Some error occurred.", Toast.LENGTH_LONG).show();
            return;
        }

        findViewById(R.id.btMoreDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                intent.putExtra("Data", data);
                intent.putExtra("city", city);
                intent.putExtra("degree", degree);
                intent.putExtra("state", stateName);
                startActivity(intent);
            }

        });

        findViewById(R.id.btViewMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ResultActivity.this, MapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                startActivity(intent);
            }

        });

        findViewById(R.id.fbIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ResultActivity.this, FacebookActivity.class);
                intent1.putExtra("SUMMARY", summaryFB);
                intent1.putExtra("TEMPERATURE", tempFB);
                intent1.putExtra("UNITS", unitsFB);
                intent1.putExtra("IMAGE", iconFB);
                intent1.putExtra("CITY", cityFB);
                intent1.putExtra("STATE", stateFB);
                startActivity(intent1);
            }
        });
    }
}
