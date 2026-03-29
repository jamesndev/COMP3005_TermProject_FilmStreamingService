import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/movie_streaming.db");
            System.out.println("Connected to SQLite!");

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM actors");

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