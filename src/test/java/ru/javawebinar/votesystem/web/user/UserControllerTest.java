package ru.javawebinar.votesystem.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.votesystem.model.Role;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.repository.UserRepository;
import ru.javawebinar.votesystem.to.UserTo;
import ru.javawebinar.votesystem.util.UserUtil;
import ru.javawebinar.votesystem.web.AbstractControllerTest;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votesystem.TestUtil.readFromJson;
import static ru.javawebinar.votesystem.UserTestData.*;

class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    UserControllerTest() {
        super(UserController.REST_URL);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(doGet())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(doDelete())
                .andExpect(status().isNoContent());
        USER_MATCHERS.assertMatch(userRepository.getAllEntries(USER_ID), ADMIN, USER2, USER3);
    }

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword", null, Set.of(Role.USER));
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(doPost("/register").jsonBody(newTo))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(userRepository.get(newId, newId), newUser);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword", null, Set.of(Role.USER));
        perform(doPut().jsonBody(updatedTo))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userRepository.get(USER_ID, USER_ID), UserUtil.updateFromTo(new User(USER), updatedTo));
    }
}