package client;

import java.util.HashMap;
import java.util.Map;

import com.ib.client.*;

public class Client implements EWrapper{
	
	private EClientSocket m_client= new EClientSocket(this);
	
	private boolean m_disconnectInProgress=false;
	public boolean  m_bIsFAAccount = false;
	public String   m_FAAcctCodes;
	private Output output;
	Manager manager;
	
    private static final int NOT_AN_FA_ACCOUNT_ERROR = 321 ;
    private int faErrorCodes[] = { 503, 504, 505, 522, 1100, NOT_AN_FA_ACCOUNT_ERROR } ;
	private boolean faError;
	
    String faGroupXML ;
    String faProfilesXML ;
    String faAliasesXML ;

	private int s_requestCounter;
	private Map<Integer, Integer> requestMap=new HashMap<Integer, Integer>();

    public Client(Output output, Manager manager){
    	this.output=output;
    	this.manager=manager;
    }
    
    public boolean isConnected(){
    	return m_client.isConnected();
    }
    
    //
    //
    //		A. Functions to send requests
    //
    //
    //
    
    /**
     * 
     * @param retIpAddress
     * @param retPort
     * @param retClientId
     */
    public void s_connect(String retIpAddress, int retPort, int retClientId){
    	
    	m_bIsFAAccount=false;
    	m_disconnectInProgress=false;
    	
    	m_client.eConnect(retIpAddress, retPort, retClientId);
    	if (m_client.isConnected()){
    		String msg="Connected to Tws server version " +
                    m_client.serverVersion() + " at " +
                    m_client.TwsConnectionTime();
            printMessage(msg);
    	}
    }
    
    public void s_disconnect(){
    	m_disconnectInProgress=true;
    	m_client.eDisconnect();
    	String msg="Disconnected from client";
    	printMessage(msg);
    }
    
    public void s_getRealTimeBars(int reqId, String symbol){
    	Contract contract=new Contract();
    	contract.m_exchange="SMART";
    	contract.m_currency="USD";
    	contract.m_symbol=symbol;
    	contract.m_secType="STK";
    	
    	m_client.reqRealTimeBars(reqId, contract, 5, "MIDPOINT", false);
    	 
    }
    
        public void s_getHistoricalData(int reqId, String symbol, String endDateTime, String durationStr, String barSizeSetting, String whatToShow){
    	Contract contract=new Contract();
    	contract.m_exchange="SMART";
    	contract.m_currency="USD";
    	contract.m_symbol=symbol;
    	contract.m_secType="STK";
    	    	
    	m_client.reqHistoricalData(s_requestCounter, contract, endDateTime, durationStr, barSizeSetting, whatToShow, 0, 2);
    	requestMap.put(s_requestCounter, reqId);
    	s_requestCounter++;
    }
    
    public void s_sellMarketOrder(int reqId, String symbol, int numberOfShares){
    	Contract contract=new Contract();
    	Order order=new Order();
    	
    	contract.m_symbol=symbol;
    	contract.m_secType="STK";
    	contract.m_exchange="SMART";
    	contract.m_currency="USD";
    	
    	order.m_action="SELL";
    	order.m_totalQuantity=numberOfShares;
    	order.m_orderType="MKT";
    	
    	m_client.placeOrder(reqId, contract, order);
    }
    
    public void s_sellLimitOrder(int reqId, String symbol, int numberOfShares, double limitPrice){
    	Contract contract=new Contract();
    	Order order=new Order();
    	
    	contract.m_symbol=symbol;
    	contract.m_secType="STK";
    	contract.m_exchange="SMART";
    	contract.m_currency="USD";
    	
    	order.m_action="SELL";
    	order.m_totalQuantity=numberOfShares;
    	order.m_orderType="LMT";
    	order.m_lmtPrice=limitPrice;
    	
    	m_client.placeOrder(reqId, contract, order);
    }
    
    public void s_buyMarketOrder(int reqId, String symbol, int numberOfShares){
    	Contract contract=new Contract();
    	Order order=new Order();
    	
    	contract.m_symbol=symbol;
    	contract.m_secType="STK";
    	contract.m_exchange="SMART";
    	contract.m_currency="USD";
    	
    	order.m_action="BUY";
    	order.m_totalQuantity=numberOfShares;
    	order.m_orderType="MKT";
    	
    	m_client.placeOrder(reqId, contract, order);

    }
    
    public void s_Order(){
    	
    }
    
    
    
    //
    //
    //
    //		B. Functions to receive responses. They cannot be modified, since they are
    //			an API used by the server to call them via the socket communication.
    //
    //
    //
    
	@Override
	public void error(Exception e) {
		if (!m_disconnectInProgress){
			String msg = EWrapperMsgGenerator.error(e);
			printMessage(msg);
		}
		
	}

	@Override
	public void error(String str) {
		String msg = EWrapperMsgGenerator.error(str);
		printMessage(msg);
		
	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {
		String msg = EWrapperMsgGenerator.error(id, errorCode, errorMsg);
		printMessage(msg);
		
	}

	@Override
	public void connectionClosed() {
		String msg = EWrapperMsgGenerator.connectionClosed();
		printMessage(msg);
		
	}

	@Override
	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {
		String msg=EWrapperMsgGenerator.tickPrice(tickerId, field, price, canAutoExecute);
		printMessage(msg);
		
	}

	@Override
	public void tickSize(int tickerId, int field, int size) {
		String msg=EWrapperMsgGenerator.tickSize(tickerId, field, size);
		printMessage(msg);
		
	}
	

	@Override
	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta,
			double undPrice) {
		String msg=EWrapperMsgGenerator.tickOptionComputation(tickerId, field, impliedVol, delta, optPrice, pvDividend, gamma, vega, theta, undPrice);
		printMessage(msg);
		
	}

	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {
		String msg = EWrapperMsgGenerator.tickGeneric(tickerId, tickType, value);
		printMessage(msg);
	}

	@Override
	public void tickString(int tickerId, int tickType, String value) {
		String msg = EWrapperMsgGenerator.tickString(tickerId, tickType, value);
		printMessage(msg);
		
	}

	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		String msg = EWrapperMsgGenerator.tickEFP(tickerId, tickType, basisPoints, formattedBasisPoints,
				impliedFuture, holdDays, futureExpiry, dividendImpact, dividendsToExpiry);
		printMessage(msg);
	}

	@Override
	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		
		
		String msg=EWrapperMsgGenerator.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld);
		printMessage(msg);
	}

	@Override
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {
		String msg = EWrapperMsgGenerator.openOrder( orderId, contract, order, orderState);
		printMessage(msg);
		
	}

	@Override
	public void openOrderEnd() {
		String msg = EWrapperMsgGenerator.openOrderEnd();
		printMessage(msg);
		
	}

	@Override
	public void updateAccountValue(String key, String value, String currency,
			String accountName) {
		String msg=EWrapperMsgGenerator.updateAccountValue(key, value, currency, accountName);
		printMessage(msg);
		
	}

	@Override
	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
		String msg=EWrapperMsgGenerator.updatePortfolio(contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL, accountName);
		printMessage(msg);
		
	}

	@Override
	public void updateAccountTime(String timeStamp) {
		String msg=EWrapperMsgGenerator.updateAccountTime(timeStamp);
		printMessage(msg);
		
	}

	@Override
	public void accountDownloadEnd(String accountName) {
		String msg = EWrapperMsgGenerator.accountDownloadEnd( accountName);
		printMessage(msg);
		
	}

	@Override
	public void nextValidId(int orderId) {
		String msg = EWrapperMsgGenerator.nextValidId( orderId);
		
		printMessage(msg);
		s_requestCounter=orderId;
	}

	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {
		String msg = EWrapperMsgGenerator.contractDetails( reqId, contractDetails);
		printMessage(msg);
		
	}

	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		String msg = EWrapperMsgGenerator.bondContractDetails( reqId, contractDetails);
		printMessage(msg);
	}

	@Override
	public void contractDetailsEnd(int reqId) {
		String msg = EWrapperMsgGenerator.contractDetailsEnd(reqId);
		printMessage(msg);
		
	}

	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
		String msg = EWrapperMsgGenerator.execDetails(reqId, contract, execution);
		printMessage(msg);
		
	}

	@Override
	public void execDetailsEnd(int reqId) {
		String msg = EWrapperMsgGenerator.execDetailsEnd(reqId);
		printMessage(msg);
		
	}

	@Override
	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {
		String msg=EWrapperMsgGenerator.updateMktDepth(tickerId, position, operation, side, price, size);
		printMessage(msg);
		
	}

	@Override
	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {
		String msg=EWrapperMsgGenerator.updateMktDepthL2(tickerId, position, marketMaker, operation, side, price, size);
		printMessage(msg);
	}

	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {
		String msg = EWrapperMsgGenerator.updateNewsBulletin(msgId, msgType, message, origExchange);
		printMessage(msg);
		
	}

	@Override
	public void managedAccounts(String accountsList) {
		m_bIsFAAccount=true;
		m_FAAcctCodes=accountsList;
		String msg = EWrapperMsgGenerator.managedAccounts(accountsList);
		printMessage(msg);
	}

	@Override
	public void receiveFA(int faDataType, String xml) {
		switch(faDataType){
		case EClientSocket.GROUPS:
			faGroupXML=xml;
			break;
		case EClientSocket.PROFILES:
			faProfilesXML=xml;
			break;
		case EClientSocket.ALIASES:
			faAliasesXML=xml;
			break;
		}
		
		if (!faError && !(faGroupXML == null || faProfilesXML == null || faAliasesXML == null)){
			
			//TODO: I cannnot put this code here, because I don't have access to the variables of the second arguments
			//m_client.replaceFA( EClientSocket.GROUPS, dlg.groupsXML );
	        //m_client.replaceFA( EClientSocket.PROFILES, dlg.profilesXML );
	        //m_client.replaceFA( EClientSocket.ALIASES, dlg.aliasesXML );

		}
		
	}

	@Override
	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) {
		
		
		manager.requestReceived(requestMap.get(reqId));
		output.update(reqId, date, open, high, low, close, volume, count, WAP, hasGaps);		
		
	}

	@Override
	public void scannerParameters(String xml) {
		String msg1=EWrapperMsgGenerator.SCANNER_PARAMETERS;
		String msg2=xml;
		printMessage(msg1);
		printMessage(msg2);
	}

	@Override
	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		String msg=EWrapperMsgGenerator.scannerData(reqId, rank, contractDetails, distance, benchmark, projection, legsStr);
		printMessage(msg);
		
	}

	@Override
	public void scannerDataEnd(int reqId) {
		String msg = EWrapperMsgGenerator.scannerDataEnd(reqId);
		printMessage(msg);
		
	}

	@Override
	public void realtimeBar(int reqId, long time, double open, double high,
			double low, double close, long volume, double wap, int count) {
		
		
		String msg=EWrapperMsgGenerator.realtimeBar(reqId, time, open, high, low, close, volume, wap, count);
		printMessage(msg);
	}

	@Override
	public void currentTime(long time) {
		String msg=EWrapperMsgGenerator.currentTime(time);
		printMessage(msg);
		
	}

	@Override
	public void fundamentalData(int reqId, String data) {
		String msg = EWrapperMsgGenerator.fundamentalData(reqId, data);
		printMessage(msg);
		
	}

	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {
		String msg = EWrapperMsgGenerator.deltaNeutralValidation(reqId, underComp);
		printMessage(msg);
		
	}

	@Override
	public void tickSnapshotEnd(int reqId) {
		String msg = EWrapperMsgGenerator.tickSnapshotEnd(reqId);
		printMessage(msg);
		
	}

	@Override
	public void marketDataType(int reqId, int marketDataType) {
		String msg = EWrapperMsgGenerator.marketDataType(reqId, marketDataType);
		printMessage(msg);
		
	}

	@Override
	public void commissionReport(CommissionReport commissionReport) {
		String msg = EWrapperMsgGenerator.commissionReport(commissionReport);
		printMessage(msg);
		
	}

	@Override
	public void position(String account, Contract contract, int pos,
			double avgCost) {
		String msg = EWrapperMsgGenerator.position(account, contract, pos, avgCost);
		printMessage(msg);
		
	}

	@Override
	public void positionEnd() {
		String msg = EWrapperMsgGenerator.positionEnd();
		printMessage(msg);
		
	}

	@Override
	public void accountSummary(int reqId, String account, String tag,
			String value, String currency) {
		String msg = EWrapperMsgGenerator.accountSummaryEnd(reqId);
		printMessage(msg);
		
	}

	@Override
	public void accountSummaryEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}
	
	private void printMessage(String msg){
		System.out.println(msg);
	}
	
	/**
	 * Receives from the server the next valid order id that can be used
	 * @param orderId
	 */

	
}
