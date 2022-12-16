import java.util.*;

public class StockController {

    StockData stockData = new StockData();

    DataController dc = new DataController();

    public StockController() {
    }

    public boolean buyStock(String accId, String stockSymbol, int noOfStocks) {

        Stock customerStock = returnStockObject(stockSymbol);

        Account customerAccount = dc.getAccount(accId);

        double buyPrice = customerStock.getPrice() * noOfStocks;
        double customerBalance = customerAccount.getBal();

        if (customerBalance >= buyPrice) {
            customerStock.setOwner(customerAccount.getAccId());
            customerBalance -= buyPrice;
            customerAccount.setBal(customerBalance);
            dc.setAccountBalance(accId, customerBalance);
            // Create a new trade object and record the trade in database
            dc.addStock(stockSymbol, customerStock.getPrice(), customerStock.getPriceC(), customerStock.getPriceCP(),
                    customerStock.getCompanyName(), accId, customerStock.getTradingDay());

            // now let's create a trade and record it in the database
            Trade trade = new Trade(Utils.generateRandomString(10));
            trade.setAccId(customerAccount.getAccId());
            trade.setCustomerId(customerAccount.getAccId());
            trade.setStockId(stockSymbol);
            trade.setAction("buy");
            trade.setBuyPrice(buyPrice);
            trade.setSellPrice(0.0);
            trade.setProfit(0.0);

            return true;

        } else {
            System.out.println("Customer can't buy the stock!!!!!");
            return false;
        }

    }

    public boolean sellStock(String accId, String stockSymbol) {

        Account customerAccount = dc.getAccount(accId);
        Map stockInfo = StockData.getStockData(stockSymbol);
        // Retrieve buy price
        double buyPrice = dc.getStockPrice((String) stockInfo.get("symbol"));
        double sellPrice = Double.parseDouble((String) stockInfo.get("price"));

        // record this transaction in our database
        double profit = sellPrice - buyPrice;

        Trade trade = new Trade(Utils.generateRandomString(10));
        trade.setAccId(customerAccount.getAccId());
        trade.setCustomerId(customerAccount.getAccId());
        trade.setStockId((String) stockInfo.get("symbol"));
        trade.setAction("sell");
        trade.setBuyPrice(buyPrice);
        trade.setSellPrice(sellPrice);
        trade.setProfit(profit);

        dc.addTrade(trade.getTradeId(), trade.getAccId(), trade.getStockId(), null, null, 0, null, 0);
        // now delete the stock from the database

        boolean flag = dc.removeStock((String) stockInfo.get("symbol"));
        // update customerBalance
        double balance = customerAccount.getBal();
        balance += profit;
        customerAccount.setBal(balance);
        dc.setAccountBalance(accId, balance);

        return flag;
    }

    public Stock returnStockObject(String stockSymbol) {

        // Retrieve data from API
        Map stockInfo = stockData.getStockData(stockSymbol);

        // Create and return stock Object
        String symbol = (String) stockInfo.get("symbol");
        double price = Double.parseDouble((String) stockInfo.get("price"));
        double priceC, priceCP;
        if (stockInfo.get("previous_close") == null) {
            priceC = 0.0;
        } else {
            priceC = Double.parseDouble((String) stockInfo.get("previous_close"));
        }

        if (stockInfo.get("priceCP") == null) {
            priceCP = 0.0;
        } else {
            priceCP = Double.parseDouble((String) stockInfo.get("priceCP"));
        }

        Map c = stockData.returnStockCompanies();
        String companyName = (String) c.get(stockSymbol);
        // String companyName = (String) stockInfo.get("company");
        String tradingDay = (String) stockInfo.get("latest_trading_day");

        Stock s = new Stock(symbol, price, priceC, priceCP, companyName, tradingDay, "");

        return s;
    }

    public Map returnStockMap(String stockSymbol) {

        // Retrieve data from API
        Map stockInfo = stockData.getStockData(stockSymbol);

        return stockInfo;
    }

    public String[] returnStockCompanies() {
        Map data = stockData.returnStockCompanies();
        ArrayList<String> l = new ArrayList<String>(data.values());

        String[] stockCompanies = new String[l.size()];
        stockCompanies = l.toArray(stockCompanies);
        return stockCompanies;

    }

    public String[] returnStockSymbols() {
        Map data = stockData.returnStockCompanies();

        ArrayList<String> l = new ArrayList<String>(data.keySet());

        String[] stockSymbols = new String[l.size()];
        stockSymbols = l.toArray(stockSymbols);
        return stockSymbols;
    }

    public Map returnStocksList() {

        return stockData.getStocksData();

    }

}