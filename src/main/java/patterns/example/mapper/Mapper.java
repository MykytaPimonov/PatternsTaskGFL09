package patterns.example.mapper;

public interface Mapper<F, T> {
    T mapFrom(F o);
    F mapTo(T o);
}
