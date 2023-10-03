package patterns.example.repository;

import java.util.List;

public interface Repository<T> {
    void save(T customer);
    List<T> findAll();
}
