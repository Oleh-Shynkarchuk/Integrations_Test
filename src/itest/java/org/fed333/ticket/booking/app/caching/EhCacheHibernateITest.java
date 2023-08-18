package org.fed333.ticket.booking.app.caching;

import org.fed333.ticket.booking.app.model.entity.Event;
import org.fed333.ticket.booking.app.model.entity.Ticket;
import org.fed333.ticket.booking.app.model.entity.User;
import org.fed333.ticket.booking.app.service.EventService;
import org.fed333.ticket.booking.app.service.TicketService;
import org.fed333.ticket.booking.app.service.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.stat.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/services.xml")
@ActiveProfiles("test")
public class EhCacheHibernateITest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Test
    public void eventCaching_getEventById_shouldHitAfterFirstMiss() {
        sessionFactory.getStatistics().clear();

        Event build = Event.builder().title("test").build();
        Event event = eventService.createEvent(build);

        eventService.getEventById(event.getId());

        int expectedHitCount = 9;
        for (long i = 0; i < expectedHitCount; i++) {
            eventService.getEventById(i+1);
        }

        EntityStatistics entityStatistics = sessionFactory.getStatistics().getEntityStatistics(Event.class.getCanonicalName());
        long actualHitCount = entityStatistics.getCacheHitCount();
        long actualMissCount = entityStatistics.getCacheMissCount();

        assertThat(actualHitCount).isEqualTo(expectedHitCount);
        assertThat(actualMissCount).isEqualTo(1);
    }

    @Test
    public void userCaching_getUserById_shouldHitAfterFirstMiss() {
        sessionFactory.getStatistics().clear();
        User testUser = User.builder().name("TEST").build();
        userService.createUser(testUser);
        userService.getUserById(testUser.getId());

        int expectedHitCount = 17;
        for (long i = 0; i < expectedHitCount; i++) {
            userService.getUserById(i+1);
        }
        EntityStatistics entityStatistics = sessionFactory.getStatistics().getEntityStatistics(User.class.getCanonicalName());
        long actualHitCount = entityStatistics.getCacheHitCount();
        long actualMissCount = entityStatistics.getCacheMissCount();

        assertThat(actualHitCount).isEqualTo(expectedHitCount);
        assertThat(actualMissCount).isEqualTo(1);
    }

    @Test
    public void ticketCaching_getById_shouldHitAfterFirstMiss() {
        sessionFactory.getStatistics().clear();
        Ticket ticket = ticketService.bookTicket(1, 1, 1, Ticket.Category.PREMIUM);
        ticketService.getById(ticket.getId());

        int expectedHitCount = 21;
        for (long i = 0; i < expectedHitCount; i++) {
            ticketService.getById(i+1);
        }
        EntityStatistics entityStatistics = sessionFactory.getStatistics().getEntityStatistics(Ticket.class.getCanonicalName());
        long actualHitCount = entityStatistics.getCacheHitCount();
        long actualMissCount = entityStatistics.getCacheMissCount();

        assertThat(actualHitCount).isEqualTo(expectedHitCount);
        assertThat(actualMissCount).isEqualTo(1);
    }
}
