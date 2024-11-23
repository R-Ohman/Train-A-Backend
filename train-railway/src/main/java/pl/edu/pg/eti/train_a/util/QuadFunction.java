package pl.edu.pg.eti.train_a.util;

@FunctionalInterface
public interface QuadFunction<A, B, C, D, O> {
    O apply(A a, B b, C c, D d);

    default <R> QuadFunction<A, B, C, D, O> andThen(QuadFunction<A, B, C, D, O> after) {
        return after;
    }
}