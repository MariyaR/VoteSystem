package ru.javawebinar.votesystem.web.resto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.votesystem.RestoTestData;
import ru.javawebinar.votesystem.TestUtil;
import ru.javawebinar.votesystem.model.Resto;
import ru.javawebinar.votesystem.repository.RestoRepository;
import ru.javawebinar.votesystem.util.exception.NotFoundException;
import ru.javawebinar.votesystem.web.AbstractControllerTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votesystem.RestoTestData.RESTO_ID;
import static ru.javawebinar.votesystem.RestoTestData.*;
import static ru.javawebinar.votesystem.UserTestData.*;
import static ru.javawebinar.votesystem.web.DayMenuTestData.MENU_ID;

public class RestoControllerTest extends AbstractControllerTest {

    @Autowired
    private RestoRepository repository;

    RestoControllerTest() {
        super(RestoAdminController.REST_URL);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(doGet(RESTO_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTO_MATCHERS.contentJson(RESTO_1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithHystory() throws Exception {
        perform(doGet( "{id}/history", RESTO_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTO_MATCHERS.contentJsonWithHistory(RESTO_1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(doGet(1))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(doDelete(RESTO_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(repository.get(RESTO_ID).isEmpty());
        //assertThrows(NotFoundException.class, () -> repository.get(RESTO_ID));
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(doDelete(1))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(doGet(RESTO_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Resto updated = RestoTestData.getUpdated();
        perform(doPut(RESTO_ID).jsonBody(updated))
                .andExpect(status().isNoContent());
        assertEquals(repository.get(RESTO_ID), Optional.of(updated));
        //RESTO_MATCHERS.assertMatch(repository.get(RESTO_ID).orElse(null), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Resto newResto = RestoTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newResto))
                .andExpect(status().isCreated());

        Resto created = TestUtil.readFromJson(action, Resto.class);
        Integer newId = created.getId();
        newResto.setId(newId);
        RESTO_MATCHERS.assertMatch(created, newResto);
        assertEquals(repository.get(newId), Optional.of(newResto));
        //RESTO_MATCHERS.assertMatch(repository.get(newId).orElse(null), newResto);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(doGet())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTO_MATCHERS.contentJson(RESTO_1, RESTO_2, RESTO_3));
    }
}
