# Employee Management System
---

## Spring Boot Application
---

### Summary

Main entity in this project is employee and the status of the employees may be managed through this other related entities.

There are 7 entities in this project
- Employee
- Job Title
- Department
- Salary
- Image
- Ticket
- Payment

There are 3 roles among the employees
- Admin
- Manager
- User

### Requirements

- Email address of the employee should be unique.
- While creating an employee, you may assign one of the existing department and job title in the database.
- The amount value of the payment should not be negative.
- The payment includes salary and incentive payments.
- In order for a ticket to be opened, there must be someone requesting it.

### Tech Stack
---
- Java 18
- Spring Boot
- Spring Data JPA
- Spring Security
- JUnit 5
- OpenAPI Documentation
- MySQL


### Prerequisties
---
- Maven

### Api Documentation
---

In order to get api documentation of the project, you may use swagger-ui with its port.<br/>
`http://localhost:${PORT}/swagger-ui.html`
