package patterns.example.repository;

import patterns.example.Main;
import patterns.example.mapper.MovieMapper;
import patterns.example.model.Movie;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepositoryText implements Repository<Movie> {

    private final File file;
    private final MovieMapper movieMapper;

    public MoviesRepositoryText(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
        try {
            URL url = Main.class.getClassLoader().getResource("data/movies.txt");
            if (url == null) {
                throw new IOException("Not found file");
            }
            URI uri = url.toURI();
            file = new File(uri);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Movie movie) {
        try (FileWriter fw = new FileWriter(file, true);
             PrintWriter pw = new PrintWriter(fw)) {

            String entity = movieMapper.mapTo(movie);
            pw.println(entity);
            pw.flush();
            fw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String s;
            while ((s = br.readLine()) != null) {
                Movie movie = movieMapper.mapFrom(s);
                movies.add(movie);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }
}
