package ru.javawebinar.votesystem.web.dayMenu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.javawebinar.votesystem.TestUtil;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.repository.DayMenuRepository;
import ru.javawebinar.votesystem.util.JacksonObjectMapper;
import ru.javawebinar.votesystem.util.Util;
import ru.javawebinar.votesystem.util.exception.NotFoundException;
import ru.javawebinar.votesystem.web.AbstractControllerTest;
import ru.javawebinar.votesystem.web.DayMenuTestData;
import ru.javawebinar.votesystem.web.ExceptionInfoHandler;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.votesystem.RestoTestData.RESTO_2;
import static ru.javawebinar.votesystem.RestoTestData.RESTO_ID;
import static ru.javawebinar.votesystem.UserTestData.*;
import static ru.javawebinar.votesystem.web.DayMenuTestData.*;

public class DayMenuAdminControllerTest extends AbstractControllerTest {

    @Autowired
    private DayMenuRepository repository;

    @Autowired
    private DayMenuAdminController controller;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    DayMenuAdminControllerTest() {
        super(DayMenuAdminController.REST_URL);
    }

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionInfoHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter( JacksonObjectMapper.getMapper()))
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
    }


    @Test
    void get() throws Exception {
        perform(doGet(MENU_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DayMenu_MATCHERS.contentJson(DAY_MENU_1));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(MENU_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.get(MENU_ID, MENU_ID));
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
    void update() throws Exception {
        DayMenu updated = DayMenuTestData.getUpdated();
        perform(doPut(RESTO_ID).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        DayMenu_MATCHERS.assertMatch(repository.get(MENU_ID, 1), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        DayMenu newMenu = DayMenuTestData.getNew();
        ResultActions action = perform(doPost().jsonBodyParam(Util.createNewTo(newMenu), "restoId", RESTO_ID+1).basicAuth(ADMIN))
                .andExpect(status().isCreated());

        DayMenu created = TestUtil.readFromJson(action, DayMenu.class);
        Integer newId = created.getId();
        newMenu.setId(newId);
        newMenu.setResto(RESTO_2);
        DayMenu_MATCHERS.assertMatch(repository.get(newId, newId), newMenu);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet("?restoId={restoId}", RESTO_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DayMenu_MATCHERS.contentJson(DAY_MENU_3, DAY_MENU_2, DAY_MENU_1));
    }
}
