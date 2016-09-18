package com.example.sanjana.weatherforecast;

/**
 * Created by sanjana
 */
public class HourlyStatics {
	private String time;
	private int summary;
	private String temp;

	public HourlyStatics(String time, int summary, String temp) {
		this.time = time;
		this.summary = summary;
		this.temp = temp;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSummary() {
		return summary;
	}

	public void setSummary(int summary) {
		this.summary = summary;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
}