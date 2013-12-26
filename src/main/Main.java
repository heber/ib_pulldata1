package main;

import java.util.HashMap;
import java.util.Map;
import client.Client;
import client.Output;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String, Integer> stock_Id=new HashMap<String, Integer>();
		Map<Integer, String> id_Stock=new HashMap<Integer, String>();
		//These symbols are the Nasdaq 100 companies
		String [] symbols={"AAPL", "ADBE", "ADI", "ADP", "ADSK", "AKAM", "ALTR", "ALXN", "AMAT", "AMGN", "AMZN", "ATVI", "AVGO", "BBBY", "BIDU", 
				"BIIB", "BRCM", "CA", "CELG", "CERN", "CHKP", "CHRW", "CHTR", "CMCSA", "COST", "CSCO", "CTRX", "CTSH", "CTXS", "DISCA", "DLTR", "DTV", "EBAY",
				"EQIX", "ESRX", "EXPD", "EXPE", "FAST", "FB", "FFIV", "FISV", "FOSL", "FOXA", "GILD", "GMCR", "GOOG", "GRMN", "HSIC", "INTC", "INTU", "ISRG", 
				"KLAC", "KRFT", "LBTYA", "LINTA", "LLTC", "LMCA", "MAR", "MAT", "MCHP", "MDLZ", "MNST", "MSFT", "MU", "MXIM", "MYL", "NFLX", "NTAP", "NUAN",
				"NVDA", "ORLY", "PAYX", "PCAR", "PCLN", "QCOM", "REGN", "ROST", "SBAC", "SBUX", "SHLD", "SIAL", "SIRI", "SNDK", "SPLS", "SRCL", "STX", "SYMC",
				"TSLA", "TXN", "VIAB", "VIP", "VOD", "VRSK", "VRTX", "WDC", "WFM", "WYNN", "XLNX", "XRAY", "YHOO"};
		
		String [] dates={
				
				"20131031 16:00:00 EST", "20131031 14:00:00 EST", "20131031 12:00:00 EST", "20131031 10:00:00 EST",
				"20131030 16:00:00 EST", "20131030 14:00:00 EST", "20131030 12:00:00 EST", "20131030 10:00:00 EST",
				"20131029 16:00:00 EST", "20131029 14:00:00 EST", "20131029 12:00:00 EST", "20131029 10:00:00 EST",
				"20131028 16:00:00 EST", "20131028 14:00:00 EST", "20131028 12:00:00 EST", "20131028 10:00:00 EST",
				"20131025 16:00:00 EST", "20131025 14:00:00 EST", "20131025 12:00:00 EST", "20131025 10:00:00 EST",
				"20131024 16:00:00 EST", "20131024 14:00:00 EST", "20131024 12:00:00 EST", "20131024 10:00:00 EST",
				"20131023 16:00:00 EST", "20131023 14:00:00 EST", "20131023 12:00:00 EST", "20131023 10:00:00 EST",
				"20131022 16:00:00 EST", "20131022 14:00:00 EST", "20131022 12:00:00 EST", "20131022 10:00:00 EST",
				"20131021 16:00:00 EST", "20131021 14:00:00 EST", "20131021 12:00:00 EST", "20131021 10:00:00 EST",
				"20131018 16:00:00 EST", "20131018 14:00:00 EST", "20131018 12:00:00 EST", "20131018 10:00:00 EST",
				"20131017 16:00:00 EST", "20131017 14:00:00 EST", "20131017 12:00:00 EST", "20131017 10:00:00 EST",
				"20131016 16:00:00 EST", "20131016 14:00:00 EST", "20131016 12:00:00 EST", "20131016 10:00:00 EST",
				"20131015 16:00:00 EST", "20131015 14:00:00 EST", "20131015 12:00:00 EST", "20131015 10:00:00 EST",
				"20131014 16:00:00 EST", "20131014 14:00:00 EST", "20131014 12:00:00 EST", "20131014 10:00:00 EST",
				"20131011 16:00:00 EST", "20131011 14:00:00 EST", "20131011 12:00:00 EST", "20131011 10:00:00 EST",
				"20131010 16:00:00 EST", "20131010 14:00:00 EST", "20131010 12:00:00 EST", "20131010 10:00:00 EST",
				"20131009 16:00:00 EST", "20131009 14:00:00 EST", "20131009 12:00:00 EST", "20131009 10:00:00 EST",
				"20131008 16:00:00 EST", "20131008 14:00:00 EST", "20131008 12:00:00 EST", "20131008 10:00:00 EST",
				"20131007 16:00:00 EST", "20131007 14:00:00 EST", "20131007 12:00:00 EST", "20131007 10:00:00 EST",
				"20131004 16:00:00 EST", "20131004 14:00:00 EST", "20131004 12:00:00 EST", "20131004 10:00:00 EST",
				"20131003 16:00:00 EST", "20131003 14:00:00 EST", "20131003 12:00:00 EST", "20131003 10:00:00 EST",
				"20131002 16:00:00 EST", "20131002 14:00:00 EST", "20131008 12:00:02 EST", "20131002 10:00:00 EST",
				"20131001 16:00:00 EST", "20131001 14:00:00 EST", "20131001 12:00:00 EST", "20131001 10:00:00 EST",
				
		};

		int count=1;
		for (String stock : symbols){
			
			stock_Id.put(stock, count);
			id_Stock.put(count, stock);
			count=count+1;
		}
		
		Output output=new Output(id_Stock);
		Client client=new Client(output);
		client.s_connect("", 4001, 0);
				
		
		for (String symbol : symbols){
			for (String date : dates){
				
				try{
					Thread.sleep(10001);
					client.s_getHistoricalData(stock_Id.get(symbol), symbol, date, "7200 S", "5 secs", "TRADES");
				}
				catch(InterruptedException ex){
					//Do nothing and keep working on your loop
				}
				
			}
		}
		
		try{
			Thread.sleep(10001);
			
		}
		catch(InterruptedException e){
			//do nothing here
		}
		
		output.flush();
		client.s_disconnect();
	}

}


