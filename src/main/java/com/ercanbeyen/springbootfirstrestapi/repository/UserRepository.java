package com.ercanbeyen.springbootfirstrestapi.repository;

import com.ercanbeyen.springbootfirstrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { // JpaRepository<Used class, Primary key>
    /*
    User findByFirstName(String firstname);
    User findByLastName(String lastname);

    @Query("")
    User getUser(String username);
     */
}
