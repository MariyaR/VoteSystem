package ru.javawebinar.votesystem.to;

import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.votesystem.model.Resto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

public class DayMenuTo extends BaseTo {

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate date;

    @NotNull
    private Resto resto;

    @NotNull
    private Map<String, Long> menu;

    private int counter = 0;

    public DayMenuTo() {
    }

    public DayMenuTo(@NotNull LocalDate date, @NotNull Resto resto, @NotNull Map<String, Long> menu, int counter) {
        this.date = date;
        this.resto = resto;
        this.menu = menu;
        this.counter = counter;
    }

    public DayMenuTo(Integer id, @NotNull LocalDate date, @NotNull Resto resto, @NotNull Map<String, Long> menu, int counter) {
        super(id);
        this.date = date;
        this.resto = resto;
        this.menu = menu;
        this.counter = counter;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Resto getResto() {
        return resto;
    }

    public void setResto(Resto resto) {
        this.resto = resto;
    }

    public Map<String, Long> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, Long> menu) {
        this.menu = menu;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean restoIsPresent() {
        return resto!= null;
    }

    @Override
    public String toString() {
        return "DayMenuTo{" +
                "date=" + date +
                ", resto=" + resto +
                ", menu=" + menu +
                ", counter=" + counter +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public int id() {
        return 0;
    }
}
