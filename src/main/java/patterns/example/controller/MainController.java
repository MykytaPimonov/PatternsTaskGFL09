package patterns.example.controller;

import patterns.example.model.Customer;
import patterns.example.model.Movie;
import patterns.example.model.Rental;
import patterns.example.model.enums.MovieType;
import patterns.example.service.CustomersService;
import patterns.example.service.MoviesService;
import patterns.example.service.StatementsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MainController {
    private final MoviesService moviesService;
    private final CustomersService customersService;
    private final StatementsService statementsService;

    private BufferedReader br;


    public MainController(MoviesService moviesService, CustomersService customersService, StatementsService statementsService) {
        this.moviesService = moviesService;
        this.customersService = customersService;
        this.statementsService = statementsService;
    }

    public void run() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
        while (true) {
            System.out.println("Choose command:\n1 - Movie menu\n2 - Customer menu\n3 - Exit");
            String com = br.readLine().trim();
            if (com.equals("1")) {
                movie();
                continue;
            }
            if (com.equals("2")) {
                customer();
                continue;
            }
            if (com.equals("3")) {
                break;
            }
        }
        br.close();
        isr.close();
    }

    private void movie() throws IOException {
        while (true) {
            System.out.println("Choose command:\n1 - Add new\n2 - Show all\n3 - Exit");
            String com = br.readLine().trim();
            if (com.equals("1")) {
                addMovie();
                continue;
            }
            if (com.equals("2")) {
                List<Movie> movies = moviesService.findAll();
                for (Movie m : movies) {
                    System.out.println(m);
                }
                continue;
            }
            if (com.equals("3")) {
                break;
            }
        }
    }

    private void addMovie() throws IOException {
        System.out.println("Enter data:");
        System.out.println("Id:");
        int id = Integer.parseInt(br.readLine());
        System.out.println("Title:");
        String title = br.readLine().trim();
        System.out.println("Type:");
        MovieType type = MovieType.valueOf(br.readLine().trim());
        System.out.println("Description:");
        String desc = br.readLine().trim();
        System.out.println("Country:");
        String county = br.readLine().trim();
        System.out.println("Director:");
        String director = br.readLine().trim();
        System.out.println("Actor separated by space:");
        String[] actors = br.readLine().trim().split("\\s+");

        Movie movie = new Movie(
                id,
                title,
                type,
                desc,
                county,
                director,
                List.of(actors)
        );

        moviesService.save(movie);
        System.out.println("Movie was saved");
    }

    private void customer() throws IOException {
        while (true) {
            System.out.println("Choose command:\n1 - Add new\n2 - Log by name\n3 - Exit");
            String com = br.readLine().trim();
            if (com.equals("1")) {
                System.out.println("Enter new name:");
                String name = br.readLine().trim();
                Customer customer = new Customer(name, Collections.emptyList());
                customersService.save(customer);
                authCustomer(customer);
                continue;
            }
            if (com.equals("2")) {
                System.out.println("Enter your name:");
                String name = br.readLine().trim();
                Optional<Customer> customer = customersService.findByName(name);
                if (customer.isEmpty()) {
                    System.out.println("Illegal name");
                    continue;
                }
                authCustomer(customer.get());
                continue;
            }
            if (com.equals("3")) {
                break;
            }
        }
    }

    private void authCustomer(Customer customer) throws IOException {
        while (true) {
            System.out.println("Choose command:\n1 - Add movie\n2 - Get statement\n3 - Exit");
            String com = br.readLine().trim();
            if (com.equals("1")) {
                System.out.println("Choose movie number:");
                List<Movie> movies = moviesService.findAll();
                for (int i = 0; i < movies.size(); i++) {
                    System.out.println((i + 1) + "\t" + movies);
                }
                int id = Integer.parseInt(br.readLine());
                System.out.println("Enter days rented:");
                int daysRented = Integer.parseInt(br.readLine());
                Rental rental = new Rental(
                        movies.get(id - 1),
                        daysRented
                );
                customer.getRentals().add(rental);
                continue;
            }
            if (com.equals("2")) {
                System.out.println("Choose place:\n1 - Console\n2 - HTML");
                String type = br.readLine().trim();
                if (type.equals("1")) {
                    System.out.println(statementsService.statement(customer));
                }
                if (type.equals("2")) {
                    System.out.println(statementsService.statementOnHtmlPage(customer));
                }
                continue;
            }
            if (com.equals("3")) {
                break;
            }
        }
    }
}
