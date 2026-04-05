import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Servicer {
    private List<Film> allFilms;
    //private List<Film> watchedFilms;

    private List<Actor> allActors = new ArrayList<>();
    private List<User> allUsers = new ArrayList<>();

    private FilmDAO filmDAO;
    private ActorDAO actorDAO;
    private UserDAO userDAO;
    private GenreDAO genreDAO;
    private FilmUserDAO filmUserDAO;
    private FilmActorDAO filmActorDAO;

    User currentUser;

    public Servicer() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/movie_streaming.db");
            filmDAO = new FilmDAO(conn);
            actorDAO = new ActorDAO(conn);
            userDAO = new UserDAO(conn);
            genreDAO = new GenreDAO(conn);
            filmUserDAO = new FilmUserDAO(conn);
            filmActorDAO = new FilmActorDAO(conn);

            loadAllFilms();
            loadAllActors();
            loadAllUsers();
            loadAllWatchedFilmsPerUser();
            loadAllFilmsForActor();
            loadCastsForFilm();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAllFilms() {
        allFilms = filmDAO.retrieveAllFilms();
    }

    public void loadAllActors() {
        allActors = actorDAO.retrieveAllActors();
    }

    public void loadAllUsers() {
        allUsers = userDAO.retrieveAllUsers();
    }

    public void loadAllWatchedFilmsPerUser() {
        for(User user: allUsers) {
            user.setWatchedFilms(filmUserDAO.getWatchedFilms(user.getUserId(), filmDAO));
        }
    }

    public void loadWatchedFilmsForCurrentUser() {
        currentUser.setWatchedFilms(
                filmUserDAO.getWatchedFilms(currentUser.getUserId(), filmDAO)
        );
    }

    public void loadAllFilmsForActor() {
        for(Actor actor: allActors) {
            actor.setFilmsIn(filmActorDAO.retreiveFilmsForAnActor(actor.getActorId(), filmDAO));
        }
    }

    public void loadCastsForFilm() {
        List<Actor> cast;
        for(Film f: allFilms) {
            cast = filmActorDAO.retreiveActorsForAFilm(f.getFilmId(), actorDAO);
            f.setActors(cast);
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
        filmUserDAO.getWatchedFilms(currentUser.getUserId(), filmDAO);
    }

    public Film findFilmById(int filmId) {
        for(Film film: allFilms) {
            if(film.getFilmId() == filmId) {
                return film;
            }
        }
        return null;
    }

    public boolean findWatchedFilmById(int filmId) {
        return currentUser.findWatchedFilmById(filmId);
    }

    public List<Film> getAllFilms() {
        return allFilms;
    }

    /*
    public void addWatchedFilm(Film film) {
        watchedFilms.add(film);
    }

    public void removeWatchedFilm(Film film) {
        film.setHasBeenWatched(false);
        watchedFilms.remove(film);
    }
    */


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
            filmUserDAO.insertWatchedFilm(film, getCurrentUser());
            currentUser.addWatchedFilm(film);
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

    public void deleteWatchedFilm(int filmId) {
        filmUserDAO.deleteWatchedFilm(currentUser.getWatchedFilmById(filmId), currentUser);
    }

    public List<Actor> getAllActors() {
        return allActors;
    }

    public void setAllActors(List<Actor> actors) {
        this.allActors = actors;
    }

    public Actor getActor(int id) {
        for (Actor actor: allActors) {
            if (actor.getActorId() == id) {
                return actor;
            }
        }
        return null;
    }

    public Actor getActorById(int id) {
        return actorDAO.retrieveActorByID(id);
    }

    public String displayActorsForAFilm(int filmId) {
        StringBuilder sb = new StringBuilder();

        for (Actor actor: getFilm(filmId).getActors()) {
            sb.append(actor.toString());
            sb.append("\n");
        }

        return sb.toString();
    }



    public User login(String email, String password) {
        currentUser = userDAO.retrieveUserByEmail(email);

        if (currentUser == null) return null;
        if (!currentUser.getPassword().equals(password)) return null;

        currentUser.setWatchedFilms(
                filmUserDAO.getWatchedFilms(currentUser.getUserId(), filmDAO)
        );

        return currentUser;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }
}
