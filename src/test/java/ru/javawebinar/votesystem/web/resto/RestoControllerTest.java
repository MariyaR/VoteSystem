package ru.javawebinar.votesystem.web.resto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.votesystem.RestoTestData;
import ru.javawebinar.votesystem.TestUtil;
import ru.javawebinar.votesystem.model.Resto;
import ru.javawebinar.votesystem.repository.RestoRepository;
import ru.javawebinar.votesystem.util.exception.NotFoundException;
import ru.javawebinar.votesystem.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votesystem.RestoTestData.RESTO_ID;
import static ru.javawebinar.votesystem.RestoTestData.*;
import static ru.javawebinar.votesystem.UserTestData.*;

public class RestoControllerTest extends AbstractControllerTest {

    @Autowired
    private RestoRepository repository;

    RestoControllerTest() {
        super(RestoAdminController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(RESTO_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTO_MATCHERS.contentJson(RESTO_1));
    }

    @Test
    void getWithHystory() throws Exception {
        perform(doGet( "{id}/history", RESTO_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTO_MATCHERS.contentJsonWithHistory(RESTO_1));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(RESTO_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.get(RESTO_ID, RESTO_ID));
    }


    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getForbidden() throws Exception {
        perform(doGet(RESTO_ID).basicAuth(USER))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        Resto updated = RestoTestData.getUpdated();
        perform(doPut(RESTO_ID).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        RESTO_MATCHERS.assertMatch(repository.get(RESTO_ID, RESTO_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Resto newResto = RestoTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newResto).basicAuth(ADMIN))
                .andExpect(status().isCreated());

        Resto created = TestUtil.readFromJson(action, Resto.class);
        Integer newId = created.getId();
        newResto.setId(newId);
        RESTO_MATCHERS.assertMatch(created, newResto);
        RESTO_MATCHERS.assertMatch(repository.get(newId, newId), newResto);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTO_MATCHERS.contentJson(RESTO_1, RESTO_2, RESTO_3));
    }
}
