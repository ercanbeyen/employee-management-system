package com.ercanbeyen.employeemanagementsystem.repository;

import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> { // JpaRepository<Used class, Primary key>
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastname);
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    /*
    User findByFirstName(String firstname);
    User findByLastName(String lastname);

    @Query("")
    User getUser(String username);
     */
}
