import processing.core.PApplet;

public class Enemy {
    private int health; // Current health of the enemy
    private Ellipse enemy; // Ellipse object representing the enemy
    private int xVel; // Velocity of the enemy along the x-axis
    private int yVel; // Velocity of the enemy along the y-axis
    protected PApplet p; // Reference to the PApplet
    private int initialHealth; // Initial health of the enemy

    // Constructor for creating an enemy with random initial position and default color
    public Enemy(int health, PApplet p) {
        this.health = health;
        this.initialHealth = health;
        this.p = p;
        float x = p.random(0, 600);
        float y = p.random(0, 400);
        enemy = new Ellipse(p, x, y, 20, 20, 255, 0, 0); // Default color: red
        xVel = (int)p.random(1,10);
        yVel = 15 - xVel;
        System.out.println(xVel);
        System.out.println(yVel);
    }

    // Constructor for creating an enemy with random initial position and specified color
    public Enemy(int health, PApplet p, int r) {
        this.health = health;
        this.initialHealth = health;
        this.p = p;
        float x = p.random(0, 600);
        float y = p.random(0, 400);
        enemy = new Ellipse(p, x, y, 20, 20, r, 0, 0); // Custom color specified by parameter r
        xVel = 10;
        yVel = 10;
    }

    // Method to move and display the enemy, checking for collisions with walls
    public void start() {
        enemy.move(xVel, yVel);
        enemy.display();
        enemyCollisionWithWalls();
    }

    // Method to decrease the health of the enemy
    public void subHealth() {
        this.health--;
    }

    // Method to set the x-coordinate of the enemy
    public void setX(float newX) {
        enemy.setX(newX);
    }

    // Method to check if the enemy has reached its target or has zero health
    public boolean check() {
        if (health <= 0 || enemy.getX() > 800) {
            return true; // Return true if the enemy needs to be removed
        }
        return false; // Return false otherwise
    }

    // Method to handle collisions of the enemy with the screen boundaries (walls)
    public void enemyCollisionWithWalls() {
        if (enemy.getX() >= 800 - enemy.getWidth() || enemy.getX() <= 0) {
            xVel = -(xVel); // Reverse the x-direction velocity
            subHealth(); // Decrease health due to collision
        }
        if (enemy.getY() < 0 || enemy.getY() > 600) {
            yVel = -(yVel); // Reverse the y-direction velocity
            subHealth(); // Decrease health due to collision
        }
    }

    // Getter method to retrieve the x-coordinate of the enemy
    public float getX() {
        return enemy.getX();
    }

    // Getter method to retrieve the y-coordinate of the enemy
    public float getY() {
        return enemy.getY();
    }

    // Getter method to retrieve the width of the enemy
    public float getWidth() {
        return enemy.getWidth();
    }

    // Getter method to retrieve the height of the enemy
    public float getHeight() {
        return enemy.getHeight();
    }

    // Method to reset the enemy's health and position
    public void reset() {
        xVel = (int)p.random(1,10);
        yVel = 15 - xVel;
        health = initialHealth;
        enemy.setX(p.random(100,700)); // Move the enemy to start pos
        enemy.setY(p.random(100,500));
    }
}