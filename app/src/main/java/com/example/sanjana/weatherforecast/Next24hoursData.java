package com.example.sanjana.weatherforecast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sanjana on 12/8/2015.
 */
public class Next24hoursData extends ArrayAdapter<HourlyStatics> {

	private final Activity context;
	private boolean nextDatas = false;
	private LayoutInflater inflater;

	public Next24hoursData(Activity context, List<HourlyStatics> stats) {
		super(context, R.layout.next_24_hours_list, stats);
		this.context = context;
		this.inflater = context.getLayoutInflater();
	}

	public View getView(final int position, View view, ViewGroup parent) {
		View viewRows;
		final HourlyStatics stat = getItem(position);

		if(position == 24 && !nextDatas) {
			viewRows = inflater.inflate(R.layout.next_48_hours_list_more, parent, false);

			ImageButton btnMoreHourlyStat = (ImageButton) viewRows.findViewById(R.id.btnMoreHourlyData);

			btnMoreHourlyStat.setImageResource(stat.getSummary());
			btnMoreHourlyStat.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					v.setOnClickListener(null);
					nextDatas = true;
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							((DetailsActivity) context).getMoreHourlyData(stat);
						}
					});
				}
			});
		} else {
			viewRows = inflater.inflate(R.layout.next_24_hours_list, parent, false);

			TextView txtTitle = (TextView) viewRows.findViewById(R.id.timesTxtView);
			ImageView imageView = (ImageView) viewRows.findViewById(R.id.summaryIcon);
			TextView txtTemp = (TextView) viewRows.findViewById(R.id.tempTxtView);


			int[] colors = new int[]{0x30FFFFFF, 0x30D8D8D8};
			int colorPos = position % colors.length;
			viewRows.setBackgroundColor(colors[colorPos]);

			txtTitle.setText(stat.getTime());
			imageView.setImageResource(stat.getSummary());
			txtTemp.setText(stat.getTemp());
		}

		return viewRows;
	}
}