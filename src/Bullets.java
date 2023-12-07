import java.util.ArrayList;
import processing.core.PApplet;

public class Bullets {
    private ArrayList<Bullet> bullets; // List to store active bullets
    private ArrayList<Bullet> inactive; // List to store inactive bullets (for object pooling)
    private PApplet p; // Reference to the PApplet

    // Constructor initializes the lists and the reference to PApplet
    public Bullets(PApplet p) {
        this.p = p;
        this.bullets = new ArrayList<>();
        this.inactive = new ArrayList<>();
    }

    // Method to add a bullet to the active bullets list
    public void addBullet(float x, float y) {
        Bullet bullet;
        // Check if there are inactive bullets available for reuse
        if (inactive.isEmpty()) {
            bullet = new Bullet(p); // If not make a new bullet
        } else {
            bullet = inactive.remove(0); // Reuse an inactive bullet
        }
        bullet.setX(x);
        bullet.setY(y);
        bullets.add(bullet);
    }

    // Method to set the target for all active bullets
    public void setTarget(float targetX, float targetY) {
        for (Bullet bullet : bullets) {
            bullet.setTarget(targetX, targetY);
        }
    }

    // Method to move and display all active bullets
    public void fullStart() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).start();
        }
    }

    // Method to move and check for collisions, removing inactive bullets
    public void start() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            if (bullet.collisionWithWalls()) {
                inactive.add(bullet); // Add bullet to inactive list
                bullets.remove(i); // Remove bullet from active list
            } else {
                bullet.start();
            }
        }
    }

    // Method to check for collisions between bullets and the player
    public boolean collideWithBullets(Ellipse player) {
        for (int i = 0; i < bullets.size(); i++) {
            float distance = PApplet.dist(player.getX(), player.getY(), bullets.get(i).getX(), bullets.get(i).getY());
            float combinedRadius = 25 + bullets.get(i).getWidth() / 2;

            // Check if the distance between the player and the bullet is less than the combined radius
            if (distance < combinedRadius) {
                bullets.get(i).setX(1000); // Move bullet off-screen which makes it dissapear and become inactive
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    // Method to display all active bullets
    public void display() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).display();
        }
    }

    // Method to get the last active bullet (used for shooting in ranged enemy class)
    public Bullet getLastBullet() {
        if (!bullets.isEmpty()) {
            return bullets.get(bullets.size() - 1);
        }
        return null;
    }
}