package patterns.example.service;

import patterns.example.model.Movie;
import patterns.example.model.enums.MovieType;
import patterns.example.repository.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class MoviesService {
    private final Repository<Movie> moviesRepository;

    public MoviesService(Repository<Movie> moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public void save(Movie movie) {
        moviesRepository.save(movie);
    }

    public List<Movie> findAll() {
        return moviesRepository.findAll();
    }

    public Optional<Movie> findById(int id) {
        return moviesRepository.findAll()
                .stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    public List<Movie> findByType(MovieType type) {
        return moviesRepository.findAll()
                .stream()
                .filter(m -> m.getType() == type)
                .toList();
    }

    public List<Movie> findByCountry(String country) {
        return moviesRepository.findAll()
                .stream()
                .filter(m -> m.getCountry().equals(country))
                .toList();
    }

    public List<Movie> findByDirector(String director) {
        return moviesRepository.findAll()
                .stream()
                .filter(m -> m.getCountry().equals(director))
                .toList();
    }

    public List<Movie> findByHasActor(String... actors) {
        return moviesRepository.findAll()
                .stream()
                .filter(m -> new HashSet<>(m.getActors()).containsAll(List.of(actors)))
                .toList();
    }
}
