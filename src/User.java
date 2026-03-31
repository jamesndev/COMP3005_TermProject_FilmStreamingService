import java.sql.Connection;

public class User {

    private int userId;
    private String username;
    private String password;
    private String email;
    private int subscriptionId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSubscriptionId(int subscription_id) {
        this.subscriptionId = subscription_id;
    }
}
