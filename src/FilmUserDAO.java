import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmUserDAO {

    private Connection conn;

    public FilmUserDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertWatchedFilm(Film film, User user) {
        String query = "SELECT * FROM film_actor where film_id = " + film.getFilmId() + " and user_id = " + user.getUserId();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDirector(rs.getString("director"));
                film.setRuntime(rs.getInt("runtime"));
                film.setReleaseDate(rs.getString("release_date"));
                film.setRating(rs.getString("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
