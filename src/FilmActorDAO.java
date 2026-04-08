import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Film Actor Database Access Object that talks to the 'film_actor' table in the database
 * and provides for the servicer.
 */
public class FilmActorDAO {

    /** Used for connection to database. **/
    private Connection conn;

    /**
     * Constructs an Actor DAO.
     * @param conn Connection session
     */
    public FilmActorDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Retrieves all films from film_actor that an actor has played in.
     * @param actorId Actor ID
     * @param filmDAO FilmDAO
     * @return Films that an actor has played in
     */
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

    /**
     * Retrieve all actors in film_actor that are in a film.
     * @param filmId Film ID
     * @param actorDAO ActorDAO
     * @return Actors that are in a film
     */
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

    /**
     * Gets a character from film_actor that an actor plays in a specific film.
     * @param filmId Film ID
     * @param actorId Actor ID
     * @return Character that an actor plays in a specific film.
     */
    public String getCharacter(int filmId, int actorId) {
        String query = "SELECT character FROM film_actor WHERE film_id = ? AND actor_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, filmId);
            ps.setInt(2, actorId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("character");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return " ";
    }

    /**
     * Gets an actor's salary from film_actor for playing a role in a specific film.
     * @param filmId Film ID
     * @param actorId Actor ID
     * @return Salary that an actor received for playing a role in a film
     */
    public int getActorSalary(int filmId, int actorId) {
        String query = "SELECT actor_salary FROM film_actor WHERE film_id = ? AND actor_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, filmId);
            ps.setInt(2, actorId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("actor_salary");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
