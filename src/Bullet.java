import processing.core.PApplet;

public class Bullet {
    private Ellipse bullet; // Ellipse object representing the bullet
    private float x; // X-coordinate of the bullet
    private float y; // Y-coordinate of the bullet
    private float xVel; // Velocity in the X direction
    private float yVel; // Velocity in the Y direction

    // Constructor initializes the bullet's position and appearance
    public Bullet(PApplet p) {
        x = 100;
        y = 100;
        bullet = new Ellipse(p, x, y, 8, 8, 0, 0, 255);
    }

    // Move method updates the position of the bullet based on its velocity
    public void move() {
        x += xVel;
        y += yVel;
        bullet.setX(x);
        bullet.setY(y);
    }

    // Getter method for the X-coordinate of the bullet
    public float getX() {
        return bullet.getX();
    }

    // Getter method for the Y-coordinate of the bullet
    public float getY() {
        return bullet.getY();
    }

    // Start method moves the bullet and displays it
    public void start() {
        move();
        bullet.display();
    }

    // Display method shows the bullet on the screen
    public void display() {
        bullet.display();
    }

    // SetTarget method calculates the velocity needed to reach a target position
    public void setTarget(float targetX, float targetY) {
        // All of this is copied
        // Calculate direction vector from current position to target
        float directionX = targetX - x;
        float directionY = targetY - y;

        // Normalize direction vector
        float magnitude = PApplet.sqrt(directionX * directionX + directionY * directionY);
        float normalizedX = directionX / magnitude;
        float normalizedY = directionY / magnitude;

        // Set bullet velocity based on the normalized direction vector and speed
        float bulletSpeed = 5.0f; // Bullet speed
        xVel = normalizedX * bulletSpeed;
        yVel = normalizedY * bulletSpeed;
    }

    // CollisionWithWalls method checks if the bullet has hit the screen boundaries
    public boolean collisionWithWalls() {
        if (bullet.getX() >= 800 - bullet.getWidth() || bullet.getX() <= 0 || bullet.getY() < 0 || bullet.getY() > 600) {
            return true; // Collision occurred
        }
        return false; // No collision
    }

    // Getter method for the width of the bullet
    public float getWidth() {
        return bullet.getWidth();
    }

    // Reset method sets the bullet back to its initial position
    public void reset() {
        x = 100;
        y = 100;
    }

    // Setter method for the X-coordinate of the bullet
    public void setX(float x) {
        bullet.setX(x);
        this.x = x;
    }

    // Setter method for the Y-coordinate of the bullet
    public void setY(float y) {
        bullet.setY(y);
        this.y = y;
    }
}