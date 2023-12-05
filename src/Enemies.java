import java.util.ArrayList;
import processing.core.*;

public class Enemies {
    private ArrayList<RangedEnemy> rangedEnemies;
    private ArrayList<Enemy> enemies;
    private PApplet p;
    private ArrayList <Enemy> inactive;
    private int helper;
    public Enemies(PApplet p) {
        helper = 0;
        rangedEnemies = new ArrayList<>();
        enemies = new ArrayList<>();
        this.p = p;
        inactive = new ArrayList<>();
    }

    public void addEnemy() {
        Enemy enemy;
        if (inactive.isEmpty()) {
            enemy = new Enemy(50, p);
            enemies.add(enemy);
        } else {
            inactive.get(0).reset();
            enemies.add(inactive.get(0));
            inactive.remove(0);
        }
    }
    public void addRangedEnemy() {
        RangedEnemy ranged;
        ranged = new RangedEnemy(10, p);
        rangedEnemies.add(ranged);
    }
    public void startRangedEnemies(Ellipse player) {
        for (RangedEnemy r : rangedEnemies) {
            r.move();
            r.startR();
            r.display();
        }
    }
    public boolean collideWithBullets(Ellipse player) {
        for (RangedEnemy r : rangedEnemies) {
            if (r.col(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean collide(Ellipse player) {
        for (int i = 0; i < enemies.size(); i++) {
            float distance = PApplet.dist(player.getX(), player.getY(), enemies.get(i).getX(), enemies.get(i).getY());
            float combinedRadius = 25 + enemies.get(i).getWidth() / 2;

            if (distance < combinedRadius) {
                enemies.get(i).setX(1000);
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }
    public boolean collideWithRanged(Ellipse player) {
        for (int i = 0; i < rangedEnemies.size(); i++) {
                float distance = PApplet.dist(player.getX(), player.getY(), rangedEnemies.get(i).getX(), rangedEnemies.get(i).getY());
            float combinedRadius = 25 + rangedEnemies.get(i).getWidth() / 2;

            if (distance < combinedRadius) {
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }
    public void shoot(Ellipse player) {
        if (helper >= rangedEnemies.size())  {
            helper = 0;
        }
        rangedEnemies.get(helper).shoot(player);
        helper++;
    }
    public void start() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).start();
            if (enemies.get(i).check()) {
                inactive.add(enemies.get(i));
                enemies.remove(i);
                i--;
            }
        }

    }
}