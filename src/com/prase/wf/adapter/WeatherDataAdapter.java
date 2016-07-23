package com.prase.wf.adapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prase.wf.R;
import com.prase.wf.util.HttpCallbackListener;
import com.prase.wf.util.HttpUtil;
import com.prase.wf.util.Utility;
import com.prase.wf.util.Weather;

public class WeatherDataAdapter extends ArrayAdapter<ListViewData>
{

	private int resourceId=0;
	Bitmap mBitmap = null;
	public WeatherDataAdapter(Context context, int resource,
			List<ListViewData> objects)
	{
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId= resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		ListViewData weather = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null)
		{
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.date = (TextView) view.findViewById(R.id.eachdate);
			viewHolder.wind = (TextView) view.findViewById(R.id.wind);
			viewHolder.tempreture = (TextView) view.findViewById(R.id.each_tempre);
			viewHolder.weather = (TextView) view.findViewById(R.id.weather);
			viewHolder.image = (ImageView) view.findViewById(R.id.weather_image);
			view.setTag(viewHolder); // 将ViewHolder存储在View中
		} else
		{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		viewHolder.date.setText(weather.getDate());
		viewHolder.wind.setText(weather.getWind());
		viewHolder.tempreture.setText(weather.getTemperature());
		viewHolder.weather.setText(weather.getWeather());
		viewHolder.image.setImageBitmap(weather.getImage());

		
//		HttpUtil.GetHttpImage(weather.getDayPictureUrl(), new HttpCallbackListener()
//		{
//			
//			@Override
//			public void onFinish(Bitmap bitmap)
//			{
//				// TODO Auto-generated method stub
//				viewHolder.Image.setImageBitmap(bitmap);
//			}
//			
//			@Override
//			public void onFinish(String response)
//			{
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onError(Exception e)
//			{
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		return view;
	}

	class ViewHolder
	{
		TextView date;
		ImageView image;
		TextView weather;
		TextView wind;
		TextView tempreture;
	}
}
