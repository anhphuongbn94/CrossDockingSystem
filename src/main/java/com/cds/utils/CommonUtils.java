package com.cds.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class CommonUtils {
	public String getJsonAssignDoor(){
		String json="";
		return json;
	}
    public static void main(String[] args) throws Exception {
    	ArrayList list=new ArrayList();
    	list.add(1); list.add(2); list.add(3);
    	
    	Object[] arr = list.toArray();
    	String str = Arrays.toString(arr).replace("[", "(").replace("]", ")");
    	System.out.println(str);
    	
    } 
}
