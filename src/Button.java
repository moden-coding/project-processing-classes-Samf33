import processing.core.PApplet;

public class Button {
    private Rectangle button; // The button itself
    private float x; // buttons x
    private float y; //buttons y
    PApplet p; // PApplet
    public Button(PApplet p, float x, float y) {
        this.p = p;
        this.x = x;
        this.y = y;
        button = new Rectangle(p, x, y, 150, 50, 200, 100, 100); // Initilization
    }
    public boolean clicked (float mouseX, float mouseY) { // Checks if it was clicked
        if (mouseX > x && mouseX < x + 150 &&
        mouseY > y && mouseY < y + 50) {
            return true;
        }
        return false;
    }
    public void display() { // Displays the button
        button.display();
    }
    public void text(String text) { // Puts text onto the button
        p.text(text, x + 75, y + 20);
    }
}
