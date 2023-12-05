

import processing.core.PApplet;

public class Enemy {
    private int health;
    private Ellipse enemy;
    private int xVel;
    private int yVel;
    protected PApplet p;
    private int initalHealth;
        public Enemy(int health, PApplet p) {
        this.health = health;
        this.initalHealth = health;
        this.p = p;
        float x = p.random(0, 600);
        float y = p.random(0, 400);
        enemy = new Ellipse(p, x, y, 20, 20, 255, 0, 0);
        xVel = 10;
        yVel = 10;
    }
    public Enemy(int health, PApplet p, int r) {
        this.health = health;
        this.initalHealth = health;
        this.p = p;
        float x = p.random(0, 600);
        float y = p.random(0, 400);
        enemy = new Ellipse(p, x, y, 20, 20, r, 0, 0);
        xVel = 10;
        yVel = 10;
    }
    public void start() {
        enemy.move(xVel, yVel);
        enemy.display();
        enemyCollisionWithWalls();
    }
    public void subHealth() {
        this.health--;
    }
    public void setX(float newX) {
        enemy.setX(newX);
    } 
    public boolean check() {
        if (health <= 0 || enemy.getX() > 800) {
            return true;
        }
        return false;
    }
    public void enemyCollisionWithWalls() {
        if (enemy.getX() >= 800 - enemy.getWidth() || enemy.getX() <= 0) {
            xVel = -(xVel);
            subHealth();
        }
        if (enemy.getY() < 0 || enemy.getY() > 600) {
            yVel = -(yVel);
            subHealth();
        }
    }
    public float getX() {
        return enemy.getX();
    }
    public float getY() {
        return enemy.getY();
    }
    public float getWidth() {
        return enemy.getWidth();
    }
    public float getHeight() {
        return enemy.getHeight();
    }
    public void reset() {
        health = initalHealth;
        enemy.setX(700);
        
    }
}
