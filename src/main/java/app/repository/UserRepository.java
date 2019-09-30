package app.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import app.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    User checkDup(@Param("username") String username, @Param("email") String emai);
    
//    @Query
//    User checkDupEmail(@Param("email") String emai);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :username")
    void delete(@PathVariable String username);
    
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUser(@Param("username") String username);
}
