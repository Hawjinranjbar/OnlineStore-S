package common;

public class Address {
    private int id;
    private String city;
    private String street;
    private String postalCode;
    private String details;

    public Address(int id, String city, String street, String postalCode, String details) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDetails() {
        return details;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return id + ";" + city + ";" + street + ";" + postalCode + ";" + details;
    }
}
