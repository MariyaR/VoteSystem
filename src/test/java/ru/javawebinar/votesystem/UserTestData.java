package ru.javawebinar.votesystem;
import java.time.LocalDate;
import java.util.List;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.votesystem.model.Role;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.model.AbstractBaseEntity;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.votesystem.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.votesystem.TestUtil.readListFromJsonMvcResult;


import java.util.*;


public class UserTestData {
    public static final int USER_ID = AbstractBaseEntity.START_SEQ;
    public static final int ADMIN_ID = AbstractBaseEntity.START_SEQ + 1;

    public static final String USER_MAIL = "user1@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final User USER = new User(USER_ID, "User1", USER_MAIL, "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User USER2 = new User(USER_ID + 2, "User2", "user2@yandex.ru", "password", Role.USER);
    public static final User USER3 = new User(USER_ID + 3, "User3", "user3@yandex.ru", "password", Role.USER);


    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", LocalDate.now(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,  "history");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("history").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class, "registered", "history", "password");
}
