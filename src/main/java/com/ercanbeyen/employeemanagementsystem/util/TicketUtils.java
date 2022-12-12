package com.ercanbeyen.employeemanagementsystem.util;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketUtils {
    public static List<Ticket> sortTickets(List<Ticket> tickets) {
        List<Ticket> sortedTickets = new ArrayList<>(tickets.size());
        Priority[] priorities = Priority.values();
        Map<Priority, List<Ticket>> priorityTickets_map = new HashMap<>();

        for (Priority currentPriority : priorities) {
            List<Ticket> sortedCurrentTickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getPriority() == currentPriority)
                    .toList();
            priorityTickets_map.put(currentPriority, sortedCurrentTickets);
        }

        for (List<Ticket> currentTickets : priorityTickets_map.values()) {
            sortedTickets.addAll(currentTickets);
        }

        return sortedTickets;
    }
}
