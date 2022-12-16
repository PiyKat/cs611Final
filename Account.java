import java.sql.Timestamp;
import java.util.Date;

public abstract class Account {
        private String accId;
        private String accType;
        private String userId;
        private Currency curr;
        private double bal;
        private Timestamp accOpenTimestamp;

        private static final Double openAccFee = 5.0;
    
        public Account(String userId, String accType, Currency curr, Double bal){
            this.setAccId(Utils.generateRandomString(10));
            this.userId = userId;
            this.accType = accType;
            this.curr = curr;
            this.bal = bal - openAccFee;
            Date now = new java.util.Date();
            this.accOpenTimestamp = new java.sql.Timestamp(now.getTime());
        }

        public Account(String accId, String userId, String accType, Currency curr, Double bal, Timestamp time){
            this.accId = accId;
            this.userId = userId;
            this.accType = accType;
            this.curr = curr;
            this.bal = bal;
            this.accOpenTimestamp = time;
        }
    
        public String getAccId() {
            return accId;
        }
        public void setAccId(String accId) {
            this.accId = accId;
        }
        public String getAccType() {
            return accType;
        }
        public void setAccType(String accType) {
            this.accType = accType;
        }
        public String getUserId() {
            return userId;
        }
        public void setUserId(String userId) {
            this.userId = userId;
        }
        public Currency getCurrency() {
            return curr;
        }
        public void setCurrency(Currency curr) {
            this.curr = curr;
        }
        public double getBal() {
            return bal;
        }
        public void setBal(double bal) {
            this.bal = bal;
        }
        public Timestamp getAccOpenTimestamp() {
            return accOpenTimestamp;
        }

        public void setAccOpenTimestamp(Timestamp accOpenTimestamp) {
            this.accOpenTimestamp = accOpenTimestamp;
        }
    }