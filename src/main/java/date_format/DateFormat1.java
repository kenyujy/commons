package date_format;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateFormat1 {
	
	public static void main(String[] args) {
		Locale China= Locale.CHINA;
		Locale US= Locale.US;

		Date date= new Date();
		//style日期风格 FULL/LONG/MEDIUM/SHORT
		DateFormat dateInstance= DateFormat.getDateInstance(DateFormat.FULL,Locale.CHINA);
		String date1= dateInstance.format(date);
		System.out.println(date1); // 2019年11月12日星期二
		
		dateInstance= DateFormat.getDateInstance(DateFormat.FULL,Locale.US);
		String date2= dateInstance.format(date);
		System.out.println(date2); // Tuesday, November 12, 2019
		
		dateInstance= DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
		String date3= dateInstance.format(date);
		System.out.println(date3); // 2019年11月12日
		
		dateInstance= DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
		String date4= dateInstance.format(date);
		System.out.println(date4); // November 12, 2019
		
		dateInstance= DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		String date5= dateInstance.format(date);
		System.out.println(date5); // 2019年11月12日
		
		dateInstance= DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
		String date6= dateInstance.format(date);
		System.out.println(date6); // Nov 12, 2019
		
		//SimpleDateFormat
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String strDate= formatter.format(date);  
	    System.out.println(strDate);   //2019-11-12
	    
	    formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    strDate= formatter.format(date);  
	    System.out.println(strDate);   //2019-11-12 01:10:14
	    
	    String s="hello ";
	    addstr(s);
	    System.out.println(s);
	    s+="world";
	    System.out.println(s);
	    
	    String[] strArray= {"hello", "world", "bonjour", "hello"};
	    List list= Arrays.asList(strArray);
	    int i= list.indexOf(list.get(3));
	    System.out.println(i);
	}
	
	public static String addstr(String s) {
		return s= s+ "world"; // 返回的s 不再是原来传入的
	}
}
