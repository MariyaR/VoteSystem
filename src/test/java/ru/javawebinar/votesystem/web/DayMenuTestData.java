package ru.javawebinar.votesystem.web;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.votesystem.TestMatchers;
import ru.javawebinar.votesystem.model.AbstractBaseEntity;
import ru.javawebinar.votesystem.model.DayMenu;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.votesystem.RestoTestData.*;
import static ru.javawebinar.votesystem.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.votesystem.TestUtil.readListFromJsonMvcResult;

public class DayMenuTestData {

    public static final int MENU_ID = AbstractBaseEntity.START_SEQ +7;

    public static final Map<String, Long> MENU_1 = new HashMap();
    public static final Map<String, Long> MENU_2 = new HashMap();
    public static final Map<String, Long> MENU_3 = new HashMap();
    public static final Map<String, Long> MENU_NEW = new HashMap<>();
    public static final Map<String, Long> MENU_UPDATED = new HashMap<>();

    static {
        MENU_1.put( "burger", 10000L);
        MENU_1.put( "cake", 500L);
        MENU_2.put("pasta", 12000L);
        MENU_2.put("ice-cream", 500L);
        MENU_3.put("sushi", 13000L);
        MENU_3.put("cheese-cake", 500L);
        MENU_NEW.put("new soup", 10000L);
        MENU_NEW.put("new tiramisu", 500L);
        MENU_UPDATED.put("updated:pizza", 10000L);
        MENU_UPDATED.put("updated:cocktail", 500L);
    }


    public static final DayMenu DAY_MENU_1 = new DayMenu(MENU_ID, LocalDate.now().minusMonths(1), MENU_1);
    public static final DayMenu DAY_MENU_2 = new DayMenu(MENU_ID+1, LocalDate.now().minusMonths(2), MENU_1);
    public static final DayMenu DAY_MENU_3 = new DayMenu(MENU_ID+2, LocalDate.now().minusMonths(3), MENU_1);
    public static final DayMenu DAY_MENU_4 = new DayMenu(MENU_ID+3, LocalDate.now().minusMonths(1), MENU_2);
    public static final DayMenu DAY_MENU_5 = new DayMenu(MENU_ID+4, LocalDate.now().minusMonths(2), MENU_2);
    public static final DayMenu DAY_MENU_6 = new DayMenu(MENU_ID+5, LocalDate.now().minusMonths(3), MENU_2);
    public static final DayMenu DAY_MENU_7 = new DayMenu(MENU_ID+6, LocalDate.now().minusMonths(1), MENU_3);
    public static final DayMenu DAY_MENU_8 = new DayMenu(MENU_ID+7, LocalDate.now().minusMonths(2), MENU_3);
    public static final DayMenu DAY_MENU_9 = new DayMenu(MENU_ID+8, LocalDate.now().minusMonths(3), MENU_3);
    public static final DayMenu DAY_MENU_TODAY_1;// = new DayMenu(MENU_1);
    public static final DayMenu DAY_MENU_TODAY_2; // = new DayMenu(MENU_2);
    public static final DayMenu DAY_MENU_TODAY_3; // = new DayMenu(MENU_3);
    public static final DayMenu DAY_MENU_UPDATED = new DayMenu(MENU_UPDATED);

    static {
         DAY_MENU_TODAY_1 = new DayMenu(MENU_1);
         DAY_MENU_TODAY_2 = new DayMenu(MENU_2);
        DAY_MENU_TODAY_3 = new DayMenu(MENU_3);
        DAY_MENU_1.setResto(RESTO_1);
        DAY_MENU_2.setResto(RESTO_1);
        DAY_MENU_3.setResto(RESTO_1);
        DAY_MENU_4.setResto(RESTO_2);
        DAY_MENU_5.setResto(RESTO_2);
        DAY_MENU_6.setResto(RESTO_2);
        DAY_MENU_7.setResto(RESTO_3);
        DAY_MENU_8.setResto(RESTO_3);
        DAY_MENU_9.setResto(RESTO_3);
        DAY_MENU_TODAY_1.setResto(RESTO_1);
        DAY_MENU_TODAY_2.setResto(RESTO_2);
        DAY_MENU_TODAY_3.setResto(RESTO_3);
        DAY_MENU_UPDATED.setResto(RESTO_1);
        DAY_MENU_UPDATED.setId(MENU_ID);
    }

    public static DayMenu getNew() {
        return new DayMenu(MENU_NEW);
    }

    public static DayMenu getUpdated() {
        return DAY_MENU_UPDATED;
    }

    public static void assertMatch(DayMenu actual, DayMenu expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<DayMenu> actual, DayMenu... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<DayMenu> actual, Iterable<DayMenu> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(DayMenu... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, DayMenu.class), List.of(expected));
    }

    public static ResultMatcher contentJson(DayMenu expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, DayMenu.class), expected);
    }

    public static TestMatchers<DayMenu> DayMenu_MATCHERS = TestMatchers.useFieldsComparator(DayMenu.class);
}

