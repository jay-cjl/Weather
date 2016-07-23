package com.prase.wf.db;

import java.util.ArrayList;
import java.util.List;

import com.prase.wf.model.City;
import com.prase.wf.model.County;
import com.prase.wf.model.Province;
import com.prase.wf.util.ProvinceAttr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB
{

	public static final String DB_NAME_STRING = "cool_weather";
	public static final int VERSION =1;
	private static CoolWeatherDB coolWeatherDB;
	public SQLiteDatabase db;
	
	private CoolWeatherDB(Context context)
	{
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME_STRING, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	public synchronized static CoolWeatherDB getInstance(Context context)
	{
		if (coolWeatherDB == null)
		{
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	public void saveProvince(Province province)
	{
		if (province != null)
		{
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	public void saveCity(City city)
	{
		if (city != null)
		{
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
//	public void saveAllProvince(ProvinceAttr attr)
//	{
//		if (attr != null)
//		{
//			ContentValues values = new ContentValues();
//			values.put("province_name", attr.getName());
//			values.put("province_code",attr.getId());
//			db.insert("Province", null, values);
//			List<Scity> cList = attr.getScitys();
//			for (int i = 0; i < cList.size(); i++)
//			{
//				ContentValues Cityvalues = new ContentValues();
//				Scity city = cList.get(i);
//				Cityvalues.put("city_name", city.getName());
//				Cityvalues.put("city_code", city.getId());
//				Cityvalues.put("province_id", attr.getId());
//				db.insert("City", null, Cityvalues);
//			}
//		}
//	}
	public List<Province> loadProvinces()
	{
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null );
		if (cursor.moveToFirst())
		{
			do
			{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}



	public List<City> loadCities(int provinceId)
	{
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst())
		{
			do
			{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * 将County实例存储到数据库。
	 */
	public void saveCounty(County county)
	{
		if (county != null)
		{
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}

	/**
	 * 从数据库读取某城市下所有的县信息。
	 */
	public List<County> loadCounties(int cityId)
	{
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst())
		{
			do
			{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor
						.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor
						.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			} while (cursor.moveToNext());
		}
		return list;
	}
//	public void saveSelectCity(String city)
//	{
//		if (city != null)
//		{
//			ContentValues values = new ContentValues();
//			values.put("city_name", city);
//
//			db.insert("Select_City", null, values);
//		}
//	}
//	public List<County> loadCounties(int cityId)
//	{
//		List<County> list = new ArrayList<County>();
//		Cursor cursor = db.query("County", null, "city_id = ?",
//				new String[] { String.valueOf(cityId) }, null, null, null);
//		if (cursor.moveToFirst())
//		{
//			do
//			{
//				County county = new County();
//				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
//				county.setCountyName(cursor.getString(cursor
//						.getColumnIndex("county_name")));
//				county.setCountyCode(cursor.getString(cursor
//						.getColumnIndex("county_code")));
//				county.setCityId(cityId);
//				list.add(county);
//			} while (cursor.moveToNext());
//		}
//		return list;
//	}
}
