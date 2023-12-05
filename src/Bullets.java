
import java.util.ArrayList;

import processing.core.PApplet;

public class Bullets {
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> inactive;
    private PApplet p;

    public Bullets(PApplet p) {
        this.p = p;
        this.bullets = new ArrayList<>();
        this.inactive = new ArrayList<>();
    }

    public void addBullet(float x, float y) {
        Bullet bullet;
        if (inactive.isEmpty()) {
            bullet = new Bullet(p);
        } else {
            bullet = inactive.remove(0);
        }
        bullet.setX(x);
        bullet.setY(y);
        bullets.add(bullet);
    }

    public void setTarget(float targetX, float targetY) {
        for (Bullet bullet : bullets) {
            bullet.setTarget(targetX, targetY);
        }
    }

    public void fullStart() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).start();
        }
    }

    // public void obtainBullet(float setX, float setY) {
    //     if (!bullets.isEmpty()) {
    //         Bullet lastBullet = bullets.get(bullets.size() - 1);
    //         lastBullet.setX(setX);
    //         lastBullet.setY(setY);
    //         lastBullet.display();
    //     }
    // }

    public void start() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            if (bullet.collisionWithWalls()) {
                inactive.add(bullet);
                bullets.remove(i);
            } else {
                bullet.start();
            }
        }
    }
    public boolean collideWithBullets(Ellipse player) {
        for (int i = 0; i < bullets.size(); i++) {
            float distance = PApplet.dist(player.getX(), player.getY(), bullets.get(i).getX(), bullets.get(i).getY());
            float combinedRadius = 25 + bullets.get(i).getWidth() / 2;

            if (distance < combinedRadius) {
                bullets.get(i).setX(1000);
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }
    public void display() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).display();
        }
    }

    public Bullet getLastBullet() {
        if (!bullets.isEmpty()) {
            return bullets.get(bullets.size() - 1);
        }
        return null;
    }
}
