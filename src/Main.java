import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/movie_streaming.db");
            System.out.println("Connected to SQLite!");

            PreparedStatement ps = conn.prepareStatement("INSERT OR IGNORE INTO actors " +
                    "(actor_id, first_name, last_name, birthday, nationality) " +
                    "VALUES (200, 'Brad', 'Pitt', 1963-12-18, 'American')"
            );

            ps.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM actors");
            ResultSet rs = ps2.executeQuery();

            System.out.println("Database contents:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("actor_id") + " - " + rs.getString("first_name") + ", " +
                        rs.getString("last_name") + ", " +
                        rs.getString("birthday") + ", " +
                        rs.getString("nationality")
                );
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}