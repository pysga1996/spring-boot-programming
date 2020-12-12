package vn.vissoft.ems.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vissoft.ems.core.repo.AbsCommonRepository;

import java.util.List;

@Service
public class AbsCommonService<T> {

  @Autowired
  private AbsCommonRepository absCommonRepository;

  //
  public List<Object> searchPaging(Class<?> modelClass, String tableName, String sqlQuery,
      List<Object> params, int page, int size) {
    int firstResult = (page - 1) * size;
    return absCommonRepository
        .searchPaging(modelClass, tableName, sqlQuery, params, firstResult, size);
  }

  public int countPage(String tableName, String sqlWhere, List<Object> params) {
    return absCommonRepository.countPage(tableName, sqlWhere, params);
  }

  // update
  public boolean updateRow(String sqlUpdate, List<Object> params) {
    return absCommonRepository.updateRow(sqlUpdate, params);
  }

  ;
}
