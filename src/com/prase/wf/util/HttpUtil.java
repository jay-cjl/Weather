package com.prase.wf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtil
{
	private static String mloaction = "";

	public static void generateHttpAddress(String location)
	{
		StringBuilder address = new StringBuilder();
		address.append("http://api.map.baidu.com/telematics/v3/weather?location=");
		String encodeLocation;
		try
		{
			encodeLocation = URLEncoder.encode(location, "UTF-8");
			address.append(encodeLocation);
			address.append("&output=json&ak=GXVRDrynRW2f9Fec56A3AvZnew8Kb3mb");
			address.append("&mcode=18:9F:E8:2F:28:38:FC:B4:E9:06:A3:FA:99:78:5E:C7:36:AA:94:D0;");
			address.append("com.swan.coolweather");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}// encode Chinese
		mloaction = address.toString();
		// return address.toString();
	}

	public static void sendHttpRequest(String location,
			final HttpCallbackListener listener)
	{
		generateHttpAddress(location);
	    final String address = mloaction;
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{

				HttpURLConnection connection = null;
				try
				{
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null)
					{
						response.append(line);
					}
					if (listener != null)
					{
						listener.onFinish(response.toString());
					}

				} catch (IOException e)
				{
//					if (listener != null) {
//						listener.onError(e);
//					}

				} finally
				{
					if (connection != null)
					{
						connection.disconnect();
					}
				}
			}
		}).start();
	}
	public static void GetHttpImage(final String address,final HttpCallbackListener listener)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				HttpURLConnection connection = null;
				try
				{
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(in);  
					if (listener != null)
					{
						listener.onFinish(bitmap);
					}
				} catch (IOException e)
				{
					if (listener != null) {
						listener.onError(e);
					}

				} finally
				{
					if (connection != null)
					{
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}