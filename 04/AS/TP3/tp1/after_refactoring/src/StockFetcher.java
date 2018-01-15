package trader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockFetcher implements Serializable {  
	
	/*
	* Returns a Stock Object that contains info about a specified stock.
	* @param 	symbol the company's stock symbol
	* @return 	a stock object containing info about the company's stock
	* @see Stock
	*/
	static Stock getStock(String symbol) {  
		String sym = symbol.toUpperCase();
		double price = 0.0; //Last Trade (Price Only)
		int volume = 0; //Volume
		double pe = 0.0; //P/E Ratio (Realtime)
		double eps = 0.0; //Earnings per Share
		double week52low = 0.0; //52 week Low
		double week52high = 0.0; //52 Week High
		double daylow = 0.0; //Day’s Low
		double dayhigh = 0.0; //Day’s High
		double movingav50day = 0.0; //50 Day Moving Average
		double marketcap = 0.0; //Market Cap (Realtime)
		String name = ""; //Name
		String currency = ""; //Currency
		double shortRatio = 0.0; //Short Ratio
		double previousClose = 0.0; //Previous Close
                double open = 0.0; //Open
		String exchange; //Stock Exchange
                double priceAsk = 0.0; //Ask (Realtime)
                double priceBid = 0.0; //Bid (Realtime)
		try { 
			
			// Retrieve CSV File
			URL yahoo = new URL("http://download.finance.yahoo.com/d/quotes.csv?s="+ sym + "&f=l1vr2ejkghm3j3ncs7poxab");
			URLConnection connection = yahoo.openConnection(); 
			InputStreamReader is = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(is);  
			
			// Parse CSV Into Array
			String line = br.readLine(); 
			//Only split on commas that aren't in quotes
			String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
			
			// Handle Our Data
			StockHelper sh = new StockHelper();
			
			price = sh.handleDouble(stockinfo[0]);
			volume = sh.handleInt(stockinfo[1]);
			pe = sh.handleDouble(stockinfo[2]);
			eps = sh.handleDouble(stockinfo[3]);
			week52low = sh.handleDouble(stockinfo[4]);
			week52high = sh.handleDouble(stockinfo[5]);
			daylow = sh.handleDouble(stockinfo[6]);
			dayhigh = sh.handleDouble(stockinfo[7]);   
			movingav50day = sh.handleDouble(stockinfo[8]);
			marketcap = sh.handleDouble(stockinfo[9]);
			name = stockinfo[10].replace("\"", "");
			currency = stockinfo[11].replace("\"", "");
			shortRatio = sh.handleDouble(stockinfo[12]);
			previousClose = sh.handleDouble(stockinfo[13]);
			open = sh.handleDouble(stockinfo[14]);
			exchange = stockinfo[15].replace("\"", "");
                        priceAsk = sh.handleDouble(stockinfo[16]);
                        priceBid = sh.handleDouble(stockinfo[17]);
                        
			
		} catch (IOException e) {
			Logger log = Logger.getLogger(StockFetcher.class.getName()); 
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}
		
		return new Stock(sym, price, volume, pe, eps, week52low, week52high, daylow, dayhigh, movingav50day, marketcap, name,currency, shortRatio,previousClose,open,exchange,priceAsk,priceBid);
		
	}
}
