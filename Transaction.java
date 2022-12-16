import java.sql.Timestamp;
import java.util.Date;

public class Transaction extends CustomerAction {
    private String senderId;
    private String receiverId;
    private Double amt;
    private Currency currency;
    private Double fee;
    
    public Transaction(String transId, String senderId, String receiverId, Double amt, Currency curr, Double fee){
        super(transId);
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amt = amt;
        this.currency = curr;
        this.fee = fee;
    }

    public Transaction(String transId, String senderId, String receiverId, Double amt, Currency curr, Timestamp time, Double fee){
        super();
        super.setActionId(transId);;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amt = amt;
        this.currency = curr;
        super.setTime(time);
        this.fee = fee;
    }

    public String getTransId() {
        return super.getActionId();
    }

    public void setTransId(String transId) {
        super.setActionId(transId);
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    public Double getAmt() {
        return amt;
    }
    public void setAmt(Double amt) {
        this.amt = amt;
    }
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public Timestamp getTime() {
        return super.getTime();
    }
    public void setTime(Timestamp time) {
        super.setTime(time);
    }
    public Double getFee() {
        return fee;
    }
    public void setFee(Double fee) {
        this.fee = fee;
    }
}
