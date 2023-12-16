import processing.core.PApplet;

public class Level {
    private Enemies enemies; // Enemiues in the level
    private int delay; // Interval of enmy spawn
    private int goal; // goal of the level
    private int numRanged; // number of ranged enemies in the level
    public Level(PApplet p, int d, int goal, int numRanged) {
        this.numRanged = numRanged;
        this.goal = goal;
        enemies = new Enemies(p);
        delay = d;
    }


public int getNumRanged() { // Returns number of ranged enemies
    return numRanged;
}
public int getDelay() { // returns interval
    return delay;
}
public int getGoal() { // returns goal
    return goal;
}
public void start(Ellipse player) { // starts the levels enemies
    enemies.start();
    enemies.startRangedEnemies(player);
}
public void add(int h) { // adds an enemy
    enemies.addEnemyWithHealth(h);
}
public void addRanged(int num) { // adds a ranged enemy
    for (int i = 0; i < num; i++) {
        enemies.addRangedEnemy();
        }
    }
        public Enemies getEnemies() { // returns the enemies used in the level
            return enemies;
        }
}

