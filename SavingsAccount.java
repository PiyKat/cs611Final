import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class SavingsAccount extends Account{

    private static double MIN_SECURITIES_BALANCE = 5000; // Minimum balance required to create a securities account
    private static double MIN_AMOUNT_SECURITIES = 1000; // Minimum amount of transaction required to securities
    private static double MIN_BALANCE_LEFT = 2500; // Minimum balance that should be left in savings after transaction

    private boolean securityAccCheck = false;

    public SavingsAccount(String userId, String accType, Currency curr, Double bal){
        super(userId, accType, curr, bal);
        this.isSecurityEligible();
    }

    public SavingsAccount(String accId, String userId, String accType, Currency curr, Double bal, Timestamp time){
        //  = Utils.generateTimeStamp();
        super(accId, userId, accType, curr, bal,time);
        this.isSecurityEligible();
    }

    private void isSecurityEligible(){
        double balance = super.getBal();

        if (balance > MIN_SECURITIES_BALANCE){
            this.securityAccCheck = true;
        }
    }

    public String transferToSecurityAccount(double amount) {

        this.isSecurityEligible();
        double balance = super.getBal();

        if(!this.securityAccCheck) {
            return "Not enough balance in the account to make a new securities account";
        } else if (amount < MIN_AMOUNT_SECURITIES) {
            return "Only transfer of money over than $1000 allowed!";
        } else if (balance - amount < MIN_BALANCE_LEFT) {
            return "Your savings account should always maintain at least 2500 dollars";
        } else {
            // create the account
            String userId = getUserId();
            Currency curr = getCurrency();
            String accType = getAccType();
            String accId = getAccId();
            // Create a AccountsFactory and DataController Objects 
            AccountFactory accFactory = new AccountFactory();
            DataController dc = new DataController();
            
            // Create security account object
            Timestamp secTimeStamp = Utils.generateTimeStamp();
            Account secAccount = accFactory.getNewAccount(userId,accType, curr);

            // update securities account balance
            secAccount.setBal(amount);

            dc.addAccount(secAccount.getAccId(), "securities", userId, secAccount.getCurrency(), secAccount.getBal(), secAccount.getAccOpenTimestamp());
            dc.addCustomerAcc(userId, secAccount.getAccId());
            // add securities to database
            // dc.addAccount(accId, accType, userId, curr.toString(), amount,secTimeStamp);
            // update savings account balance
            balance = balance - amount;
            super.setBal(balance);
            //dc. Update balance in database


            
            // Feed this new securities to our database
            } 
            
            return "Success";
                
            }
        
        

}