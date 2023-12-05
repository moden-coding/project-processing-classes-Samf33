
import processing.core.PApplet;

public class RangedEnemy extends Enemy {
    Bullets bullets;
    // private int bulletSpawnCooldown = 60;

    public RangedEnemy(int health, PApplet p) {
        super(health, p, 100);
        bullets = new Bullets(p);
    }

    public void move() {
        bullets.start();
    }

    public boolean col(Ellipse player) {
        return bullets.collideWithBullets(player);

    }

    public void startR() {
        super.start();
        // giveBullet(getX(), getY());
        bullets.start();
    }

    public void display() {
        bullets.display();
    }

    public void shoot(Ellipse player) {
        bullets.addBullet(getX(), getY());

        // Set the target for the recently added bullet
        Bullet lastBullet = bullets.getLastBullet();
        if (lastBullet != null) {
            lastBullet.setTarget(player.getX(), player.getY());
        }
    }
}