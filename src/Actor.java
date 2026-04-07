import java.util.ArrayList;
import java.util.List;

public class Actor {
    private int actorId;
    private String firstName;
    private String lastName;
    private String birthday;
    private String nationality;

    private List<Film> filmsIn;

    public Actor(int actorId, String firstName, String lastName, String birthday, String nationality) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.nationality = nationality;

        filmsIn = new ArrayList<>();
    }

    public Actor() {

    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Film> getFilmsIn() {
        return filmsIn;
    }

    public void setFilmsIn(List<Film> filmsIn) {
        this.filmsIn = filmsIn;
    }

    public String displayActorForFilm(String character, int actor_salary) {
        return actorId + ", " + firstName + ", " + lastName + ", " + character + ", Salary: $" + actor_salary;
    }

    @Override
    public String toString() {
        return actorId + ", " + firstName + ", " + lastName + ", " + birthday + ", " + nationality;
    }


}
