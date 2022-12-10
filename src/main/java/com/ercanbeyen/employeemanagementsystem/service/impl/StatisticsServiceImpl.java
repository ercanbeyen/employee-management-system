package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;
import com.ercanbeyen.employeemanagementsystem.entity.*;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private final DepartmentService departmentService;

    @Autowired
    private final JobTitleService jobTitleService;

    @Autowired
    private final SalaryService salaryService;

    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final TicketService ticketService;

    @Override
    public Statistics<Integer> getDepartmentStatistics() {
        Statistics<Integer> statistics = new Statistics<>();
        List<Department> departments = departmentService.getDepartmentsForStatistics();

        /* Set minimum */
        Department minimumDepartment = departments
                .stream()
                .min(Comparator.comparing(Department::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMinimum(minimumDepartment.getName());

        /* Set maximum */
        Department maximumDepartment = departments
                .stream()
                .max(Comparator.comparing(Department::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMaximum(maximumDepartment.getName());

        /* Set average */
        Double average = null;
        OptionalDouble optionalAverage = departments
                .stream()
                .mapToDouble(Department::getSize)
                .average();

        if (optionalAverage.isPresent()) {
            average = optionalAverage.getAsDouble();
        }

        statistics.setAverage(average);

        /* Set sizes */
        HashMap<String, Integer> map = new HashMap<>();

        for (Department department : departments) {
            map.put(department.getName(), department.getSize());
        }

        statistics.setSizes(map);

        return statistics;
    }

    @Override
    public Statistics<Integer> getJobTitleStatistics() {
        Statistics<Integer> statistics = new Statistics<>();
        List<JobTitle> jobTitles = jobTitleService.getJobTitlesForStatistics();

        /* Set minimum */
        JobTitle minimumJobTitle = jobTitles
                .stream()
                .min(Comparator.comparing(JobTitle::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMinimum(minimumJobTitle.getName());

        /* Set maximum */
        JobTitle maximumJobTitle = jobTitles
                .stream()
                .max(Comparator.comparing(JobTitle::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMaximum(maximumJobTitle.getName());

        /* Set average */
        Double average = null;

        OptionalDouble optionalAverage = jobTitles
                .stream()
                .mapToDouble(JobTitle::getSize)
                .average();

        if (optionalAverage.isPresent()) {
            average = optionalAverage.getAsDouble();
        }

        statistics.setAverage(average);

        /* Set sizes */
        HashMap<String, Integer> map = new HashMap<>();

        for (JobTitle jobTitle: jobTitles) {
            map.put(jobTitle.getName(), jobTitle.getSize());
        }

        statistics.setSizes(map);

        return statistics;
    }

    @Override
    public Statistics<Integer> getSalaryStatistics() {
        Statistics<Integer> statistics = new Statistics<>();
        List<Salary> salaries = salaryService.getSalariesForStatistics();

        /* Set minimum */
        Salary minimumSalary = salaries
                .stream()
                .min(Comparator.comparing(Salary::getAmount))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMinimum(minimumSalary.getAmount().toString());

        /* Set maximum */
        Salary maximumSalary = salaries
                .stream()
                .max(Comparator.comparing(Salary::getAmount))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMaximum(maximumSalary.getAmount().toString());

        /* Set average */
        Double average = null;

        OptionalDouble optionalAverage = salaries
                .stream()
                .mapToDouble(Salary::getAmount)
                .average();

        if (optionalAverage.isPresent()) {
            average = optionalAverage.getAsDouble();
        }

        statistics.setAverage(average);

        List<Currency> currencies = salaries
                .stream()
                .map(Salary::getCurrency)
                .toList();

        /* Set sizes */
        HashMap<String, Integer> map = new HashMap<>();

        for (Currency currency : currencies) {
            map.put(String.valueOf(currency), 0);
        }

        for (Salary salary : salaries) {
            String key = String.valueOf(salary.getCurrency());
            Integer value = map.get(key);
            map.put(key, ++value);
        }

        statistics.setSizes(map);

        return statistics;
    }

    @Override
    public Statistics<Map<Role, Integer>> getRoleStatistics() {

        Statistics<Map<Role, Integer>> statistics = new Statistics<>();

        /* Set sizes */
        List<DepartmentDto> departments = departmentService.
                getDepartments()
                .stream()
                .distinct()
                .toList();

        List<Role> roles = employeeService.getEmployeesForStatistics()
                .stream()
                .map(Employee::getRole)
                .distinct()
                .toList();

        Map<String, Map<Role, Integer>> sizesOfDepartments = new HashMap<>();

        for (DepartmentDto department : departments) {
            HashMap<Role, Integer> currentMap = new HashMap<>();

            for (Role role : roles) {
                currentMap.put(role, 0);
            }

            String departmentName = department.getName();
            List<Role> rolesInDepartment = employeeService.findRolesByDepartmentForStatistics(departmentName);

            for (Role role : rolesInDepartment) {
                int currentOccurrence = currentMap.get(role);
                currentMap.put(role, ++currentOccurrence);
            }

            sizesOfDepartments.put(departmentName, currentMap);
        }

        statistics.setSizes(sizesOfDepartments);

        /* Set minimum, maximum and average */
        Map<Role, Integer> roleMap = new HashMap<>();

        for (Role role : roles) {
            roleMap.put(role, 0);
        }

        Collection<Map<Role, Integer>> departmentRoleMapList = sizesOfDepartments.values();

        for (Map<Role, Integer> departmentRoleSize : departmentRoleMapList) {
            for (Role role : departmentRoleSize.keySet()) {
                int numberOfOccurrenceInDepartment = departmentRoleSize.get(role); // number of occurrence of the role in current department
                int currentOccurrence = roleMap.get(role);
                currentOccurrence += numberOfOccurrenceInDepartment;
                roleMap.put(role, currentOccurrence);
            }
        }

        int minimum = Integer.MAX_VALUE;
        int maximum = Integer.MIN_VALUE;
        double average = 0;
        Role minimumRole = Role.ADMIN;
        Role maximumRole = Role.USER;

        for (Role role : roleMap.keySet()) {
            int current = roleMap.get(role);
            average += current;
            if (current < minimum) {
                minimumRole = role;
                minimum = current;
            }
            if (current > maximum) {
                maximumRole = role;
                maximum = current;
            }
        }

        int numberOfRoles = roles.size();

        if (numberOfRoles > 0) {
            average /= numberOfRoles;
        }

        statistics.setMinimum(minimumRole.toString());
        statistics.setMaximum(maximumRole.toString());
        statistics.setAverage(average);

        return statistics;
    }

    @Override
    public Statistics<Map<String, Integer>> getTicketStatistics() {
        Statistics<Map<String, Integer>> statistics = new Statistics<>();
        Map<String, Map<String, Integer>> sizes = new HashMap<>();
        List<Ticket> tickets = ticketService.getTicketsForStatistics();

        /* Fill the type map */
        TicketType[] ticketTypes = new TicketType[] { TicketType.BUG, TicketType.TASK, TicketType.STORY };
        Map<String, Integer> typeMap = new HashMap<>();

        for (TicketType ticketType : ticketTypes) {
            typeMap.put(String.valueOf(ticketType), 0);
        }

        for (Ticket ticket: tickets) {
            String type = String.valueOf(ticket.getType());
            int currentOccurrence = typeMap.get(type);
            typeMap.put(type, ++currentOccurrence);
        }

        int minimum = Integer.MAX_VALUE;
        int maximum = Integer.MIN_VALUE;
        double average = 0;
        TicketType maximumTicketType = TicketType.BUG;
        TicketType minimumTicketType = TicketType.STORY;

        for (TicketType ticketType : ticketTypes) {
            int current = typeMap.get(ticketType.toString());
            average += current;
            if (current < minimum) {
                minimumTicketType = ticketType;
                minimum = current;
            }
            if (current > maximum) {
                maximumTicketType = ticketType;
                maximum = current;
            }
        }

        int numberOfTypes = ticketTypes.length;
        average /= numberOfTypes;

        /* Set minimum, maxim and average */
        statistics.setMinimum(minimumTicketType.toString());
        statistics.setMaximum(maximumTicketType.toString());
        statistics.setAverage(average);

        sizes.put("Type", typeMap);

        /* Fill the topic map */
        Topic[] topics = new Topic[] { Topic.SOFTWARE, Topic.NETWORK, Topic.HARDWARE };
        Map<String, Integer> topicMap = new HashMap<>();

        for (Topic topic : topics) {
            topicMap.put(String.valueOf(topic), 0);
        }

        for (Ticket ticket: tickets) {
            String topic = String.valueOf(ticket.getTopic());
            int currentOccurrence = topicMap.get(topic);
            topicMap.put(topic, ++currentOccurrence);
        }

        sizes.put("Topic", topicMap);

        /* Fill the priority map */
        Priority[] priorities = new Priority[] { Priority.CRITICAL, Priority.IMPORTANT, Priority.NORMAL, Priority.LOW };
        Map<String, Integer> priorityMap = new HashMap<>();

        for (Priority priority : priorities) {
            priorityMap.put(String.valueOf(priority), 0);
        }

        for (Ticket ticket: tickets) {
            String priority = String.valueOf(ticket.getPriority());
            int currentOccurrence = priorityMap.get(priority);
            priorityMap.put(priority, ++currentOccurrence);
        }

        sizes.put("Priority", priorityMap);

        statistics.setSizes(sizes);
        return statistics;
    }
}
