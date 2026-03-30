import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true if found

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User retrieveUserByUsername(String email) {
        List<Film> users = new ArrayList<>();

        String query = "SELECT * FROM users WHERE email = '" + email + "'";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            //stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setSubscriptionId(rs.getInt("subscription_id"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void updateUser(Servicer model) {

    }

    public void deleteUser(Servicer model) {

    }
}
