import java.sql.Timestamp;
import java.util.Date;

public class Trade extends CustomerAction{
    private String accId;
    private String customerId;
    private String stockId;
    private String action; 
    private double buyPrice; // buy or sell
    private double sellPrice; // buy or sell
    private double profit;
    
    public Trade(String tradeId){
        super(tradeId);
    }

    public String getTradeId() {
        return super.getActionId();
    }

    public void setTradeId(String tradeId) {
        super.setActionId(tradeId);;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Timestamp getTime() {
        return super.getTime();
    }

    public void setTime(Timestamp time) {
        super.setTime(time);;
    }

    public double getBuyPrice() {
        return buyPrice;
    }
    
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }
    
    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    
}