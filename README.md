# Music Visualiser Project

Name: Aleksey Makarevich, Finn Maguire, Yaroslav Hrabas

Student Number: C20402732, C20492576, C20394791

Songs: Poison Jam - 2 Mello, Gummi Bear Song, DARE

# Description of the assignment
Our assignment consists of a rotating menu. This was done as a challenge, seeing if we could utilise the camera to move around within the 3d space.
The user "stands" in the centre of the menu, able to turn the view point between the three visualations while it changes the song to match the visualisation.

### Finn
For my visualiser I wanted to try and utilise some kind of fractals. I decided to use tree fractals generated circularly around a point which would alter themselves
in amplitude and rotation depending on the sound levels from the audio. I included some forms of interactivity with the visualiser which allows the user to change the 
amount of branches and a modifier for the trees amplitude which allows for some interesting and psychedelic patterns. The visual also provides function to manually rotate following the users mouse instead of its preset rotation. Some other features include some display options and switching rotation directions. 

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

### Aleksey
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

### Finn

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

## Youtube Video:

[![YouTube](https://i9.ytimg.com/vi_webp/LAfiIlEsi7o/mqdefault.webp?sqp=CKid1pMG&rs=AOn4CLAph3F2JmcomgSWJaoR8ZqGq1-Zdg)](https://youtu.be/LAfiIlEsi7o)
