import java.sql.Timestamp;

public class SecuritiesAccount extends Account{

    public SecuritiesAccount(String userId, String accType, Currency curr, Double bal){
        super(userId, accType, curr, bal);
    }

    public SecuritiesAccount(String accId, String userId, String accType, Currency curr, Double bal, Timestamp time){
        super(accId, userId, accType, curr, bal, time);
    }

}