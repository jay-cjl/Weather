package com.prase.wf.service;

import com.prase.wf.MainActivity;
import com.prase.wf.util.HttpCallbackListener;
import com.prase.wf.util.HttpUtil;
import com.prase.wf.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service
{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String listString =  mPreferences.getString("citylist", "");
		if(listString!="")
		{
			String[] listbuffer = listString.split(",");
			for (final String city : listbuffer)
			{
				if(city !="")
				{
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							updateWeatherInfo(city);
						}
					}).start();
				}
			}
		}
		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int updateHours = 8*60*60*1000;
		long triggerAtTimer = SystemClock.elapsedRealtime()+updateHours;
		Intent intent2 = new Intent(this,AutoUpdateReceiver.class);
		PendingIntent piIntent=PendingIntent.getBroadcast(this, 0,intent2, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTimer, piIntent);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	private void updateWeatherInfo(final String locationString)
	{
		
		HttpUtil.sendHttpRequest(locationString, new HttpCallbackListener()
		{

			@Override
			public void onFinish(String response)
			{
				// TODO Auto-generated method stub
				Utility.saveWeatherInfo(AutoUpdateService.this, locationString, response);
			}

			@Override
			public void onFinish(Bitmap bitmap)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Exception e)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
}
