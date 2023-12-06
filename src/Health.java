import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Health {
    private ArrayList<PImage> list;
    private PApplet parent;  // Reference to the PApplet instance
    private PImage img;
    private boolean isEmpty;

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

    public void add() {
        // Clone the PImage before adding it to the list
        // Note: Make sure img.copy() works as expected for your use case
        list.add(img.copy());
    }
    public boolean isEmpty() {
        return isEmpty;
    }
    public void place() {
        for (int i = 0; i < list.size(); i++) {
            PImage currentImg = list.get(i);
            parent.image(currentImg, i * currentImg.width, 5);
        }
    }
    public void remove() {
        if (list.size() > 0) {
        list.remove(list.size() - 1);
        } 
        if (list.size() == 0) {
            isEmpty = true;
        }
    }
    public int getHealth() {
        return list.size();
    }
    public void reset() {
        isEmpty = false;
        for (int i = 0; i < 3; i++) {
            add();
        }
    }
}