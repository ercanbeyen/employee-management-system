package com.ercanbeyen.springbootfirstrestapi.repository;

import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> { // JpaRepository<Used class, Primary key>

    List<Employee> findAllByStatus(boolean status);
    List<Employee> findAllByFirstName(String firstName);
    List<Employee> findAllByLastName(String lastname);
    List<Employee> findAllByFirstNameAndLastName(String firstName, String lastName);

    /*
    User findByFirstName(String firstname);
    User findByLastName(String lastname);

    @Query("")
    User getUser(String username);
     */
}
