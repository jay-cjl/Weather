package com.prase.wf.util;

import java.util.ArrayList;
import java.util.List;

public class ProvinceAttr
{
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<CityAttr> getCityAttrs()
	{
		return CityAttrs;
	}
	public void setCityAttrs(List<CityAttr> CityAttrs)
	{
		if (this.CityAttrs==null)
		{
			this.CityAttrs= new ArrayList<ProvinceAttr.CityAttr>(CityAttrs.size());
		}
		for (int i = 0; i < CityAttrs.size(); i++)
		{
			this.CityAttrs.add(CityAttrs.get(i));
		}
		
	}
	public String id;
	public String name;
	public List<CityAttr> CityAttrs =null;
	public class CityAttr
	{
		public String getId()
		{
			return id;
		}
		public void setId(String id)
		{
			this.id = id;
		}
		public String getName()
		{
			return name;
		}
		public void setName(String name)
		{
			this.name = name;
		}
		public  String id;
		public String name;
		public List<CountyAttr> CountyAttr =null;
		public List<CountyAttr> getCountyAttr()
		{
			return CountyAttr;
		}
		public void setCountyAttr(List<CountyAttr> CountyAttr)
		{
			if (this.CountyAttr==null)
			{
				this.CountyAttr= new ArrayList<ProvinceAttr.CountyAttr>(CountyAttr.size());
			}
			for (int i = 0; i < CountyAttr.size(); i++)
			{
				this.CountyAttr.add(CountyAttr.get(i));
			}
		}
		
		
	}
	public class CountyAttr
	{
		public String getId()
		{
			return id;
		}
		public void setId(String id)
		{
			this.id = id;
		}
		public String getName()
		{
			return name;
		}
		public void setName(String name)
		{
			this.name = name;
		}
		public String id;
		public String name;
	}
}
