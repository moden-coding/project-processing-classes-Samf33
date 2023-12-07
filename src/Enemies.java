import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class Enemies {
    private ArrayList<RangedEnemy> rangedEnemies; // List to store ranged enemies
    private ArrayList<Enemy> enemies; // List to store regular enemies
    private PApplet p; // Reference to the PApplet
    private ArrayList<Enemy> inactive; // List to store inactive enemies (for object pooling)
    private int helper; // Helper variable for managing ranged enemy shooting

    // Constructor initializes lists and variables
    public Enemies(PApplet p) {
        helper = 0;
        rangedEnemies = new ArrayList<>();
        enemies = new ArrayList<>();
        this.p = p;
        inactive = new ArrayList<>();
    }

    // Method to remove all enemies, adding them to the inactive list
    public void clear() {
        List<Enemy> enemiesToRemove = new ArrayList<>(enemies);
        for (Enemy enemy : enemiesToRemove) {
            enemies.remove(enemy);
            inactive.add(enemy);
        }
    }

    // Method to add a regular enemy to the active enemies list
    public void addEnemy() {
        Enemy enemy;
        // Check if there are inactive enemies available for reuse
        if (inactive.isEmpty()) {
            enemy = new Enemy(50, p); // If not add a new enemy
            enemies.add(enemy);
        } else {
            inactive.get(0).reset(); // If yes, reuse an inactive enemy
            enemies.add(inactive.get(0));
            inactive.remove(0);
        }
    }

    // Method to add a ranged enemy to the ranged enemies list
    public void addRangedEnemy() {
        RangedEnemy ranged;
        ranged = new RangedEnemy(10, p);
        rangedEnemies.add(ranged);
    }

    // Method to move and display all ranged enemies
    public void startRangedEnemies(Ellipse player) {
        for (RangedEnemy r : rangedEnemies) {
            r.move();
            r.startR();
            r.display();
        }
    }

    // Method to check for collisions between bullets and ranged enemies
    public boolean collideWithBullets(Ellipse player) {
        for (RangedEnemy r : rangedEnemies) {
            if (r.col(player)) {
                return true;
            }
        }
        return false;
    }

    // Method to check for collisions between player and regular enemies
    public boolean collide(Ellipse player) {
        for (int i = 0; i < enemies.size(); i++) {
            float distance = PApplet.dist(player.getX(), player.getY(), enemies.get(i).getX(), enemies.get(i).getY());
            float combinedRadius = 25 + enemies.get(i).getWidth() / 2;

            // Check if the distance between the player and the enemy is less than the combined radius
            if (distance < combinedRadius) {
                enemies.get(i).setX(1000); // Move enemy off-screen
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    // Method to check for collisions between player and ranged enemies
    public boolean collideWithRanged(Ellipse player) {
        for (int i = 0; i < rangedEnemies.size(); i++) {
            float distance = PApplet.dist(player.getX(), player.getY(), rangedEnemies.get(i).getX(),
                    rangedEnemies.get(i).getY());
            float combinedRadius = 25 + rangedEnemies.get(i).getWidth() / 2;

            // Check if the distance between the player and the ranged enemy is less than the combined radius
            if (distance < combinedRadius) {
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    // Method for ranged enemies to shoot bullets towards the player
    public void shoot(Ellipse player) {
        if (rangedEnemies.size() > 0) {
            if (helper >= rangedEnemies.size()) {
                helper = 0;
            }
            rangedEnemies.get(helper).shoot(player);
            helper++;
        }
    }

    // Method to move and check for inactive enemies, removing them from the active list
    public void start() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).start();
            // Check if the enemy has reached 0 health or is off screen
            if (enemies.get(i).check()) {
                inactive.add(enemies.get(i)); // Add enemy to inactive list
                enemies.remove(i); // Remove enemy from active list
                i--; // Adjust the loop index after removal
            }
        }
    }
}
