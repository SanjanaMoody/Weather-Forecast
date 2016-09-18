package com.example.sanjana.weatherforecast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by sanjana on 12/8/2015.
 */
public class DetailsActivity extends Activity {

    ArrayList<HourlyStatics> next24Hours = new ArrayList<HourlyStatics>();
    ArrayList<HourlyStatics> after24Hours = new ArrayList<HourlyStatics>();

    ArrayList<String> dayValues = new ArrayList<String>();
    ArrayList<String> minMaxTemps = new ArrayList<String>();
    ArrayList<Integer> icons = new ArrayList<Integer>();
    String[] dateValuesArray;
    String[] minMaxTempArray;
    Integer[] iconArray;

    LinearLayout lnext24hours;
    LinearLayout lnext7days;
    ListView list;
    String city;
    String degree;
    String state;
    TextView moreDetails;

    Next24hoursData adapterTabOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        lnext24hours = (LinearLayout) findViewById(R.id.lyNextTfHours);
        lnext7days = (LinearLayout) findViewById(R.id.lyNext7Hours);
        lnext7days.setVisibility(View.GONE);

        Bundle b = getIntent().getExtras();
        String data = b.getString("Data");
        city = b.getString("city");
        degree = b.getString("degree");
        state = b.getString("state");



        moreDetails = (TextView) findViewById(R.id.moreDetailsTxtView);
        moreDetails.setText("More details for " + city + ", " + state);
        try {
            JSONObject jsonObject = new JSONObject(data);

            for (int i = 0; i < 47; i++) {
                String index = String.valueOf(i);
                String timeRow = jsonObject.getJSONArray(index).getString(0);
                Integer summaryIconImage = getImage(jsonObject.getJSONArray(index).getString(1));
                Integer tr = (int) Double.parseDouble(jsonObject.getJSONArray(index).getString(3));
                String tempRow = tr.toString();

                if (i <= 23) {
                    next24Hours.add(new HourlyStatics(timeRow, summaryIconImage, tempRow));
                } else if (i == 24) {
                    next24Hours.add(new HourlyStatics("          ", R.mipmap.ic_more_hourly_stat, "  "));
                    after24Hours.add(new HourlyStatics(timeRow, summaryIconImage, tempRow));
                } else {
                    after24Hours.add(new HourlyStatics(timeRow, summaryIconImage, tempRow));
                }
            }


            for (int i = 48; i < 55; i++) {
                String index = String.valueOf(i);
                String dayRow = jsonObject.getJSONArray(index).getString(0);
                String dateRow = jsonObject.getJSONArray(index).getString(1);
                Integer summaryRow = getImage(jsonObject.getJSONArray(index).getString(2));
                String minTempRow = formatTemperature(jsonObject.getJSONArray(index).getString(3));
                String maxTempRow = formatTemperature(jsonObject.getJSONArray(index).getString(4));
                int j = i - 48;


                dayValues.add(j, dayRow + ", " + dateRow);
                icons.add(j, summaryRow);
                minMaxTemps.add(j, "Min:" + minTempRow + " | Max: " + maxTempRow);
            }


            dateValuesArray = dayValues.toArray(new String[dayValues.size()]);
            iconArray = icons.toArray(new Integer[icons.size()]);
            minMaxTempArray = minMaxTemps.toArray(new String[minMaxTemps.size()]);

        } catch (JSONException e) {

        }

        TextView tvTemp = (TextView) findViewById(R.id.temptxtView);
        tvTemp.setText(formatTemperature("Temp"));
        adapterTabOne = new Next24hoursData(this, next24Hours);
        list = (ListView) findViewById(R.id.lvNext24Hours);
        list.setAdapter(adapterTabOne);


        NextWeekData adapterTabTwo = new NextWeekData(this, dateValuesArray, iconArray, minMaxTempArray);
        list = (ListView) findViewById(R.id.next7DaystxtView);
        list.setAdapter(adapterTabTwo);


        findViewById(R.id.btNext24Hours).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                lnext7days.setVisibility(View.GONE);
                lnext24hours.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btNext7Days).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                lnext24hours.setVisibility(View.GONE);
                lnext7days.setVisibility(View.VISIBLE);
            }
        });
    }

    public void getMoreHourlyData(final HourlyStatics more) {
        this.adapterTabOne.remove(more);
        this.adapterTabOne.addAll(after24Hours);
        this.adapterTabOne.notifyDataSetChanged();
    }


    public String formatTemperature(String temp) {
        if (degree.equals("us")) return temp + "\u2109";
        else return temp + "\u2103";
    }


    public static Integer getImage(String summary) {
        if (summary.equals("clear-day"))
            return R.drawable.clear;
        if (summary.equals("clear-night"))
            return R.drawable.clear_night;
        if (summary.equals("rain"))
            return R.drawable.rain;
        if (summary.equals("snow"))
            return R.drawable.snow;
        if (summary.equals("sleet"))
            return R.drawable.sleet;
        if (summary.equals("wind"))
            return R.drawable.wind;
        if (summary.equals("fog"))
            return R.drawable.fog;
        if (summary.equals("cloudy"))
            return R.drawable.cloudy;
        if (summary.equals("partly-cloudy-day"))
            return R.drawable.cloud_day;
        else
            return R.drawable.cloud_night;
    }
}
