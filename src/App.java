import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

import processing.core.*;

public class App extends PApplet {
    Ellipse player;
    int xPos = 500;
    int yPos = 300;
    int xVel = 0;
    int yVel = 0;
    Health health;
    PImage heart;
    Enemies enemies;
    int timer = 0;
    int randomNum = 70;
    RangedEnemy range;
    int hs = 0;
    int currentHS = 0;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        player = new Ellipse(this, xPos, yPos, 50, 50, 0);
        heart = loadImage("test.png");
        heart.resize(50, 50);
        enemies = new Enemies(this);
        range = new RangedEnemy(10, this);
        health = new Health(this, heart, 3);
        enemies.addRangedEnemy();
        enemies.addRangedEnemy();
        read();

    }

    public void settings() {
        size(800, 600);
    }

    public void draw() {
        background(200);
        displayhs();
        write();
        addhs();
        enemies.startRangedEnemies(player);
        addEnemies();
        enemies.start();
        health.place();
        player.display();
        player.move(xVel, yVel);
        collision();
        timer += 1;
    }
    public void addhs() {
        if (timer % 60 == 0 && timer != 0) {
            hs += 1;
        }
    }
    public void displayhs() {
        textSize(30);
        text(hs, 730, 40);
    }
    public void addEnemies() {
        if (timer % 250 == 0) {
            enemies.addEnemy();
        }
        if (timer % randomNum == 0) {
            randomNum = Math.round(random(40,1));
            enemies.shoot(player);
        }
    }

    public void keyPressed() {
        if (keyCode == RIGHT) {
            xVel = 10;
        }
        if (keyCode == LEFT) {
            xVel = -10;
        }
        if (keyCode == UP) {
            yVel = -10;
        }
        if (keyCode == DOWN) {
            yVel = 10;
        }
    }

    public void collision() {
        if (player.getX() >= 800 - player.getWidth() || player.getX() <= 0 || player.getY() < 0
                || player.getY() > 600) {
            health.remove();
            reset();
        }
        if (enemies.collide(player)) {
            health.remove();
            reset();
        }
        if(enemies.collideWithRanged(player)) {
            health.remove();
            reset();
        }
        if (enemies.collideWithBullets(player)) {
            health.remove();
            reset();
        }

    }

    public void keyReleased() {
        if (keyCode == RIGHT) {
            xVel = 0;
        }
        if (keyCode == LEFT) {
            xVel = 0;
        }
        if (keyCode == UP) {
            yVel = 0;
        }
        if (keyCode == DOWN) {
            yVel = 0;
        }
    }

    public void reset() {
        player.setX(100);
        player.setY(100);
    }
    public void read() {
        try (Scanner read = new Scanner(Paths.get("HS.txt"))) {
            if (read.hasNextLine()) {
                hs = Integer.valueOf(read.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }`
    public void write() {
        try(PrintWriter pw = new PrintWriter("HS.txt")) {
            pw.println(hs);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
