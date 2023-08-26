package repository;

import java.util.List;

public interface CRUDRepository<K, T> {

    T findById(K id);

    List<T> findAll();

    T create(T object);

    T update(T object);

    boolean delete(K id);
}
