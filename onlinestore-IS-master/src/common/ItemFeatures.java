package common;

public class ItemFeatures {
    private int productId;
    private String brand;
    private String description;
    private String skinType;
    private boolean isOrganic;

    public ItemFeatures(int productId, String brand, String description, String skinType, boolean isOrganic) {
        this.productId = productId;
        this.brand = brand;
        this.description = description;
        this.skinType = skinType;
        this.isOrganic = isOrganic;
    }

    public int getProductId() { return productId; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public String getSkinType() { return skinType; }
    public boolean isOrganic() { return isOrganic; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setSkinType(String skinType) { this.skinType = skinType; }
    public void setOrganic(boolean isOrganic) { this.isOrganic = isOrganic; }

    @Override
    public String toString() {
        return productId + ";" + brand + ";" + description + ";" + skinType + ";" + isOrganic;
    }

    public static ItemFeatures fromString(String row) {
        String[] parts = row.split(";");
        if (parts.length != 5) throw new IllegalArgumentException("Invalid itemfeatures row: " + row);
        int productId = Integer.parseInt(parts[0]);
        return new ItemFeatures(productId, parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
    }
}
