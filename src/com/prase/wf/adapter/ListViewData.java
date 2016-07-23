package com.prase.wf.adapter;

import android.graphics.Bitmap;

public class ListViewData
{
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public Bitmap getImage()
	{
		return image;
	}
	public void setImage(Bitmap image)
	{
		this.image = image;
	}
	public String getWeather()
	{
		return weather;
	}
	public void setWeather(String weather)
	{
		this.weather = weather;
	}
	public String getWind()
	{
		return wind;
	}
	public void setWind(String wind)
	{
		this.wind = wind;
	}
	public String getTemperature()
	{
		return temperature;
	}
	public void setTemperature(String temperature)
	{
		this.temperature = temperature;
	}
	private String date;
	private Bitmap image; 
	private String weather;
	private String wind;
	private String temperature;
}
