package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String date1="01/01/2014";
		String date2="02/04/2014";
		
		List<String> range=dateRange(date1, date2);
		System.out.println(range);
	}
	
	
	/**
	 * This method takes as input two strings with startDate and endDate, and produces
	 * a list of strings with all the date ranges in between, using the special
	 * form that Interactive Brokers API takes as input.
	 * 
	 * The form of the startDate and endDate is "mm/dd/yyyy"
	 * The form of the strings in the return list is "yyyymmdd hh:mm:ss TZ"
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private static List<String> dateRange(String startDate, String endDate){
		List<String> toReturn=new ArrayList<String>();
		
		String [] sdTokens=startDate.split("/");
		String [] edTokens=endDate.split("/");
		
		int sdMonth=Integer.parseInt(sdTokens[0])-1;
		int sdDay=Integer.parseInt(sdTokens[1]);
		int sdYear=Integer.parseInt(sdTokens[2]);
		
		int edMonth=Integer.parseInt(edTokens[0])-1;
		int edDay=Integer.parseInt(edTokens[1]);
		int edYear=Integer.parseInt(edTokens[2]);
		
		Calendar roller=GregorianCalendar.getInstance();
		TimeZone zn=TimeZone.getTimeZone("EST");
		roller.setTimeZone(zn);
		roller.set(sdYear, sdMonth, sdDay, 11, 30, 0);
		
		Calendar end=GregorianCalendar.getInstance();
		end.setTimeZone(zn);
		end.set(edYear, edMonth, edDay, 16, 0, 0);
		
		if (end.getTime().before(roller.getTime())) return toReturn;
		
		int [] hourValues={16, 14, 12, 10};
		
		System.out.println(roller.getTime());
		System.out.println(end.getTime());
		while (roller.getTime().before(end.getTime())){
			StringBuilder sb=new StringBuilder("");
			if (isDayOfWeek(roller)){
				
				//1. append year
				sb.append(Integer.toString(roller.get(Calendar.YEAR)));
				
				//2. append month
				int month=roller.get(Calendar.MONTH)+1;
				if (month<=9){
					sb.append(Integer.toString(0));
				}
				sb.append(Integer.toString(month));
				
				//3. append day
				int day=roller.get(Calendar.DAY_OF_MONTH);
				if (day<=9){
					sb.append(Integer.toString(0));
				}
				sb.append(Integer.toString(day));
				
				//4. append hours
				sb.append(" ");
				for (int i=0; i<hourValues.length; ++i){
					int hour=hourValues[i];
					StringBuilder temp=new StringBuilder(sb.toString());
					if (hour<=9){
						temp.append(Integer.toString(0));
					}
					temp.append(Integer.toString(hour)).append(":00:00 ");
					
					//4.1. append time zone
					temp.append("EST");
					toReturn.add(temp.toString());
				}		
				
			}
			roller.add(Calendar.HOUR, 24);
			
			System.out.println(roller.getTime());
		}
		return toReturn;
	}
	
	private static boolean isDayOfWeek(Calendar cal){
		if (cal!=null){
			int day=cal.get(Calendar.DAY_OF_WEEK);
			if (day==Calendar.MONDAY || day==Calendar.TUESDAY || day==Calendar.WEDNESDAY ||
					day==Calendar.THURSDAY || day==Calendar.FRIDAY){
				return true;
			}
			return false;
		}
		return false;
	}	
}


