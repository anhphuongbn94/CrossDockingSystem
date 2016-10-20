package com.cds.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTool {
	public String getDateSystem(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date); 
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
	public int getNumPage(int total, int sizePage){
		int numPage=total/sizePage + 1;
		if(total%sizePage == 0) numPage = total/sizePage;
		return numPage;
	}
}
