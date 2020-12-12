package vn.vissoft.ems.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.vissoft.ems.core.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

  User findUserByUserId(long id);

  // START - BLOCK FOR LOGIN
  @Query(
      value =
          // USER INFO
          " SELECT cus.user_id, cus.code userCode, cus.user_name, cus.email, cus.password, cus.is_locked "
              +
              " FROM Users cus " +
              " WHERE cus.user_id = :id ",
      nativeQuery = true)
  Object findByUserId(@Param("id") Long id);

  @Query(
      value =
          // USER INFO
          " SELECT cus.user_id, cus.code userCode, cus.user_name, cus.email, cus.password, cus.is_locked "
              +
              " FROM Users cus " +
              " WHERE " +
              " cus.user_name = :valueSearch ",
      nativeQuery = true)
  Object findByUserName(@Param("valueSearch") String valueSearch);
  // END - BLOCK FOR LOGIN

}
