package com.ercanbeyen.employeemanagementsystem.repository;

import com.ercanbeyen.employeemanagementsystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
