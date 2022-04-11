package ru.javawebinar.votesystem.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.votesystem.TestUtil;
import ru.javawebinar.votesystem.UserTestData;
import ru.javawebinar.votesystem.model.User;
import ru.javawebinar.votesystem.repository.UserRepository;
import ru.javawebinar.votesystem.util.exception.NotFoundException;
import ru.javawebinar.votesystem.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javawebinar.votesystem.UserTestData.*;

class AdminControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository repository;

    AdminControllerTest() {
        super(AdminController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(ADMIN_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getByEmail() throws Exception {
        perform(doGet("by?email={email}", ADMIN.getEmail()).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN));
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(USER_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.get(USER_ID, USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        User updated = UserTestData.getUpdated();
        perform(doPut(USER_ID).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        USER_MATCHERS.assertMatch(repository.get(USER_ID, USER_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(doPost().jsonUserWithPassword(newUser).basicAuth(ADMIN))
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(repository.get(newId, newId), newUser);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN, USER, USER2, USER3));
    }
}