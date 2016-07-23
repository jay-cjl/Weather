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
			// ����������
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

	// SAX�ࣺDefaultHandler����ʵ����ContentHandler�ӿڡ���ʵ�ֵ�ʱ��ֻ��Ҫ�̳и��࣬������Ӧ�ķ������ɡ�
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

		// �����ĵ���ʼ��֪ͨ���������ĵ��Ŀ�ͷ��ʱ�򣬵������������������������һЩԤ����Ĺ�����
		@Override
		public void startDocument() throws SAXException
		{
			plist = new ArrayList<ProvinceAttr>();
			clist = new ArrayList<CityAttr>();
			thirdlist = new ArrayList<CountyAttr>();
		}

		// ����Ԫ�ؿ�ʼ��֪ͨ��������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������������namespaceURI��ʾԪ�ص������ռ䣻
		// localName��ʾԪ�صı������ƣ�����ǰ׺����qName��ʾԪ�ص��޶�������ǰ׺����atts ��ʾԪ�ص����Լ���
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

		// �����ַ����ݵ�֪ͨ���÷�������������XML�ļ��ж��������ݣ���һ���������ڴ���ļ������ݣ�
		// �������������Ƕ������ַ�������������е���ʼλ�úͳ��ȣ�ʹ��new String(ch,start,length)�Ϳ��Ի�ȡ���ݡ�
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

		// �����ĵ��Ľ�β��֪ͨ��������������ǩ��ʱ�򣬵���������������У�uri��ʾԪ�ص������ռ䣻
		// localName��ʾԪ�صı������ƣ�����ǰ׺����name��ʾԪ�ص��޶�������ǰ׺��
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
