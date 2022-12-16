public abstract class BankHuman {
    private String userId;
    private String name;
    private String password;
    private String userType;
    
    public BankHuman(String userId, String name, String pass, String userType){
        this.userId = userId;
        this.name = name;
        this.password = pass;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
