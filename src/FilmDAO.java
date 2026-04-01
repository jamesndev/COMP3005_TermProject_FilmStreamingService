import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    private Connection conn;

    public FilmDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Film> retrieveAllFilms() {
        List<Film> films = new ArrayList<>();

        String query = "SELECT * FROM films";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Film film = new Film();

                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDirector(rs.getString("director"));
                film.setRuntime(rs.getInt("runtime"));
                film.setReleaseDate(rs.getString("release_date"));
                film.setRating(rs.getString("rating"));

                films.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    public Film retrieveFilmByID(int filmId) {

        Film film = new Film();
        String query = "SELECT * FROM films where film_id = " + filmId;

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
        return film;

    }
    public void deleteFilm(Servicer model) {

    }


}
