import processing.core.PApplet;

public class RangedEnemy extends Enemy {
    Bullets bullets;  // Bullets fired by the ranged enemy

    // Constructor to initialize a RangedEnemy with a specified health and PApplet instance
    public RangedEnemy(int health, PApplet p) {
        // Call the constructor of the superclass (Enemy) with extended health and a red color
        super(health, p, 100);
        bullets = new Bullets(p);  // Initialize the bullets associated with this ranged enemy
    }

    // Method to handle the movement and shooting behavior of the ranged enemy
    public void move() {
        bullets.start();  // Start moving the bullets associated with this ranged enemy
    }

    // Method to check if the ranged enemy's bullets collide with the player
    public boolean col(Ellipse player) {
        return bullets.collideWithBullets(player);
    }

    // Method to start the ranged enemy's movement, shooting, and superclass behavior
    public void startR() {
        super.start();  // Call the start method of the superclass (Enemy) for common behavior
        bullets.start();  // Start moving the bullets associated with this ranged enemy
    }

    // Method to display the bullets fired by the ranged enemy
    public void display() {
        bullets.display();
    }

    // Method to make the ranged enemy shoot a bullet towards the player
    public void shoot(Ellipse player) {
        bullets.addBullet(getX(), getY());  // Add a new bullet at the position of the ranged enemy

        // Set the target for the recently added bullet to the player's position
        Bullet lastBullet = bullets.getLastBullet();
        if (lastBullet != null) {
            lastBullet.setTarget(player.getX(), player.getY());
        }
    }
}
