import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a film that can be watched by users and contain actors.
 * One film can have many actors, one actor can be in many films.
 * One film can be watched by many users, many users can watch one film.
 */
public class Film {
    /** This film's ID **/
    private int filmId;

    /** This film's title **/
    private String title;

    /** This film's director name **/
    private String director;

    /** Duration of this film **/
    private int runtime;

    /** What date this film was released **/
    private String releaseDate;

    /** Rating for this film. i.e., PG-13, R, etc. **/
    private String rating;

    /** Indicates if film has been watched by a user **/
    private boolean hasBeenWatched = false;

    /** How much time a user has watched this film so far **/
    private int watchedSoFar = 0;

    /** Actors in this film **/
    private List<Actor> actors;


    /**
     * Constructs a film.
     */
    public Film() {}

    /**
     * Gets this film's ID.
     * @return Film ID
     */
    public int getFilmId() {
        return filmId;
    }

    /**
     * Sets this film's ID.
     * @param filmId Film ID
     */
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    /**
     * Gets this film's title.
     * @return Film title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets this film's title.
     * @param title Film title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets this film's director.
     * @param director Film director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Sets this film's runtime.
     * @param runtime Film runtime
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Sets this film's release data.
     * @param releaseDate Film release date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Sets this film's rating.
     * @param rating Film rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Watches a film for a random duration and updates time watched so far and updates film watched status.
     * @return True if film has not reached the end of its duration, false if the film has completed.
     */
    public boolean watch() {
        Random rand = new Random();
        int timeWatched = rand.nextInt(50 - 20 + 1) + 20;

        watchedSoFar += timeWatched;

        if (watchedSoFar >= runtime) {
            hasBeenWatched = false;
            watchedSoFar = 0;
            return false;
        }
        return true;
    }

    /**
     * Sets the value for the has been watched status.
     * @param hasBeenWatched Has been watched status
     */
    public void setHasBeenWatched(boolean hasBeenWatched) {
        this.hasBeenWatched = hasBeenWatched;
    }

    /**
     * Gets amount of watch time for this film.
     * @return Amount of film time watched
     */
    public int getWatchedSoFar() {
        return watchedSoFar;
    }

    /**
     * Sets amount of watch time for this film.
     * @param watchedSoFar Amount of film time watched
     */
    public void setWatchedSoFar(int watchedSoFar) {
        this.watchedSoFar = watchedSoFar;
    }

    /**
     * Displays detailed film information.
     * @return Detailed string representation of this film
     */
    public String displayDetailedFilmInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("Directed by: " + director);
        sb.append("\n");
        sb.append("Length: " + runtime);
        sb.append("\n");
        sb.append("Released on: " + releaseDate);
        sb.append("\n");
        sb.append("Rated " + rating);
        sb.append("\n");

        if(hasBeenWatched) {
            sb.append(runtime - watchedSoFar + " minutes remaining");
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Display information for this film which has been watched already.
     * @param watchProgress Film watch progress
     * @return String representation of this film's information and watch progress.
     */
    public String displayWatchedFilmInfo(int watchProgress) {
        StringBuilder sb = new StringBuilder();

        sb.append(getFilmId() + ") " + getTitle() + " | (" + watchProgress + " min watched) | ");

        if(hasBeenWatched) {
            sb.append(runtime - watchProgress + " minutes remaining");
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Gets all actors in this film.
     * @return All actors in this film
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Sets all actors in this film.
     * @param actors All actors in this film
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * String representation of this film.
     * @return String with this film's information
     */
    @Override
    public String toString() {
        return filmId + ", " + title + ", Dir. " + director + ", " + runtime + " minutes, " + rating;
    }
}
