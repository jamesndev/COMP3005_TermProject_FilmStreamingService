import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmActorDAO {

    private Connection conn;

    public FilmActorDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Film> retreiveFilmsForAnActor(int actorId, FilmDAO filmDAO) {
        List<Film> filmsOfActor = new ArrayList<>();

        String query = "SELECT film_id FROM film_actor WHERE actor_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, actorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int filmId = rs.getInt("film_id");

                Film film = filmDAO.retrieveFilmByID(filmId);

                if (film != null) {
                    filmsOfActor.add(film);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filmsOfActor;
    }

    public List<Actor> retreiveActorsForAFilm(int filmId, ActorDAO actorDAO) {
        List<Actor> actorsOfFilm = new ArrayList<>();

        String query = "SELECT actor_id FROM film_actor WHERE film_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, filmId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int actorId = rs.getInt("actor_id");

                Actor actor = actorDAO.retrieveActorByID(actorId);

                if (actor != null) {
                    actorsOfFilm.add(actor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actorsOfFilm;
    }
}
