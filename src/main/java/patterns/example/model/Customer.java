package patterns.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Customer {
    private String name;
    private List<Rental> rentals;

    public Customer() {
        name = "";
        rentals = new ArrayList<>();
    }
}
