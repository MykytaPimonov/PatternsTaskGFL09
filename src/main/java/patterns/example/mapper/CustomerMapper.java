package patterns.example.mapper;

import patterns.example.model.Customer;
import patterns.example.model.Movie;
import patterns.example.model.Rental;
import patterns.example.model.enums.MovieType;
import patterns.example.service.MoviesService;

import java.util.Arrays;
import java.util.Objects;

public class CustomerMapper implements Mapper<String, Customer> {

    private final MoviesService moviesService;

    public CustomerMapper(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public Customer mapFrom(String o) {
        String[] info = o.split("\\|");
        Customer customer = new Customer();
        customer.setName(info[0] == null ? "" : info[0]);
        customer.setRentals(
                Arrays.stream(Objects.requireNonNull(info[1]).split(","))
                        .map(l -> {
                            String[] value = l.split("-");
                            return new Rental(
                                    moviesService.findById(Integer.parseInt(value[0])).orElse(null),
                                    Integer.parseInt(value[1])
                            );
                        })
                        .toList()
        );
        return customer;
    }

    @Override
    public String mapTo(Customer o) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getName()).append("|");
        sb.append(String.join(",",
                        o.getRentals()
                                .stream()
                                .map(r -> r.getMovie().getId() + "-" + r.getDaysRented())
                                .toList()
                        )
        );
        return sb.toString();
    }
}
