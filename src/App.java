import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;
import processing.sound.*;

import processing.core.*;

public class App extends PApplet {
    Levels levels;
    boolean cStarted = false;
    Ellipse player; // player
    SoundFile bgMusic;
    SoundFile playerHit;
    int xPos = 500; // starting xPos
    int yPos = 300; // starting yPos
    int xVel = 0; // x velocity
    int yVel = 0; // y velocity
    Health health; // health bar
    Rectangle gameOver; // play again button
    Button freeplay;
    Button campaign;
    PImage heart; // heart itself
    PImage background; // background
    Enemies enemies; // enemy handling
    int timer = 0; // timer
    int randomNum = 110; // random number used for shooting
    int hs = 0; // highscore
    int score = 0; // initial score
    boolean scoreHelper = false; // helps calculate difference between score and high score
    int initialHS = 0; // also helps score calculation
    boolean invince = true; // protection
    boolean started = false;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        // initializing everything, (rectangles, images, player, enemies, health, and it
        // adds two ranged enemies)
        gameOver = new Rectangle(this, 200, 275, 400, 50, 200, 100, 100);
        freeplay = new Button(this, 550, 275);
        campaign = new Button(this, 100, 275);
        background = loadImage("potential-image-1.jpeg"); //background
        playerHit = new SoundFile(this, "playerHit.mp3");//sound effects
        bgMusic = new SoundFile(this, "bgMusic.mp3"); //background music
        bgMusic.play(); //playing music
        bgMusic.loop(); //lopping it
        bgMusic.amp(0.2f); // making it not blow out your eardrums
        background.resize(800, 600);
        player = new Ellipse(this, xPos, yPos, 50, 50, 0);
        levels = new Levels(this, player);
        heart = loadImage("test.png");
        heart.resize(50, 50);
        enemies = new Enemies(this);
        health = new Health(this, heart, 3);
        enemies.addRangedEnemy();
        enemies.addRangedEnemy();
        initialSetup();

    }

    public void settings() {
        size(800, 600);
    }

    public void draw() {
        // displaying everything
        background(background); // idisplaying background
        initialSetup();
        if (cStarted || started) {
            if (cStarted) { // makes the campaign work
                spawnProc();
                health.place();
                levels.start();
                campaignAdding();
                player.display();
                player.move(xVel, yVel);
                addhs();
                levels.levelText(score);
                collision();
            }
            if (started) {
                write(); // writing high score
                spawnProc(); // checking for spawn protection
                gameOver(); // checking for game over
                displayScores(); // displaying scores
                addhs(); // increment score
                health.place(); // placing health bar
                if (!(health.isEmpty())) { // only add enemies and show them if you have health
                    checkRanged();
                    enemies.startRangedEnemies(player);
                    addEnemies();
                    enemies.start();
                    player.display();
                    player.move(xVel, yVel);
                    collision();
                }
            }
            timer += 1;
        }
    }

    public void initialSetup() {
        if (started == false && cStarted == false) { //displays freeplay and campagn buttons
            stroke(0, 0, 0);
            campaign.display();
            freeplay.display();
            textAlign(CENTER, CENTER);
            fill(98, 23, 11);
            textSize(18);
            freeplay.text("Freeplay");
            campaign.text("Campaign");
        }
    }

    public void campaignAdding() {
        if (timer % levels.getDelay() == 0) {
            levels.add();
        }
    }

    public void gameOver() {
        if (health.isEmpty() && started) { // game over check
            removeEnemies(); // remove the enemies
            player.setFill(0, 255, 0); // make the player green to show protection
            invince = true; // protect the player
            timer = 0; // reset timer
            scoreHelper = false; // reset
            int diff = initialHS - score; // calc difference
            gameOver.display(); // show button
            textAlign(CENTER, CENTER);
            fill(98, 23, 21);
            textSize(40);
            text("Try again?", 400, 295); // button text
            textSize(25);
            if (diff >= 0) { // post screen text depending on whether or not you beat your high score
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

    public void checkRanged() { // if there are less than 2 ranged enemies on screen at once adds ramnged enemies until there are two
        if (enemies.getNumRanged() < 2 && timer % 40 == 0) {
            enemies.addRangedEnemy();
        }
    }

    public void mousePressed() {
        // checking for game over button clicked and ressetting health and score if true
        if (health.isEmpty()) {
            if (mouseX > gameOver.getX() && mouseX < gameOver.getX() + gameOver.getWidth() &&
                    mouseY > gameOver.getY() && mouseY < gameOver.getY() + gameOver.getHeight()) {
                health.reset();
                score = 0;
            }

        }
        if (started == false && cStarted == false) { // freeplay and campaign b utton collision
            if (freeplay.clicked(mouseX, mouseY)) {
                read();
                started = true;
            }

            if (campaign.clicked(mouseX, mouseY)) {

                cStarted = true;
            }
        }
    }

    public void addhs() {
        // increment score and adjust high score accordingly
        if (timer % 60 == 0 && timer != 0 && !(health.isEmpty())) {
            score += 1;
            if (score > hs && started) {
                if (scoreHelper == false) { // initial hs helps find difference between high score and score when score
                                            // > than hs
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
        // displaying scores if you aren't dead
        if (!(health.isEmpty())) {
            fill(100, 28, 29);
            textSize(30);
            text("Score: " + score, 600, 40);
            text("Highscore: " + hs, 600, 80);
        }
    }

    public void removeEnemies() {
        enemies.clear(); // removed all non ranged enemies
    }

    public void spawnProc() {
        // spawn protection removal
        if (timer % 100 == 0 && timer != 0 && timer < 102) {
            player.setFill(0);
            invince = false;
        }
        if (timer % 60 == 0 && !(health.sizeIs(3)) && invince) {
            invince = false;
        }
    }

    public void addEnemies() {
        // adding normal enemies and shooting ranged enemies on a random timer
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
        // movement, pretty basic
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
        // collision with walls if you aren't invincible
        if (player.getX() >= 800 || player.getX() <= 0 || player.getY() < 0
                || player.getY() > 600 && !(invince)) {
            health.remove();
            reset();
        }
        // collision with enemies if you aren't invincible
        if (enemies.collide(player) && !(invince)) {
            playerHit.play();
            health.remove();
            reset();
        } // collision with ranged enemies if you aren't invincible
        if (enemies.collideWithRanged(player) && !(invince)) {
            playerHit.play();
            health.remove();
            reset();
        }
        // collision with bullets if you aren't invincible
        if (enemies.collideWithBullets(player) && !(invince)) {
            playerHit.play();
            health.remove();
            reset();
        }
        if (levels.collision() && !(invince)) {
            playerHit.play();
            health.remove();
            reset();
        }

    }

    public void keyReleased() {
        // stop if you stop moving
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
        // gives a small amount of invincibility for QOL and resets you to middle
        invince = true;
        player.setX(400);
        player.setY(300);
    }

    public void read() { // read high score
        try (Scanner read = new Scanner(Paths.get("HS.txt"))) {
            if (read.hasNextLine()) {
                hs = Integer.valueOf(read.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        // write high score
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
