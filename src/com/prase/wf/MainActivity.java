package com.prase.wf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Locale;
import java.util.Set;

import com.prase.wf.adapter.ListViewData;
import com.prase.wf.adapter.PingyinMaping;
import com.prase.wf.adapter.WeatherDataAdapter;
import com.prase.wf.ativity.ChooseAreaActivity;
import com.prase.wf.ativity.ObtainedCityActivity;
import com.prase.wf.db.CoolWeatherDB;
import com.prase.wf.service.AutoUpdateService;
import com.prase.wf.util.HttpCallbackListener;
import com.prase.wf.util.HttpUtil;
import com.prase.wf.util.Utility;
import com.prase.wf.util.Weather;
import com.prase.wf.util.WeatherDataStruct;
import com.prase.wf.util.WeatherUIData;


import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String yygypathstr = "";
	public String location=null;
	public List<WeatherUIData> mData = new ArrayList<WeatherUIData>(); 
	private ArrayList<Fragment> fragmentList;  
	//
	private int PageIndex=0;
	private int mCurrentPage=1;
	private int mImageCount=0;
	public List<Bitmap> mBitmaps = new ArrayList<Bitmap>();
	public List<WeatherDataAdapter> adapterlist = new ArrayList<WeatherDataAdapter>();
	public List<List<ListViewData>> WtdataList = new ArrayList<List<ListViewData>>();
	//
	private PingyinMaping mPingyin = new PingyinMaping();
	private ProgressDialog progressDialog=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		fragmentList = new ArrayList<Fragment>();  
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(),fragmentList);
		//
	//	adapter = new WeatherDataAdapter(MainActivity.this, R.layout.weatherlist, WtdataList);
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		location = getIntent().getStringExtra("location");
		//mFragment1.
		Button mMenuButton = (Button)findViewById(R.id.menu_button);
		mMenuButton.setOnClickListener(this);
		Button mRefreshButton = (Button)findViewById(R.id.refresh_weather);
		mRefreshButton.setOnClickListener(this);
		//
		
		//
		querySavedCityData();
		Intent intent = new Intent(this,AutoUpdateService.class);
		startService(intent);
//		mData= Utility.WeatherInfoParse(MainActivity.this, "京");
//		if(mData!=null)
//		{
//			for (Weather weather:mData.getList())
//			{		
//				int idx=0;
//				loadImage(weather.getWeather(),idx);
//				idx++;
//			}
//		
//			showWeather();
//		}
		
	//	adapter = new WeatherDataAdapter(this, R.layout.weatherlist, WtdataList);
	//	listView.setAdapter(adapter);
	//	

	}
	public void querySavedCityData()
	{
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String listString =  mPreferences.getString("citylist", "");
		if(listString!="")
		{
			String[] listbuffer = listString.split(",");
			for (String city : listbuffer)
			{
				if(city !="")
				{
					WeatherUIData data= Utility.WeatherInfoParse(MainActivity.this, city);
					
					if (data != null)
					{
						for (Weather weather : data.getList())
						{
							int idx = 0;
							loadImage(weather.getWeather(), idx);
							idx++;
						}

						showWeather(data);
					    PageIndex++;
					}
				}
			}
			mViewPager.setAdapter(mSectionsPagerAdapter); 
			String paraString ="";
			paraString = getIntent().getStringExtra("location");
			if(paraString!="")
				mViewPager.setCurrentItem(PageIndex-1);
		}
//		else 
//		{
//			Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
//			startActivity(intent);
//			finish();
//		}
 	}
	public void loadImage(String filename,int index)
	{
		try
		{
			String name = mPingyin.getString(filename);
			if(name==null)
			{
				String[] subString = filename.split("转");
				name = mPingyin.getString(subString[0]);
				if (name==null)
				{
					name = "qing";
				}
			}
			InputStream in= getAssets().open("WeatherBitmap/day/"+name+".png");
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			mBitmaps.add(bitmap);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		
	}
//	public void queryWeatherData(String location)
//	{
//		HttpUtil.sendHttpRequest(location, new HttpCallbackListener()
//		{
//				@Override
//				public void onFinish(String response)
//				{
//					Utility.saveWeatherInfo(MainActivity.this, MainActivity.this.location, response);
					
//					runOnUiThread(
//						new Runnable() 
//						{				
//							public void run() {
//							// TODO Auto-generated method stub	
//								WeatherUIData data = Utility.WeatherInfoParse(MainActivity.this, MainActivity.this.location);
//								
//								if(mData!=null)
//								{
//									for (Weather weather:data.getList())
//									{		
//										int idx=0;
//										loadImage(weather.getWeather(),idx);
//										idx++;
//									}
//								
//									showWeather(data);
//								}
//							}
//						}
//					);

			
					

		//			TextView mWeatherView = (TextView)view.findViewById(R.id.weather);
		//			mWeatherView.setText(databuffer[3]);
		//			TextView mTempre = (TextView)view.findViewById(R.id.tempreture);
		//			mTempre.setText(databuffer[4]);
		//			Log.d("showWeatherData", "showWeatherData:"+mPreference.getString("cityName",""));
		//			try
		//			{
		//
		//				File file = new File(yygypathstr, "data.json");
		//				file.createNewFile();
		//
		//				FileOutputStream fileInput = new FileOutputStream(file);
		//				try
		//				{
		//					byte[] Mybytes = response.getBytes("UTF8");
		//					fileInput.write(Mybytes);
		//					fileInput.close();
		//				} catch (IOException e)
		//				{
		//					// TODO Auto-generated catch block
		//					e.printStackTrace();
		//				}
		//
		//			} catch (Exception e)
		//			{
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		
					
//				}
//		
//				@Override
//				public void onError(Exception e)
//				{
//					// TODO Auto-generated method stub
//					e.printStackTrace();
//				}
//
//				@Override
//				public void onFinish(Bitmap bitmap)
//				{
//					// TODO Auto-generated method stub
//					
//				}
//		
//			}
//		);
//	}
	public void showWeather(WeatherUIData data)
	{
	//	LayoutInflater lf = getLayoutInflater().from(this);
	//	View rootView = lf.inflate(R.layout.fragment_main,null);
		List<ListViewData> tempListViewDatas = new ArrayList<ListViewData>();
		for (int i = 0; i < data.getList().size(); i++)
		{
			Weather weather = data.getList().get(i);
			ListViewData listViewData = new ListViewData();
			if (i==0)
			{
				String dateString = data.getList().get(0).getDate();
				String textString = dateString.substring(0, 2);
				listViewData.setDate(textString);
			}
			else {
				listViewData.setDate(weather.getDate());
			}
			
			listViewData.setTemperature(weather.getTemperature());
			listViewData.setWeather(weather.getWeather());
			listViewData.setWind(weather.getWind());
			listViewData.setImage(mBitmaps.get(i));
			tempListViewDatas.add(listViewData);
		}
		 WtdataList.add(tempListViewDatas);
		 adapterlist.add(new WeatherDataAdapter(this,  R.layout.weatherlist, WtdataList.get(PageIndex)));
		 Fragment newFragment = new PlaceholderFragment(PageIndex);  
	     fragmentList.add(newFragment); 
	     mViewPager = (ViewPager) findViewById(R.id.pager);
	     MainActivity.this.mData.add(data);
	    
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{

		ArrayList<Fragment> list;  
		public SectionsPagerAdapter(FragmentManager fm,ArrayList<Fragment> list)
		{
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int position)
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return list.get(position);
		}

		@Override
		public int getCount()
		{
			// Show 3 total pages.
			return list.size();
		}
//         public void destroyItem(ViewGroup container, int position,  
//                 Object object) {  
//             container.removeViewAt(position);  
//         }  

		public CharSequence getPageTitle(int position)
		{
			Locale l = Locale.getDefault();
			switch (position)
			{
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
	//		case 2:
	//			return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public  class PlaceholderFragment extends Fragment
	{


		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private  static final String ARG_SECTION_NUMBER = "section_number";
		
		
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public  PlaceholderFragment (int sectionNumber)//newInstance(int sectionNumber)
		{
			//PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			this.setArguments(args);
			//return fragment;
		}
		
		public PlaceholderFragment()
		{
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			int key = getArguments().getInt(ARG_SECTION_NUMBER);		
			WeatherUIData data =  MainActivity.this.mData.get(key);
		//	switch (key)
		//	{
		//	case 1:
			ListView listView = (ListView) rootView.findViewById(R.id.listView1);
			listView.setAdapter(MainActivity.this.adapterlist.get(key));
			TextView mWeaView = (TextView) rootView.findViewById(R.id.weather);
			mWeaView.setText(data.getCurrentWeather());
			
			TextView mCityView = (TextView) rootView.findViewById(R.id.city);
			mCityView.setText(data.getCityName());

			TextView mDateView = (TextView) rootView.findViewById(R.id.date);
			String dateString = data.getList().get(0).getDate();
			String textString = dateString.substring(3, 8);
			String mtempString = dateString.substring(14, 17);
			mDateView.setText(textString);
			TextView mView1 = (TextView) rootView.findViewById(R.id.tempreture);
			mView1.setText(mtempString);
			// break;
			//
		//	default:
		//		break;
		//	}
			//showWeatherData(rootView,key);

			
//			switch (getArguments().getInt(ARG_SECTION_NUMBER))
//			{
//			case 1:
//				rootView = inflater.inflate(R.layout.fragment_main, container,
//						false);
//				break;
//			case 2:
//				rootView = inflater.inflate(R.layout.fragment2, container,
//						false);
//				break;
//			case 3:
//				rootView = inflater.inflate(R.layout.fragment2, container,
//						false);
//				break;
//			default:
//				rootView = inflater.inflate(R.layout.fragment2, container,
//						false);
//				break;
//			}
//				
			return rootView;
		}
	}
	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		int id = arg0.getId();
		if (id == R.id.menu_button)
		{
			{
				//queryWeatherData();
				//Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
				Intent intent = new Intent(MainActivity.this, ObtainedCityActivity.class);
				startActivity(intent);
				finish();
			}
		}
		else if (id == R.id.refresh_weather)
		{
//			
////			fragmentList.remove(mCurrentPage);
//		//	mViewPager.removeViewAt(mCurrentPage)

			showProgressDialog();
			mCurrentPage = mViewPager.getCurrentItem();
			final String locationString = mData.get(mCurrentPage).getCityName();
			HttpUtil.sendHttpRequest(locationString, new HttpCallbackListener()
			{
					@Override
					public void onFinish(String response)
					{
						closeProgressDialog();
						Utility.saveWeatherInfo(MainActivity.this, locationString, response);
						
						runOnUiThread(new Runnable()
						{
							
							@Override
							public void run()
							{
								// TODO Auto-generated method stub
								mCurrentPage = mViewPager.getCurrentItem();
								PageIndex=0;
								mData.clear();
								WtdataList.clear();
								adapterlist.clear();
								fragmentList.clear();
								querySavedCityData();
								mViewPager.setCurrentItem(mCurrentPage);
							}
						});
						
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
	/**
	 * 显示进度对话框
	 */
	private void showProgressDialog()
	{
		if (progressDialog == null)
		{
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在刷新...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog()
	{
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}
}
