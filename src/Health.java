import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Health {
    private ArrayList<PImage> list;
    private PApplet parent;  // Reference to the PApplet instance
    private PImage img;

    public Health(PApplet parent, PImage img, int initialHearts) {
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

    public void place() {
        for (int i = 0; i < list.size(); i++) {
            PImage currentImg = list.get(i);
            parent.image(currentImg, i * currentImg.width, 5);
        }
    }
    public void remove() {
        if (list.size() > 1) {
        list.remove(list.size() - 1);
        } else {
            reset();
        }
    }
    public void reset() {
        for (int i = 0; i < 2; i++) {
            add();
        }
    }
}