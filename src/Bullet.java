
import processing.core.PApplet;

public class Bullet {
    private Ellipse bullet;
    private float x;
    private float y;
    private float xVel;
    private float yVel;
    private float targetX;
    private float targetY;

    public Bullet(PApplet p) {
        targetX = 200;
        targetY = 300;
        x = 100;
        y = 100;
        bullet = new Ellipse(p, x, y, 8, 8, 0, 0, 255);
    }

    public void move() {
        x += xVel;
        y += yVel;
        bullet.setX(x);
        bullet.setY(y);
    }
    public float getX() {
        return bullet.getX();
    }
    public float getY() {
        return bullet.getY();
    }

    public void start() {
        move();
        bullet.display();
    }
    public void display() {
        bullet.display();
    }

    public void setTarget(float targetX, float targetY) {
        this.targetX = targetX;
        this.targetY = targetY;

        // Calculate direction vector from current position to target
        float directionX = targetX - x;
        float directionY = targetY - y;

        // Normalize direction vector
        float magnitude = PApplet.sqrt(directionX * directionX + directionY * directionY);
        float normalizedX = directionX / magnitude;
        float normalizedY = directionY / magnitude;

        // Set bullet velocity based on the normalized direction vector and speed
        float bulletSpeed = 5.0f; // Adjust this value based on your desired bullet speed
        xVel = normalizedX * bulletSpeed;
        yVel = normalizedY * bulletSpeed;
    }

    public boolean collisionWithWalls() {
        if (bullet.getX() >= 800 - bullet.getWidth() || bullet.getX() <= 0 || bullet.getY() < 0 || bullet.getY() > 600) {
            System.out.println("Bullet position: (" + bullet.getX() + ", " + bullet.getY() + ")");
            return true;
        }
        return false;
    }
    

    public float getWidth() {
        return bullet.getWidth();
    }

    public void reset() {
        x = 100;
        y = 100;
    }
    public void setX(float x) {
        bullet.setX(x);
        this.x = x;
    }
    public void setY(float y) {
        bullet.setY(y);
        this.y = y;
    }
}
