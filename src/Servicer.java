import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Servicer {
    private List<Film> allFilms;
    private List<Film> watchedFilms;

    private List<Actor> allActors = new ArrayList<>();
    private List<User> allUsers = new ArrayList<>();

    private FilmDAO filmDAO;
    private ActorDAO actorDAO;
    private UserDAO userDAO;
    private GenreDAO genreDAO;
    private FilmUserDAO filmUserDAO;

    User currentUser;
    Film currentChosenFilm;

    public Servicer() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/movie_streaming.db");
            filmDAO = new FilmDAO(conn);
            actorDAO = new ActorDAO(conn);
            userDAO = new UserDAO(conn);
            genreDAO = new GenreDAO(conn);
            filmUserDAO = new FilmUserDAO(conn);

            allFilms = filmDAO.retrieveAllFilms();
            watchedFilms = new ArrayList<>();

            allActors = actorDAO.retrieveAllActors();

            allUsers = userDAO.retrieveAllUsers();

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

    public void setUserWatchedFilms() {
        filmUserDAO.getWatchedFilms(currentUser.getUserId());
    }

    public boolean findFilmById(int filmId) {
        return currentUser.findFilmById(filmId);
    }

    public List<Film> getAllFilms() {
        return allFilms;
    }

    public void addWatchedFilm(Film film) {
        watchedFilms.add(film);
    }

    public void removeWatchedFilm(Film film) {
        film.setHasBeenWatched(false);
        watchedFilms.remove(film);
    }

    public Film getFilm(int id) {
        for (Film film: allFilms) {
            if (film.getFilmId() == id) {
                return film;
            }
        }
        return null;
    }

    public List<Film> getWatchedFilms(User user) {
        /*
        List<Film> watched = new ArrayList<>();

        List<Integer> filmIds = filmUserDAO.getWatchedFilmIds(user.getUserId());

        for (int id : filmIds) {
            Film film = getFilmById(id);
            if (film != null) {
                watched.add(film);
            }
        }

        */

        return filmUserDAO.getWatchedFilms(currentUser.getUserId());
    }

    public Film getFilmById(int id) {
        return filmDAO.retrieveFilmByID(id);
    }

    public void setFilms(List<Film> allFilms) {
        this.allFilms = allFilms;
    }

    public int watchFilm(Film film, User user) {
        int watchedSoFar;
        if (isFilmWatchedAlready(film, user)) {
            film.setHasBeenWatched(true);

            if (!film.watch()) {
                film.setHasBeenWatched(false);
                filmUserDAO.updateWatchTime(film, user);
                return -1;
            }

            watchedSoFar = film.getWatchedSoFar();

        } else {
            film.watch();
            user.addWatchedFilm(film);
            filmUserDAO.insertWatchedFilm(film, getCurrentUser());
            addWatchedFilm(film);
            film.setHasBeenWatched(true);

            watchedSoFar = film.getWatchedSoFar();
        }
        filmUserDAO.updateWatchTime(film, user);
        return watchedSoFar;
    }

    public void addFilmToWatched(Film film, User user) {
        filmUserDAO.insertWatchedFilm(film, user);
    }

    public boolean isFilmWatchedAlready(Film film, User user) {
        return filmUserDAO.checkIfFilmWatched(film, user);
    }

    public int getWatchAmount(Film film, User user) {
        if(isFilmWatchedAlready(film, user)) {
            film.setHasBeenWatched(true);
            return filmUserDAO.getProgress(film.getFilmId(), user.getUserId());
        }
        return 0;
    }

    public String displayFilmsForAUser() {
        StringBuilder sb = new StringBuilder();

        for (Film watchedFilm: currentUser.getWatchedFilms()) {
            sb.append(watchedFilm.toString());
        }

        return sb.toString();
    }

    public List<Actor> getAllActors() {
        return allActors;
    }

    public void setAllActors(List<Actor> actors) {
        this.allActors = actors;
    }

    public User login(String email, String password) {
        currentUser = userDAO.retrieveUserByEmail(email);

        if (currentUser == null) {
            return null;
        }

        if (!currentUser.getPassword().equals(password)) {
            return null;
        }

        return currentUser;
    }
}
