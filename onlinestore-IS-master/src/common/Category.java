package common;

public class Category {
    private int id;
    private String name;
    private String description;
    private int parentId;
    private String imageFileName;

    public Category(int id, String name, String description, int parentId, String imageFileName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
        this.imageFileName = imageFileName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getParentId() {
        return parentId;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + description + ";" + parentId + ";" + imageFileName;
    }
}
