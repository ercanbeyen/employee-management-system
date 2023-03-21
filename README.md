# Employee Management System
---

## Spring Boot Application
---

### Summary

Main entity in this project is employee and the status of the employees may be managed through this other related entities.

There are 8 entities in this project
- Employee
- Job Title
- Department
- Salary
- Image
- Payment
- Ticket
- Comment
- Address

There are 3 roles among the employees
- Admin
- Manager
- User

### Requirements

- Each employee must have an unique email address.
- While creating an employee, you must fill employee's department, job title, salary and address.
- While creating an employee, you may select one of the existing department and job title in the database.
- The amount value of the payment should not be negative.
- The payment types are salary, incentive and bonus.
- In order for a ticket to be opened, someone registered in the system must be a requester.
- The tickets in the system can be commented on and the employees who comment must be registered in the system.
- If you want to apply insert/delete/update operations on entities, you need to migrate the database.

### Additional
Admin and manager roles may observe the statistical value for each entities other than employee.

```
GET /statistics/{entity}
```

### Tech Stack
---
- Java 18
- Spring Boot
- Spring Data JPA
- Spring Security
- JUnit 5
- Model Mapper
- OpenAPI Documentation
- MySQL
- Flyway


### Prerequisties
---
- Maven

### Api Documentation
---

In order to get api documentation of the project, you may use swagger-ui with its port.<br/>
`http://localhost:${PORT}/swagger-ui.html`
