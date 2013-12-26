package client;

public class Entry {

	private String stock;
	private long time;
	private String marker;
	private double value;
	
	public Entry(String stock, long time, String marker, double value){
		this.stock=stock;
		this.time=time;
		this.marker=marker;
		this.value=value;
	}
	
	public String getStock(){
		return stock;
	}
	
	public long getTime(){
		return time;
		
	}
	
	public String getMarker(){
		return marker;
	}
	
	public double getValue(){
		return value;
	}

	public String toString(){
		StringBuilder sb=new StringBuilder("");
		sb.append(marker).append(" ").append(time).append(" ");
		sb.append(value).append(" ").append("reqtype=").append(stock).append("\n");
		return sb.toString();
	}
}
