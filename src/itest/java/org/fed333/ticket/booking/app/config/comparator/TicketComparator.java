package org.fed333.ticket.booking.app.config.comparator;

import org.fed333.ticket.booking.app.model.entity.Ticket;

import java.util.Comparator;

public class TicketComparator implements Comparator<Ticket> {

    @Override
    public int compare(Ticket ticket1, Ticket ticket2) {
        return Comparator.comparingLong(Ticket::getId)
                .thenComparingLong(Ticket::getEventId)
                .thenComparingLong(Ticket::getUserId)
                .thenComparing(Ticket::getCategory)
                .thenComparingInt(Ticket::getPlace)
                .thenComparing(Ticket::isCancelled)
                .compare(ticket1, ticket2);

    }

    public static TicketComparator comparator() {
        return new TicketComparator();
    }
}