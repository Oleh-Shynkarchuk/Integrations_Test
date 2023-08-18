package org.fed333.ticket.booking.app.config;

import org.fed333.ticket.booking.app.config.comparator.EventComparator;
import org.fed333.ticket.booking.app.config.comparator.TicketComparator;
import org.fed333.ticket.booking.app.config.comparator.UserComparator;
import org.fed333.ticket.booking.app.model.entity.Event;
import org.fed333.ticket.booking.app.model.entity.Ticket;
import org.fed333.ticket.booking.app.model.entity.User;
import org.fed333.ticket.booking.app.util.StorageDataUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/services.xml")
@ActiveProfiles("test")
public class InitializeStorageWithPreparedDataBeanPostProcessorITest {

    @Autowired
    private StorageDataUtil storageDataUtil;

    private EventData eventData;

    @Before
    public void setUp() throws Exception {
        eventData = new EventData();
    }

    @Test
    public void eventRepository_shouldBePreInitializedWithJSON() {
        List<Event> expectedList = eventData.getEvents();

        List<Event> actual = storageDataUtil.getEventRepository().findAll();

        assertThat(actual).usingComparatorForType(EventComparator.comparator(), Event.class).isEqualTo(expectedList);
    }

    @Test
    public void userRepository_shouldBePreInitializedWithJSON() {
        List<User> expectedList = eventData.getUsers();

        List<User> actual = storageDataUtil.getUserRepository().findAll();

        assertThat(actual).usingComparatorForType(UserComparator.comparator(), User.class).isEqualTo(expectedList);
    }

    @Test
    public void ticketRepository_shouldBePreInitializedWithJSON() {
        List<Ticket> expectedList = eventData.getTickets();

        List<Ticket> actual = storageDataUtil.getTicketRepository().findAll();

        assertThat(actual).usingComparatorForType(TicketComparator.comparator(), Ticket.class).isEqualTo(expectedList);
    }
}