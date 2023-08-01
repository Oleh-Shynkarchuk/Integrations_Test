package org.fed333.ticket.booking.app.config.comparator;

import org.fed333.ticket.booking.app.model.entity.User;
import java.util.Comparator;
import java.util.Objects;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        return Comparator.comparingLong(User::getId)
                .thenComparing(User::getName)
                .thenComparing((user, otherUser) -> {
                    if (Objects.isNull(user.getAccount()) && Objects.isNull(otherUser.getAccount())) {
                        return 0;
                    } else if (Objects.isNull(user.getAccount())) {
                        return -1;
                    } else if (Objects.isNull(otherUser.getAccount())) {
                        return 1;
                    }
                    return user.getEmail().compareTo(otherUser.getEmail());
                })
                .compare(user1, user2);
    }

    public static UserComparator comparator() {
        return new UserComparator();
    }
}