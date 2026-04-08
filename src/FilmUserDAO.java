import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Film User Database Access Object that talks to the film_user table in the database
 * and provdes for the servicer.
 */
public class FilmUserDAO {

    /** Used for connection to database. **/
    private Connection conn;

    /**
     * Constructs a FilmUserDAO.
     * @param conn Connection session
     */
    public FilmUserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Checks from 'film_user' database table if a user has watched a specific film.
     * @param film Film
     * @param user User
     * @return True if a user and film pair exists in the film_user database table
     */
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

    /**
     * Insert a film into 'film_user' database table that a user has begun watching
     * @param film Film
     * @param user User
     */
    public void insertWatchedFilm(Film film, User user) {

        if (checkIfFilmWatched(film, user)) {
            return;
        }

        LocalDate currentDate = LocalDate.now();

        String query = "INSERT INTO film_user (film_id, user_id, watch_date, progress) VALUES ("
                + film.getFilmId() + ", "
                + user.getUserId() + ", '"
                + currentDate + "', "
                + film.getWatchedSoFar() + ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all watched films from 'film_user' database table for a specific user
     * @param userId User ID
     * @param filmDAO FilmDAO
     * @return Films from film_user database table watched by a specific user
     */
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

    /**
     * Gets amount of film time from 'film_user' database table watched by a specific user.
     * @param filmId Film ID
     * @param userId User ID
     * @return Watch progress from 'film_user' database table for a specific user
     */
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

    /**
     * Update watch time in 'film_user' database table for a specific user.
     * @param film Film
     * @param user User
     */
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

    /**
     * Delete a watched film from 'film_user' database table for a specific user.
     * @param film Film
     * @param user User
     * @return true watched film was deleted successfully, false otherwise
     */
    public boolean deleteWatchedFilm(Film film, User user) {

        String query = "DELETE FROM film_user WHERE film_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, film.getFilmId());
            ps.setInt(2, user.getUserId());

            int rowsRemoved = ps.executeUpdate();

            return rowsRemoved > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
