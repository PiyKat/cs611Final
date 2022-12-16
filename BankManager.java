import java.util.ArrayList;

public class BankManager extends BankHuman{
    private static final String userId = "PSTZ01";
    private static final String name = "PSTZ";
    private static final String password = "pstz";
    private static final String userType = "bankManager";
    private static BankManager bMInstance = new BankManager();

    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Stock> stockList = new ArrayList<>();
    private ArrayList<Transaction> todayTrans = new ArrayList<>();
    
    // Private Contructor
    private BankManager(){
        super(userId, name, password, userType);
    }

    public String getUserId(){
        return super.getUserId();
    }

    public String getPassword(){
        return super.getPassword();
    }
    // Function to get instance of bank manager
    public static BankManager getBankManager(){
        return bMInstance;
    }

    // Function to set today's transaction 
    public void setTodayTransaction(ArrayList<Transaction> trans){
        todayTrans.addAll(trans);
    }

    // Function to get today's transaction from Data Base
    public ArrayList<Transaction> getTodayTransaction(){
        return todayTrans;
    }

    // Function to get customer list
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    // Function to add new customer
    public void addCustomerList(Customer cust) {
        this.customerList.add(cust);
    }

    // Function to get stock list
    public ArrayList<Stock> getStockList(){
        return stockList;
    }

    // Function to add new stock
    public void addStockList(Stock s){
        this.stockList.add(s);
    }

}