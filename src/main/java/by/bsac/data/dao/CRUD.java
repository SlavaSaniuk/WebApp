package by.bsac.data.dao;

import java.util.Collection;

public interface CRUD<T> {

    void create(T t);
    Collection<T> read();
    default void update(T t){};
    void delete(T t);
}
