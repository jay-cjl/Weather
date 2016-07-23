package com.prase.wf.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.prase.wf.MainActivity;
import com.prase.wf.R;
import com.prase.wf.adapter.WeatherDataAdapter.ViewHolder;
import com.prase.wf.ativity.ObtainedCityActivity;
import com.prase.wf.util.Utility;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ObCityAdapter extends ArrayAdapter<String>
{
	private Button curDel_btn;
	private int resourceID;
	private List<String> mList;
	private Context mContext;
	private ObtainedCityActivity mActivity;
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return this.mList.size();
	}
	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	
	public ObCityAdapter(Context context, ObtainedCityActivity activity,int resource,List<String> objects)
	{
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceID = resource;
		mList= objects;
		mContext =  context;
		mActivity= activity;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		
		
		View view = null;
		ViewHolder mViewHolder;
		
		if (convertView == null)
		{
			view = LayoutInflater.from(getContext()).inflate(resourceID, null);
			mViewHolder= new ViewHolder();
			mViewHolder.cityText = (TextView)view.findViewById(R.id.obcity1);
			
			mViewHolder.deleteButton = (Button)view.findViewById(R.id.delbutton);
			mViewHolder.deleteButton.setVisibility(view.GONE);
			
			view.setTag(mViewHolder);
		} else
		{
			view = convertView;
			mViewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		//
		//listen
		view.setOnTouchListener(new OnTouchListener()
		{
			float prex=0;
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				// TODO Auto-generated method stub
				final ViewHolder tempHolder =(ViewHolder)arg0.getTag();
				
				if (arg1.getAction() == MotionEvent.ACTION_DOWN)
				{
					prex = arg1.getX();
					 if (curDel_btn != null) {
						if(curDel_btn.getVisibility() == View.VISIBLE){
						 curDel_btn.setVisibility(View.GONE);
						}
					 }
					
					return true;
				}
				else if (arg1.getAction() == MotionEvent.ACTION_UP)
				{
					float curX = arg1.getX();
					if (Math.abs(curX-prex) >10)
					{
//						{
//							android.view.ViewGroup.LayoutParams para1 = tempHolder.deleteButton.getLayoutParams();
//							tempHolder.cityText.scrollTo((int) para1.width, 0);
//						}
						curDel_btn = tempHolder.deleteButton;
						curDel_btn.setVisibility(View.VISIBLE);
					}
					else {
					//	mActivity.BackToMainActivity();
					}
					return true;
				}
//				else if (arg1.getAction() == MotionEvent.ACTION_MOVE)
//				{
//					float curX = arg1.getX();
//					if(prex>curX)
//					{
//						tempHolder.deleteButton.scrollTo((int)(curX-prex), 0);
//					}
//					return true;
//				}
				return false;
			}
		});
		
		mViewHolder.deleteButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub	
				curDel_btn.setVisibility(View.GONE);
				mList.remove(position);
				Utility.deleteWeatherInfo(mContext, position);
				notifyDataSetChanged();
			}
		});
		String city = getItem(position);
		mViewHolder.cityText.setText(city);
		//
		return view;
		
	}
	
	class ViewHolder
	{
		TextView 	cityText;
		Button   	deleteButton;
	}

}
