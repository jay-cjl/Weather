package com.prase.wf.util;

import java.util.ArrayList;
import java.util.List;

public class WeatherUIData
{
	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public String getCurrentWeather()
	{
		return currentWeather;
	}

	public void setCurrentWeather(String currentWeather)
	{
		this.currentWeather = currentWeather;
	}

	public String getCurrentTempreture()
	{
		return currentTempreture;
	}

	public void setCurrentTempreture(String currentTempreture)
	{
		this.currentTempreture = currentTempreture;
	}

	public List<Weather> getList()
	{
		return list;
	}

	public void setList(List<Weather> list)
	{
		if (this.list==null)
		{
			this.list= new ArrayList<Weather>(list.size());
		}
		int size =list.size();
		for (int i = 0; i < size; i++)
		{
			this.list.add(list.get(i));
		}
	}

	private String cityName;
	private String currentWeather;
	private String currentTempreture;
	private List<Weather> list;
	
}
