package patterns.example.service;

import patterns.example.model.Customer;
import patterns.example.repository.Repository;

import java.util.List;
import java.util.Optional;

public class CustomersService {

    private final Repository<Customer> customerRepository;

    public CustomersService(Repository<Customer> customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findByName(String name) {
        return customerRepository.findAll()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }
}
