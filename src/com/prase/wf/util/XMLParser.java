package com.prase.wf.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.prase.wf.util.ProvinceAttr.CountyAttr;
import com.prase.wf.util.ProvinceAttr.CityAttr;

public class XMLParser
{
	private XMLContentHandler handler;
	public List<ProvinceAttr> readXML(InputStream inStream)
	{
		try
		{
			// 创建解析器
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser saxParser = spf.newSAXParser();
			handler= new XMLContentHandler();
			saxParser.parse(inStream, handler);
			inStream.close();
			List<ProvinceAttr> list = handler.getProvinceAttrs();
			return list;
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// SAX类：DefaultHandler，它实现了ContentHandler接口。在实现的时候，只需要继承该类，重载相应的方法即可。
	public class XMLContentHandler extends DefaultHandler
	{

		private List<ProvinceAttr> plist = null;
		private List<CityAttr> clist = null;
		private List<CountyAttr> thirdlist= null;
		private ProvinceAttr currentProvinceAttr ;
		private CityAttr currentCity;
		private CountyAttr currentCountyAttr;
		public List<ProvinceAttr> getProvinceAttrs()
		{
			return plist;
		}

		// 接收文档开始的通知。当遇到文档的开头的时候，调用这个方法，可以在其中做一些预处理的工作。
		@Override
		public void startDocument() throws SAXException
		{
			plist = new ArrayList<ProvinceAttr>();
			clist = new ArrayList<CityAttr>();
			thirdlist = new ArrayList<CountyAttr>();
		}

		// 接收元素开始的通知。当读到一个开始标签的时候，会触发这个方法。其中namespaceURI表示元素的命名空间；
		// localName表示元素的本地名称（不带前缀）；qName表示元素的限定名（带前缀）；atts 表示元素的属性集合
		@Override
		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) throws SAXException
		{

			if (localName.equals("province"))
			{
				currentProvinceAttr = new ProvinceAttr();
				currentProvinceAttr.setId(atts.getValue("id"));
				currentProvinceAttr.setName(atts.getValue("name"));
				if(!clist.isEmpty())
					clist.clear();
				
			}
			else if (localName.equals("city"))
			{
				currentCity = currentProvinceAttr.new CityAttr();
				currentCity.setId(atts.getValue("id"));
				currentCity.setName(atts.getValue("name"));
				
			}
			else if (localName.equals("county"))
			{
				currentCountyAttr = currentProvinceAttr.new CountyAttr();
				currentCountyAttr.setId(atts.getValue("id"));
				currentCountyAttr.setName(atts.getValue("name"));
				thirdlist.add(currentCountyAttr);
			}
		}

		// 接收字符数据的通知。该方法用来处理在XML文件中读到的内容，第一个参数用于存放文件的内容，
		// 后面两个参数是读到的字符串在这个数组中的起始位置和长度，使用new String(ch,start,length)就可以获取内容。
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException
		{

//			if (tagName != null)
//			{
//				String data = new String(ch, start, length);
//				if (tagName.equals("name"))
//				{
//		//			this.currentPerson.setName(data);
//				} else if (tagName.equals("age"))
//				{
//		//			this.currentPerson.setAge(Short.parseShort(data));
//				}
//			}
		}

		// 接收文档的结尾的通知。在遇到结束标签的时候，调用这个方法。其中，uri表示元素的命名空间；
		// localName表示元素的本地名称（不带前缀）；name表示元素的限定名（带前缀）
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException
		{

			if (localName.equals("province"))
			{
				currentProvinceAttr.setCityAttrs(clist);
				plist.add(currentProvinceAttr);
				currentProvinceAttr = null;
				clist.clear();
			}
			else if(localName.equals("city"))
			{
				
				currentCity.setCountyAttr(thirdlist);
				clist.add(currentCity);
				thirdlist.clear();
				currentCity = null;
			}
			else if (localName.equals("county"))
			{
				currentCountyAttr = null;
			}
		}
	}
}
