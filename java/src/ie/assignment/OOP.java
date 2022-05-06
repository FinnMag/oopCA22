package ie.assignment;

import C20394791.CircleLines;
import C20402732.*;
import C20492576.*;
import damkjer.ocd.*;

import ddf.minim.analysis.BeatDetect;

import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

public class OOP extends Visual {
    float rotation = 0;
    float direction = 0;
    public Camera camera1;

    CircleLines yaris;

    GOOBAvisual gooba;

    // fractal tree
    FractalTree fv;
    int treeCount = 8;
    public float rotationCycle = 0;
    public Boolean changeVisual = true;
    public int rotateDirection = 0;
    public int noBranches = 15;
    // colour
    public Colours clYaris;
    public Colours clFinn;
    public Colours clGooba;

    // input tracking
    Boolean hideMenu = true;
    PVector mouseLocation = new PVector();
    public float rotationAngle = 0;
    public float mouseVal, mouseRotation, easing = 0.07f;
    public float scrollVal = 1.5f;

    static final int FADE = 2500;

    int menu = 0;
    String[] Songs = { "POISON.mp3", "GUMMY.mp3", "DARE.mp3" };

    int screenBrightness = 0;
    Boolean lockScreenBrightness = true;

    public static ParticleSystem tlps;
    public static ParticleSystem trps;
    public static ParticleSystem blps;
    public static ParticleSystem brps;

    public BeatDetect beat;
    public BeatDetection fBeat = new BeatDetection();

    public boolean lock = false;
    public boolean play = true;

    public void keyPressed() {
        if (keyCode == LEFT && direction == 0 && lock == false) {
            resetTree();
            direction -= 120;

            if (menu == 0)
                menu = 2;

            else
                menu--;

            loadMusic(Songs[menu]);
        }

        if (keyCode == RIGHT && direction == 0 && lock == false) {
            resetTree();
            direction += 120;

            if (menu == 2)
                menu = 0;

            else
                menu++;

            loadMusic(Songs[menu]);
        }

        if (keyCode == UP && direction == 0) {
            if (!(noBranches == 20))
                noBranches += 1;

        }

        if (keyCode == DOWN && direction == 0) {
            if (!(noBranches == 0))
                noBranches -= 1;

        }

        if (keyCode == ' ') {
            if (play == true) {
                getAudioPlayer().pause();
                play = false;
            } else {
                getAudioPlayer().play();
                play = true;
            }
        }

        if (keyPressed) {
            if ((key == 'l' || key == 'L') && menu == 0 && direction == 0) {
                if (lock == false)
                    lock = true;
                else if (lock == true)
                    lock = false;
                camera1.jump(width / 2, height / 2, 0);
            }
        }

        if (keyCode == 'X') {
            if (rotateDirection == 0)
                rotateDirection = 1;
            else
                rotateDirection = 0;
        }

        if (keyCode == 'C') {
            if (changeVisual == false)
                changeVisual = true;

            else
                changeVisual = false;

        }

        if (keyCode == 'H') {
            if (hideMenu == true)
                hideMenu = false;

            else
                hideMenu = true;

        }

        if (keyCode == 'B') {
            if (lockScreenBrightness == true)
                lockScreenBrightness = false;

            else
                lockScreenBrightness = true;

        }

    }

    public void mouseWheel(MouseEvent mouse) {
        float mouseWheel = mouse.getCount();

        if (mouseWheel == 1.0f) {
            if (scrollVal < 1.5f) {
                scrollVal += 0.1f;
            }

        }

        else if (mouseWheel == -1.0f) {

            if (scrollVal > -0.3f) {
                scrollVal -= 0.1f;
            }

        }
    }

    public void settings() {
        size(1920, 1080, P3D);
        //fullScreen(P3D);
    }

    public void setup() {
        startMinim();
        loadAudio("POISON.mp3");
        getAudioPlayer().play();
        

        colorMode(RGB);

        // BeatDetect(1024, 44100.0f);
        beat = new BeatDetect(getAudioPlayer().bufferSize(), getAudioPlayer().sampleRate());
        beat.setSensitivity(300);

        PImage img = loadImage("images/poison.png");

        tlps = new ParticleSystem(5, new PVector(-width / 2, -height / 2), img, this);
        trps = new ParticleSystem(5, new PVector(width / 2, -height / 2), img, this);
        blps = new ParticleSystem(5, new PVector(-width / 2, height / 2), img, this);
        brps = new ParticleSystem(5, new PVector(width / 2, height / 2), img, this);

        camera1 = new Camera(this,
                width / 2, height / 2, 0,
                width / 2, height / 2, -width,
                0, 1, 0);

        clGooba = new Colours();
        gooba = new GOOBAvisual(this, clGooba);
        clYaris = new Colours();

        yaris = new CircleLines(this, clYaris);

        clFinn = new Colours();

    }

    float off = 0;

    public void draw() {

        calculateAverageAmplitude();
        changeBackground(lockScreenBrightness);
        textSize(100);
        textAlign(CENTER);

        camera1.feed();
        rectMode(CENTER);
        double third = width * 0.866;
        float move = (float) third;

        translate(width / 2, height / 2, -width);
        pushMatrix();

        if ((menu == 1 && direction > 0) || (menu == 2 && direction < 0) || (menu == 0))
            gooba();

        popMatrix();
        translate(move, 0, width + (width / 2));
        rotateY(-2 * PI / 3);

        if ((menu == 2 && direction > 0) || (menu == 0 && direction < 0) || (menu == 1))
            yaris();

        rotateY(2 * PI / 3);
        translate(-2 * move, 0, 0);
        rotateY(2 * PI / 3);

        if ((menu == 0 && direction > 0) || (menu == 1 && direction < 0) || (menu == 2))
            finn();

        hint(DISABLE_DEPTH_TEST); // 2D code starts here
        camera();
        noLights();
        fill(screenBrightness, 50);
        rect(0, 0, (width * 2) + 10, (height * 2) + 10);
        hint(ENABLE_DEPTH_TEST); // 2D code ends here

        if (direction > 0) {
            RotateRight();
            direction--;
        }
        if (direction < 0) {
            RotateLeft();
            direction++;
        }

    }

    public void yaris() {
        pushMatrix();
        translate(0, 0, width / 2);
        yaris.render();
        popMatrix();
    }

    public void finn() {

        stroke(255);
        fill(255);
        textSize(30);

        if (hideMenu == false) {
            text("UP/DOWN  |  Branch Count: " + noBranches, -(width / 2) - 15, -(height / 2) - 100);
            text("SCROLL   |  Amplitude Modifier: " + Math.round(scrollVal * 100.0f) / 100.0f, -(width / 2) + 20,
                    -(height / 2) - 50);
        }

        fv = new FractalTree(this, OOP.map(smoothedAmplitude + 0.3f, 0, .5f, -height / 15f, -height / 4f), 0, noBranches,
                clFinn, treeCount);
        fv.render();

        stroke(255);
        fill(255);
        textSize(30);
    }

    public void gooba() {
        gooba.render();
    }

    public void loadMusic(String Song) {
        Shiftdown();
        changeAudio(Song);
        ShiftUp();
        getAudioPlayer().loop();
    }

    public void changeBackground(Boolean b) {

        if (b == false) {

            BeatDetection fBeat = new BeatDetection();
            beat.detect(getAudioPlayer().mix);
            Boolean type = fBeat.readBeat((beat));

            // background(screenBrightness);
            screenBrightness = backgroundBeat(type, screenBrightness);

            if (screenBrightness > 10)
                screenBrightness -= 3;

        }
    }

    public int backgroundBeat(Boolean type, int i) {

        if (type == true)
            i += 25;

        return i;
    }

    public void RotateRight() {
        camera1.look((float) (radians((float) (1))), 0);
    }

    public void RotateLeft() {
        camera1.look(-(float) (radians((float) (1))), 0);
    }

    public void getMouseAngle() {

        mouseLocation.set(mouseX, mouseY, 0);
        mouseLocation.sub(width / 2, height / 2, 0);
        mouseVal = mouseLocation.heading();
        mouseRotation = (mouseVal - rotationAngle) / PI;
        if (mouseRotation < -1)
            rotationAngle -= PI * 2;

        else if (mouseRotation > 1)
            rotationAngle += PI * 2;

        rotationAngle += (mouseVal - rotationAngle) * easing;

    }

    public void resetTree() {
        noBranches = 15;
        scrollVal = 1.5f;
    }

}
