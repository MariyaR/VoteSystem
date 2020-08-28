package ru.javawebinar.votesystem.util;

import ru.javawebinar.votesystem.model.DayMenu;
import ru.javawebinar.votesystem.model.Record;
import ru.javawebinar.votesystem.model.Resto;
import ru.javawebinar.votesystem.to.DayMenuTo;
import ru.javawebinar.votesystem.to.RecordTo;
import ru.javawebinar.votesystem.to.RestoTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    private Util() {
    }

    public static List<RecordTo> getRecordTos(Collection<Record> records) {
        return records.stream()
                .map(resto -> createTo(resto))
                .collect(Collectors.toList());
    }

    public static RecordTo createTo(Record record) {
        return new RecordTo(record.getId(), record.getDate(), record.getResto().getName(),
                record.getResto().getAddress(), record.getUser().getId());
    }

    public static List<RestoTo> getRestoTos(Collection<Resto> restos) {
        return restos.stream()
                .map(resto -> createTo(resto))
                .collect(Collectors.toList());
    }

    public static RestoTo createTo(Resto resto) {
        return new RestoTo(resto.getId(), resto.getName(), resto.getAddress());
    }

    public static List<DayMenuTo> getDayMenuTos(Collection<DayMenu> menus) {
        return menus.stream()
                .map(menu -> createTo(menu))
                .collect(Collectors.toList());
    }

    public static DayMenuTo createTo(DayMenu menu) {
        return new DayMenuTo(menu.getId(), menu.getDate(), menu.getResto(), menu.getMenu(), menu.getCounter());
    }

    public static DayMenuTo createNewTo(DayMenu menu) {
        return new DayMenuTo(menu.getId(), menu.getDate(), null, menu.getMenu(), menu.getCounter());
    }

    public static Resto createNewFromToResto (RestoTo restoTo) {
        return new Resto (null, restoTo.getName(), restoTo.getAddress());
    }

    public static Resto updateFromToResto(Resto resto, RestoTo restoTo) {
        resto.setName(restoTo.getName());
        resto.setAddress(restoTo.getAddress());
        return resto;
    }

    public static DayMenu updateFromToDayMenu(DayMenu dayMenu, DayMenuTo dayMenuTo) {
        dayMenu.setResto(dayMenuTo.getResto());
        dayMenu.setDate(dayMenuTo.getDate());
        dayMenu.setMenu(dayMenuTo.getMenu());
        dayMenu.setCounter(dayMenuTo.getCounter());
        return dayMenu;

    }

    public static DayMenu createNewFromToDayMenu(DayMenuTo dayMenuTo) {
        DayMenu dayMenu = new DayMenu();
        dayMenu.setMenu(dayMenuTo.getMenu());
        dayMenu.setDate(dayMenuTo.getDate());
        return dayMenu;
    }
}