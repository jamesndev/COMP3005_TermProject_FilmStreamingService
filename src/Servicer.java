import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Servicer {
    private List<Film> allFilms;
    private List<Film> watchedFilms;

    private List<Actor> actors = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    private FilmDAO filmDAO;
    private ActorDAO actorDAO;
    private UserDAO userDAO;
    private GenreDAO genreDAO;

    User currentUser;
    Film currentChosenFilm;

    public Servicer() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/movie_streaming.db");
            filmDAO = new FilmDAO(conn);
            actorDAO = new ActorDAO(conn);
            userDAO = new UserDAO(conn);
            genreDAO = new GenreDAO(conn);

            allFilms = filmDAO.retrieveAllFilms();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean addUser(User user) {
        if (userDAO.emailExists(user.getEmail())) {
            System.out.println("Email already exists!");
            return false;
        }
        userDAO.addUser(user);
        return true;
    }

    public List<Film> getAllFilms() {
        return allFilms;
    }

    public Film getFilm(int id) {
        for (Film film: allFilms) {
            if (film.getFilmId() == id) {
                return film;
            }
        }
        return null;
    }

    public Film getFilmById(int id) {
        return filmDAO.retrieveFilmByID(id);
    }

    public void setFilms(List<Film> allFilms) {
        this.allFilms = allFilms;
    }

    public int watchFilm(Film film) {
        int watchedSoFar;
        if (film.isHasBeenWatched()) {
            for(Film f: watchedFilms) {
                if(f.equals(film)){
                    film.watch();
                }
            }
            watchedSoFar = film.getWatchedSoFar();
        } else {
            for(Film f: allFilms) {
                if(f.equals(film)){

                    film.watch();
                }
            }
            watchedSoFar = film.getWatchedSoFar();

        }
        return watchedSoFar;
    }

    public void addFilmToWatched(Film film, User user) {
        filmDAO.insertWatchedFilm(film, user);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public User login(String email, String password) {
        currentUser = userDAO.retrieveUserByUsername(email);

        if (currentUser == null) {
            return null; // user doesn't exist
        }

        if (!currentUser.getPassword().equals(password)) {
            return null; // wrong password
        }

        return currentUser; // success
    }
}
