package ru.javawebinar.votesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Table(name = "restos")
public class Resto extends AbstractNamedEntity {

    @Column(name = "address", nullable = false)
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resto", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("date DESC")
    @JsonIgnore
    //@JsonManagedReference //todo resto is not extracted with hystory
    private List<DayMenu> history;

    public Resto() {

    }

    public Resto(Resto r) {
        this(r.getId(), r.getName(), r.getAddress());
    }

    public Resto(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public Resto(String name, String address) {
        this(null, name, address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DayMenu> getHistory() {
        return history;
    }

    public void setHistory(List<DayMenu> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "Resto{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
