public class Stock {

    private String stockSymbol;
    private double price;
    private double priceC;
    private double priceCP;
    private String companyName;
    private String tradingDay;
    private String owner;
    

    public Stock(){

    }


    public Stock(String stockSymbol, double price, double priceC, double priceCP, String companyName, String tradingDay, String owner){

        this.stockSymbol = stockSymbol;
        this.price = price;
        this.priceC = priceC;
        this.priceCP = priceCP;
        this.companyName = companyName;
        this.tradingDay = tradingDay;
        this.owner = owner;

    }

    public String getStockSymbol() {
        return stockSymbol;
    }
    public void setStockId(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public double getPriceC() {
        return priceC;
    }
    public void setPriceC(double priceC) {
        this.priceC = priceC;
    }
    public double getPriceCP() {
        return priceCP;
    }
    public void setPriceCP(double priceCP) {
        this.priceCP = priceCP;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setName(String companyName) {
        this.companyName = companyName;
    }

    
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public String getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(String day){
        this.tradingDay = day;
    }


}
