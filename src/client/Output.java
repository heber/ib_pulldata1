package client;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Output {

	private Map<Integer, String> id_Stock;
	private DataStore dataStore;
	
	public Output(Map<Integer, String> id_Stock){
		this.id_Stock=id_Stock;
		dataStore=new DataStore();
		
	}
	
	

	/**
	 
	 * @param reqId
	 * @param date
	 * @param open
	 * @param high
	 * @param low
	 * @param close
	 * @param volume
	 * @param count
	 * @param WAP
	 * @param hasGaps
	 */
	public void update(String stock, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP){
		
		
		try{
			long time=Long.parseLong(date);
		
			Entry openData=new Entry(stock, time, "open", open);
			Entry highData=new Entry(stock, time, "high", high);
			Entry lowData=new Entry(stock, time, "low", low);
			Entry closeData=new Entry(stock, time, "close", close);
			Entry volumeData=new Entry(stock, time, "volume", volume);
			Entry countData=new Entry(stock, time, "count", count);
			Entry WAPData=new Entry(stock, time, "WAP", WAP);
		
			dataStore.addEntry(openData);
			dataStore.addEntry(highData);
			dataStore.addEntry(lowData);
			dataStore.addEntry(closeData);
			dataStore.addEntry(volumeData);
			dataStore.addEntry(countData);
			dataStore.addEntry(WAPData);
		}
		catch(NumberFormatException e){
			//do nothing, because I don't want anything happening here.
		}
		
	}
	
	public void flush(){
		dataStore.flush();
	}
}
