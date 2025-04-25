package common;

public class Customer {
    private int id;
    private String fullName;
    private String email;
    private String phone;

    public Customer() {
    }

    public Customer(int id, String fullName, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // برای ذخیره در فایل
    @Override
    public String toString() {
        return id + ";" + fullName + ";" + email + ";" + phone;
    }

    // برای خواندن از فایل
    public static Customer fromString(String line) {
        String[] parts = line.split(";");
        return new Customer(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3]
        );
    }
}
