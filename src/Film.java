import java.time.LocalDate;

public class Film {

    private int filmId;
    private String title;
    private String director;
    private int runtime;
    private String releaseDate;
    private String rating;

    public Film(int filmId, String title, String director, int runtime, String releaseDate, String rating) {
        this.filmId = filmId;
        this.title = title;
        this.director = director;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public Film() {

    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
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
}
