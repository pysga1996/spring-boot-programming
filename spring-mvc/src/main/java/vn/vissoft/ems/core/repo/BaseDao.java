package vn.vissoft.ems.core.repo;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

  List<T> getAll();

  Optional<T> get(int id);

  T save(T t);

  void update(T t);

  void delete(T t);
}
