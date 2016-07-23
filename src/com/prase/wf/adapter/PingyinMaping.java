package com.prase.wf.adapter;

import java.util.HashMap;
import java.util.Map;

public class PingyinMaping
{
	private Map<String, String> table = new HashMap<String, String>();
	public PingyinMaping()
	{
		// TODO Auto-generated constructor stub
		table.put("±©Ñ©", "baoxue");
		table.put("±©Óê", "baoyu");
		table.put("±©Óê×ª´ó±©Óê", "baoyuzhuandabaoyu");
		table.put("´ó±©Óê×ªÌØ±©Óê", "dabaoyuzhuantedabaoyu");
		table.put("´óÑ©", "daxue");
		table.put("´óÑ©×ª±©Ñ©", "daxuezhuanbaoxue");
		
		table.put("´óÓê", "dayu");
		table.put("´óÓê×ª±©Óê", "dayuzhuanbaoyu");
		table.put("¶³Óê", "dongyu");
		table.put("¶àÔÆ", "duoyun");
		table.put("¸¡³¾", "fuchen");
		table.put("À×ÕóÓê", "leizhenyu");
		table.put("À×ÕóÓê°éÓĞ±ù±¢", "leizhenyubanyoubingbao");
		
		table.put("ö²", "mai");
		table.put("Ç¿É³³¾±©", "qiangshachenbao");
		table.put("Çç", "qing");
		table.put("É³³¾±©", "shachenbao");
		table.put("ÌØ±©Óê", "tedabaoyu");
		table.put("Îí", "wu");
		table.put("Ğ¡Ñ©", "xiaoxue");

		table.put("Ğ¡Ñ©×ªÖĞÑ©", "xiaoxuezhuanzhongxue");
		table.put("Ğ¡Óê", "xiaoyu");
		table.put("Ğ¡Óê×ªÖĞÓê", "xiaoyuzhuanzhongyu");
		table.put("ÑïÉ³", "yangsha");
		table.put("Òõ", "yin");
		table.put("Óê¼ĞÑ©", "yujiaxue");
		table.put("ÕóÑ©", "zhenxue");
		
		table.put("ÕóÓê", "zhenyu");
		table.put("ÖĞÑ©", "zhongxue");
		table.put("ÖĞÑ©×ª´óÑ©", "zhongxuezhuandaxue");
		table.put("ÖĞÓê", "zhongyu");
		table.put("ÖĞÓê×ª´óÓê", "zhongyuzhuandayu");
	}
	public String getString(String wt)
	{
		return table.get(wt);
	}
}
