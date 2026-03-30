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

    public void insertFilm(Servicer model) {

    }

    public List<Film> retrieveAllFilms(Servicer model) {
        List<Film> films = new ArrayList<>();

        String query = "SELECT * FROM students";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Film film = new Film();

                film.setFilmId(rs.getInt("id"));
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

    public void retrieveFilmByID(Servicer model) {

    }
    public void deleteFilm(Servicer model) {

    }


}
