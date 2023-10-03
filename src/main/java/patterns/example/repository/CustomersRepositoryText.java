package patterns.example.repository;

import patterns.example.Main;
import patterns.example.mapper.CustomerMapper;
import patterns.example.model.Customer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomersRepositoryText implements Repository<Customer> {
    private final File file;
    private final CustomerMapper customerMapper;

    public CustomersRepositoryText(CustomerMapper movieMapper) {
        this.customerMapper = movieMapper;
        try {
            URL url = Main.class.getClassLoader().getResource("data/customers.txt");
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
    public void save(Customer customer) {
        try (FileWriter fw = new FileWriter(file, true);
             PrintWriter pw = new PrintWriter(fw)) {

            String entity = customerMapper.mapTo(customer);
            pw.println(entity);
            pw.flush();
            fw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> movies = new ArrayList<>();

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String s;
            while ((s = br.readLine()) != null) {
                Customer customer = customerMapper.mapFrom(s);
                movies.add(customer);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }
}
