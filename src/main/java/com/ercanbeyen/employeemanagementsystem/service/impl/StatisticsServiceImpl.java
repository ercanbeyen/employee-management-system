package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
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
    @Autowired
    private final PaymentService paymentService;

    @Override
    public Statistics<String, Integer> getDepartmentStatistics() {
        Statistics<String, Integer> statistics = new Statistics<>();
        List<Department> departments = departmentService.getDepartmentsForStatistics();

        /* Set minimum and maximum */
        Department maximumDepartment = null;
        Department minimumDepartment = null;
        int maximumDepartmentSize = Integer.MIN_VALUE;
        int minimumDepartmentSize = Integer.MAX_VALUE;
        double summationOfSizes = 0;


        for (Department department : departments) {
            int currentSize = department.getSize();

            if (currentSize < minimumDepartmentSize) {
                minimumDepartment = department;
                minimumDepartmentSize = currentSize;
            } else if (currentSize > maximumDepartmentSize) {
                maximumDepartment = department;
                maximumDepartmentSize = currentSize;
            }

            summationOfSizes += currentSize;
        }

        String maximumDepartmentName;
        String minimumDepartmentName;

        if (minimumDepartment == null && maximumDepartment == null) {
            minimumDepartmentName = Messages.NOT_HAVE_SUCH_STATISTICS;
            maximumDepartmentName = Messages.NOT_HAVE_SUCH_STATISTICS;
        } else if (minimumDepartment == null) {
            maximumDepartmentName = maximumDepartment.getName();
            minimumDepartmentName = maximumDepartmentName;
        } else if (maximumDepartment == null) {
            minimumDepartmentName = minimumDepartment.getName();
            maximumDepartmentName = minimumDepartmentName;
        } else {
            minimumDepartmentName = minimumDepartment.getName();
            maximumDepartmentName = maximumDepartment.getName();
        }

        statistics.setMinimum(minimumDepartmentName);
        statistics.setMaximum(maximumDepartmentName);

        /* Set average */
        double average = 0;
        int numberOfDepartments = departments.size();

        if (numberOfDepartments != 0) {
            average = summationOfSizes / numberOfDepartments;
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
    public Statistics<String, Integer> getJobTitleStatistics() {
        Statistics<String, Integer> statistics = new Statistics<>();
        List<JobTitle> jobTitles = jobTitleService.getJobTitlesForStatistics();

        /* Set minimum and maximum */
        JobTitle maximumJobTitle = null;
        JobTitle minimumJobTitle = null;
        int maximumJobTitleSize = Integer.MIN_VALUE;
        int minimumJobTitleSize = Integer.MAX_VALUE;
        double summationOfSizes = 0;

        for (JobTitle jobTitle : jobTitles) {
            int currentSize = jobTitle.getSize();

            if (currentSize < minimumJobTitleSize) {
                minimumJobTitle = jobTitle;
                minimumJobTitleSize = currentSize;
            } else if (currentSize > maximumJobTitleSize) {
                maximumJobTitle = jobTitle;
                maximumJobTitleSize = currentSize;
            }

            summationOfSizes += currentSize;
        }

        String maximumJobTitleName;
        String minimumJobTitleName;

        if (minimumJobTitle == null && maximumJobTitle == null) {
            minimumJobTitleName = Messages.NOT_HAVE_SUCH_STATISTICS;
            maximumJobTitleName = Messages.NOT_HAVE_SUCH_STATISTICS;
        } else if (minimumJobTitle == null) {
            maximumJobTitleName = maximumJobTitle.getName();
            minimumJobTitleName = maximumJobTitleName;
        } else if (maximumJobTitle == null) {
            minimumJobTitleName = minimumJobTitle.getName();
            maximumJobTitleName = minimumJobTitleName;
        } else {
            minimumJobTitleName = minimumJobTitle.getName();
            maximumJobTitleName = maximumJobTitle.getName();
        }

        statistics.setMinimum(minimumJobTitleName);
        statistics.setMaximum(maximumJobTitleName);

        /* Set average */
        double average = 0;
        int numberOfJobTitles = jobTitles.size();

        if (numberOfJobTitles != 0) {
            average = summationOfSizes / numberOfJobTitles;
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
    public Statistics<Currency,Integer> getSalaryStatistics() {
        Statistics<Currency, Integer> statistics = new Statistics<>();
        List<Salary> salaries = salaryService.getSalariesForStatistics();

        /* Set minimum and maximum */
        Salary maximumSalary = null;
        Salary minimumSalary = null;
        double maximumAmount = Double.MIN_VALUE;
        double minimumAmount = Double.MAX_VALUE;
        double summationOfAmounts = 0;

        for (Salary salary : salaries) {
            double currentAmount = salary.getAmount();

            if (currentAmount < minimumAmount) {
                minimumSalary = salary;
                minimumAmount = currentAmount;
            } else if (currentAmount > maximumAmount) {
                maximumSalary = salary;
                maximumAmount = currentAmount;
            }

            summationOfAmounts += currentAmount;
        }

        String maximumSalaryAmount;
        String minimumSalaryAmount;

        if (minimumSalary == null && maximumSalary == null) {
            minimumSalaryAmount = Messages.NOT_HAVE_SUCH_STATISTICS;
            maximumSalaryAmount = Messages.NOT_HAVE_SUCH_STATISTICS;
        } else if (minimumSalary == null) {
            maximumSalaryAmount = maximumSalary.getAmount().toString();
            minimumSalaryAmount = maximumSalaryAmount;
        } else if (maximumSalary == null) {
            minimumSalaryAmount = minimumSalary.getAmount().toString();
            maximumSalaryAmount = minimumSalaryAmount;
        } else {
            minimumSalaryAmount = minimumSalary.getAmount().toString();
            maximumSalaryAmount = maximumSalary.getAmount().toString();
        }

        statistics.setMinimum(minimumSalaryAmount);
        statistics.setMaximum(maximumSalaryAmount);

        /* Set average */
        double average = 0;
        int numberOfSalaries = salaries.size();

        if (numberOfSalaries != 0) {
            average = summationOfAmounts / numberOfSalaries;
        }

        statistics.setAverage(average);

        /* Set sizes */
        HashMap<Currency, Integer> map = new HashMap<>();
        Currency[] currencies = Currency.values();

        for (Currency currency : currencies) {
            map.put(currency, 0);
        }

        for (Salary salary : salaries) {
            Currency key = salary.getCurrency();
            Integer value = map.get(key);
            map.put(key, ++value);
        }

        statistics.setSizes(map);

        return statistics;
    }

    @Override
    public Statistics<String, Map<Role, Integer>> getRoleStatistics() {

        Statistics<String, Map<Role, Integer>> statistics = new Statistics<>();

        /* Set sizes */
        List<DepartmentDto> departments = departmentService.
                getDepartments()
                .stream()
                .toList();

        Role[] roles = Role.values();
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

        int numberOfRoles = roles.length;

        if (numberOfRoles > 0) {
            average /= numberOfRoles;
        }

        statistics.setMinimum(minimumRole.toString());
        statistics.setMaximum(maximumRole.toString());
        statistics.setAverage(average);

        return statistics;
    }

    @Override
    public Statistics<String, Map<String, Integer>> getTicketStatistics() {
        Statistics<String, Map<String, Integer>> statistics = new Statistics<>();
        Map<String, Map<String, Integer>> sizes = new HashMap<>();
        List<Ticket> tickets = ticketService.getTicketsForStatistics();

        /* Fill the type map */
        TicketType[] ticketTypes = TicketType.values();
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
        Topic[] topics = Topic.values();
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
        Priority[] priorities = Priority.values();
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

    @Override
    public Statistics<PaymentType, Integer> getPaymentStatistics() {
        Statistics<PaymentType, Integer> statistics = new Statistics<>();
        List<Payment> payments = paymentService.getPaymentsForStatistics();

        /* Set minimum and maximum */
        Payment maximumPayment = null;
        Payment minimumPayment = null;
        double maximumAmount = Double.MIN_VALUE;
        double minimumAmount = Double.MAX_VALUE;
        double summationOfAmounts = 0;

        for (Payment payment : payments) {
            double currentAmount = payment.getAmount();

            if (currentAmount < minimumAmount) {
                minimumPayment = payment;
                minimumAmount = currentAmount;
            } else if (currentAmount > maximumAmount) {
                maximumPayment = payment;
                maximumAmount = currentAmount;
            }

            summationOfAmounts += currentAmount;
        }

        String maximumPaymentAmount;
        String minimumPaymentAmount;

        if (minimumPayment == null && maximumPayment == null) {
            minimumPaymentAmount = Messages.NOT_HAVE_SUCH_STATISTICS;
            maximumPaymentAmount = Messages.NOT_HAVE_SUCH_STATISTICS;
        } else if (minimumPayment == null) {
            maximumPaymentAmount = maximumPayment.getAmount().toString();
            minimumPaymentAmount = maximumPaymentAmount;
        } else if (maximumPayment == null) {
            minimumPaymentAmount = minimumPayment.getAmount().toString();
            maximumPaymentAmount = minimumPaymentAmount;
        } else {
            minimumPaymentAmount = minimumPayment.getAmount().toString();
            maximumPaymentAmount = maximumPayment.getAmount().toString();
        }

        statistics.setMinimum(minimumPaymentAmount);
        statistics.setMaximum(maximumPaymentAmount);

        /* Set average */
        double average = 0;
        int numberOfPayments = payments.size();

        if (numberOfPayments != 0) {
            average = summationOfAmounts / numberOfPayments;
        }

        statistics.setAverage(average);

        /* Set sizes */
        HashMap<PaymentType, Integer> map = new HashMap<>();
        PaymentType[] paymentTypes = PaymentType.values();

        for (PaymentType paymentType : paymentTypes) {
            map.put(paymentType, 0);
        }

        for (Payment payment : payments) {
            PaymentType key = payment.getType();
            Integer occurrence = map.get(key);
            map.put(key, ++occurrence);
        }

        statistics.setSizes(map);

        return statistics;
    }
}
