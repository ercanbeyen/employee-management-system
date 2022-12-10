package com.ercanbeyen.employeemanagementsystem.util;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketUtils {
    public static List<Ticket> sortTickets(List<Ticket> tickets) {
        List<Ticket> sortedTickets = new ArrayList<>(tickets.size());

        Priority[] priorities = Priority.values();
        List<List<Ticket>> sortedTicketsByPriority = new ArrayList<>();

        for (Priority currentPriority : priorities) {
            List<Ticket> sortedCurrentTickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getPriority() == currentPriority)
                    .toList();

            sortedTicketsByPriority.add(sortedCurrentTickets);
        }

        for (List<Ticket> currentTickets : sortedTicketsByPriority) {
            sortedTickets.addAll(currentTickets);
        }

        return sortedTickets;
    }
}
