package vn.vissoft.ems.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vissoft.ems.core.model.Menu;

import java.util.List;

@Repository
public interface MenusRepository extends JpaRepository<Menu, Integer> {

  List<Menu> findAllByIsActiveAndParentIsNullOrderByPriorityAsc(Boolean isActive);
}
