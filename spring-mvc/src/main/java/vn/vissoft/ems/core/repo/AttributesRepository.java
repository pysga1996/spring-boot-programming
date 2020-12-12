package vn.vissoft.ems.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vissoft.ems.core.model.Attributes;

@Repository
public interface AttributesRepository extends JpaRepository<Attributes, Integer> {

  Attributes findByKeyIgnoreCase(String key);

  Attributes findByKeyIgnoreCaseAndIsActiveTrue(String key);

}
