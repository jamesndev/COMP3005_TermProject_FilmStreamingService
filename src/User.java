import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int userId;
    private String username;
    private String password;
    private String email;
    private int subscriptionId;

    private List<Film> watchedFilms;

    public User(int userId, String username, String password, String email, int subscriptionId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.subscriptionId = subscriptionId;

        watchedFilms = new ArrayList<>();
    }

    public User() {
        watchedFilms = new ArrayList<>();
    }

    public List<Film> getWatchedFilms() {
        return watchedFilms;
    }

    public void setWatchedFilms(List<Film> watchedFilms) {
        this.watchedFilms = watchedFilms;
    }

    public void addWatchedFilm(Film film) {
        watchedFilms.add(film);
    }

    public boolean findFilmById(int filmId) {
        for (Film film: watchedFilms) {
            if (film.getFilmId() == filmId) {
                return true;
            }
        }
        return false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSubscriptionId(int subscription_id) {
        this.subscriptionId = subscription_id;
    }
}
