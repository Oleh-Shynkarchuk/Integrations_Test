package org.fed333.ticket.booking.app.config;


import lombok.Getter;
import lombok.Setter;
import org.fed333.ticket.booking.app.model.entity.Event;
import org.fed333.ticket.booking.app.model.entity.Ticket;
import org.fed333.ticket.booking.app.model.entity.User;
import org.fed333.ticket.booking.app.model.entity.UserAccount;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class EventData {

    private final List<Event> events;
    private final List<User> users;
    private final List<Ticket> tickets;

    public EventData() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        events = Arrays.asList(
                Event.builder().id(1L).title("EPAM Townhall").date(dateFormat.parse("2022-09-05 15:30:00"))
                        .ticketPrice(20).build(),
                Event.builder().id(2L).title("VNTU openday").date(dateFormat.parse("2022-09-05 11:20:00"))
                        .ticketPrice(20).build(),
                Event.builder().id(3L).title("Kalush charity music concert").date(dateFormat.parse("2022-09-06 18:45:00"))
                        .ticketPrice(3600).build(),
                Event.builder().id(4L).title("EPAM Townhall").date(dateFormat.parse("2022-10-05 15:30:00"))
                        .ticketPrice(20).build(),
                Event.builder().id(5L).title("EPAM Townhall").date(dateFormat.parse("2022-11-05 15:30:00"))
                        .ticketPrice(20).build(),
                Event.builder().id(6L).title("Event1 to be deleted").date(dateFormat.parse("2022-12-05 15:30:00"))
                        .ticketPrice(20).build(),
                Event.builder().id(7L).title("Jazz concert").date(dateFormat.parse("2022-09-25 19:30:00"))
                        .ticketPrice(20).build(),
                Event.builder().id(8L).title("Speaking club").date(dateFormat.parse("2022-09-25 15:30:00"))
                        .ticketPrice(20).build());
        users = Arrays.asList(
                User.builder().id(1L).name("Roman").email("kovalchuk.roman03@gmail.com")
                        .account(UserAccount.builder().id(1L).money(6500.0).build()).build(),
                User.builder().id(2L).name("Pips").email("mpostryk@gmail.com")
                        .account(UserAccount.builder().id(2L).money(4500.0).build()).build(),
                User.builder().id(3L).name("Ivan").email("builbik@gmail.com")
                        .account(UserAccount.builder().id(3L).money(7500.0).build()).build(),
                User.builder().id(4L).name("Kyrylo").email("kyrylo@gmail.com").build(),
                User.builder().id(5L).name("Ivan").email("vanno8782@gmail.com").build(),
                User.builder().id(6L).name("Serhiy").email("meizum@gmail.com").build(),
                User.builder().id(7L).name("Andrii").email("riko@gmail.com").build(),
                User.builder().id(8L).name("Iryna").email("ira@gmail.com").build(),
                User.builder().id(9L).name("Tetyana").email("taniusha@gmail.com").build(),
                User.builder().id(10L).name("Viktoria").email("viktoria@gmail.com").build(),
                User.builder().id(11L).name("Ivan").email("tocker342@gmail.com").build(),
                User.builder().id(12L).name("Mykhailo").email("mykhailo@gmail.com").build(),
                User.builder().id(13L).name("Hlib").email("glego@gmail.com")
                        .account(UserAccount.builder().id(4L).money(2600.0).build()).build(),
                User.builder().id(14L).name("Pavlo").email("pavlo_makushak@gmail.com")
                        .account(UserAccount.builder().id(5L).money(2500.0).build()).build(),
                User.builder().id(15L).name("Updated").email("updated@gmail.com").build(),
                User.builder().id(16L).name("Deleted").email("deleted@gmail.com").build());
        tickets = Arrays.asList(
                Ticket.builder().id(1L).category(Ticket.Category.PREMIUM).place(12)
                        .event(Event.builder().id(1L).build()).user(User.builder().id(1L).build()).cancelled(false).build(),
                Ticket.builder().id(2L).category(Ticket.Category.STANDARD).place(13)
                        .event(Event.builder().id(1L).build()).user(User.builder().id(3L).build()).cancelled(false).build(),
                Ticket.builder().id(3L).category(Ticket.Category.ADVANCED).place(2)
                        .event(Event.builder().id(1L).build()).user(User.builder().id(5L).build()).cancelled(true).build(),
                Ticket.builder().id(4L).category(Ticket.Category.STANDARD).place(10)
                        .event(Event.builder().id(1L).build()).user(User.builder().id(13L).build()).cancelled(true).build(),
                Ticket.builder().id(5L).category(Ticket.Category.STANDARD).place(1)
                        .event(Event.builder().id(2L).build()).user(User.builder().id(11L).build()).cancelled(false).build(),
                Ticket.builder().id(6L).category(Ticket.Category.STANDARD).place(2)
                        .event(Event.builder().id(2L).build()).user(User.builder().id(14L).build()).cancelled(false).build(),
                Ticket.builder().id(7L).category(Ticket.Category.STANDARD).place(3)
                        .event(Event.builder().id(2L).build()).user(User.builder().id(6L).build()).cancelled(true).build(),
                Ticket.builder().id(8L).category(Ticket.Category.STANDARD).place(132)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(4L).build()).cancelled(false).build(),
                Ticket.builder().id(9L).category(Ticket.Category.STANDARD).place(133)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(3L).build()).cancelled(false).build(),
                Ticket.builder().id(10L).category(Ticket.Category.STANDARD).place(134)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(1L).build()).cancelled(false).build(),
                Ticket.builder().id(11L).category(Ticket.Category.STANDARD).place(135)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(2L).build()).cancelled(false).build(),
                Ticket.builder().id(12L).category(Ticket.Category.STANDARD).place(136)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(7L).build()).cancelled(false).build(),
                Ticket.builder().id(13L).category(Ticket.Category.STANDARD).place(137)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(6L).build()).cancelled(false).build(),
                Ticket.builder().id(14L).category(Ticket.Category.PREMIUM).place(10)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(8L).build()).cancelled(false).build(),
                Ticket.builder().id(15L).category(Ticket.Category.PREMIUM).place(11)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(9L).build()).cancelled(false).build(),
                Ticket.builder().id(16L).category(Ticket.Category.PREMIUM).place(12)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(10L).build()).cancelled(false).build(),
                Ticket.builder().id(17L).category(Ticket.Category.STANDARD).place(138)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(13L).build()).cancelled(false).build(),
                Ticket.builder().id(18L).category(Ticket.Category.STANDARD).place(139)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(5L).build()).cancelled(false).build(),
                Ticket.builder().id(19L).category(Ticket.Category.STANDARD).place(140)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(12L).build()).cancelled(false).build(),
                Ticket.builder().id(20L).category(Ticket.Category.STANDARD).place(141)
                        .event(Event.builder().id(3L).build()).user(User.builder().id(14L).build()).cancelled(false).build());
    }
}