package tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTool {
	public String getDateSystem(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
		Date date = new Date();
		return dateFormat.format(date); 
	}
	public String getTimeSystem(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
