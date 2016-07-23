package com.prase.wf.util;

import java.io.Reader;
import java.io.StringReader;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class JsonParse
{
	//public WeatherDataStruct mStruct;
	public WeatherDataStruct Parse(String data)
	{
		WeatherDataStruct mdataDataStruct=null;
	//	try
		{
			//JsonReader mReader = new JsonReader(new StringReader(data));
			//mReader.beginArray();
			Gson mGson = new Gson();
			
			mdataDataStruct = mGson.fromJson(data, WeatherDataStruct.class);
			return mdataDataStruct;
			
		} 
	}
}
