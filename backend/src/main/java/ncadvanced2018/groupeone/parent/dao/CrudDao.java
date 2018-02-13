package ncadvanced2018.groupeone.parent.dao;

import java.io.Serializable;
import java.util.Optional;


public interface CrudDao<T, ID extends Serializable> {

    T save(T entity);

    Optional<T> findOne(ID id);

    void delete(T entity);

    void delete(ID id);

    boolean exists(ID id);

    Long count();
}
