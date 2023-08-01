package org.fed333.ticket.booking.app.config.comparator;

import org.fed333.ticket.booking.app.model.entity.Event;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event event1, Event event2) {
        return Comparator.comparing(Event::getId)
                .thenComparing(Event::getTitle)
                .thenComparingDouble(Event::getTicketPrice)
                .thenComparingLong(event -> event.getDate().getTime())
                .compare(event1, event2);
    }

    public static EventComparator comparator() {
        return new EventComparator();
    }
}
