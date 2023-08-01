package org.fed333.ticket.booking.app.facade;

import org.fed333.ticket.booking.app.config.EventData;
import org.fed333.ticket.booking.app.config.comparator.EventComparator;
import org.fed333.ticket.booking.app.config.comparator.TicketComparator;
import org.fed333.ticket.booking.app.config.comparator.UserComparator;
import org.fed333.ticket.booking.app.model.entity.Event;
import org.fed333.ticket.booking.app.model.entity.Ticket;
import org.fed333.ticket.booking.app.model.entity.User;
import org.fed333.ticket.booking.app.model.entity.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/services.xml")
@ActiveProfiles("test")
@Transactional
public class BookingFacadeITest {

    @Autowired
    private BookingFacade bookingFacade;

    private EventData eventData;
    private Map<Long,Event> eventMap;
    private Map<Long,User> userMap;
    private Map<Long,Ticket> ticketMap;
    @Before
    public void setUp() throws Exception {
        eventData = new EventData();
        eventMap = new HashMap<>();
        for(Event event : eventData.getEvents()){
            eventMap.put(event.getId(),event);
        }
        userMap = new HashMap<>();
        for(User user : eventData.getUsers()){
            userMap.put(user.getId(),user);
        }
        ticketMap = new HashMap<>();
        for(Ticket ticket : eventData.getTickets()){
            ticketMap.put(ticket.getId(),ticket);
        }
    }


    @Test
    public void getEventById_shouldReturnEvent() {
        long eventId = 1L;
        Event expectedEvent = eventMap.get(eventId);

        Event actualEvent = bookingFacade.getEventById(eventId);

        assertThat(actualEvent).usingComparator(EventComparator.comparator())
                .isEqualTo(expectedEvent);
    }

    @Test
    public void getEventsByTitle_shouldReturnAll() {
        String title = "EPAM Townhall";
        List<Event> expectedEventList = eventData.getEvents()
                .stream()
                .filter(event -> event.getTitle().equals(title))
                .collect(Collectors.toList());

        List<Event> actualEventListByTitle = bookingFacade.getEventsByTitle(title, 20, 1);

        assertThat(actualEventListByTitle).usingComparatorForType(EventComparator.comparator(), Event.class)
                .isEqualTo(expectedEventList);

    }

    @Test
    public void getEventsByTitle_shouldReturnPage1() {
        String title = "EPAM Townhall";
        List<Event> expectedEvent = eventData.getEvents()
                .stream()
                .filter(event -> event.getTitle().equals(title))
                .limit(1)
                .collect(Collectors.toList());

        List<Event> actualEvent = bookingFacade.getEventsByTitle(title, 1, 1);

        assertThat(actualEvent).usingComparatorForType(EventComparator.comparator(), Event.class)
                .isEqualTo(expectedEvent);
    }

    @Test
    public void getEventsByTitle_shouldReturnPage2() {
        String title = "EPAM Townhall";
        List<Event> expectedEvent = eventData.getEvents()
                .stream()
                .filter(event -> event.getTitle().equals(title))
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());

        List<Event> actualEvent = bookingFacade.getEventsByTitle(title, 1, 2);

        assertThat(actualEvent).usingComparatorForType(EventComparator.comparator(), Event.class)
                .isEqualTo(expectedEvent);
    }

    @Test
    public void getEventsForDay_shouldReturnAll() {
        Date date = Date.from(Instant.parse("2022-09-05T12:30:00.00Z"));
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Event> expectedEventList = eventData.getEvents()
                .stream()
                .filter(event -> event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getDayOfYear() == localDate.getDayOfYear())
                .collect(Collectors.toList());

        List<Event> actualEventList = bookingFacade.getEventsForDay(date, 20, 1);

        assertThat(actualEventList).usingComparatorForType(EventComparator.comparator(), Event.class)
                .isEqualTo(expectedEventList);
    }

    @Test
    public void getEventsForDay_shouldReturnPage1() {
        Date date = Date.from(Instant.parse("2022-09-05T12:30:00.00Z"));
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Event> expectedEvent = eventData.getEvents()
                .stream()
                .filter(event -> event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getDayOfYear() == localDate.getDayOfYear())
                .limit(1)
                .collect(Collectors.toList());

        List<Event> actualEvent = bookingFacade.getEventsForDay(date, 1, 1);

        assertThat(actualEvent).usingComparatorForType(EventComparator.comparator(), Event.class)
                .isEqualTo(expectedEvent);
    }

    @Test
    public void getEventsForDay_shouldReturnPage2() {
        Date date = Date.from(Instant.parse("2022-09-05T12:30:00.00Z"));
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Event> expectedEvent = eventData.getEvents()
                .stream()
                .filter(event -> event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getDayOfYear() == localDate.getDayOfYear())
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());

        List<Event> actualEvent = bookingFacade.getEventsForDay(date, 1, 2);

        assertThat(actualEvent).usingComparatorForType(EventComparator.comparator(), Event.class)
                .isEqualTo(expectedEvent);
    }

    @Test
    public void createEvent_shouldGenerateIdForEvent() {
        Event expectedEvent = bookingFacade.createEvent(Event.builder()
                .title("Test Event")
                .date(new Date(2001))
                .ticketPrice(100.0)
                .build());

        assertThat(expectedEvent.getId()).isPositive().isGreaterThan(eventData.getEvents().size());
    }

    @Test
    public void createEvent_shouldGetCreatedEvent() {
        // Given
        Event newEvent = Event.builder()
                .title("Test Event")
                .date(new Date())
                .ticketPrice(50.0)
                .build();

        // When
        Event createdEvent = bookingFacade.createEvent(newEvent);

        // Then
        assertThat(bookingFacade.getEventById(createdEvent.getId()))
                .usingComparator(EventComparator.comparator())
                .isEqualTo(createdEvent);
    }

    @Test
    public void updateEvent_shouldChangeData() {
        Event update = Event.builder()
                .id(1L)
                .title("Test Event")
                .date(new Date())
                .ticketPrice(50.0)
                .build();
        Event expectedEvent = eventMap.get(update.getId());

        Event actualEvent = bookingFacade.updateEvent(update);

        assertThat(actualEvent).isNotEqualTo(expectedEvent);
    }

    @Test
    public void deleteEvent_shouldRemoveEvent() {
        long id = 1L;

        bookingFacade.deleteEvent(id);

        assertThat(bookingFacade.getEventById(id)).isNull();
    }

    @Test
    public void getUserById_shouldReturn() {
        long userId = 1L;
        User expectedUser = userMap.get(userId);

        User actualUser = bookingFacade.getUserById(userId);

        assertThat(actualUser).usingComparator(UserComparator.comparator()).isEqualTo(expectedUser);
    }

    @Test
    public void getUserByEmail_shouldReturn() {
        String email = "mpostryk@gmail.com";
        User expectedUser = eventData.getUsers()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);

        User actualUser = bookingFacade.getUserByEmail(email);

        assertThat(actualUser).usingComparator(UserComparator.comparator()).isEqualTo(expectedUser);
    }

    @Test
    public void getUsersByName_shouldReturnAll() {
        String name = "Ivan";
        List<User> expectedUserList = eventData.getUsers()
                .stream()
                .filter(user -> user.getName().equals(name))
                .collect(Collectors.toList());

        List<User> actualUserList = bookingFacade.getUsersByName(name, 20, 1);

        assertThat(actualUserList).usingComparatorForType(UserComparator.comparator(), User.class).isEqualTo(expectedUserList);
    }

    @Test
    public void getUsersByName_shouldReturnPage1() {
        String name = "Ivan";
        List<User> expectedUser = eventData.getUsers()
                .stream()
                .filter(user -> user.getName().equals(name))
                .limit(1)
                .collect(Collectors.toList());

        List<User> actualUser = bookingFacade.getUsersByName(name, 1, 1);

        assertThat(actualUser).usingComparatorForType(UserComparator.comparator(),User.class).isEqualTo(expectedUser);
    }

    @Test
    public void getUsersByName_shouldReturnPage2() {
        String name = "Ivan";
        List<User> expectedUser = eventData.getUsers()
                .stream()
                .filter(user -> user.getName().equals(name))
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());

        List<User> actualUser = bookingFacade.getUsersByName(name, 1, 2);

        assertThat(actualUser).usingComparatorForType(UserComparator.comparator(),User.class).isEqualTo(expectedUser);
    }

    @Test
    public void createUser_shouldGenerateIdForUser() {
        User newUser = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();

        User createdUser = bookingFacade.createUser(newUser);

        assertThat(createdUser.getId()).isPositive();
    }

    @Test
    public void createUser_shouldGetCreatedUser() {
        User newUser = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();

        User createdUser = bookingFacade.createUser(newUser);

        User retrievedUser = bookingFacade.getUserById(createdUser.getId());
        assertThat(retrievedUser).isEqualTo(createdUser);
    }

    @Test
    public void updateUser_shouldChangeData() {
        User update = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .build();
        User previousUser = userMap.get(update.getId());

        User updatedUser = bookingFacade.updateUser(update);
        User currentUser = bookingFacade.getUserById(update.getId());

        assertThat(updatedUser).isEqualTo(currentUser).isNotEqualTo(previousUser);
    }

    @Test
    public void deleteUser_shouldRemoveUser() {
        long deletedId = 1L;

        bookingFacade.deleteUser(deletedId);

        assertThat(bookingFacade.getUserById(deletedId)).isNull();
    }

    @Test
    public void bookTicket_shouldCreateTicketWithId() {
        long userId = 1;
        long eventId = 1;
        int place = 5;
        Ticket.Category category = Ticket.Category.PREMIUM;

        Ticket actualTicket = bookingFacade.bookTicket(userId, eventId, place, category);

        assertThat(actualTicket.getUserId()).isEqualTo(userId);
        assertThat(actualTicket.getEventId()).isEqualTo(eventId);
        assertThat(actualTicket.getPlace()).isEqualTo(place);
        assertThat(actualTicket.getCategory()).isEqualTo(category);
    }

    @Test
    public void bookTicket_shouldWithdrawUserAccountMoney() {
        long userId = 1;
        long eventId = 2;
        int place = 2;
        Ticket.Category category = Ticket.Category.ADVANCED;
        UserAccount previousUserAccount = eventData.getUsers()
                .stream()
                .filter(user1 -> user1.getId().equals(userId))
                .findFirst()
                .map(User::getAccount).orElse(null);
        Double eventTicketPrice = eventData.getEvents()
                .stream()
                .filter(event1 -> event1.getId().equals(eventId))
                .findFirst()
                .map(Event::getTicketPrice).orElse(null);

        bookingFacade.bookTicket(userId, eventId, place, category);
        Double actualAccountMoney = bookingFacade.getUserById(userId).getAccount().getMoney();

        assertThat(previousUserAccount).isNotNull();
        assertThat(actualAccountMoney).isLessThan((previousUserAccount.getMoney()));
        assertThat(previousUserAccount.getMoney() - actualAccountMoney).isEqualTo(eventTicketPrice);
    }

    @Test
    public void getBookedTickets_shouldReturnByUser() {
        long userId = 1;
        List<Ticket> expected = eventData.getTickets()
                .stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .collect(Collectors.toList());

        List<Ticket> actualBookedTickets = bookingFacade.getBookedTickets(bookingFacade.getUserById(userId), 20, 1);

        assertThat(actualBookedTickets).usingComparatorForType(TicketComparator.comparator(), Ticket.class)
                .isEqualTo(expected);
    }

    @Test
    public void getBookedTickets_shouldReturnByUserPage1() {
        long userId = 1;
        List<Ticket> expected = eventData.getTickets()
                .stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .collect(Collectors.toList());

        List<Ticket> actual = bookingFacade.getBookedTickets(bookingFacade.getUserById(userId), 1, 1);

        assertThat(actual).usingComparatorForType(TicketComparator.comparator(), Ticket.class)
                .isEqualTo(expected);
    }

    @Test
    public void getBookedTickets_shouldReturnByUserPage2() {
        long userId = 1;
        List<Ticket> expected = eventData.getTickets()
                .stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());

        List<Ticket> actual = bookingFacade.getBookedTickets(bookingFacade.getUserById(userId), 1, 2);

        assertThat(actual).usingComparatorForType(TicketComparator.comparator(), Ticket.class)
                .isEqualTo(expected);
    }

    @Test
    public void getBookedTickets_shouldReturnByEvent() {
        long eventId = 2;
        List<Ticket> expected = eventData.getTickets()
                .stream()
                .filter(ticket -> ticket.getEventId().equals(eventId))
                .collect(Collectors.toList());

        List<Ticket> actual = bookingFacade.getBookedTickets(bookingFacade.getEventById(eventId), 20, 1);

        assertThat(actual).usingComparatorForType(TicketComparator.comparator(), Ticket.class)
                .isEqualTo(expected);
    }

    @Test
    public void getBookedTickets_shouldReturnByEventPage1() {
        long eventId = 2;
        List<Ticket> expected = eventData.getTickets()
                .stream()
                .filter(ticket -> ticket.getEventId().equals(eventId))
                .limit(1)
                .collect(Collectors.toList());

        List<Ticket> actual = bookingFacade.getBookedTickets(bookingFacade.getEventById(eventId), 1, 1);

        assertThat(actual).usingComparatorForType(TicketComparator.comparator(), Ticket.class)
                .isEqualTo(expected);

    }

    @Test
    public void getBookedTickets_shouldReturnByEventPage2() {
        long eventId = 2;
        List<Ticket> expected = eventData.getTickets()
                .stream()
                .filter(ticket -> ticket.getEventId().equals(eventId))
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());

        List<Ticket> actual = bookingFacade.getBookedTickets(bookingFacade.getEventById(eventId), 1, 2);

        assertThat(actual).usingComparatorForType(TicketComparator.comparator(), Ticket.class)
                .isEqualTo(expected);
    }

    @Test
    public void cancelTicket_shouldCancel() {
        long ticketId = 1;
        Ticket ticket = ticketMap.get(ticketId);

        boolean cancellationResult = bookingFacade.cancelTicket(ticketId);
        Ticket actualTicket = bookingFacade.getBookedTickets(ticket.getUser(), 20, 1).stream()
                .filter(ticket1 -> ticket1.getId().equals(ticketId))
                .findFirst()
                .orElse(Ticket.builder().build());

        assertThat(cancellationResult).isTrue();
        assertThat(actualTicket.isCancelled()).isTrue();
    }

    @Test
    public void refillAccount_shouldUpdateMoney() {
        long userAccountId = 1;
        double money = 2000;
        Optional<User> user = userMap.values().stream()
                .filter(user1 -> user1.getAccount().getId().equals(userAccountId))
                .findFirst();
        UserAccount expected = user.map(User::getAccount).orElse(UserAccount.builder().build());
        expected.setMoney(money);

        UserAccount actual = bookingFacade.refillAccount(user.map(User::getAccount).orElse(null), money);
        assertThat(actual).usingComparator(UserAccountComparator()).isEqualTo(expected);
    }

    private Comparator<UserAccount> UserAccountComparator() {
        return Comparator.comparing(UserAccount::getId)
                .thenComparing(UserAccount::getMoney);
    }
}