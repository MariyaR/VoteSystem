package ru.javawebinar.votesystem.to;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class RecordTo extends BaseTo {

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate date;

    @NotBlank
    @Size(min = 3, max = 32, message = "length must be between 5 and 32 characters")
    private String resto_name;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    private String resto_address;

    @NotNull
    private int user_id;

    public RecordTo() {
    }

    public RecordTo(@NotNull LocalDate date, @NotBlank @Size(min = 3, max = 32, message = "length must be between 5 and 32 characters") String resto_name, @NotBlank @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters") String resto_address, @NotNull int user_id) {
        this.date = date;
        this.resto_name = resto_name;
        this.resto_address = resto_address;
        this.user_id = user_id;
    }

    public RecordTo(Integer id, @NotNull LocalDate date, @NotBlank @Size(min = 3, max = 32, message = "length must be between 5 and 32 characters") String resto_name, @NotBlank @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters") String resto_address, @NotNull int user_id) {
        super(id);
        this.date = date;
        this.resto_name = resto_name;
        this.resto_address = resto_address;
        this.user_id = user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getResto_name() {
        return resto_name;
    }

    public void setResto_name(String resto_name) {
        this.resto_name = resto_name;
    }

    public String getResto_address() {
        return resto_address;
    }

    public void setResto_address(String resto_address) {
        this.resto_address = resto_address;
    }

    @Override
    public String toString() {
        return "RecordTo{" +
                "date=" + date +
                ", resto_name='" + resto_name + '\'' +
                ", resto_address='" + resto_address + '\'' +
                ", user_id=" + user_id +
                ", id=" + id +
                '}';
    }
}
