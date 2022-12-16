import java.sql.Timestamp;
import java.util.Date;

public abstract class CustomerAction {
    private String actionId;
    private Timestamp time;
    private Date now;

    public CustomerAction(String actionId) {
        this.now = new java.util.Date();
        this.actionId = actionId;
        this.time = new java.sql.Timestamp(now.getTime());
    }

    public CustomerAction() {
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    
}
