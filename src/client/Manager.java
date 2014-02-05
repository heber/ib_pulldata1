package client;

import java.util.HashMap;
import java.util.Map;
import endlessqueue.EndlessQueue;

public class Manager {

	private Output output;
	private Map<Integer, Tuple> reqId_Request;
	private Map<Tuple, Integer> request_reqId;
	private EndlessQueue<Tuple> requests;
	private Client client;
		
	public Manager(Output output){
		this.output=output;
		this.client=new Client(output, this);
		reqId_Request=new HashMap<Integer, Tuple>();
		request_reqId=new HashMap<Tuple, Integer>();
		requests=new EndlessQueue<Tuple>();
	}
	
	public void addRequest(String symbol, String date){
		Tuple request=new Tuple(symbol, date);
		int reqId=reqId_Request.size();
		reqId_Request.put(reqId, request);
		request_reqId.put(request, reqId);
		requests.add(request);
	}
	
	public void requestData(){
		
		client.s_connect("", 4002, 5);
		
		while (!requests.isEmpty()){
			try{
				Thread.sleep(10001);
				Tuple request=requests.popHead();
				if (request!=null){
					client.s_getHistoricalData(request_reqId.get(request), request.getSymbol(), request.getDate(), "7200 S", "5 secs", "TRADES");
				}
			}
			catch(InterruptedException ex){
				
			}
		}
		
		output.flush();
		client.s_disconnect();
	}
	
	
	public void requestReceived(int requestId, String date, double open, double high, double low, double close, int volume, int count, double WAP){
		
		//This is interesting here. I don't know how this would work with multithreading.
		
		if (reqId_Request.containsKey(requestId)){
			Tuple request=reqId_Request.get(requestId);
			output.update(request.getSymbol(), date, open, high, low, close, volume, count, WAP);
			requests.remove(request);
		}
	}
	
}