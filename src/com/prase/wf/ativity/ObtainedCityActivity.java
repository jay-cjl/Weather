package com.prase.wf.ativity;

import java.util.ArrayList;
import java.util.List;

import com.prase.wf.MainActivity;
import com.prase.wf.R;
import com.prase.wf.R.layout;
import com.prase.wf.adapter.ObCityAdapter;
import com.prase.wf.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ObtainedCityActivity extends Activity
{
	private List<String> dataList = new ArrayList<String>();
	private ObCityAdapter adapter; 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_obtained_city);
		
		ListView listView = (ListView)findViewById(R.id.listview2);
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String listString =  mPreferences.getString("citylist", "");
		if(listString!="")
		{
			String[] listbuffer = listString.split(",");
			for (String city : listbuffer)
			{
				if(city !="")
				{
					dataList.add(city);
				}
			}
		}
		adapter = new ObCityAdapter(this,this, R.layout.obtained_city_list, dataList);
		listView.setAdapter(adapter);
		
		Button mButton = (Button)findViewById(R.id.add_button);
		mButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(ObtainedCityActivity.this, ChooseAreaActivity.class);
				startActivity(intent);
				finish();
			}
		});
		Button back = (Button)findViewById(R.id.back_icon);
		back.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(ObtainedCityActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
