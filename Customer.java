import java.util.*;

public class Customer extends BankHuman{
    private ArrayList<String> accounts = new ArrayList<>();
    private ArrayList<String> loans = new ArrayList<>();
    private ArrayList<String> stocks = new ArrayList<>();

    public Customer(String userId, String name, String password, String userType){
        super(userId, name, password, userType);
    }

    public void setUserId(String userId){
        super.setUserId(userId);
    }

    public String getUserId(){
        return super.getUserId();
    }
    
    public ArrayList<String> getAccounts() {
        return accounts;
    }

    public void addAccount(String accounts) {
        this.accounts.add(accounts);
    }

    public ArrayList<String> getLoans() {
        return loans;
    }

    public void addLoan(String loans) {
        this.loans.add(loans);
    }

    public void removeLoan(String loanId){
        this.loans.remove(loanId);
    }

    public void removeAccount(String accId){
        this.accounts.remove(accId);
    }

    public ArrayList<String> getStocks() {
        return stocks;
    }

    public void addStocks(String stockId) {
        this.stocks.add(stockId);
    }

    public void removeStock(String stockId){
        this.stocks.remove(stockId);
    }
}