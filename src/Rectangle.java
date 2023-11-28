import processing.core.*;

public class Rectangle {
    float x, y, width, height;
    int fillColor, strokeColor;
    boolean noStroke;
    PApplet parent;

    public Rectangle(PApplet p, float x, float y, float width, float height, int fillColor, int strokeColor) {
        parent = p;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.noStroke = false;
    }

    public Rectangle(PApplet p, float x, float y, float width, float height, int fillColor) {
        this(p, x, y, width, height, fillColor, 0);
    }

    public Rectangle(PApplet p, float x, float y, float width, float height, int fillRed, int fillGreen, int fillBlue,
            int strokeRed, int strokeGreen, int strokeBlue) {
        this(p, x, y, width, height, p.color(fillRed, fillGreen, fillBlue), p.color(strokeRed, strokeGreen, strokeBlue));
    }

    public Rectangle(PApplet p, float x, float y, float width, float height) {
        this(p, x, y, width, height, 0, 0, 0, 0, 0, 0);

    }

    public Rectangle(PApplet p, float x, float y, float width, float height, int r, int g, int b) {
        this(p, x, y, width, height, r, g, b, 0, 0, 0);
    }

    // Method to display the rectangle
    public void display() {
        if (noStroke) {
            parent.noStroke();
        } else {
            parent.stroke(strokeColor);
        }
        parent.fill(fillColor);
        parent.rect(x, y, width, height);
    }

    // Method to move the rectangle
    public void move(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    // Method to set the fill color
    public void setFill(int newColor) {
        this.fillColor = newColor;
    }

    public void setFill(float r, float g, float b){
        setFill(parent.color(r, g, b));
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float xPos){
        x = xPos;
    }

    public void setY(float yPos){
        y = yPos;
    }

    public float getWidth(){
        return width;
    }
    public void setWidth(float w){
        width = w;
    }

    public float getHeight(){
        return height;
    }
    public void setHeight(float h){
        height = h;
    }


    // Method to set the stroke color
    public void setStroke(int newColor) {
        this.strokeColor = newColor;
    }

     public void setStroke(int r, int g, int b){
        setStroke(parent.color(r,g,b));
    }

    // Method to remove the stroke
    public void noStroke() {
        this.noStroke = true;
    }

}
