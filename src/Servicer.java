import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the back-end logic of the streaming service.
 * Services requests from the user-interface that processes user input.
 * Communicates with DAOs to talk to the database without directly accessing it.
 */
public class Servicer {

    /** All films **/
    private List<Film> allFilms;

    /** All actors **/
    private List<Actor> allActors = new ArrayList<>();

    /** All users **/
    private List<User> allUsers = new ArrayList<>();

    /** Film DAO to manipulate and retrieve from films database table **/
    private FilmDAO filmDAO;

    /** Actor DAO to manipulate and retrieve from actors database table **/
    private ActorDAO actorDAO;

    /** User DAO to manipulate and retrieve from users database table **/
    private UserDAO userDAO;

    /** Film User DAO to manipulate and retrieve from film_user database table **/
    private FilmUserDAO filmUserDAO;

    /** Film Actor DAO to manipulate and retrieve from film_actor database table **/
    private FilmActorDAO filmActorDAO;

    /** Current user interacting with the system **/
    User currentUser;

    /**
     * Constructs a Servicer with a connection to the movie_streaming.db database.
     * Loads all data from the database and populates the model objects with them.
     */
    public Servicer() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/movie_streaming.db");
            filmDAO = new FilmDAO(conn);
            actorDAO = new ActorDAO(conn);
            userDAO = new UserDAO(conn);
            filmUserDAO = new FilmUserDAO(conn);
            filmActorDAO = new FilmActorDAO(conn);

            loadAllFilms();
            loadAllActors();
            loadAllUsers();
            loadAllWatchedFilmsPerUser();
            loadAllFilmsForActor();
            loadCastForFilm();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all films from the database.
     */
    public void loadAllFilms() {
        allFilms = filmDAO.retrieveAllFilms();
    }

    /**
     * Loads all actors from the database.
     */
    public void loadAllActors() {
        allActors = actorDAO.retrieveAllActors();
    }

    /**
     * Loads all users from the database.
     */
    public void loadAllUsers() {
        allUsers = userDAO.retrieveAllUsers();
    }

    /**
     * Loads all watched films for each user.
     */
    public void loadAllWatchedFilmsPerUser() {
        for(User user: allUsers) {
            user.setWatchedFilms(filmUserDAO.getWatchedFilms(user.getUserId(), filmDAO));
        }
    }

    /**
     * Loads all watch films for the current user.
     */
    public void loadWatchedFilmsForCurrentUser() {
        currentUser.setWatchedFilms(
                filmUserDAO.getWatchedFilms(currentUser.getUserId(), filmDAO)
        );
    }

    /**
     * Loads all films for a specific actor.
     */
    public void loadAllFilmsForActor() {
        for(Actor actor: allActors) {
            actor.setFilmsIn(filmActorDAO.retreiveFilmsForAnActor(actor.getActorId(), filmDAO));
        }
    }

    /**
     * Loads the cast for a specific film.
     */
    public void loadCastForFilm() {
        List<Actor> cast;
        for(Film f: allFilms) {
            cast = filmActorDAO.retreiveActorsForAFilm(f.getFilmId(), actorDAO);
            f.setActors(cast);
        }
    }

    /**
     * Gets the current user.
     * @return Current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     * @param currentUser Current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Adds a user to all users.
     * @param user User
     * @return True if the user did not exist and was added, false if the user already exists.
     */
    public boolean addUser(User user) {
        if (userDAO.emailExists(user.getEmail())) {
            System.out.println("Email already exists!");
            return false;
        }
        userDAO.addUser(user);
        return true;
    }

    /**
     * Determines if a user exists based on an email.
     * @param email Email
     * @return True if the user exists, false otherwise
     */
    public boolean seeIfUserExists(String email) {
        if(userDAO.emailExists(email)) {
            return true;
        }
        return false;
    }

    /**
     * Finds watched film by film ID.
     * @param filmId Film ID
     * @return True if the specified film has been watched, false otherwise
     */
    public boolean findWatchedFilmById(int filmId) {
        return currentUser.findWatchedFilmById(filmId);
    }

    /**
     * Gets all films.
     * @return All films
     */
    public List<Film> getAllFilms() {
        return allFilms;
    }

    /**
     * Gets a film using a film ID.
     * @param filmId Film ID
     * @return Film with specified ID
     */
    public Film getFilm(int filmId) {
        for (Film film: allFilms) {
            if (film.getFilmId() == filmId) {
                return film;
            }
        }
        return null;
    }

    /**
     * A specified user watches a specified film.
     * @param film Film
     * @param user User
     * @return Amount of time the user watched the film
     */
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

    /**
     * Determine if a film has been watched already.
     * @param film Film
     * @param user User
     * @return True if the film has been watched by the user, false otherwise
     */
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

    /**
     * Deletes a watched film once a user has finished it.
     * @param filmId Film ID
     */
    public void deleteWatchedFilm(int filmId) {
        filmUserDAO.deleteWatchedFilm(currentUser.getWatchedFilmById(filmId), currentUser);
    }

    /**
     * Gets all actors.
     * @return All actors
     */
    public List<Actor> getAllActors() {
        return allActors;
    }

    public Actor getActor(int id) {
        for (Actor actor: allActors) {
            if (actor.getActorId() == id) {
                return actor;
            }
        }
        return null;
    }

    /**
     * Displays actors for a specified film.
     * @param filmId FIlm ID
     * @return String representation of a cast for a film.
     */
    public String displayActorsForAFilm(int filmId) {
        StringBuilder sb = new StringBuilder();

        for (Actor actor: getFilm(filmId).getActors()) {
            sb.append(actor.displayActorForFilm(filmActorDAO.getCharacter(filmId, actor.getActorId()), filmActorDAO.getActorSalary(filmId, actor.getActorId())));
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Logs in a user.
     * Determines if a user exists based on email, returns the user as the service's current user.
     * @param email Email
     * @param password Password
     * @return User as current user after logging in successfully
     */
    public User login(String email, String password) {
        currentUser = userDAO.retrieveUserByEmail(email);

        if (currentUser == null) return null;
        if (!currentUser.getPassword().equals(password)) return null;

        currentUser.setWatchedFilms(
                filmUserDAO.getWatchedFilms(currentUser.getUserId(), filmDAO)
        );

        return currentUser;
    }
}
