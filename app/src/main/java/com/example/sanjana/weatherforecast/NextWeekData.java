package com.example.sanjana.weatherforecast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sanjana on 12/9/2015.
 */
public class NextWeekData extends ArrayAdapter<String> {

    private final Activity activityContext;
    private final String[] datesOfDays;
    private final Integer[] icons;
    private final String[] tempMinMax;


    public NextWeekData(Activity context, String[] daysDates, Integer[] icons, String[] minMaxTemps) {
        super(context, R.layout.next_24_hours_list, daysDates);
        this.activityContext =context;
        this.datesOfDays =daysDates;
        this.icons=icons;
        this.tempMinMax =minMaxTemps;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater= activityContext.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.next_7days_list, null, true);

        TextView txtDayDate = (TextView) rowView.findViewById(R.id.dayDateTxtView);
        ImageView imgSummaryIcon = (ImageView) rowView.findViewById(R.id.ivSummaryWeeklyIcon);
        TextView txtMinMaxTemp = (TextView) rowView.findViewById(R.id.minMaxTxtView);

        txtDayDate.setText(datesOfDays[position]);
        imgSummaryIcon.setImageResource(icons[position]);
        txtMinMaxTemp.setText(tempMinMax[position]);
        return rowView;

    }
}