import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class BackEndController {
    private BankHuman currentUser;
    private Transaction lastTrans;
    private static final Double transFee = 5.0;
    private Double interest = 0.001;
    private Double intOnLoan = 0.01;
    private DataController dc = new DataController();

    // Function to check if a userId exists or not
    public Boolean checkUserId(String userId){
        ArrayList<BankHuman> allUsers = dc.getAllUsers();
        for(int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).getUserId().equals(userId)){
                return true;
            }
        }
        return false;
    }

    // Function to validate User credentials
    public Boolean validateUser(String userId, String pass){
        BankHuman user = dc.getUser(userId);
        BankManager temp = BankManager.getBankManager();
        if(user != null && user.getPassword().equals(pass)){
                currentUser = getOldUser(userId);
                updateUserBalance(currentUser.getUserId());
                return true;
        }
        else if((userId.equals(temp.getUserId())) && (pass.equals(temp.getPassword()))){
            return true;
        }
        return false;
    }

    // function to check if it is manager 
    public boolean isManager(String userId){
        BankManager temp = BankManager.getBankManager();
        if(temp.getUserId().equals(userId)){
            return true;
        }
        return false;
    }

    // Function to update user balance
    public void updateUserBalance(String userId){
        currentUser = getOldUser(userId);
        if(((Customer) currentUser).getAccounts() != null){
            for(int i = 0; i < ((Customer) currentUser).getAccounts().size(); i++){
                if(dc.getAccType(((Customer) currentUser).getAccounts().get(i)).equalsIgnoreCase("savings")){
                    Date date1 = dc.getAccTime(((Customer) currentUser).getAccounts().get(i));
                    Date now = new java.util.Date();
                    Date date2 = new java.sql.Timestamp(now.getTime());
                    Double days = Double.valueOf(Duration.between(((java.sql.Timestamp) date1).toLocalDateTime(), ((java.sql.Timestamp) date2).toLocalDateTime()).toDays());
                    System.out.println("Days = " + days);
                    if(days != 0.0){
                        System.out.println("in if new");
                        Double newBal = dc.getAccountBalance(((Customer) currentUser).getAccounts().get(i));
                        newBal = newBal * interest * days;
                        dc.setAccountBalance(((Customer) currentUser).getAccounts().get(i), newBal);
                    }
                    
                }
            }
        }
        if(((Customer) currentUser).getLoans() != null){
            for(int i = 0; i < ((Customer) currentUser).getLoans().size(); i++){
                if(dc.getLoanAppDate(((Customer) currentUser).getLoans().get(i)) != null){
                    LocalDateTime date1 = dc.getLoanAppDate(((Customer) currentUser).getLoans().get(i)).toLocalDateTime();
                    if(date1 != null){
                        Date now = new java.util.Date();
                        Date date2 = new java.sql.Timestamp(now.getTime());
                        Double days = Double.valueOf(Duration.between(date1, ((java.sql.Timestamp) date2).toLocalDateTime()).toDays());
                        Double newBal = dc.getAccountBalance(dc.getLoanAccId(((Customer) currentUser).getLoans().get(i)));
                        newBal = newBal * intOnLoan * days;
                        dc.setAccountBalance(dc.getLoanAccId(((Customer) currentUser).getLoans().get(i)), newBal);
                    }
                }
            }
        }
    }

    // Function to return a user object from DB using userId
    public BankHuman getOldUser(String userId){
        return dc.getCustomer(userId);
    }

    // Function to signup/create a new user
    public BankHuman createNewUser(String userId, String name, String password, String userType){
        if(!checkUserId(userId)){
            dc.addUser(userId, name, password, userType);
            dc.addCustomer(userId, null, null, null);
            BankHuman user =  new Customer(userId, name, password, userType);
            return user;
        }
        return null;
    }

    // Function to set today's transaction 
    public void setTodayTransaction(){
        BankManager temp = BankManager.getBankManager();
        temp.setTodayTransaction(dc.getTodayTransaction());
    }

    // Function to get all user transactions 
    public ArrayList<Transaction> getTransactions(String userId){
        currentUser = getOldUser(userId);
        return dc.getUserTrans(currentUser.getUserId());
    }

    // Function to get balance in an account
    public Double getAccBal(String accId){
        ArrayList<String> accounts = dc.getAllAccIds();
        for(int i = 0; i < accounts.size(); i++){
            if(accId.equals(accounts.get(i))){
                return dc.getAccountBalance(accId);
            }
        }
        return null;
    }

    // Function to set balance
    public void setAccBal(String userId, String accId, Double bal){
        currentUser = getOldUser(userId);
        if(getAllAccounts(currentUser.getUserId()) != null){
            ArrayList<String> accounts = getAllAccounts(currentUser.getUserId());
            for(int i = 0; i < accounts.size(); i++){
                if(accId.equals(accounts.get(i))){
                    dc.setAccountBalance(accId, bal);
                }
            }
        }
    }

    // Function to create a new loan
    public Boolean createNewLoan(String userId, String accId, Double collateral, String loanType, Double amt){
        currentUser = getOldUser(userId);
        if(collateral > amt){
            String loanId = Utils.generateRandomString(10);
            ((Customer) currentUser).addLoan(loanId);
            Loan loan = new Loan(loanId, currentUser.getUserId(), accId, collateral, loanType, amt);
            dc.addLoan(loanId, accId, currentUser.getUserId(), collateral, loan.getAppDate(), loan.getReqDate(), loan.getStatus(), loanType, amt);
            dc.addCustomerLoan(currentUser.getUserId(), loanId);
            return true;
        }
        return false;
    }

    // Function to approve loan
    public void approveLoan(String userId, String loanId){
        currentUser = getOldUser(userId);
        if(currentUser.getUserType().equalsIgnoreCase("bankManager")){
            dc.approveLoan(loanId);
        }
    }

    // function to retrieve a loan details
    public Loan getLoan(String loanId){
        return dc.getLoan(loanId);
    }

    // Function to create a new account
    public void createNewAccount(String userId, String accType, Currency curr){
        AccountFactory af = new AccountFactory();
        Account acc = af.getNewAccount(userId, accType, curr);
        dc.addAccount(acc.getAccId(), accType, userId, acc.getCurrency(), acc.getBal(), acc.getAccOpenTimestamp());
        dc.addCustomerAcc(userId, acc.getAccId());
    }

    // Function to validate Account Id
    public Boolean validateAccId(String accId){
        ArrayList<String> allIds = dc.getAllAccIds();
        if(allIds != null){
            for(int i = 0; i < allIds.size(); i++){
                if(allIds.get(i).equals(accId)){
                    return true;
                }
            }
        }
        return false;
    }

    // Function to validate the transaction
    public Boolean validateTrans(String accId, Double amount){
        Double bal = getAccBal(accId);
        if(bal < amount + transFee){
            return false;
        }
        return true;
    }

    // Function to withdraw money
    public Boolean withdrawMoney(String userId, String accId, Double amount){
        if(validateTrans(accId, amount)){
            Double newBal = getAccBal(accId) - amount - transFee;
            setAccBal(userId, accId, newBal);
            Currency curr = dc.getCurrency(accId);
            String transId = Utils.generateRandomString(10);
            lastTrans = new Transaction(transId, accId, "withdrawal", amount, curr, newBal);
            dc.addTransaction(transId, accId, "withdrawal", amount, curr, lastTrans.getTime(), transFee);
            return true;
        }
        return false;
    }

    // Function to deposit money
    public Boolean depositMoney(String userId, String accId, Double amount){
        try{
            Double newBal = getAccBal(accId) + amount;
            setAccBal(userId, accId, newBal);
            Currency curr = dc.getCurrency(accId);
            String transId = Utils.generateRandomString(10);
            lastTrans = new Transaction(transId, accId, "deposit", amount, curr, newBal);
            dc.addTransaction(transId, accId, "deposit", amount, curr, lastTrans.getTime(), transFee);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    // Function to send money
    public Boolean sendMoney(String userId, String sAccId, String rAccId, Double amount){
        if(validateTrans(sAccId, amount) && (dc.getCurrency(rAccId) == dc.getCurrency(sAccId)) && (validateAccId(rAccId))){
            Double fee = 0.0;
            if(dc.getAccType(sAccId).equalsIgnoreCase("checking")){
                fee = transFee;
            }
            Double newBal = getAccBal(sAccId) - amount - fee;
            setAccBal(userId, sAccId, newBal);
            newBal = getAccBal(rAccId) + amount;
            setAccBal(userId, rAccId, newBal);
            Currency curr = dc.getCurrency(sAccId);
            String transId = Utils.generateRandomString(10);
            lastTrans = new Transaction(transId, sAccId, rAccId, amount, curr, newBal);
            dc.addTransaction(transId, sAccId, rAccId, amount, curr, lastTrans.getTime(), transFee);
            return true;
        }
        return false;
    }

    // function to close account
    public Boolean accountClose(String userId, String accId){
        currentUser = getOldUser(userId);
        if(validateAccId(accId)){
            dc.removeAccount(accId);
            dc.removeCustomerAcc(currentUser.getUserId(), accId);
            ((Customer) currentUser).removeAccount(accId);
            return true;
        }
        return false;
        
    }

    // Function to get user name
    public String getUserName(String userId){
        BankHuman temp  = getOldUser(userId);
        return temp.getName();
    }

    // function to get checking accounts
    public ArrayList<String> getCheckingAccounts(String userId){
        Customer temp = (Customer) getOldUser(userId);
        ArrayList<String> acc = new ArrayList<>();
        for(int i = 0; i < temp.getAccounts().size(); i++){
            if(dc.getAccType(temp.getAccounts().get(i)).equalsIgnoreCase("checking")){
                acc.add(temp.getAccounts().get(i));
            }
        }
        return acc;
    }

    // function to get checking accounts
    public ArrayList<String> getSavingAccounts(String userId){
        Customer temp = (Customer) getOldUser(userId);
        ArrayList<String> acc = new ArrayList<>();
        for(int i = 0; i < temp.getAccounts().size(); i++){
            if(dc.getAccType(temp.getAccounts().get(i)).equalsIgnoreCase("savings")){
                acc.add(temp.getAccounts().get(i));
            }
            if(dc.getAccType(temp.getAccounts().get(i)).equalsIgnoreCase("saving")){
                acc.add(temp.getAccounts().get(i));
            }
        }
        return acc;
    }

    // function to get checking accounts
    public ArrayList<String> getSecurityAccounts(String userId){
        Customer temp = (Customer) getOldUser(userId);
        ArrayList<String> acc = new ArrayList<>();
        for(int i = 0; i < temp.getAccounts().size(); i++){
            if(dc.getAccType(temp.getAccounts().get(i)).equalsIgnoreCase("securities")){
                acc.add(temp.getAccounts().get(i));
            }
        }
        return acc;
    }

    // function to get all accounts
    public ArrayList<String> getAllAccounts(String userId){
        Customer temp = (Customer) getOldUser(userId);
        return temp.getAccounts();
    }

    // function to get account tyep
    public String getAccountType(String accId){
        return dc.getAccType(accId);
    }

    // function to get user Ids
    public ArrayList<String> getAllUserIds(){
        ArrayList<BankHuman> user = dc.getAllUsers();
        ArrayList<String> ids = new ArrayList<>();
        if(user != null){
            for(int i = 0; i < user.size(); i++){
                ids.add(user.get(i).getUserId());
            }
            return ids;
        }
        return null;
    }

    // function to get total balance
    public Double getTotalBal(){
        Double bal = 0.0;
        ArrayList<String> acc = dc.getAllAccIds();
        if(acc != null){
            for(int i = 0; i < acc.size(); i++){
                bal += getAccBal(acc.get(i));
            }
            return bal;
        }
        return null;
    }

    // function to get all loans of a user
    public ArrayList<String> getAllLoans(String userId){
        ArrayList<String> loans = new ArrayList<>();
        Customer cust = (Customer) getOldUser(userId);
        if(cust.getLoans() != null){
            for(int i = 0; i < cust.getLoans().size(); i++){
                loans.add(cust.getLoans().get(i));
            }
            return loans;
        }
        return null;
    }

    // function to get loan type
    public String getLoanType(String loanId){
        Loan temp = getLoan(loanId);
        return temp.getLoanType();
    }

    // function to get loan amount
    public Double getLoanAmt(String loanId){
        Loan temp = getLoan(loanId);
        return temp.getAmt();
    }

    // function to get loan amount
    public String getLoanStatus(String loanId){
        Loan temp = getLoan(loanId);
        return temp.getStatus();
    }

    // function to get all stocks of a user
    public ArrayList<String> getAllStocks(String userId){
        ArrayList<String> stocks = new ArrayList<>();
        Customer cust = (Customer) getOldUser(userId);
        if(cust.getStocks() != null){
            for(int i = 0; i < cust.getStocks().size(); i++){
                stocks.add(cust.getStocks().get(i));
            }
            return stocks;
        }
        return null;
    }

    // function to get stock owner
    public String getStockOwner(String stockId){
        return dc.getStock(stockId).getOwner();
    }

    // function to check if user can make security account
    public Boolean checkSecurityEligibility(String userId){
        currentUser = getOldUser(userId);
        if(getSecurityAccounts(userId).size() == 0){
            ArrayList<String> saving = getSavingAccounts(userId);
            for(int i = 0; i < saving.size(); i++){
                if(getAccBal(saving.get(i)) >= 5000.0){
                    return true;
                }
            }
        }
        return false;
    }

    // function to get savings account with 5000+ bal
    public String getRichSaving(String userId){
        ArrayList<String> saving = getSavingAccounts(userId);
        for(int i = 0; i < saving.size(); i++){
            if(getAccBal(saving.get(i)) > 5000.0){
                return saving.get(i);
            }
        }
        return null;
    }

}