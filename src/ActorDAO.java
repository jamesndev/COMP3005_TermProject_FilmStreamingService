import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Actor Database Access Object that talks to the 'actors' table in the database and provides for the servicer.
 */
public class ActorDAO {

    /** Used for connection to database. **/
    private Connection conn;

    /**
     * Constructs an Actor DAO.
     * @param conn Connection session
     */
    public ActorDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Retrieves all actors from the 'actors' table in the database.
     * @return All actors in the database
     */
    public List<Actor> retrieveAllActors() {
        List<Actor> actors = new ArrayList<>();

        String query = "SELECT * FROM actors";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Actor actor = new Actor();

                actor.setActorId(rs.getInt("actor_id"));
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                actor.setBirthday(rs.getString("birthday"));
                actor.setNationality(rs.getString("nationality"));

                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    /**
     * Retrieves an actor from the 'actor' table in the database by Actor ID.
     * @param actorId Actor ID
     * @return An actor with a specific actor ID
     */
    public Actor retrieveActorByID(int actorId) {

        Actor actor = new Actor();
        String query = "SELECT * FROM actors where actor_id = " + actorId;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                actor.setActorId(rs.getInt("actor_id"));
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                actor.setBirthday(rs.getString("birthday"));
                actor.setNationality(rs.getString("nationality"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actor;
    }
}
