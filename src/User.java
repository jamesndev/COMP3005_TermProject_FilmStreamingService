import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user that can log into the system and browse and watch films and browse actors.
 * A user can watch many films, a film can be watched by many users.
 */
public class User {

    /** User ID that uniquely identifies a user **/
    private int userId;

    /** This user's username **/
    private String username;

    /** This user's password **/
    private String password;

    /** User email that must be unique **/
    private String email;

    /** All watched films for this user **/
    private List<Film> watchedFilms;

    /**
     * Constructs a User. Initializes with a watched films collections.
     */
    public User() {
        watchedFilms = new ArrayList<>();
    }

    /**
     * Gets watched films for this user.
     * @return Watched films for this user.
     */
    public List<Film> getWatchedFilms() {
        return watchedFilms;
    }

    /**
     * Set watched films for this user.
     * @param watchedFilms Watched films
     */
    public void setWatchedFilms(List<Film> watchedFilms) {
        this.watchedFilms = watchedFilms;
    }

    /**
     * Adds a watched film to all of this user's watched films.
     * @param film Film
     */
    public void addWatchedFilm(Film film) {
        watchedFilms.add(film);
    }

    /**
     * Removes a watched film from this user's watched films.
     * @param film
     */
    public void removeWatchedFilm(Film film) {
        watchedFilms.remove(film);
    }

    /**
     * Find a watched film based on ID.
     * @param filmId Film ID
     * @return True if the watched film exists, false otherwise
     */
    public boolean findWatchedFilmById(int filmId) {
        for (Film film: watchedFilms) {
            if (film.getFilmId() == filmId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a watched film based on ID.
     * @param filmId Film ID
     * @return Watched film if it exists, null otherwise
     */
    public Film getWatchedFilmById(int filmId) {
        for (Film film: watchedFilms) {
            if (film.getFilmId() == filmId) {
                return film;
            }
        }
        return null;
    }

    /**
     * Gets this user's ID.
     * @return This user's ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets this user's ID
     * @param userId User ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets this user's username.
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets this user's username.
     * @return This user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets this user's password.
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets this user's password.
     * @return This user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets this user's email.
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets this user's email.
     * @return This user's email
     */
    public String getEmail() {
        return email;
    }
}
