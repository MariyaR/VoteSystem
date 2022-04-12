package ru.javawebinar.votesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.votesystem.util.HashMapConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;


@Entity
@Table(name = "restos_history")
public class DayMenu extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate date;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resto_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonBackReference
    private Resto resto;


    @Convert(converter = HashMapConverter.class)
    @Column(name = "resto_menu")
    private Map<String, Long> menu;

    @Column(name = "counter")
    private int counter = 0;

    public DayMenu() {

    }

    public DayMenu(Map< String, Long> menu) {
        this.date = LocalDate.now();
        this.menu = menu;
    }

    public DayMenu(LocalDate date, Map< String, Long> menu) {
        this.date = date;
        this.menu = menu;
    }

    public DayMenu(Integer id, LocalDate date, Map<String, Long> menu) {
        super(id);
        this.date = date;
        this.menu = menu;
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

    public int vote() {
        return counter += 1;
    }

    public int unvote() {
        return counter -= 1;
    }

    @Override
    public String toString() {
        return "DayMenu{" +
                "date=" + date +
                ", resto=" + resto +
                ", menu=" + menu +
                ", id=" + id +
                ", counter=" + counter +
                '}';
    }

}
