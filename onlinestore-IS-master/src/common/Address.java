package common;

public class Address {
    // فیلدهای مربوط به آدرس
    private int id;
    private String city;
    private String street;
    private String postalCode;
    private String details;

    // کانستراکتور برای ساخت آدرس جدید با اطلاعات کامل
    public Address(int id, String city, String street, String postalCode, String details) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.details = details;
    }

    // متدهای گرفتن اطلاعات (getterها)
    public int getId() { return id; }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public String getPostalCode() { return postalCode; }
    public String getDetails() { return details; }

    // متدهای تنظیم مقدار (setterها)
    public void setId(int id) { this.id = id; }
    public void setCity(String city) { this.city = city; }
    public void setStreet(String street) { this.street = street; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setDetails(String details) { this.details = details; }

    // تبدیل آدرس به فرمت قابل ذخیره در فایل (برای txt)
    @Override
    public String toString() {
        return id + ";" + city + ";" + street + ";" + postalCode + ";" + details;
    }
}
