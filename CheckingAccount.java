import java.sql.Timestamp;

public class CheckingAccount extends Account{
    public CheckingAccount(String userId, String accType, Currency curr, Double bal){
        super(userId, accType, curr, bal);
    }

    public CheckingAccount(String accId, String userId, String accType, Currency curr, Double bal, Timestamp time){
        super(accId, userId, accType, curr, bal, time);
    }
    
}
