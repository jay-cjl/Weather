package com.prase.wf.ativity;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.prase.wf.MainActivity;
import com.prase.wf.R;
import com.prase.wf.db.CoolWeatherDB;
import com.prase.wf.model.City;
import com.prase.wf.model.County;
import com.prase.wf.model.Province;
import com.prase.wf.util.HttpCallbackListener;
import com.prase.wf.util.HttpUtil;
import com.prase.wf.util.ProvinceAttr;
import com.prase.wf.util.ProvinceAttr.CountyAttr;
import com.prase.wf.util.Utility;
import com.prase.wf.util.XMLParser;
import com.prase.wf.util.ProvinceAttr.CityAttr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseAreaActivity extends Activity
{
	private static final int LEVEL_PROVINCE = 0;
	private static final int LEVEL_CITY = 1;
	private static final int LEVEL_COUNTRY = 2;
	Province selectedProvince;
	City selectedCity;
	County selcCounty;
	int selProvinceIndex=0;
	int selCityIndex=0;
	List<ProvinceAttr> provinceAttr; 
	ProgressDialog progressDialog;
	private List<Province> provinceList;
	private List<City> cityList;
	private List<County> countryList;
	private int currentLevel=0;
	
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.citylist);
		listView = (ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.title_text);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		coolWeatherDB = CoolWeatherDB.getInstance(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3)
			{
				if (currentLevel == LEVEL_PROVINCE)
				{
					selectedProvince = provinceList.get(index);
					selProvinceIndex= index;
					queryCities();
				} 
				else if (currentLevel == LEVEL_CITY)
				{
					selectedCity = cityList.get(index);
					selCityIndex= index;
					queryCounties();
					
				}
				else if(currentLevel == LEVEL_COUNTRY)
				{
					selcCounty = countryList.get(index);
					queryWeatherData(selcCounty.getCountyName());
					showProgressDialog();
				}
			}

		});
		{
			try
			{			
				queryFromXML();
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		queryProvinces(); // 加载省级数据
		
		//coolWeatherDB
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		return true;
	}
	public void queryWeatherData(final String location)
	{
		HttpUtil.sendHttpRequest(location, new HttpCallbackListener()
		{
				@Override
				public void onFinish(String response)
				{
					closeProgressDialog();
					Utility.saveWeatherInfo(ChooseAreaActivity.this, location, response);
					Intent intent = new Intent(ChooseAreaActivity.this, MainActivity.class);
					intent.putExtra("location",location);
					startActivity(intent);
					finish();
					
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
	/**
	* 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
	*/
	private void queryProvinces()
	{
		provinceList = coolWeatherDB.loadProvinces();
		if (provinceList.size() > 0)
		{
			dataList.clear();
			for (Province province : provinceList)
			{
				dataList.add(province.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText("中国");
		    currentLevel = LEVEL_PROVINCE;
		} 
		else {
			GetProivinceFromXML();
			queryProvinces();
		}
	}
	/**
	* 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
	*/
	private void queryCities()
	{
		cityList = coolWeatherDB.loadCities(selectedProvince.getId());
		if (cityList.size() > 0)
		{
			dataList.clear();
			for (City city : cityList)
			{
				dataList.add(city.getCityName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLevel = LEVEL_CITY;
		}
		else {
			GetCityFromXML();
			queryCities();
		}
	}
	/**
	* 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
	*/
	private void queryCounties()
	{
		countryList = coolWeatherDB.loadCounties(selectedCity.getId());
		if (countryList.size() > 0)
		{
			dataList.clear();
			for (County county : countryList)
			{
				dataList.add(county.getCountyName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTRY;
		}
		else {
			GetCountyFromXML();
			queryCounties();
		}
	}

	public void onBackPressed()
	{

		if (currentLevel == LEVEL_CITY)
		{
			queryProvinces();
		}
		else if (currentLevel == LEVEL_COUNTRY)
		{
			queryCities();
		}
		else
		{
			Intent intent = new Intent(ChooseAreaActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void queryFromXML() throws IOException
	{
		//showProgressDialog();
//		Thread thread1 = new Thread(new Runnable()
//		{
//			
//			@Override
//			public void run()
//			{
//				// TODO Auto-generated method stub
//				try {  
//		            InputStream is = getAssets().open("CityID.xml");  
//		            XMLParser parser = new XMLParser();  //创建SaxBookParser实例  
//		            provinceAttr= parser.readXML(is);  //解析输入流  
//		            
//		        } catch (Exception e) {  
//		        } 
//				
//			}
//		});
//		thread1.start();
		//closeProgressDialog();
		InputStream is = getAssets().open("CityID.xml");  
        XMLParser parser = new XMLParser();  //创建SaxBookParser实例  
        provinceAttr= parser.readXML(is);  //解析输入流  
        
	}
	public void GetProivinceFromXML()
	{
		coolWeatherDB.db.beginTransaction();
		
        int idx=1;
		for (ProvinceAttr attr : provinceAttr) { 
			
			Province province = new Province();
			province.setProvinceCode(attr.getId());
			province.setProvinceName(attr.getName());
            coolWeatherDB.saveProvince(province);
        }  
		coolWeatherDB.db.setTransactionSuccessful();
		coolWeatherDB.db.endTransaction();
	}

	public void GetCityFromXML()
	{
		coolWeatherDB.db.beginTransaction();

		ProvinceAttr attr = provinceAttr.get(selProvinceIndex);
		List<CityAttr> clistList = attr.getCityAttrs();
		for (int i = 0; i < clistList.size(); i++)
		{
			City city = new City();
			CityAttr CityAttr = clistList.get(i);
			if (CityAttr != null)
			{
				city.setCityCode(CityAttr.getId());
				city.setCityName(CityAttr.getName());
				city.setProvinceId(selectedProvince.getId());
				coolWeatherDB.saveCity(city);
			}
		}
		coolWeatherDB.db.setTransactionSuccessful();
		coolWeatherDB.db.endTransaction();
	}
	public void GetCountyFromXML()
	{
		CityAttr ctyAttr= provinceAttr.get(selProvinceIndex).getCityAttrs().get(selCityIndex);
    	List<CountyAttr> Countries = ctyAttr.getCountyAttr();
    	for (int j = 0; j < Countries.size(); j++)
		{
    		County  county = new County();
    		CountyAttr countyAttr = Countries.get(j);
    		if(countyAttr!=null)
    		{
    		county.setCountyCode(countyAttr.getId());
    		county.setCountyName(countyAttr.getName());
    		county.setCityId(selectedCity.getId());
    		coolWeatherDB.saveCounty(county);
    		}
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
			progressDialog.setMessage("正在加载...");
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
