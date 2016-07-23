package com.prase.wf.util;

import java.util.List;

public class Results
{
	private String currentCity;
	private String pm25;
	private List<Tips> index;
	private List<Weather> weather_data;
	public String getCurrentCity()
	{
		return currentCity;
	}
	public void setCurrentCity(String currentCity)
	{
		this.currentCity = currentCity;
	}
	public String getPm25()
	{
		return pm25;
	}
	public void setPm25(String pm25)
	{
		this.pm25 = pm25;
	}


	public List<Tips> getIndex()
	{
		return index;
	}
	public void setIndex(List<Tips> index)
	{
		this.index = index;
	}
	public List<Weather> getWeather_data()
	{
		return weather_data;
	}
	public void setWeather_data(List<Weather> weather_data)
	{
		this.weather_data = weather_data;
	}
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		//return super.toString();
		 return "Results [city=" + currentCity + ", pm25="  
         + pm25+", Tips="+index+",Weather="+weather_data+"]"; 
	}
	
	
}
