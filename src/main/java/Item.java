import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Item {

    public enum ItemType {
        STARTER,
        OPTIONAL
    }

    String name;
    ItemType itemType;
    ImageIcon image;
    String imagePath;
    String message;

    public Item(String name, String itemType, String imagePath, String message) {
        this.name = name;
        this.itemType = ItemType.valueOf(itemType.toUpperCase());
        this.image = new ImageIcon((new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)))).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        this.imagePath = imagePath;
        System.out.println(imagePath);
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getMessage() {
        return message;
    }
}
