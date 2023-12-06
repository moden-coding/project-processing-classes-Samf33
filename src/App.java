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
    Rectangle gameOver;
    PImage heart;
    PImage background;
    Enemies enemies;
    int timer = 0;
    int randomNum = 110;
    RangedEnemy range;
    int hs = 0;
    int score = 0;
    boolean scoreHelper = false;
    int initialHS = 0;
    boolean invince;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        gameOver = new Rectangle(this, 200, 275, 400, 50, 200, 100, 100);
        background = loadImage("potential-image-1.jpeg");
        background.resize(800, 600);
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
        background(background);
        spawnProc();
        gameOver();
        displayScores();
        write();
        addhs();
        health.place();
        if (!(health.isEmpty())) {
        enemies.startRangedEnemies(player);
        addEnemies();
        enemies.start();
        player.display();
        player.move(xVel, yVel);
        collision();
        timer += 1;
        }
    }

    public void gameOver() {
        if (health.isEmpty()) {
            removeEnemies();
            player.setFill(0, 255, 0);
            invince = true;
            timer = 0;
            scoreHelper = false;
            int diff = initialHS - score;
            gameOver.display();
            textAlign(CENTER, CENTER);
            fill(98, 23, 21);
            textSize(40);
            text("Try again?", 400, 295);
            textSize(25);
            if (diff >= 0) {
                fill(69, 24, 24);
                text("You survived for " + score + " seconds. That is " + diff
                        + " seconds from your high score! play again and try to improve next time.", 50, 100, 750, 200);
                        
            }
            if (diff < 0) {
                fill(69, 24, 24);
                text("You survived for " + score + " seconds! That is " + Math.abs(diff)
                        + " seconds past your high score! Play again and keep raising that high score!", 50, 100, 750,
                        200);
            }
        }
    }

    public void mousePressed() {
        if (health.isEmpty())
            if (mouseX > gameOver.getX() && mouseX < gameOver.getX() + gameOver.getWidth() &&
                    mouseY > gameOver.getY() && mouseY < gameOver.getY() + gameOver.getHeight()) {
                health.reset();
                score = 0;
                
            }
    }

    public void addhs() {
        if (timer % 60 == 0 && timer != 0 && !(health.isEmpty())) {
            score += 1;
            if (score > hs) {
                if (scoreHelper == false) {
                    initialHS = hs;
                    scoreHelper = true;
                }
                hs = score;
            } else {
                initialHS = hs;
            }
        }
    }

    public void displayScores() {
        if (!(health.isEmpty())) {
            fill(100, 28, 29);
            textSize(30);
            text("Score: " + score, 600, 40);
            text("Highscore: " + hs, 600, 80);
        }
    }
    public void removeEnemies() {
        enemies.clear();
    }
    public void spawnProc() {
        if (timer % 100 == 0 && timer != 0 && timer < 150) {
            player.setFill(0);
            invince = false;
        }
    }
    public void addEnemies() {
        if (!(health.isEmpty())) {
        if (timer % 250 == 0 && !(health.isEmpty())) {
            enemies.addEnemy();
        }
        if (timer % randomNum == 0 && !(health.isEmpty()) && timer > 0) {
            randomNum = Math.round(random(40, 1));
            enemies.shoot(player);
        }
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
                || player.getY() > 600 && !(invince)) {
            health.remove();
            reset();
        }
        if (enemies.collide(player) && !(invince)) {
            health.remove();
            reset();
        }
        if (enemies.collideWithRanged(player) && !(invince)) {
            health.remove();
            reset();
        }
        if (enemies.collideWithBullets(player) && !(invince)) {
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
    }

    public void write() {
        try (PrintWriter pw = new PrintWriter("HS.txt")) {
            pw.println(hs);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
