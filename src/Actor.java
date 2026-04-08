import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Actor that can be in films.
 * An actor can be in many films, a film can have many actors.
 */
public class Actor {

    /** Actor ID that uniquely identifies an actor **/
    private int actorId;

    /** First name **/
    private String firstName;

    /** Last name **/
    private String lastName;

    /** Birthday  **/
    private String birthday;

    /** Nationality **/
    private String nationality;

    /** Films that this actor is in **/
    private List<Film> filmsIn;

    /**
     * Constructs an Actor.
     * @param actorId Actor ID
     * @param firstName First name
     * @param lastName Last name
     * @param birthday Birthday
     * @param nationality Nationality
     */
    public Actor(int actorId, String firstName, String lastName, String birthday, String nationality) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.nationality = nationality;

        filmsIn = new ArrayList<>();
    }

    /**
     * Constructs an Actor.
     */
    public Actor() {

    }

    /**
     * Gets this actor ID.
     * @return Actor ID
     */
    public int getActorId() {
        return actorId;
    }


    /**
     * Sets this actor ID.
     * @param actorId Actor ID
     */
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    /**
     * Gets first name of this actor.
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name of this actor.
     * @param firstName First name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name of this actor.
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name of this actor.
     * @param lastName Last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets this actor's birthday.
     * @param birthday Birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Sets this actor's nationality.
     * @param nationality Nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets films this actor is in.
     * @return Films this actor is in
     */
    public List<Film> getFilmsIn() {
        return filmsIn;
    }

    /**
     * Sets films this actor is in.
     * @param filmsIn Films this actor is in
     */
    public void setFilmsIn(List<Film> filmsIn) {
        this.filmsIn = filmsIn;
    }

    /**
     * Displays actor information in the context of a role in a specific film. With character and salary details.
     * @param character Character that this actor plays.
     * @param actor_salary Salary that this actor received playing the role.
     * @return String representation of this actor playing a role in a film
     */
    public String displayActorForFilm(String character, int actor_salary) {
        return actorId + ", " + firstName + ", " + lastName + ", " + character + ", Salary: $" + actor_salary;
    }

    /**
     * String representation of this actor.
     * @return String representation of this actor
     */
    @Override
    public String toString() {
        return actorId + ", " + firstName + ", " + lastName + ", " + birthday + ", " + nationality;
    }


}
