import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {

    private Connection conn;

    public ActorDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertActor(Servicer model) {

    }

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

    public void retrieveActorByID(Servicer model) {

    }

    public void deleteActor(Servicer model) {

    }
}
