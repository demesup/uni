package com.kpop.demesup.repository;

import com.kpop.demesup.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findOneByEmail(String email);

  @Query(value = "select * from users where active=true order by id desc", nativeQuery = true)
  List<User> findAll();

  @Query(value = "select * from users where authorities='ROLE_ADMIN' and active=true order by id desc", nativeQuery = true)
  List<User> findAllOwners();

  @Query(value = "select * from users where id= :id and active=true", nativeQuery = true)
  Optional<User> findById(@Param("id") Long id);

  @Query(value = "select id from users where email= :email and active=true", nativeQuery = true)
  Optional<Long> findIdByEmail(@Param("email") String email);

  @Query(value = "select id from users where phone= :phone and active=true", nativeQuery = true)
  Optional<Long> findIdByPhone(@Param("phone") String phone);
}
