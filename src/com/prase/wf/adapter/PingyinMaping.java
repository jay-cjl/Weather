package com.prase.wf.adapter;

import java.util.HashMap;
import java.util.Map;

public class PingyinMaping
{
	private Map<String, String> table = new HashMap<String, String>();
	public PingyinMaping()
	{
		// TODO Auto-generated constructor stub
		table.put("��ѩ", "baoxue");
		table.put("����", "baoyu");
		table.put("����ת����", "baoyuzhuandabaoyu");
		table.put("����ת�ر���", "dabaoyuzhuantedabaoyu");
		table.put("��ѩ", "daxue");
		table.put("��ѩת��ѩ", "daxuezhuanbaoxue");
		
		table.put("����", "dayu");
		table.put("����ת����", "dayuzhuanbaoyu");
		table.put("����", "dongyu");
		table.put("����", "duoyun");
		table.put("����", "fuchen");
		table.put("������", "leizhenyu");
		table.put("��������б���", "leizhenyubanyoubingbao");
		
		table.put("��", "mai");
		table.put("ǿɳ����", "qiangshachenbao");
		table.put("��", "qing");
		table.put("ɳ����", "shachenbao");
		table.put("�ر���", "tedabaoyu");
		table.put("��", "wu");
		table.put("Сѩ", "xiaoxue");

		table.put("Сѩת��ѩ", "xiaoxuezhuanzhongxue");
		table.put("С��", "xiaoyu");
		table.put("С��ת����", "xiaoyuzhuanzhongyu");
		table.put("��ɳ", "yangsha");
		table.put("��", "yin");
		table.put("���ѩ", "yujiaxue");
		table.put("��ѩ", "zhenxue");
		
		table.put("����", "zhenyu");
		table.put("��ѩ", "zhongxue");
		table.put("��ѩת��ѩ", "zhongxuezhuandaxue");
		table.put("����", "zhongyu");
		table.put("����ת����", "zhongyuzhuandayu");
	}
	public String getString(String wt)
	{
		return table.get(wt);
	}
}
