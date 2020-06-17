package repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface RepositoryDB<E> {
    public void adauga(E entry) throws SQLException;
    public void sterge(E entry) throws SQLException;
    public void update(E entry) throws SQLException;
    public Iterable<E> getAll() ;
}
