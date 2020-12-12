package vn.vissoft.ems.core.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.vissoft.ems.core.helper.RepoCommonUtil;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AbsCommonRepository<T> {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  private SessionFactory getCurrentSessionFactory() {
    return entityManagerFactory.unwrap(SessionFactory.class);
  }

  public List<Object> searchPaging(Class<?> modelClass, String tableName, String sqlQuery,
      List<Object> params, int firstResult, int maxResults) {
    List<Object> lstRs = new ArrayList<>();
    Session session = null;
    try {
      SessionFactory sessionFactory = getCurrentSessionFactory();
      session = sessionFactory.openSession();
      // TODO
      Query query = session.createNativeQuery(sqlQuery, modelClass);
      setParams(query, params);
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      lstRs = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }

    return lstRs;
  }

  public int countPage(String tableName, String sqlWhere, List<Object> params) {
    int count = 0;
    Session session = null;
    try {
      SessionFactory sessionFactory = getCurrentSessionFactory();
      session = sessionFactory.openSession();
      // TODO
      Query query = session.createNativeQuery(" SELECT COUNT (*) From " + tableName + sqlWhere);
      setParams(query, params);
      count = RepoCommonUtil.getIntegerFromBigInteger(query.getSingleResult());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }

    return count;
  }

  public boolean updateRow(String sqlUpdate, List<Object> params) {
    boolean isSuccess = false;
    Session session = null;
    Transaction tx = null;
    try {
      SessionFactory sessionFactory = getCurrentSessionFactory();
      session = sessionFactory.openSession();
      tx = session.beginTransaction();
      // TODO
      Query query = session.createNativeQuery(sqlUpdate);
      setParams(query, params);
      query.executeUpdate();
      tx.commit();
      isSuccess = true;
    } catch (Exception e) {
      isSuccess = false;
      tx.rollback();
      e.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return isSuccess;
  }

  private void setParams(Query query, List<Object> params) {
    for (int i = 0; i < params.size(); i++) {
      query.setParameter(i + 1, params.get(i));
    }
  }
}
