package com.cds.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cds.field.Constants;

public class DateUtils {
	public String getDateSystem(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date); 
	}
	public String getYear(String date){
		if(!date.equals("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    try {
		        Date sDate = dateFormat.parse(date);
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(sDate);
		        int year = cal.get(Calendar.YEAR);
		        return year + Constants.EMPTY;
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		}
	    return Constants.EMPTY;
	}
	public String getMonth(String date){
		if(!date.equals("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    try {
		        Date sDate = dateFormat.parse(date);
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(sDate);
		        int month = cal.get(Calendar.MONTH) + 1;
		        return month + Constants.EMPTY;
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		}
	    return Constants.EMPTY;
	}
	public String getMonthYearSystem(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		return dateFormat.format(date); 
	}
	public String getTimeSystem(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public String getStartDateSystem(){
		return getMonthYearSystem() + "-01";
	}
	public String getEndDateSystem(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateFormat.parse(getStartDateSystem());
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, 1);
			c.add(Calendar.DATE, -1);
			return dateFormat.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
