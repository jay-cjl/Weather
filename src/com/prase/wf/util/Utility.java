package com.prase.wf.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.prase.wf.db.CoolWeatherDB;
import com.prase.wf.model.City;
import com.prase.wf.model.County;
import com.prase.wf.model.Province;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class Utility
{
	public static WeatherUIData WeatherInfoParse(Context context,String cityName)
	{
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String jsondata = mPreferences.getString(cityName, "");
		JsonParse mParse = new JsonParse();
		WeatherDataStruct mDataStruct = mParse.Parse(jsondata);
		Results mResults = mDataStruct.getResults().get(0);
		List<Weather> mWeathers =  mResults.getWeather_data();
		WeatherUIData mUiData = new WeatherUIData();
		Weather currentWeather = mWeathers.get(0);
 		mUiData.setCityName(mResults.getCurrentCity());
		
		mUiData.setCurrentWeather(currentWeather.getWeather());
		mUiData.setCurrentTempreture(currentWeather.getTemperature());
		List<Weather> mListData = new ArrayList<Weather>();
		for (Weather weather:mWeathers)
		{			
			mListData.add(weather);
		}
		mUiData.setList(mListData);
		return mUiData;
	//	String date = mDataStruct.getDate();
		//List<Results> results = mDataStruct.getResults();
		//String city = results.get(0).getCurrentCity();
	//	List<Set<String>> wDataList = new ArrayList<Set<String>>();
	//	for (int i = 0; i < results.get(0).getWeather_data().size(); i++)

	}
	public static void saveWeatherInfo(Context context, String cityName,String json) 
	{
		//	SimpleDateFormat sdf = new SimpleDateFormat("yyyyÄêMÔÂdÈÕ",
		//	Locale.CHINA);
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String citylist = mPreferences.getString("citylist", "");
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putBoolean("city_selected", true);
		editor.putString(cityName, json);
		if (!citylist.contains(cityName))
		{
			editor.putString("citylist", citylist+cityName+",");
		}	
		editor.commit();
	}
	public static void deleteWeatherInfo(Context context, int index)
	{
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String citylist = mPreferences.getString("citylist", "");
		SharedPreferences.Editor editor = mPreferences.edit();
		String []citybuff = citylist.split(",");
		if (index < citybuff.length)
		{
			String newlist = "";
			for (int i = 0; i < citybuff.length; i++)
			{
				if(i!=index)
				{
					newlist += (citybuff[i]+",");
				}
			}
			
			editor.putString("citylist", newlist);
		}
		editor.commit();
	}
	public static void RefreshWeatherInfo(Context context, String list)
	{
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString("citylist", list);
		editor.commit();
	}
}
