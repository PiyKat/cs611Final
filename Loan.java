import java.sql.Timestamp;
import java.util.Date;

public class Loan extends CustomerAction{
    private String accId;
    private String userId;
    private Double collateral;
    private Timestamp appDate;
    private Timestamp reqDate;
    private String status;
    private String loanType;
    private Double amt;
    
    public Loan(String loanId, String userId, String accId, Double collateral, String loanType, Double amt){
        super(loanId);
        this.accId = accId;
        this.userId = userId;
        this.collateral = collateral;
        this.appDate = null;
        this.status = "UnderReview";
        this.loanType = loanType;
        this.amt = amt;
    }
     
    public Loan(String loanId, String accId, String userId, Double collateral, Timestamp appDate, Timestamp reqDate, String status, String loanType, Double amt){
        super();
        this.accId = accId;
        this.userId = userId;
        this.collateral = collateral;
        this.appDate = appDate;
        this.status = status;
        this.loanType = loanType;
        this.amt = amt;
        this.reqDate = reqDate;
        super.setActionId(loanId);
        
    }

    public String getLoanId() {
        return super.getActionId();
    }
    public void setLoanId(String loanId) {
        super.setActionId(loanId);
    }
    public String getAccId() {
        return accId;
    }
    public void setAccId(String accId) {
        this.accId = accId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Double getCollateral() {
        return collateral;
    }
    public void setCollateral(Double collateral) {
        this.collateral = collateral;
    }
    public Timestamp getAppDate() {
        return appDate;
    }
    public void setAppDate(Timestamp appDate) {
        this.appDate = appDate;
    }
    public Timestamp getReqDate() {
        return super.getTime();
    }
    public void setReqDate(Timestamp reqDate) {
        super.setTime(reqDate);
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getLoanType() {
        return loanType;
    }
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
    public Double getAmt() {
        return amt;
    }
    public void setAmt(Double amt) {
        this.amt = amt;
    }
}
