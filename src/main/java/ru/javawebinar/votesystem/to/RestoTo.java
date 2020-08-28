package ru.javawebinar.votesystem.to;


public class RestoTo extends BaseNamedTo {

    private String address;

    public RestoTo() {
    }

    public RestoTo(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RestoTo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                '}';
    }
}