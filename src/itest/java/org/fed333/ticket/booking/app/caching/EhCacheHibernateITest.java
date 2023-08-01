package org.fed333.ticket.booking.app.caching;

import org.fed333.ticket.booking.app.model.entity.Event;
import org.fed333.ticket.booking.app.model.entity.Ticket;
import org.fed333.ticket.booking.app.model.entity.User;
import org.fed333.ticket.booking.app.util.StorageDataUtil;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/services.xml")
@ActiveProfiles("test")
public class EhCacheHibernateITest {

    @Autowired
    private StorageDataUtil storageDataUtil;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void eventCaching_getEventById_shouldHitAfterFirstMiss() {
        Event event = storageDataUtil.getEventRepository().findById(1L).orElse(null);
        assertThat(event).isNotNull();

        Statistics statistics = sessionFactory.getStatistics();

    }

    @Test
    public void userCaching_getUserById_shouldHitAfterFirstMiss() {
        User user = storageDataUtil.getUserRepository().findById(1L).orElse(null);
        assertThat(user).isNotNull();

        Statistics statistics = sessionFactory.getStatistics();
    }

    @Test
    public void ticketCaching_getById_shouldHitAfterFirstMiss() {
        Ticket user = storageDataUtil.getTicketRepository().findById(1L).orElse(null);
        assertThat(user).isNotNull();

        Statistics statistics = sessionFactory.getStatistics();
    }
}
