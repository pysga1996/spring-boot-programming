package vn.vissoft.ems.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vissoft.ems.core.model.Chart;

@Repository
public interface ChartRepo extends JpaRepository<Chart, Integer> {

  Chart findByLegendAndCategory(String legend, String category);

}
