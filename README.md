# Music Visualiser Project

| Name | Student Number | Song |
|-----------|-----------|-----------|
| Aleksey Makarevich |  C20402732 | Poison Jam - 2 Mello |
| Finn Maguire | C20492576 | Gummy Bear Song |
| Yaroslav Hrabas | C20394791 | DARE |

# Description of the assignment
Our assignment consists of a rotating menu. This was done as a challenge, seeing if we could utilise the camera to move around within the 3d space.
The user "stands" in the centre of the menu, able to turn the view point between the three visualisations while it changes the song to match the visualisation.

### Finn
For my visualiser I wanted to try and utilise some kind of fractals. I decided to use tree fractals generated circularly around a point which would alter themselves
in amplitude and rotation depending on the sound levels from the audio. I included some forms of interactivity with the visualiser which allows the user to change the 
amount of branches and a modifier for the trees amplitude which allows for some interesting and psychedelic patterns. The visual also provides function to manually rotate following the users mouse instead of its preset rotation. Some other features include some display options and switching rotation directions. 

### Yaris 
My audio visualisation is a cycle of different shapes, colours and spirals that change depending on the beat of a song which I think is quite unique as the visualisation can change slightly when a different song is imported. This is done by having different scenes being played whenever a drum, beat, snare or even white noise is played throughout a song which ends up giving a very cool, fun and almost hypnotising aesthetic to the entire audio visualiser!

![YarisVisual](https://user-images.githubusercontent.com/72357455/167223554-11677a88-20ff-4306-8b85-0c88fc461436.png)


# Instructions
Each menu option has its own controls that can manipulate features in each visualations.

## The Global Controls
- "Right arrow" rotates the menu one slot to the right.
- "Left arrow" rotates the menu one slow to the left.
- "Space bar" to pause / play the music.
- "B" to turn on/off background beat brightness.

## Poison controls
- Mouse controls direction the poison bubbles move in.
- "L" to lock/unlock camera.
With camera locked you can now
- "K" to move back from the visual.
- "I" to move in towards the visual.

## Dare controls
- "X"
- "C"
- "H"
- "Up Arrow"
- "Down Arrow"

# How it works
The main menu works by use of a camera centered between the three visualisations. Each visualisation only runs when transitioning to/from the visual and when looking directly at it. The visualisations themselves are stored in their own packages which are called from the draw method. This allows all of us to keep the code to ourselves to avoid merging conflicts. Each visualisation has a somewhat noticiable *trailing* effect which is caused by a semi transparent layer being put onto the camera to give the visualisations a smoother, more *trippy* effect.

## Yaris
NOTE: (Due to git BASH not co-operating with me when trying to commit, all my sending and receiving of files had to be done manually so huge thank you to my team mates). 

For the unique beat detection to work in the audio visualiser, there had to be a variable created at the beginning of the program for nearly every aspect in a song which was then called later depending on the sound played. These variables would be executed by a selector depending on the sensitivity of the bass or snare played in the song. 

```Java 
	public void render() {
  
  oop.getFFT().forward(oop.getAudioPlayer().mix); 
  bass = (int) (oop.getFFT().getFreq(50)); 
  snare = (int) (oop.getFFT().getFreq(1760)); 
  noise = map(oop.getFFT().getFreq(19000), 0, 1, 0, 500); 
  if (bass>kickboxSens && frameCount>300) {
    selector = floor(random(1, (float) 5.57)); 
    weight = (int) (random(1, 50)); 
  } else {
    if (snare>snareSens) {
      weight = (int) (random(1, 65));
    }
  }
  
  if (selector==2) {
    oop.strokeWeight(weight + noise/15);
    square(20, 20, 1);
  }
  if (selector==3) {
    if (bass>kickboxSens/3 & frameCount>600) {
      oop.strokeWeight(weight + noise/15);
      square(20, 20, 0);
    } else {
      circlepop();
      fxRain();
    }
  }
  if (selector==4) {
    kickbox(50, bass, "warm", 2);
    if (bass>kickboxSens) {
      selector = floor(random(1, (float) 2.99)); 
    }
  }
  if (selector==5) {
    kickbox(50, bass, "cool", 1);
    if (bass>kickboxSens) {
      selector = floor(random(1, (float) 2.99)); 
    }
  }
  if (selector==2) {
    spiral();
  }
  
  if (mousePressed) {
    oop.fill(255);
    oop.textSize(12);
    oop.text(kickboxSens, width-40, 30);
    oop.text(bass, width-80, 30);
    oop.text(snareSens, width-120, 30);
    oop.text(snare, width-160, 30); 
  }
  
  if (noise < 1) {
    selector = 2;
  }
```

As you can see in the code above, depending on the sesitivity of the bass or snare played, the program scrolls through until the visual for that particular sound played is displayed. Due to the ever changing sounds played throughout the song, the 'if else' statements are being ran through constantly to keep the visuals up to date. As an example, if the bass was higher than the sensitivity of the snares, there would be more of a "bold" look to the visualisation along with the spirals looking a little more filled etc. 

```Java 
	void kickbox(int margin, int kickjerk, String colorMode, int thickness) {
  
 
  topLx = (int) (margin+(random(-kickjerk, kickjerk)));
  topLy = (int) (margin+(random(-kickjerk, kickjerk)));
  topCx = (int) (width/2+(random(-kickjerk, kickjerk)));
  topCy = (int) (margin+(random(-kickjerk, kickjerk)));
  topRx = (int) (width-margin+(random(-kickjerk, kickjerk)));
  topRy = (int) (margin+(random(-kickjerk, kickjerk)));
  
  midLx = (int) (margin+(random(-kickjerk, kickjerk)));
  midLy = (int) (height/2+(random(-kickjerk, kickjerk)));
  midCx = (int) (width/2+(random(-kickjerk, kickjerk)));
  midCy = (int) (height/2+(random(-kickjerk, kickjerk)));
  midRx = (int) (width-margin+(random(-kickjerk, kickjerk)));
  midRy = (int) (height/2+(random(-kickjerk, kickjerk)));
  
  botLx = (int) (margin+(random(-kickjerk, kickjerk)));
  botLy = (int) (height-margin+(random(-kickjerk, kickjerk)));
  botCx = (int) (width/2+(random(-kickjerk, kickjerk)));
  botCy = (int) (height-margin+(random(-kickjerk, kickjerk)));
  botRx = (int) (width-margin+(random(-kickjerk, kickjerk)));
  botRy = (int) (height-margin+(random(-kickjerk, kickjerk)));

  if (colorMode == "cool") {
    if (bass>kickboxSens) {
      oop.stroke(0, random(200, 255), random(150, 255), 230);
    } else { 
      oop.noStroke();
    }
  }
  if (colorMode == "warm") {
    if (bass>kickboxSens) {
      oop.stroke(random(180, 255), random(50, 75), random(30, 60), 230);
    } else { 
      oop.noStroke();
    }
  }
  oop.strokeWeight(random(thickness, thickness+10));
  
  oop.line(topLx, topLy, topCx, topCy);
  oop.line(topCx, topCy, topRx, topRy);
  oop.line(midLx, midLy, midCx, midCy);
  oop.line(midCx, midCy, midRx, midRy);
  oop.line(botLx, botLy, botCx, botCy);
  oop.line(botCx, botCy, botRx, botRy);
 
  oop.line(topLx, topLy, midLx, midLy);
  oop.line(topCx, topCy, midCx, midCy);
  oop.line(topRx, topRy, midRx, midRy);
  oop.line(midLx, midLy, botLx, botLy);
  oop.line(midCx, midCy, botCx, botCy);
  oop.line(midRx, midRy, botRx, botRy);
  }
``` 

The code above adds to the different visuals already being created by letting the objects cycle through a colour array and also slightly "jerking" up or down depending on the beat, snare or other noise played. This was done by having variables based on different positions on screen (Top, middle, Bottom, Height, Width, etc.) which added a little more movement and fun to the visualisation. The stroke weight would also control how bold the shapes would look depending on sensitvity. 

The code below is a personal favourite as it drew the spirals you see in the background of the visualisation. 

```Java
	void spiral() {
  oop.noFill();
  arcLength = (float) (arcLength + 0.0001);
  if (arcLength == 10) {
    arcLength = (float) 0.0005;
  }
  oop.stroke(255);
  oop.translate(width/2, height/2);
  for (int r=50; r<650; r=r+5) {
    ydc.changeColour(0.1f);
    oop.rotate((float) (millis()/2000.0));
    oop.strokeWeight(3);
    oop.arc(0, 0, r*bass/10, r*bass/10, 0, arcLength);
  }
```
Code for the grainy effect seen in the background alongside the spiral: 

```Java 
	void fxRain() {
  oop.fill(255);
  oop.textSize(random(noise));
  oop.text("l", random(width), random(height));
}

void fxGrain() {
  oop.fill(255);
  oop.textSize(random(noise));
  oop.text(".", random(width), random(height));
  oop.text(".", random(width), random(height));
}
```
These 2 pieces of code went hand in hand with each other to give the hypnotic background look to the visualistation. arcLength controlled the size of the spiral throughout different points in the song giving it that zooming in and out look while the changeColour array was ran through to have that flashing lights look. The "for" loop essentially kept its eye on the sensitivity of the base or snare and executed accordingly. 
oop.text and textSize are used to display different sized dots at different positions on screen at random to give it that constantly changing grainy aesthetic once again acting accordingly to the sounds played. 


## Aleksey
My part of the visualisations work through a number of methods which i call from my render method.
```Java
    public void render() {
        oop.calculateAverageAmplitude();
        dolly();
        oop.noFill();
        oop.pushMatrix();
        shrinkngrow();
        circle();
        centre();
        poison();
        oop.popMatrix();
    }
```
Each method corresponds to a part of my visualisation.
![image](https://user-images.githubusercontent.com/72228959/167220494-627934c5-1ed4-444f-a180-04080610e8b1.png)


#### The dolly method takes care of the moving back and forth of the camera.
This is done by using keypresses and boolean "locks". This makes it so that the user can only dolly in while locked into my visualisatino to avoid messing with any other visulisation.

#### Shrinkngrow is what allows the entire "eye" to shrink and grow while also rendering in the triangle when it is at its smallest.
This is done by changing the "limit" integer in the file at certain parts of the song. The size of the eye and ammount of Iris strokes are both linked back to this "limit" interger meaning that everything gets scaled down simultaneously.

#### Circle creates the "whites" of the eye which respond to the music, growing and shrinking with response to the volume.
This is done with a simple ellipse as well as a for loop which uses trigonometry to create outwards lines from the ellipse which grow in size in relation to the volume of the music. The colour of the whites are also cycled in order to give more texture to the eye.

#### Centre is the "Iris" of the eye, its done by using a single for loop which transforms and rotates to create the effect.
```Java
    public void centre() {
        oop.pushMatrix();
        hypnogrow = map(diameter, limit, limit + 70, limit - 30, limit - 10);
        oop.strokeWeight(3);
        for (int i = 0; i < hypnogrow; i++) {
            gdc.changeColour(0.1f);
            oop.rotate(radians((hypnocontrol % 360) + 180));
            oop.pushMatrix();
            oop.translate(0, (hypno % limit), -i);
            oop.line(0, 0, 0, (float) (limit / 7.5));
            oop.popMatrix();
            hypno++;
        }

        oop.popMatrix();
        hypnocontrol = (float) (hypnocontrol + 0.01);

    }
```
This was one of the most satisfying parts of doing this assignment as figuring out the right math needed to produce the required pattern was a bit challenging.
altering *hypnogrow* and *hypno* allowed me to create multiple different styles for the pattern but creating two discrete sections allowed the pattern to not look cluttered while also giving it that *trippy* effect.

#### Poison calls for the ParticleSystem and Particle classes to create the "poison" clouds that appear during the song.
These utilize pvectors in order to give the *poison* particles velocity and acceleration. They have distinct starting points but each poison particle is given different pvectors for side to side motion by use of random guassians. Then a windforce is applied in order to push the particles in towards the mouse giving a sense of *control* to the user.

## Finn

![image](https://user-images.githubusercontent.com/98547854/167222169-910a7ccc-9cda-4fdd-97f4-c5e33ffee54f.png)

#### FractalTree :

This java class is used to create and display tree fractals circularly around a point.
The class's constructor reads in variables which determines the amplitude, angles, number of branches and the amount of trees for the visual.

The constructor also calls the branch method which creates one fractal tree.

Inside the method the angles for the branches are determined by the audios averaged amplitude using PApplets map function. 
The method utlises recursion to create branches which are connected to its parent branch using arrays.

The tree is displayed on screen by calling the render() method which inside calls the display() method which draws each individual tree.
Inside render() is where the position of the trees are determined using rotate() and translate().  

The visual is then either spun using a default method or using the users cursor.


#### Colours & DynamicColours :

These 2 classes serve as a way to provide smooth colour shifting to drawn objects.
The colours class is used to create variables that will be used in the DynamicColours class.
This is done to prevent the same variables being manipulated if the DynamicColours method is being used more than once at a time which in turn would increase the 
rate at which the colours would chance for every instance.
RGB values are assigned at random which can allow for a different colour scheme for every use of the method. 

Inside the DynamicColours class the RBG values are changed at a rate depending on the speed variable. 
Every frame the values are either incremented or decremented by 1 depending on whether the value is coming from the lowest value (0) or the max (255)
This provides a smooth changing colour scheme for an object it is decided to be used on.



# What we are most proud of in the assignment
### Aleksey 
I am most proud the rotational menu that runs the project. 
It allowed me to look into how to camera works in processing and finding the Obsessive Camera Directions (OCD) package allowed
me to do more than I looked for. 
This also lead me to adding the ability to dolly back and forth into my visualiser as i think its a cool addition to the exerience

### Finn

### Yaris

I am most definitely proud of the little beat detection system created as it took quite some time and at one point, I didn't even think it would ever work. I liked experimenting with it and seeing how crazy I could go and it was most definitely relieving once we had the visualisation working well alongside the other 2 as it posed problematic quite a few times throughout the creation of the audio visualisation. 
I also liked discovering and implementing the different processing concepts throughout the project such as the PApplet library as it gave even more possibilities to our visualisations and making me more knowledgable on JAVA as a whole. 


## Youtube Video:

[![YouTube](https://i9.ytimg.com/vi_webp/LAfiIlEsi7o/mqdefault.webp?sqp=CKid1pMG&rs=AOn4CLAph3F2JmcomgSWJaoR8ZqGq1-Zdg)](https://youtu.be/LAfiIlEsi7o)
