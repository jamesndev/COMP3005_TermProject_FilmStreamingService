import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User Database Access Object that talks to the 'users' table in the database and provides for the servicer.
 */
public class UserDAO {

    /** Used for connection to database. **/
    private Connection conn;

    /**
     * Constructs a User DAO
     * @param conn Connection session
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Check 'users' table in the database to see if an email exists.
     * @param email Email
     * @return True if the email exists in the users database table, false otherwise.
     */
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Adds a user to the 'users' table in the database.
     * @param user User to add
     */
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

    /**
     * Retrieves a user from the 'users' table in the database based on email.
     * @param email Email
     * @return User from the users database table based on email
     */
    public User retrieveUserByEmail(String email) {

        String query = "SELECT * FROM users WHERE email = '" + email + "'";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all users from the 'users' table in the database.
     * @return All users from the users database table
     */
    public List<User> retrieveAllUsers() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
