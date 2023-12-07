import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Health {
    private ArrayList<PImage> list;  // List to store heart images representing health
    private PApplet parent;  // Reference to the PApplet instance
    private PImage img;  // Image of a heart representing health
    private boolean isEmpty;  // Flag to indicate if health is empty

    // Constructor to initialize the Health object with a specified PApplet, heart image, and initial hearts
    public Health(PApplet parent, PImage img, int initialHearts) {
        isEmpty = false;
        this.parent = parent;
        this.img = img;
        this.list = new ArrayList<>();

        // Add initial hearts
        for (int i = 0; i < initialHearts; i++) {
            add();
        }
    }

    // Method to add a heart to the health
    public void add() {
        // Clone the PImage before adding it to the list
        list.add(img.copy());
    }

    // Method to check if health is empty
    public boolean isEmpty() {
        return isEmpty;
    }

    // Method to display the health hearts on the screen and space them
    public void place() {
        for (int i = 0; i < list.size(); i++) {
            PImage currentImg = list.get(i);
            parent.image(currentImg, i * currentImg.width, 5);
        }
    }

    // Method to remove a heart from the health
    public void remove() {
        if (list.size() > 0) {
            list.remove(list.size() - 1);
        } 
        if (list.size() == 0) {
            isEmpty = true;
        }
    }

    // Method to check if the health size is equal to a specified value
    public boolean sizeIs(int comp) {
        if (list.size() == comp) {
            return true;
        }
        return false;
    }

    // Method to get the current health value (number of hearts)
    public int getHealth() {
        return list.size();
    }

    // Method to reset the health by adding three hearts
    public void reset() {
        isEmpty = false;
        for (int i = 0; i < 3; i++) {
            add();
        }
    }
}