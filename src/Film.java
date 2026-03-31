import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Film {

    private int filmId;
    private String title;
    private String director;
    private int runtime;
    private String releaseDate;
    private String rating;

    private boolean hasBeenWatched = false;
    private int watchedSoFar = 0;

    private List<Actor> actors;

    public Film(int filmId, String title, String director, int runtime, String releaseDate, String rating) {
        this.filmId = filmId;
        this.title = title;
        this.director = director;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.rating = rating;

        actors = new ArrayList<>();
    }

    public Film() {

    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean watch() {
        Random rand = new Random();
        int timeWatched = rand.nextInt(30 - 10 + 1) + 10;

        watchedSoFar += timeWatched;

        if (watchedSoFar >= runtime) {
            hasBeenWatched = false;
            watchedSoFar = 0;
            return false;
        }
        return true;
    }

    public boolean isHasBeenWatched() {
        return hasBeenWatched;
    }

    public void setHasBeenWatched(boolean hasBeenWatched) {
        this.hasBeenWatched = hasBeenWatched;
    }

    public int getWatchedSoFar() {
        return watchedSoFar;
    }

    public void setWatchedSoFar(int watchedSoFar) {
        this.watchedSoFar = watchedSoFar;
    }

    public String displayDetailedFilmInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("Directed by: " + director);
        sb.append("\n");
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

    @Override
    public String toString() {
        return filmId + ", " + title + ", Dr. " + director + ", " + runtime + " minutes, " + rating;
    }
}
