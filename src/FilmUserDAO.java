import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FilmUserDAO {

    private Connection conn;

    public FilmUserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean checkIfFilmWatched(Film film, User user) {
        String query = "SELECT 1 FROM film_user WHERE film_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, film.getFilmId());
            ps.setInt(2, user.getUserId());

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertWatchedFilm(Film film, User user) {

        if (checkIfFilmWatched(film, user)) {
            return;
        }

        LocalDate currentDate = LocalDate.now();

        String query = "INSERT INTO film_user (film_id, user_id, watch_date, progress, user_rating) VALUES ("
                + film.getFilmId() + ", "
                + user.getUserId() + ", '"
                + currentDate + "', "
                + film.getWatchedSoFar() + ", NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Film> getWatchedFilms(int userId, FilmDAO filmDAO) {
        List<Film> watchedFilms = new ArrayList<>();

        String query = "SELECT film_id FROM film_user WHERE user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int filmId = rs.getInt("film_id");

                Film film = filmDAO.retrieveFilmByID(filmId);

                if (film != null) {
                    watchedFilms.add(film);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return watchedFilms;
    }

    public int getProgress(int filmId, int userId) {
        String query = "SELECT progress FROM film_user WHERE film_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, filmId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("progress");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateWatchTime(Film film, User user) {
        String query = "UPDATE film_user SET progress = ? WHERE film_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, film.getWatchedSoFar());
            ps.setInt(2, film.getFilmId());
            ps.setInt(3, user.getUserId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteWatchedFilm(Film film, User user) {

        String query = """
        DELETE FROM film_user
        WHERE film_id = ? AND user_id = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, film.getFilmId());
            ps.setInt(2, user.getUserId());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
