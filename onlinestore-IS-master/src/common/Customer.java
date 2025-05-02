package common;

public class Customer {
    // فیلدهای اطلاعات مشتری
    private int id;
    private String name;
    private String phone;
    private String email;
    private String password;

    // سازنده: موقع ساخت مشتری، مقداردهی می‌کنه
    public Customer(int id, String name, String phone, String email, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    // Getterها برای گرفتن اطلاعات
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setterها برای تغییر اطلاعات
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // تبدیل مشتری به رشته برای ذخیره در فایل (مثلاً تو customer.txt)
    @Override
    public String toString() {
        return id + ";" + name + ";" + phone + ";" + email + ";" + password;
    }
}
