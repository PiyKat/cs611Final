import java.sql.Timestamp;

public class AccountFactory {
    // Function to get new account object
    public Account getNewAccount(String userId, String accType, Currency curr){
        Account acc;
        if(accType.equalsIgnoreCase("securities")){
            acc = new SecuritiesAccount(userId, accType, curr, 0.0);
        }
        else if(accType.equalsIgnoreCase("checking")){
            acc = new CheckingAccount(userId, accType, curr, 0.0);
        }
        else{
            acc = new SavingsAccount(userId, accType, curr, 0.0);
        }
        return acc;
    }

    // Function to get existing account object
    public Account getOldAccount(String accId, String accType, String userId, Currency curr, Double bal, Timestamp time){
        Account acc;
        if(accType.equalsIgnoreCase("securities")){
            acc = new SecuritiesAccount(accId, userId, accType, curr, bal, time);
        }
        else if(accType.equalsIgnoreCase("checking")){
            acc = new CheckingAccount(accId, userId, accType, curr, bal, time);
        }
        else{
            acc = new SavingsAccount(accId, userId, accType, curr, bal, time);
        }
        return acc;
    }

}
