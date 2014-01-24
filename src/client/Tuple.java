package client;

public class Tuple {

	private String date;
	private String symbol;
	
	public Tuple (String symbol, String date){
		this.date=date;
		this.symbol=symbol;
		
	}
	
	public String getDate(){ return date; }
	public String getSymbol(){ return symbol; }
	
	@Override
	public int hashCode(){
		return date.hashCode()+37*symbol.hashCode();
	}

	@Override
	public String toString(){
		return date+" "+symbol;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Tuple){
			Tuple temp=(Tuple)o;
			if (temp.date.equals(this.date) && temp.symbol.equals(this.symbol))
			{
				return true;
			}
			else return false;
		}
		else{
			return false;
		}
	}
}
