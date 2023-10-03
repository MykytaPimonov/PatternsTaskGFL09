package patterns.example.model;

import lombok.*;
import patterns.example.model.enums.MovieType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {
    private Integer id;
    private String title;
    private MovieType type;
    private String description;
    private String country;
    private String director;
    private List<String> actors = new ArrayList<>();
}
