package patterns.example.mapper;

import patterns.example.model.Movie;
import patterns.example.model.enums.MovieType;

import java.util.Arrays;
import java.util.Objects;

public class MovieMapper implements Mapper<String, Movie> {
    @Override
    public Movie mapFrom(String o) {
        String[] info = o.split("\\|");
        Movie movie = new Movie();
        movie.setId(Integer.parseInt(info[0]));
        movie.setTitle(info[1] == null ? "" : info[1]);
        movie.setType(MovieType.valueOf(info[2]));
        movie.setDescription(info[3] == null ? "" : info[3]);
        movie.setCountry(info[4] == null ? "" : info[4]);
        movie.setDirector(info[5] == null ? "" : info[5]);
        movie.setActors(
                Arrays.stream(Objects.requireNonNull(info[6]).split(",")).toList()
        );
        return movie;
    }

    @Override
    public String mapTo(Movie o) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getId().toString()).append("|");
        sb.append(o.getTitle() == null ? "" : o.getTitle()).append("|");
        sb.append(o.getType().toString()).append("|");
        sb.append(o.getDescription() == null ? "" : o.getDescription()).append("|");
        sb.append(o.getCountry() == null ? "" : o.getCountry()).append("|");
        sb.append(o.getDirector() == null ? "" : o.getDirector()).append("|");
        sb.append(String.join(",", o.getActors()));
        return sb.toString();
    }
}
