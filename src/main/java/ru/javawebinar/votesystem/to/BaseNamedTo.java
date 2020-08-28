package ru.javawebinar.votesystem.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BaseNamedTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    public BaseNamedTo() {
    }

    public BaseNamedTo(@NotBlank @Size(min = 2, max = 100) String name) {
        this.name = name;
    }

    public BaseNamedTo(Integer id, @NotBlank @Size(min = 2, max = 100) String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
