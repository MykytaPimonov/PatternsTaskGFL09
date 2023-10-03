package patterns.example;

import patterns.example.controller.MainController;
import patterns.example.mapper.CustomerMapper;
import patterns.example.mapper.MovieMapper;
import patterns.example.model.Customer;
import patterns.example.model.Movie;
import patterns.example.model.Rental;
import patterns.example.model.enums.MovieType;
import patterns.example.repository.CustomersRepositoryText;
import patterns.example.repository.MoviesRepositoryText;
import patterns.example.repository.Repository;
import patterns.example.service.CustomersService;
import patterns.example.service.MoviesService;
import patterns.example.service.StatementsService;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        MovieMapper movieMapper = new MovieMapper();
        Repository<Movie> moviesRepository = new MoviesRepositoryText(movieMapper);
        MoviesService moviesService = new MoviesService(moviesRepository);
        CustomerMapper customerMapper = new CustomerMapper(moviesService);
        Repository<Customer> customerRepository = new CustomersRepositoryText(customerMapper);
        CustomersService customersService = new CustomersService(customerRepository);
        StatementsService statementsService = new StatementsService();

        MainController controller = new MainController(moviesService, customersService, statementsService);
        controller.run();

        /*Movie movie1 = new Movie(
                1,
                "Title1",
                MovieType.DRAMA,
                "Bla-bla-bla",
                "Ukraine",
                "Mykyta",
                List.of("Actor1", "Actor2", "Actor3")
        );

        Movie movie2 = new Movie(
                2,
                "Title2",
                MovieType.COMEDY,
                "Without desc",
                "USA",
                "Ruri",
                List.of("Actor3", "Actor4")
        );

        moviesService.save(movie1);
        moviesService.save(movie2);
        System.out.println(moviesService.findAll());

        Rental rental1 = new Rental(moviesService.findById(1).orElse(null), 15);
        Rental rental2 = new Rental(moviesService.findById(2).orElse(null), 15);

        Customer customer = new Customer("Nick", List.of(rental1, rental2));
        customersService.save(customer);*/


    }
}