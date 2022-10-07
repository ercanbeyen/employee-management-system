package com.ercanbeyen.springbootfirstrestapi.repository;

import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> { // JpaRepository<Used class, Primary key>
    /*
    User findByFirstName(String firstname);
    User findByLastName(String lastname);

    @Query("")
    User getUser(String username);
     */
}
