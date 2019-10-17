package com.yc.news.utils;

import java.io.Serializable;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author navy
 */
@SuppressWarnings("serial")
public class StringUtil implements Serializable {
	/**
	 * 判断给定的字符是否为空
	 * @param str：要判断的字符
	 * @return：如果为空返回true,否则返回false
	 */
	public static boolean isNullorEmpty(String str){
		if(str==null){
			return true;
		}
		if("".equals(str.trim())){
			return true;
		}
		return false;
	}

	/**
	 * 比较两个字符串是否相等
	 * @param str1
	 * @param str2
	 * @return true:表示两个字符串相等  false:表示不相等
	 */
	public static boolean Compare(String str1,String str2){
		if(str1.equals(str2)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 编码集转换
	 * @param str：要转为utf-8的字符串
	 * @return：转换后的字符串
	 */
	public static String Utf8Util(String str){
		String result="";

		try {
			result=new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 日期格式转换
	 * @param date   如：12/1/2013
	 * @return 2013-12-1
	 */
	public static String dateConvert(String date)
	{	if(!"".equals(date)&&date!=null){
		String[] dates = date.split("/");
		String dateResult = dates[2] + "-" + dates[0] + "-" + dates[1];
		return dateResult;
		}else{
			Date dt=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 return	sdf.format(dt).toString();
		}
		
	}

}
