package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import client.Client;
import client.Manager;
import client.Output;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String, Integer> stock_Id=new HashMap<String, Integer>();
		Map<Integer, String> id_Stock=new HashMap<Integer, String>();
		
		List<String> symbols;
		List<String> dates;
		
		if (args.length>0){
			symbols=readSymbols(args[0]);
		}
		else {
			symbols=new ArrayList<String>();
			symbols.add("AAPL");
			symbols.add("MU");
		}
		
		if (args.length>1){
			dates=readDates(args[1]);
		}
		else{
			dates=new ArrayList<String>();
			dates.add("20140124 10:30:00 EST");	
		}
		
		int clientId=5;
		//These symbols are the Nasdaq 100 companies
		/*
		String [] symbols={"AAPL", "ADBE", "ADI", "ADP", "ADSK", "AKAM", "ALTR", "ALXN", "AMAT", "AMGN", "AMZN", "ATVI", "AVGO", "BBBY", "BIDU", 
				"BIIB", "BRCM", "CA", "CELG", "CERN", "CHKP", "CHRW", "CHTR", "CMCSA", "COST", "CSCO", "CTRX", "CTSH", "CTXS", "DISCA", "DLTR", "DTV", "EBAY",
				"EQIX", "ESRX", "EXPD", "EXPE", "FAST", "FB", "FFIV", "FISV", "FOSL", "FOXA", "GILD", "GMCR", "GOOG", "GRMN", "HSIC", "INTC", "INTU", "ISRG", 
				"KLAC", "KRFT", "LBTYA", "LINTA", "LLTC", "LMCA", "MAR", "MAT", "MCHP", "MDLZ", "MNST", "MSFT", "MU", "MXIM", "MYL", "NFLX", "NTAP", "NUAN",
				"NVDA", "ORLY", "PAYX", "PCAR", "PCLN", "QCOM", "REGN", "ROST", "SBAC", "SBUX", "SHLD", "SIAL", "SIRI", "SNDK", "SPLS", "SRCL", "STX", "SYMC",
				"TSLA", "TXN", "VIAB", "VIP", "VOD", "VRSK", "VRTX", "WDC", "WFM", "WYNN", "XLNX", "XRAY", "YHOO"};
		*/
		
		
		
		
		//Dates from where I will extract the info.
		/*
		String [] dates={
				
				"20130930 16:00:00 EST", "20130930 14:00:00 EST", "20130930 12:00:00 EST", "20130930 10:00:00 EST",
				"20130927 16:00:00 EST", "20130927 14:00:00 EST", "20130927 12:00:00 EST", "20130927 10:00:00 EST",
				"20130926 16:00:00 EST", "20130926 14:00:00 EST", "20130926 12:00:00 EST", "20130926 10:00:00 EST",
				"20130925 16:00:00 EST", "20130925 14:00:00 EST", "20130925 12:00:00 EST", "20130925 10:00:00 EST",
				"20130924 16:00:00 EST", "20130924 14:00:00 EST", "20130924 12:00:00 EST", "20130924 10:00:00 EST",
				"20130923 16:00:00 EST", "20130923 14:00:00 EST", "20130923 12:00:00 EST", "20130923 10:00:00 EST",
				"20130920 16:00:00 EST", "20130920 14:00:00 EST", "20130920 12:00:00 EST", "20130920 10:00:00 EST",
				"20130919 16:00:00 EST", "20130919 14:00:00 EST", "20130919 12:00:00 EST", "20130919 10:00:00 EST",
				"20130918 16:00:00 EST", "20130918 14:00:00 EST", "20130918 12:00:00 EST", "20130918 10:00:00 EST",
				"20130917 16:00:00 EST", "20130917 14:00:00 EST", "20130917 12:00:00 EST", "20130917 10:00:00 EST",
				"20130916 16:00:00 EST", "20130916 14:00:00 EST", "20130916 12:00:00 EST", "20130916 10:00:00 EST",
				"20130913 16:00:00 EST", "20130913 14:00:00 EST", "20130913 12:00:00 EST", "20130913 10:00:00 EST",
				"20130912 16:00:00 EST", "20130912 14:00:00 EST", "20130912 12:00:00 EST", "20130912 10:00:00 EST",
				"20130911 16:00:00 EST", "20130911 14:00:00 EST", "20130911 12:00:00 EST", "20130911 10:00:00 EST",
				"20130910 16:00:00 EST", "20130910 14:00:00 EST", "20130910 12:00:00 EST", "20130910 10:00:00 EST",
				"20130909 16:00:00 EST", "20130909 14:00:00 EST", "20130909 12:00:00 EST", "20130909 10:00:00 EST",
				"20130906 16:00:00 EST", "20130906 14:00:00 EST", "20130906 12:00:00 EST", "20130906 10:00:00 EST",
				"20130905 16:00:00 EST", "20130905 14:00:00 EST", "20130905 12:00:00 EST", "20130905 10:00:00 EST",
				"20130904 16:00:00 EST", "20130904 14:00:00 EST", "20130904 12:00:00 EST", "20130904 10:00:00 EST",
				"20130903 16:00:00 EST", "20130903 14:00:00 EST", "20130903 12:00:00 EST", "20130903 10:00:00 EST",
				"20130902 16:00:00 EST", "20130902 14:00:00 EST", "20130902 12:00:00 EST", "20130902 10:00:00 EST"
				
								
		};
		 */
		
		
		
		
		int count=1;
		for (String stock : symbols){
			
			stock_Id.put(stock, count);
			id_Stock.put(count, stock);
			count=count+1;
		}
		
		Output output=new Output(id_Stock);
		Manager manager=new Manager(output);
				
		for (String symbol : symbols){
			for (String date : dates){
				
				manager.addRequest(symbol, date);
				
			}
		}
		
		manager.requestData();
		output.flush();
	}

	private static List<String> readSymbols(String filePath){
		return null;
	}
	
	private static List<String> readDates(String filePath){
		return null;
	}
	
}