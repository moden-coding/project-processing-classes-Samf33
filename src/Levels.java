import java.util.ArrayList;

import processing.core.PApplet;

public class Levels {

    private ArrayList<Level> levels; // List of levels
    private int currentLevel; // Which level you are on
    private Ellipse player; // The player for ease of access to methods
    PApplet p; // PApplet

    public Levels(PApplet p, Ellipse player) {
        this.p = p;
        this.player = player;
        Level l1 = new Level(p, 4, 15,0); // Making level one
        currentLevel = 0; 
        levels = new ArrayList<>();
        levels.add(l1);
    }

    public void start() { //Starts the current level
        levels.get(currentLevel).start(player);
    }

    public void nextLevel() { // Goes to the next level
        currentLevel += 1;
    }

    public void add() { // Adds enemies to the current level
        levels.get(currentLevel).add(15);
    }
    public void addRanged() { // Adds ranged enemies to the level if applicable
        levels.get(currentLevel).addRanged(levels.get(currentLevel).getNumRanged());
    }

    public int getDelay() { // Returns interval in seconds between each enemy spawn
        return levels.get(currentLevel).getDelay() * 60;
    }

    public boolean collision() { // Level collision
        if (levels.get(currentLevel).getEnemies().collide(player) ||
                levels.get(currentLevel).getEnemies().collideWithBullets(player) ||
                levels.get(currentLevel).getEnemies().collideWithRanged(player)) {
            return true;
        }
        return false;
    }

    public void levelText(int score) { // High score and goal dislpaying
        p.textSize(30);
        p.fill(100, 28, 29);
        p.text("Score: " + score, 650, 40);
        p.text("Goal: " + levels.get(currentLevel).getGoal(), 650, 80);
    }
    public int getCurrentGoal() { // Returns goal of level
        return levels.get(currentLevel).getGoal();
    }
}
