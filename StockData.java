import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.io.UnsupportedEncodingException;

public class StockData {

    /*
     * Class to retrieve stock data from API
     */

    private static Map stockSymbols = new HashMap<String, String>();
    public static Map<String, Map<String, String>> stockData = new HashMap<String, Map<String, String>>();


    public StockData(){
        setStockSymbols();
    }



    private void setStockSymbols(){

        String[] cNames = {"Apple","Microsoft","Saudi Arabco","Alphabet(Google)",
        "Berkshire Hathaway","UnitedHealth","Tesla","Johnson & Johnson",
        "Visa","Nvidia","Exxon Mobil","TSMC","Tencent","Walmart","JPMorgan Chase",
        "LVMH","Proctor & Gamble","Eli Lilly","Mastercard","Home Depot","Chevron",
        "Nestle", "Meta Platforms(Facebook)","Kweichow Moutai","Samsung","Pfizer",
        "Novo Nordisk","AbbVie","Merck"};

        String[] symbols = {"AAPL","MSFT","2222.SR","GOOG","AMZN","BRK-B",
        "UNH","TSLA","JNJ","V","NVDA","XOM","TSM","TCEHY","WMT","JPM","PG",
        "LLY","MA","HD","CVX","NESN.SW","META","600519.SS","005930.KS","PFE",
        "NVO","ABBV","MRK"};

        for(int i = 0; i < cNames.length;i++){
            stockSymbols.put(symbols[i], cNames[i]);
        }

    }

    public Map returnStockCompanies(){
        return stockSymbols;
    }

    private static String urlEncode(Map<String, Object>data) {
        
        StringBuilder stringBuilder = new StringBuilder();
        
        for(Map.Entry i : data.entrySet()) {
            try {
                stringBuilder.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    private static String httpRequest(String requestUrl, Map params) {

        StringBuffer buffer = new StringBuffer();
        try {
            // create url
            URL url;
             if(params==null)
                url = new URL(requestUrl);
            else
                url = new URL(requestUrl + "?" + urlEncode(params));
            // open http connection
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();
            // get the input
            InputStream inputStream = httpUrlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");// encode
            BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);
            // put the values of bufferedReader into String
            String str;
            while((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // shut bufferReader and inputStream
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            // disconnect
            httpUrlConnection.disconnect();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return buffer.toString();
    }


    public static Map getStockData(String symbol) {

        String requestUrl = "https://www.alphavantage.co/query";
        String apiKey = "95DVQXHZ6FSG7O94";

        Map params = new HashMap();
        params.put("function", "GLOBAL_QUOTE");
        params.put("apikey", apiKey);
        params.put("symbol", symbol);
        String res = httpRequest(requestUrl, params);

        Map stockInfo = new HashMap();

        try {
            JSONObject stockRes = new JSONObject(res);
            JSONObject stockJsonObj = new JSONObject(stockRes.getString("Global Quote"));

            Iterator it = stockJsonObj.keys();
            while(it.hasNext()){
                String keyInJson = it.next().toString();
                String key = keyInJson.replaceAll("[^a-zA-Z]", " ").trim().replaceAll(" ", "_");
                String value = stockJsonObj.get(keyInJson).toString();
                stockInfo.put(key, value);
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stockInfo;
    }

    public static Map<String, Map<String, String>> getStocksData() {
        //        getStocks();
        Map<String, String> symbols = stockSymbols;
        //Map<String, Map<String, String>>  stocksInfo = new HashMap<String, Map<String, String>>();
        
        for (Map.Entry<String, String> entry : symbols.entrySet()) {
                    Map stockInfo  = getStockData(entry.getKey());
                    stockInfo.put("company", entry.getValue());
                    stockData.put(entry.getKey(),stockInfo);
        }
        //stocksSnapShot = stocksInfo;
        return stockData;
    
    }

    public static void main(String[] args){
        
        StockData s = new StockData();
        Map stock = s.getStocksData();
        System.out.println(stock);
        //Map stock = s.getStockData("GOOGL");
        

    }
    
}
