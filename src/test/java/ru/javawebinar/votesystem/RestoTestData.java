package ru.javawebinar.votesystem;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.votesystem.model.AbstractBaseEntity;
import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.model.Resto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.votesystem.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.votesystem.TestUtil.readListFromJsonMvcResult;

public class RestoTestData {
    public static final int RESTO_ID = AbstractBaseEntity.START_SEQ+4;

    public static final Resto RESTO_1 = new Resto(RESTO_ID, "resto1", "address1");
    public static final Resto RESTO_2 = new Resto(RESTO_ID + 1, "resto2", "address2");
    public static final Resto RESTO_3 = new Resto(RESTO_ID + 2, "resto3", "address3");
    public static final Map<Long, String> MENU_1 = new HashMap();

    static {
        MENU_1.put(10000L, "burger");
        MENU_1.put(500L, "cake");
        final DayMenu dayMenu_1 = new DayMenu(100007, LocalDate.of(2000, 01, 01), MENU_1);
        final DayMenu dayMenu_2 = new DayMenu(100008, LocalDate.of(2000, 01, 02), MENU_1);
        final DayMenu dayMenu_3 = new DayMenu(100009, LocalDate.of(2000, 01, 03), MENU_1);
        RESTO_1.setHistory(List.of(dayMenu_1, dayMenu_2, dayMenu_3));
    }

    public static Resto getNew() {
        return new Resto(null, "newResto", "newAddress");
    }

    public static Resto getUpdated() {
        Resto updated = new Resto(RESTO_1);
        updated.setName("UpdatedName");
        return updated;
    }

    public static void assertMatch(Resto actual, Resto expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,  "history");
    }

    public static void assertMatch(Iterable<Resto> actual, Resto... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Resto> actual, Iterable<Resto> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("history").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Resto... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Resto.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Resto expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Resto.class), expected);
    }

    public static TestMatchers<Resto> RESTO_MATCHERS = TestMatchers.useFieldsComparator(Resto.class,  "history");
}
